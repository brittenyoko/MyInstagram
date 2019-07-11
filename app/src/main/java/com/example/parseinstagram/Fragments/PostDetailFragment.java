package com.example.parseinstagram.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.parseinstagram.Models.Post;
import com.example.parseinstagram.R;
import com.parse.ParseFile;

public class PostDetailFragment extends Fragment {
    private Post post;
    private TextView tvHandle;
    private TextView tvDescription;
    private ImageView ivImage;
    private TextView tvCreatedAt;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return (View) inflater.inflate(R.layout.post_detail,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        post = (Post) getArguments().getSerializable("post");

        tvHandle = view.findViewById(R.id.tvHandle);
        tvDescription = view.findViewById(R.id.tvDescription);
        ivImage = view.findViewById(R.id.ivImage);
        tvCreatedAt = view.findViewById(R.id.tvCreatedAt);

        tvHandle.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(this)
                    .load(image.getUrl())
                    .into(ivImage);
        }
        tvDescription.setText(post.getDescription());
        tvCreatedAt.setText(post.getCreatedAt().toString());
    }
}
