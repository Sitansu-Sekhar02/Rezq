package com.sa.rezq.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.sa.rezq.R;
import com.sa.rezq.account.AccountActivity;

public class ActivityOtpVerification extends AppCompatActivity {

    Button btnVerify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.otpverification_activity);

        btnVerify=findViewById(R.id.btnLogin);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ActivityOtpVerification.this, AccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
