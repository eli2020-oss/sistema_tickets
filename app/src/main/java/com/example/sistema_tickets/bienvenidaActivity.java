package com.example.sistema_tickets;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class bienvenidaActivity extends AppCompatActivity {
     //variables
     RelativeLayout tickets;
    RelativeLayout inbox;
    RelativeLayout perfil;
    TextView mail;
    String codigo,nombre;
    SharedPreferences preferences;
    RequestQueue requestQueue;
    conexion con= new conexion();
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
        validarUsuario(con.ruta+"buscar_user.php?email='" + email + "'");

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
    private void validarUsuario(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                       // id.setText(jsonObject.getString("f_name"));
                        codigo= jsonObject.getString("id");
                       // Toast.makeText(getApplicationContext(),codigo, Toast.LENGTH_LONG).show();
                        guardarpreferencias(codigo);
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(bienvenidaActivity.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

    }
    private void guardarpreferencias( String id){
        SharedPreferences preferences=getSharedPreferences("preferenciasid", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("codigo",id);
        editor.commit();
        Toast.makeText(bienvenidaActivity.this, "dentro "+id, Toast.LENGTH_SHORT).show();
    }
}