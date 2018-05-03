package com.example.rgarcia21.weatherappandroid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Transparent code, testing
        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //
    }
//
    public static void setWindowFlag(Activity activity, final int bits, boolean on) {
        Window win = activity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
//

public void getWeather(View v)
{
    // Disable threading. We'll fix this later.
    StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
    StrictMode.setThreadPolicy(policy);

    // Get the text from the input field
    EditText location = (EditText) findViewById(R.id.searchBar);
    String dataType = location.getText().toString();
    String state = "";

    try {
        Integer.parseInt(dataType);
        }
        catch (NumberFormatException e) {
            String d = dataType;
            dataType = d.substring(0,d.indexOf(','));
            dataType = dataType.replaceAll(" ", "_");

            state = d.substring(d.indexOf(','), d.length());
            state = state.replaceAll(" ", "");
        }

        Weather b = new Weather(dataType, state);
        Conditions a = b.getCond();

        String temF = a.tempF;
        String felF = a.feelF;
        temF.replaceAll("\\.0*$", "");
        felF.replaceAll("\\.0*$", "");

    // Set the text GUI elements
        TextView temp = (TextView) findViewById(R.id.weatherNumber);
        temp.setText(temF + "°");

        TextView city = (TextView) findViewById(R.id.weatherCity);
        city.setText(a.city + ", " + a.inputState);

        TextView feels = (TextView) findViewById(R.id.weatherFeel);
        feels.setText("Feels like: " + felF + "° (F)");

        //Icon code
        int id = getResources().getIdentifier("drawable/" + a.icon, null, null);
        ImageView weatherIcon = (ImageView) findViewById(R.id.weatherImage);
        weatherIcon.setImageResource(id);

        TextView wind = (TextView) findViewById(R.id.weatherWind);
        wind.setText(a.wind + "mph");

        TextView rain = (TextView) findViewById(R.id.weatherRain);
        rain.setText(a.rain + "%");
    }
}
