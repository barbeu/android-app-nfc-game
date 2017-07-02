package com.example.tzadmin.nfc_reader_writer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import com.example.tzadmin.nfc_reader_writer.Adapters.MainGridViewAdapter;
import com.example.tzadmin.nfc_reader_writer.Database.DatabaseHelper;
import com.example.tzadmin.nfc_reader_writer.Enums.MainMenu;
import com.example.tzadmin.nfc_reader_writer.Models.Route;
import com.example.tzadmin.nfc_reader_writer.Models.Shop;
import com.example.tzadmin.nfc_reader_writer.NET.Sync;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    Timer timerSync;
    MyTimerTask timerSyncTask;
    String[] values = {
            "1",
            "1",
            "1",
            "1",
            "1",
            "1",
            "1",
            "1"
    };
    
    int[] imageId = {
            R.drawable.jiraf_xxxhdpi,
            R.drawable.jiraf_xxxhdpi,
            R.drawable.jiraf_xxxhdpi,
            R.drawable.jiraf_xxxhdpi,
            R.drawable.jiraf_xxxhdpi,
            R.drawable.jiraf_xxxhdpi,
            R.drawable.jiraf_xxxhdpi,
            R.drawable.jiraf_xxxhdpi
    };

    GridView gridView;

    Button refreshButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);

        gridView = (GridView) findViewById(R.id.gridView_main);
        MainGridViewAdapter adapter = new MainGridViewAdapter(MainActivity.this, values, imageId);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

        refreshButton = (Button) findViewById(R.id.btnRefresh);

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Sync();
            }
        });

        timerSync = new Timer();
        timerSyncTask = new MyTimerTask();
        timerSync.schedule(timerSyncTask, 1000, 50000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timerSync.cancel();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view,
                            int position, long id) {
        switch (position) {
            case MainMenu.REGISTER:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case MainMenu.REGISTER_TEAM:
                startActivity(new Intent(this, TeamsActivity.class));
                break;
            case MainMenu.CHEKIN_SCANER:
                startActivity(new Intent(this, CheckinScaner.class));
                break;
            case MainMenu.CUBES:
                startActivity(new Intent(this, ThrowCubes.class));
                break;
            case MainMenu.REGISTER_ROUTES:
                startActivity(new Intent(this, RouteActivity.class));
                break;
            case MainMenu.REGISTER_SPICKERS:
                startActivity(new Intent(this, SpickersActivity.class));
                break;
            case MainMenu.SHOP:
                startActivity(new Intent(this, ShopActivity.class));
                break;
            case MainMenu.VALIDATION:
                startActivity(new Intent(this, ValidationActivity.class));
                break;
        }
    }

    class MyTimerTask extends TimerTask {

        @Override
        public void run() {
            new Sync(true);
        }
    }
}
