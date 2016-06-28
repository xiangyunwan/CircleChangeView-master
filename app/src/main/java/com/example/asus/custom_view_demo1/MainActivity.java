package com.example.asus.custom_view_demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private CircleChangeView circleChangeView;
    private Button buttonStart;
    private Button buttonStop;
    private int[] temp = new int[]{R.color.color1
            , R.color.color2, R.color.color3, R.color.color4, R.color.color5, R.color.color6};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonStart= (Button) findViewById(R.id.btnStart);
        buttonStop= (Button) findViewById(R.id.btnStop);
        circleChangeView = (CircleChangeView) findViewById(R.id.demoView);
        circleChangeView.setColors(temp);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleChangeView.start();
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleChangeView.stop();
            }
        });

        circleChangeView.setOnCircleChangeListener(new CircleChangeView.OnCircleChangeListener() {
            @Override
            public void onStart() {
                Toast.makeText(MainActivity.this, "onStart", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStop() {
                Toast.makeText(MainActivity.this, "onStop", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
