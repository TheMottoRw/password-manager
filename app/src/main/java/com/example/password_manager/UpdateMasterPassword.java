package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.password_manager.helper.Utils;

public class UpdateMasterPassword extends AppCompatActivity {
    private EditText edtCurrentPassword,edtNewPassword,edtConfirmPassword;
    private Button btnSave;
    private Utils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_master_password);
        edtCurrentPassword = findViewById(R.id.edtCurrentPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtConfirmPassword = findViewById(R.id.edtConfirmPassword);
        btnSave = findViewById(R.id.btnSave);
        utils = new Utils(this);
        edtCurrentPassword.setText(utils.getField("master_password"));
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNewPassword.getText().toString().equals(edtConfirmPassword.getText().toString())){
                    utils.setField("master_password",edtNewPassword.getText().toString());
                    Toast.makeText(UpdateMasterPassword.this,"Master password changed successful",Toast.LENGTH_SHORT).show();
                    edtConfirmPassword.setText("");
                    edtNewPassword.setText("");
                    edtCurrentPassword.setText(utils.getField("master_password"));
                }else{
                    Toast.makeText(UpdateMasterPassword.this,"Master password does not match",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}