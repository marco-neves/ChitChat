package com.example.chitchat.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.chitchat.R;
import com.example.chitchat.util.Constants;

import static com.example.chitchat.view.ScreenTwo.userTwo;

public class ScreenOne extends AppCompatActivity {

    private int REQUEST_CODE_A = 707;
    private TextView myTextView;

    private Button sendMessageButton;
    private EditText messageEditText;

    public static String userOne = "USER_ONE";

    private SharedPreferences sharedPreferences;

    private ImageView mainImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_one);


        mainImageView = findViewById(R.id.imageView1);

        Glide.with(this)
                .applyDefaultRequestOptions(RequestOptions.centerInsideTransform())
                .load(getString(R.string.image_one_url))
                .into(mainImageView);


        //getSharedPreferences
        sharedPreferences = getSharedPreferences(Constants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);

        //link layout ID's
        myTextView = findViewById(R.id.my_textview_one);
        myTextView.setMovementMethod(new ScrollingMovementMethod());
        sendMessageButton = findViewById(R.id.send_button_one);
        messageEditText = findViewById(R.id.chatbox_one_view);

        //only have to read from prefs once on onCreate
        String message = sharedPreferences.getString("my_string", "Welcome");
        myTextView.setText(message);

        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = messageEditText.getText().toString().trim();
                messageEditText.setText("");

                Intent intentOne = new Intent(ScreenOne.this, ScreenTwo.class);

                String currentMsg = myTextView.getText().toString() + "\n" + userOne + ": " +message;
                //Log.d("TAG_X", "Appended my new message to the current one and attempting to re-set the textView");
                myTextView.setText(currentMsg);
                //Log.d("TAG_X", "I've re-set the textView");

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("my_key",  currentMsg).apply();

                //Log.d("TAG_X", "Saved my new message to my sharedPreferences");

                intentOne.putExtra("my_string", message);

                //Log.d("TAG_X", "Put my message in the Intent for ScreenTwo and attempting to startActivityForResult");
                startActivityForResult(intentOne, REQUEST_CODE_A);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 707 && resultCode == AppCompatActivity.RESULT_OK){
            //Log.d("TAG_X", "Message from UserTwo has been received");
            String received = data.getStringExtra("my_string");
            String msgPlus = myTextView.getText().toString() + "\n" + userTwo + ": " + received;
            myTextView.setText(msgPlus);
            sharedPreferences.edit().putString("my_key",  msgPlus).apply();
        }
    }
}

