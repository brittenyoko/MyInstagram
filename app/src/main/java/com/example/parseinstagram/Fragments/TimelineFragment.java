package com.example.parseinstagram.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parseinstagram.Models.Post;
import com.example.parseinstagram.PostAdapter;
import com.example.parseinstagram.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class TimelineFragment extends Fragment {
    public static final String TAG = "PostsFragemnt";
    private RecyclerView rvPosts;
    private PostAdapter adapter;
    private ArrayList<Post> mPosts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_timeline, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        rvPosts = view.findViewById(R.id.rvPosts);

        //create the adapter
        mPosts = new ArrayList<>();
        //create the data source
        adapter = new PostAdapter(mPosts);
        // set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setAdapter(adapter);
        queryPosts();

    }
    private void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Error");
                    e.printStackTrace();
                    return;
                }
                adapter.clear();
                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);
                    mPosts.add(post);
                    adapter.notifyItemInserted(mPosts.size() - 1);
                Log.d(TAG,"Post: "+ post.getDescription() + ", username " +  post.getUser().getUsername());
                }

            }
        });
    }
}

