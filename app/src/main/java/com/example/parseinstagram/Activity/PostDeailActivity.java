package com.example.parseinstagram.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.parseinstagram.R;

public class PostDeailActivity extends AppCompatActivity {
    private ImageView ivImage;
    private TextView tvHandle;
    private TextView tvDescription;
    private Intent shareIntent;

    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.post_detail);
        //Fetch views
        ivImage = findViewById(R.id.ivImage);
        tvHandle = findViewById(R.id.tvHandle);
        tvDescription = (TextView) findViewById(R.id.tvDescription);

        //Extract Post object from intent extras
    }

}
