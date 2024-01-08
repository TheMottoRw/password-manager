package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.accessibility.AccessibilityManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddPassword extends AppCompatActivity {
    private EditText edtPassword;
    private Button btnGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password);
        btnGenerate = findViewById(R.id.btnGenerate);
        edtPassword = findViewById(R.id.edtPassword);
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
    }
}