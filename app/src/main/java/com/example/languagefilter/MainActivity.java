package com.example.languagefilter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.languagefilter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    SessionManager sessionManager;
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.getSwitch()) {
            context = LocaleHelper.setLocale(MainActivity.this, "bn");
            resources = context.getResources();
            setText(resources);
        }

        binding.loginBtn.setOnClickListener(v -> {
            Toast.makeText(getApplicationContext(), ""+binding.password.getText().toString(), Toast.LENGTH_SHORT).show();
            if (binding.username.getText().toString().trim().isEmpty()) {
                binding.username.setError("Enter username");
                Toast.makeText(getApplicationContext(), "Enter username", Toast.LENGTH_SHORT).show();
            }
            if (binding.password.getText().toString().trim().isEmpty()) {
                binding.password.setError("Enter password");
                Toast.makeText(getApplicationContext(), "Enter password", Toast.LENGTH_SHORT).show();
            }
            if (binding.password.getText().toString().equals("root")) {
                Toast.makeText(getApplicationContext(), "Logged In", Toast.LENGTH_SHORT).show();
                sessionManager.setLogin(true);
                sessionManager.setUsername(binding.username.getText().toString().trim());
                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "Wrong password", Toast.LENGTH_SHORT).show();
            }
        });

        if (sessionManager.getLogin()) {
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }
    }

    private void setText(Resources resources) {

        binding.loginTV.setText(resources.getString(R.string.login));

        binding.username.setHint(resources.getString(R.string.hint_username));
        binding.password.setHint(resources.getString(R.string.hint_password));
        binding.loginBtn.setText(resources.getString(R.string.login));
    }
}