package com.example.chitchat.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chitchat.R;

public class ScreenTwo extends AppCompatActivity {

    private TextView myTextView;
    private Button returnMessageButton;
    private EditText messageEditText;
    public static String userTwo = "USER_TWO";

    private ImageView mainImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_two);

        mainImageView = findViewById(R.id.imageView2);

        Glide.with(this)
                .applyDefaultRequestOptions(RequestOptions.centerInsideTransform())
                .load(getString(R.string.image_two_url))
                .into(mainImageView);

        myTextView = findViewById(R.id.my_textview_two);
        returnMessageButton = findViewById(R.id.send_button_two);
        messageEditText = findViewById(R.id.chatbox_two_view);

        //Log.d("TAG_X", "Got to ScreenTwo");

        String messageReceived = getIntent().getStringExtra("my_string");

        Log.d("TAG_X", messageReceived);
        myTextView.setText(ScreenOne.userOne + ": " + messageReceived);

        returnMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScreenTwo.this, ScreenOne.class);
                intent.putExtra("my_string", messageEditText.getText().toString());
                messageEditText.setText("");
                setResult(AppCompatActivity.RESULT_OK, intent);
                finish();
            }
        });
    }
}

