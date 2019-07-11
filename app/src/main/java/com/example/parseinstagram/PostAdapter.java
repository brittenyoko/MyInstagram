package com.example.parseinstagram;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.parseinstagram.Fragments.PostDetailFragment;
import com.example.parseinstagram.Models.Post;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>{

    private Context context;
    private List<Post> posts;

    public PostAdapter(List<Post> posts) {
        //this.context = context;
        this.posts = posts;
    }

    @NonNull
    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = (View) LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostAdapter.ViewHolder holder, int position) {
        Post post = posts.get(position);

        holder.tvHandle.setText(post.getUser().getUsername());
        ParseFile image = post.getImage();
        if (image != null) {
            Glide.with(context)
                    .load(image.getUrl())
                    .into(holder.ivImage);
        }
        holder.tvDescription.setText(post.getDescription());
        holder.tvCreatedAt.setText(post.getCreatedAt().toString());
        //holder.bind(post);
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvHandle;
        private TextView tvDescription;
        private ImageView ivImage;
        private TextView tvCreatedAt;


        public ViewHolder(View itemView) {
            super(itemView);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvCreatedAt = itemView.findViewById(R.id.tvCreatedAt);
            itemView.setOnClickListener((View.OnClickListener) this);
        }

        @Override
        public void onClick(View view) {
            Post post = posts.get(getAdapterPosition());
            FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
            Fragment fragment = new PostDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable("post", post);
            fragment.setArguments(bundle);
            fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
        }
    }


    public void clear(){
        posts.clear();
        notifyDataSetChanged();;
    }

    // Add a list of items -- change to type used
    public void addAll(List<Post> list) {
        posts.addAll(list);
        notifyDataSetChanged();
    }

}

