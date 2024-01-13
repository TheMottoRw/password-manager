package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.password_manager.helper.DatabaseManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton fab,fabMaster;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        fab = findViewById(R.id.fab);
        fabMaster = findViewById(R.id.fabMasterPassword);
        dbManager = new DatabaseManager(this);
        dbManager.open();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddPassword.class);
                startActivity(intent);
            }
        });
        fabMaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UpdateMasterPassword.class);
                startActivity(intent);
            }
        });
        initData();
    }
    public void initData(){
        PasswordAdapter adapter = new PasswordAdapter(MainActivity.this,dbManager.load());
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}