package com.example.parseinstagram.Fragments;

import android.util.Log;

import com.example.parseinstagram.Models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileFragment extends TimelineFragment {
    @Override
    protected void queryPosts() {
        super.queryPosts();
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
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
                mPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });


    }


}
