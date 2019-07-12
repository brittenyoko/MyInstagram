package com.example.parseinstagram.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.parseinstagram.Models.EndlessRecyclerViewScrollListener;
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
    protected PostAdapter adapter;
    protected ArrayList<Post> mPosts;
    protected SwipeRefreshLayout swipeContainer;
    private int pagesize = 20;


    private EndlessRecyclerViewScrollListener scrollListener;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {


        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_timeline, parent, false);


    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvPosts = view.findViewById(R.id.rvPosts);

        //create the adapter
        mPosts = new ArrayList<>();
        //create the data source
        adapter = new PostAdapter(mPosts);
        // set the layout manager on the recycler view
        rvPosts.setLayoutManager(new LinearLayoutManager(getContext()));
        rvPosts.setAdapter(adapter);
        // Lookup the swipe container view
        swipeContainer = view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                swipeContainer.setRefreshing(false);
                fetchTimelineAsync(0);
            }
        });
        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                loadNextDataFromApi(page);
            }
        };
        rvPosts.addOnScrollListener(scrollListener);
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        queryPosts();
    }

    private void loadNextDataFromApi(final int page) {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e(TAG,"Error");
                    e.printStackTrace();
                    return;
                }
                adapter.clear();
                for (int i = pagesize * page; i < posts.size(); i++) {
                    if (i < pagesize * page + 1) {
                        break;
                    }
                    Post post = posts.get(i);
                    mPosts.add(post);
                }
                adapter.notifyDataSetChanged();
                for (int i = 0; i < posts.size(); i++) {
                    Post post = posts.get(i);

                    mPosts.add(post);
                    adapter.notifyItemInserted(mPosts.size() - 1);
                    Log.d(TAG,"Post: "+ post.getDescription() + ", username " +  post.getUser().getUsername());
                }

            }
        });
    }

    public void fetchTimelineAsync (int page) {

        //CLEAR OUT old items before appending in the new ones
        adapter.clear();
        queryPosts();
    }

    protected void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(pagesize);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
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

