package com.example.uda.networkerprofesional;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final String USERPREFERENCES = "Users";
    public static final String USERNAME = "Username";
    //public static final String NETWORKERURL = "http://staging.networkerprofessional.net/Default.aspx?id=";
    public static final String NETWORKERURL = "http://networkerprofessional.net/Default.aspx?id=";

    EditText edittextusername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        edittextusername = (EditText) findViewById(R.id.editText_user);

        //Looks on SharedPreferences for a non-empty user field and adds it into the URL
        SharedPreferences sharedPreferences = getSharedPreferences(USERPREFERENCES, Context.MODE_PRIVATE);
        String newusername = sharedPreferences.getString(USERNAME, "");
        edittextusername.setText(newusername);
        //Checks if the store value in shared preferences is not ""
//        if (newusername.trim().length() > 0)
//        {
//            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
//                    Uri.parse(NETWORKERURL+newusername));
//            startActivity(browserIntent);
//        }
    }

    //Checks for a live configuration change, in this case, orientation
    //Configuration changes are set on AndroidManifest.xml -> android:configChanges="orientation|keyboardHidden|screenSize"
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        //Getting Resources from activity_main.xml
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativelayout);
        Drawable portrait = ContextCompat.getDrawable(this, R.drawable.background_portrait);
        Drawable landscape = ContextCompat.getDrawable(this, R.drawable.background_landscape);

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            assert relativeLayout != null;
            relativeLayout.setBackground(landscape);
            Log.i("MainActivy", "LANDSCAPE orientation");
            //Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            assert relativeLayout != null;
            relativeLayout.setBackground(portrait);
            Log.i("MainActivy", "PORTRAIT orientation");
            //Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    //Opens Internet URL - edittext is add at the end of the URL
    public void browseropen(View view) {

        //Stores the username textfield in sharedpreferences
        SharedPreferences sharedPreferences = getSharedPreferences(USERPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, edittextusername.getText().toString());
        editor.apply();

        assert edittextusername != null;
        if (edittextusername.getText().toString().trim().length() > 0)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(NETWORKERURL+edittextusername.getText().toString().trim()));
            startActivity(browserIntent);
        }
        else {
            Toast.makeText(this, R.string.errormessageID, Toast.LENGTH_SHORT).show();
        }
    }

}
