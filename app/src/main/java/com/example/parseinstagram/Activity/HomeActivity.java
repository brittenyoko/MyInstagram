package com.example.parseinstagram.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.parseinstagram.Fragments.ComposeFragment;
import com.example.parseinstagram.Fragments.TimelineFragment;
import com.example.parseinstagram.Models.Post;
import com.example.parseinstagram.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private Button logoutbtn;
    private Button composebtn;
    private BottomNavigationView bottomnavigationview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        final FragmentManager fragmentManager = getSupportFragmentManager();

        // define your fragments here
        final Fragment fragment1 = new ComposeFragment();
        final Fragment fragment2 = new TimelineFragment();
//      final Fragment fragment3 = new ThirdFragment();

        logoutbtn = findViewById(R.id.logout_btn);

        bottomnavigationview = findViewById(R.id.bottomNavigationView);

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            logout();
            }
        });

        bottomnavigationview.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.action_home:
                        fragment = new TimelineFragment();
                        Toast.makeText(HomeActivity.this, "Home was Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_profile:
                        fragment = new ComposeFragment();
                        Toast.makeText(HomeActivity.this, "Profile was Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.newpost:
                    default:
                        fragment = new ComposeFragment();
                        Toast.makeText(HomeActivity.this, "New Post was selected ", Toast.LENGTH_SHORT).show();
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment).commit();
                return true;
            }

        });

        // Set default selection
        bottomnavigationview.setSelectedItemId(R.id.action_home);
    }

    private void logout() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // show the signup or login screen
            ParseUser.logOut();
            currentUser = ParseUser.getCurrentUser(); // this will now be null
            final Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();

        }
    }

    private void queryPosts() {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if (e != null) {
                    Log.e("HomeActivity", "Error");
                    e.printStackTrace();
                    return;
                }
                for (int i = 0; i < posts.size(); i++) {
                    Log.d("ComposeFragment","Post: "+ posts.get(i).getDescription() + ", username " +  posts.get(i).getUser().getUsername());
                }
            }
        });
    }
}
