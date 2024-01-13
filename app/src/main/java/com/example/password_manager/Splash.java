package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.password_manager.helper.Constants;
import com.example.password_manager.helper.Utils;

public class Splash extends AppCompatActivity {
    private EditText edtPassword, edtPasswordUnlock;
    private Button btnSetMasterPassword, btnSave, btnUnlock;
    private LinearLayout lnySetMasterPassword, lnySetMasterPasswordUnlock;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        edtPassword = findViewById(R.id.edtPassword);
        edtPasswordUnlock = findViewById(R.id.edtPasswordUnlock);
        btnSetMasterPassword = findViewById(R.id.btnSetMasterPassword);
        btnSave = findViewById(R.id.btnSave);
        btnUnlock = findViewById(R.id.btnUnlock);
        lnySetMasterPassword = findViewById(R.id.lnySetMasterPassword);
        lnySetMasterPasswordUnlock = findViewById(R.id.lnySetMasterPasswordUnlock);
        utils = new Utils(this);
        init();

        btnSetMasterPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lnySetMasterPassword.setVisibility(View.VISIBLE);
                lnySetMasterPasswordUnlock.setVisibility(View.GONE);
                btnSetMasterPassword.setVisibility(View.GONE);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                utils.setField(Constants.MASTER_PASSWORD_KEY, edtPassword.getText().toString());
            }
        });
        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (utils.getField(Constants.MASTER_PASSWORD_KEY).equals(edtPasswordUnlock.getText().toString())) {
                    Toast.makeText(Splash.this,getString(R.string.unlock_success),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Splash.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Splash.this,getString(R.string.unlock_fail),Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void init() {
        if (utils.getField("master_password").equals("")) {
            lnySetMasterPasswordUnlock.setVisibility(View.GONE);
            btnSetMasterPassword.setVisibility(View.VISIBLE);
        } else {
            lnySetMasterPassword.setVisibility(View.GONE);
            lnySetMasterPasswordUnlock.setVisibility(View.VISIBLE);
            btnSetMasterPassword.setVisibility(View.GONE);
        }
    }
}