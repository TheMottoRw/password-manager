package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.password_manager.helper.DatabaseManager;
import com.example.password_manager.helper.PasswordGenerator;

public class AddPassword extends AppCompatActivity {
    private EditText edtPlatform,edtEmail,edtPassword;
    private Button btnGenerate,btnSave;
    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        btnGenerate = findViewById(R.id.btnGenerate);
        edtPlatform = findViewById(R.id.edtPlatform);
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnSave = findViewById(R.id.btnSave);
        dbManager = new DatabaseManager(this);
        dbManager.open();
        btnGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordGenerator.PasswordGeneratorBuilder passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                        .useDigits(true)
                        .useLower(true)
                        .useUpper(true)
                        .usePunctuation(true);
                String password = passwordGenerator.generate(12);
                edtPassword.setText(password);
            }
        });
        edtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("password", edtPassword.getText().toString());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(AddPassword.this,"Password copied successful",Toast.LENGTH_SHORT).show();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbManager.insert(edtPlatform.getText().toString().trim(),edtEmail.getText().toString().trim(),edtPassword.getText().toString().trim());
                Toast.makeText(AddPassword.this,"Password saved successful",Toast.LENGTH_SHORT).show();
                edtPlatform.setText("");
                edtEmail.setText("");
                edtPassword.setText("");
            }
        });
    }
}