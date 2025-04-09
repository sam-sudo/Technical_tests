package com.ck.casfid_challenge.presentation.home.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.ck.casfid_challenge.R;
import com.ck.casfid_challenge.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<User> userList = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    private static final int VIEW_TYPE_USER = 0;
    private static final int VIEW_TYPE_LOADING = 1;
    private boolean isLoading = false;

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setData(List<User> users) {
        userList.clear();
        userList.addAll(users);
        notifyDataSetChanged();
    }

    public void showLoading() {
        if (!isLoading) {
            isLoading = true;
            userList.add(null);
            notifyItemInserted(userList.size() - 1);
        }
    }

    public void hideLoading() {
        if (isLoading && !userList.isEmpty()) {
            int position = userList.size() - 1;
            if (userList.get(position) == null) {
                userList.remove(position);
                notifyItemRemoved(position);
            }
            isLoading = false;
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return userList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_USER;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_LOADING) {
            View view = inflater.inflate(R.layout.loading_item, parent, false);
            return new LoadingViewHolder(view);
        } else {
            View view = inflater.inflate(R.layout.user_item, parent, false);
            return new UserViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            User user = userList.get(position);
            ((UserViewHolder) holder).bind(user);
        }
    }

    static class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    class UserViewHolder extends RecyclerView.ViewHolder {
        private final ImageView imgUsuario;
        private final TextView txtNombre;
        private final TextView txtInfo;

        UserViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUsuario = itemView.findViewById(R.id.imgUser);
            txtNombre = itemView.findViewById(R.id.txtUserName);
            txtInfo = itemView.findViewById(R.id.txtUserInfo);

            itemView.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (onItemClickListener != null && pos != RecyclerView.NO_POSITION) {
                    User user = userList.get(pos);
                    if (user != null) onItemClickListener.onItemClick(user);
                }
            });
        }

        void bind(User user) {
            txtNombre.setText(user.fullName);
            txtInfo.setText(user.email);
            Glide.with(itemView.getContext())
                    .load(user.pictureUrl.thumbnail)
                    .placeholder(R.drawable.ic_user_placeholder)
                    .circleCrop()
                    .into(imgUsuario);
        }
    }
}