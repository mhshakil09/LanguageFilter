package com.example.languagefilter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.languagefilter.databinding.ActivityDashboardBinding;
import com.example.languagefilter.databinding.ActivityMainBinding;

import java.util.Locale;

public class DashboardActivity extends AppCompatActivity {

    ActivityDashboardBinding binding;
    SessionManager sessionManager;
    Context context;
    Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        sessionManager = new SessionManager(getApplicationContext());

        if (sessionManager.getSwitch()) {
            binding.languageSwitch.setChecked(true);
            context = LocaleHelper.setLocale(DashboardActivity.this, "bn");
            resources = context.getResources();
            setText(resources);
        } else {
            binding.languageSwitch.setChecked(false);
        }
        binding.userName.setText(sessionManager.getUsername());

        initClickListener();
    }

    public void initClickListener() {

        binding.languageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                sessionManager.setSwitch(true);
                context = LocaleHelper.setLocale(DashboardActivity.this, "bn");
            } else {
                sessionManager.setSwitch(false);
                context = LocaleHelper.setLocale(DashboardActivity.this, "en");
            }
            resources = context.getResources();
            setText(resources);

        });

        binding.userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(DashboardActivity.this, ""+sessionManager.getUsername(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(resources.getString(R.string.logout));
                builder.setMessage(resources.getString(R.string.alert_dialog_1));
                builder.setCancelable(true);

                builder.setPositiveButton(
                        resources.getString(R.string.yes),
                        (dialog, id) -> {
                            dialog.cancel();
                            sessionManager.setLogin(false);
                            sessionManager.setUsername("");
                            /*sessionManager.setSwitch(false);

                            context = LocaleHelper.setLocale(DashboardActivity.this, "en");
                            resources = context.getResources();
                            setText(resources);*/

                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();
                        });

                builder.setNegativeButton(
                        resources.getString(R.string.cancel),
                        (dialog, id) -> dialog.cancel());

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }

    private void setText(Resources resources) {

        binding.logoutBtn.setText(resources.getString(R.string.logout));

        binding.profileTV.setText(resources.getString(R.string.profile));
        binding.calculatorTV.setText(resources.getString(R.string.calculator));
        binding.galleryTV.setText(resources.getString(R.string.gallery));

        binding.eventsTV.setText(resources.getString(R.string.events));
        binding.linksTV.setText(resources.getString(R.string.links));
        binding.journalsTV.setText(resources.getString(R.string.journals));

        binding.noticesTV.setText(resources.getString(R.string.notices));
        binding.medicineTV.setText(resources.getString(R.string.medicine));
        binding.patientRegistrationTV.setText(resources.getString(R.string.patient_registration));
    }

}