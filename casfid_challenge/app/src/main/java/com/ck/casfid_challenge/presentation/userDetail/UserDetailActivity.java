package com.ck.casfid_challenge.presentation.userDetail;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.ck.casfid_challenge.R;
import com.ck.casfid_challenge.domain.model.User;
import com.ck.casfid_challenge.infrastructure.qr.QRManager;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class UserDetailActivity extends AppCompatActivity {

    public static final String EXTRA_USER = "extra_user";

    private ImageView ivUserDetail;
    private ImageButton btnBack;
    private TextView tvFullName;
    private TextView tvEmail;
    private TextView tvPhone;
    private TextView tvAddress;
    private TextView tvGender;
    private TextView tvCell;
    private TextView tvUsername;
    private TextView tvNationality;
    private ExtendedFloatingActionButton fabMain;
    private FloatingActionButton fabAddContact, fabShowQR;
    private TextView tvAddContactLabel, tvShowQRLabel;
    private boolean isFabExpanded = false;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_item_detail);

        ivUserDetail = findViewById(R.id.imgUserPicture);
        tvFullName = findViewById(R.id.tvFullName);
        tvEmail = findViewById(R.id.tvEmailValue);
        tvPhone = findViewById(R.id.tvPhoneValue);
        tvGender = findViewById(R.id.tvGenderValue);
        tvCell = findViewById(R.id.tvCellValue);
        tvUsername = findViewById(R.id.tvUsernameValue);
        tvNationality = findViewById(R.id.tvNationalityValue);
        tvAddContactLabel = findViewById(R.id.tvAddContactLabel);
        tvShowQRLabel = findViewById(R.id.tvShowQRLabel);
        fabMain = findViewById(R.id.fabMain);
        fabAddContact = findViewById(R.id.fabAddContact);
        fabShowQR = findViewById(R.id.fabShowQR);
        btnBack = findViewById(R.id.btnBack);

        fabMain.shrink();

       fabMain.setOnClickListener(v -> {
            if (!isFabExpanded) {
                fabAddContact.show();
                fabShowQR.show();
                tvAddContactLabel.setVisibility(View.VISIBLE);
                tvShowQRLabel.setVisibility(View.VISIBLE);
                fabMain.extend();
            } else {
                fabAddContact.hide();
                fabShowQR.hide();
                tvAddContactLabel.setVisibility(View.GONE);
                tvShowQRLabel.setVisibility(View.GONE);
                fabMain.shrink();
            }
            isFabExpanded = !isFabExpanded;
        });

        LottieAnimationView lottieView = findViewById(R.id.lottieCornerBg);
        lottieView.setAnimation(R.raw.background_orange);
        lottieView.playAnimation();

        ConstraintLayout rootLayout = findViewById(R.id.rootLayout);
        ProgressBar loadingIndicator = findViewById(R.id.loadingIndicator);

        // Ocultar contenido al principio
        rootLayout.setVisibility(View.INVISIBLE);
        loadingIndicator.setVisibility(View.VISIBLE);

        user = (User) getIntent().getSerializableExtra(EXTRA_USER);
        if (user != null) {
            bindUserData(user);
        }

        lottieView.addLottieOnCompositionLoadedListener(composition -> {
            lottieView.playAnimation();

            // Mostrar contenido y ocultar loading
            rootLayout.setVisibility(View.VISIBLE);
            loadingIndicator.setVisibility(View.GONE);
        });

        btnBack.setOnClickListener(v -> finish());

        fabAddContact.setOnClickListener(v -> {
            addContact(user);
        });

        fabShowQR.setOnClickListener(v -> {
            Bitmap qrBitmap = QRManager.generateQRCodeFromUser(user, 800);
            if (qrBitmap == null) return;

            View dialogView = getLayoutInflater().inflate(R.layout.dialog_qr_code, null);
            ImageView imgQRCode = dialogView.findViewById(R.id.imgQRCode);
            imgQRCode.setImageBitmap(qrBitmap);

            BottomSheetDialog dialog = new BottomSheetDialog(this);
            dialog.setContentView(dialogView);
            dialog.show();
        });

    }

    private void addContact(User user) {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);

        intent.putExtra(ContactsContract.Intents.Insert.NAME, user.fullName != null ? user.fullName : "");
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, user.phone != null ? user.phone : "");
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, user.email != null ? user.email : "");

        startActivity(intent);
    }


    private void bindUserData(User user) {
        tvFullName.setText(user.fullName != null ? user.fullName : "N/A");
        tvEmail.setText(user.email != null ? user.email : "N/A");
        tvPhone.setText(user.phone != null ? user.phone : "N/A");
        //tvAddress.setText(user.address != null ? user.address : "N/A");
        tvGender.setText(user.gender != null ? user.gender : "N/A");
        tvCell.setText(user.cell != null ? user.cell : "N/A");
        tvUsername.setText(
                user.login != null && user.login.username != null ? user.login.username : "N/A"
        );
        tvNationality.setText(user.nat != null ? user.nat : "N/A");

        Glide.with(this)
                .load(user.pictureUrl != null ? user.pictureUrl.large : "")
                .error(Glide.with(this)
                        .load(user.pictureUrl.thumbnail)
                        .circleCrop())
                .placeholder(R.drawable.ic_user_placeholder)
                .circleCrop()
                .into(ivUserDetail);
    }

}

