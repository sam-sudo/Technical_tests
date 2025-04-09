package com.ck.casfid_challenge.presentation.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.ck.casfid_challenge.R;
import com.ck.casfid_challenge.data.local.LocalDatasource;
import com.ck.casfid_challenge.data.local.db.AppDatabase;
import com.ck.casfid_challenge.data.remote.RemoteUserDatasource;
import com.ck.casfid_challenge.data.remote.api.RetrofitClient;
import com.ck.casfid_challenge.data.repository.UserRepositoryImpl;
import com.ck.casfid_challenge.domain.usercase.GetUsersPageUseCase;
import com.ck.casfid_challenge.domain.usercase.GetUsersUseCase;
import com.ck.casfid_challenge.domain.usercase.RefreshUsersFromAPIUseCase;
import com.ck.casfid_challenge.presentation.home.adapter.UserAdapter;
import com.ck.casfid_challenge.presentation.userDetail.UserDetailActivity;
import com.ck.casfid_challenge.infrastructure.qr.QRManager;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView recyclerView;
    private FloatingActionButton fabAddContacts;
    private HomeViewModel homeViewModel;
    private UserAdapter adapter;
    private ActivityResultLauncher<Intent> qrScanLauncher;
    private Snackbar currentSnackbar;
    private String lastErrorMessage = "";
    private long lastErrorTimestamp = 0;
    private boolean forcedRefresh = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_activity);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "users-db").build();

        initViews();
        initViewModel(db);
        initListeners();

        homeViewModel.updateUsersFromDB();
    }

    private void initViews() {
        MaterialToolbar toolbarMain = findViewById(R.id.toolbarMain);
        setSupportActionBar(toolbarMain);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerUsers);
        fabAddContacts = findViewById(R.id.fabAddContactsByQR);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisible = layoutManager.findLastVisibleItemPosition();

                    if (!homeViewModel.isLoading().getValue() && totalItemCount <= (lastVisible + 2)) {
                        adapter.showLoading();
                        homeViewModel.loadNextPage();
                    }
                }
            }
        });

        adapter = new UserAdapter();
        recyclerView.setAdapter(adapter);
    }

    private void initViewModel(AppDatabase db) {
        homeViewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.Factory() {
                    @Override
                    @SuppressWarnings("unchecked")
                    public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
                        RemoteUserDatasource remoteDataSource = new RemoteUserDatasource(RetrofitClient.getApiService());
                        LocalDatasource localDatasource = new LocalDatasource(db);
                        UserRepositoryImpl repository = new UserRepositoryImpl(remoteDataSource, localDatasource);
                        GetUsersUseCase getUsersUseCase = new GetUsersUseCase(repository);
                        GetUsersPageUseCase getUsersPageUseCase = new GetUsersPageUseCase(repository);
                        RefreshUsersFromAPIUseCase refreshUsersFromAPIUseCase = new RefreshUsersFromAPIUseCase(repository);
                        return (T) new HomeViewModel(getUsersUseCase,getUsersPageUseCase,refreshUsersFromAPIUseCase);
                    }
                }
        ).get(HomeViewModel.class);

        homeViewModel.getUsers().observe(this, users -> {
            adapter.hideLoading();
            adapter.setData(users);
            swipeRefresh.setRefreshing(false);
        });

        homeViewModel.getErrorMessage().observe(this, error -> {
            adapter.hideLoading();
            swipeRefresh.setRefreshing(false);

            if (error == null || error.isEmpty()) return;

            long now = System.currentTimeMillis();

            if (!forcedRefresh && error.equals(lastErrorMessage) && (now - lastErrorTimestamp) < 60_000) {
                return;
            }

            lastErrorMessage = error;
            lastErrorTimestamp = now;

            if (currentSnackbar != null && currentSnackbar.isShown()) {
                currentSnackbar.dismiss();
            }

            currentSnackbar = Snackbar.make(
                    findViewById(R.id.main),
                    "No API access: " + error,
                    Snackbar.LENGTH_LONG
            );
            currentSnackbar.show();

            forcedRefresh = false;
            homeViewModel.clearError();
        });

    }

    private void initListeners() {
        swipeRefresh.setOnRefreshListener(() -> {
            homeViewModel.refresh();
            forcedRefresh = true;
        });

        fabAddContacts.setOnClickListener(v -> {
            QRManager.startQRScanWithCamera(this);
        });

        adapter.setOnItemClickListener(user -> {
            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtra(UserDetailActivity.EXTRA_USER, user);
            startActivity(intent);
        });
    }
}


