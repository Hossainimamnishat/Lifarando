package com.example.lifarandojava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button loginbutton = findViewById(R.id.loginButton);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Log.d("test","login");
                EditText email = findViewById(R.id.emailInput);
                TextInputEditText password = findViewById(R.id.passwordInput);
                login(email.getText().toString(),password.getText().toString());

            }
        });

    }

    private void login( String email,String password){
        try {
            if (Objects.equals(email, "1234@gmail.com") && Objects.equals(password, "123456")){
                Toast.makeText(this,"login succesfull",Toast.LENGTH_LONG).show();

                // startActivity(new Intent(this,shift.class));
                startActivity(new Intent(this,HomeActivity.class));
            }else{
                Toast.makeText(this,"Invalid Credential",Toast.LENGTH_LONG).show();
            }
        
        }catch (Exception e){
            Log.d("Exeption","login"+e.toString());
        }
    }
}
