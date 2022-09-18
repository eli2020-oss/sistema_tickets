package com.example.sistema_tickets;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

public class bienvenidaActivity extends AppCompatActivity {
     //variables
     RelativeLayout tickets;
    RelativeLayout inbox;
    RelativeLayout perfil;
    TextView mail;
    SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
       tickets =(RelativeLayout)findViewById(R.id.tickets);
        inbox =(RelativeLayout)findViewById(R.id.inbox);
        perfil =(RelativeLayout)findViewById(R.id.perfil);
        mail =findViewById(R.id.mail);
        preferences = getSharedPreferences("preferenciasLogin",MODE_PRIVATE);
        String email = preferences.getString("usuario","");
        mail.setText(email);
        tickets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),New_ticket.class);
                startActivity(intent);
                finish();
            }
        });
        inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),inbox_Activity.class);
                startActivity(intent);
                finish();
            }
        });
        perfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),PerfilActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}