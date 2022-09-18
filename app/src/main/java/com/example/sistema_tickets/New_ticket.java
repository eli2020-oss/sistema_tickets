package com.example.sistema_tickets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class New_ticket extends AppCompatActivity {

    EditText id;
    TextView codigo;
    RequestQueue requestQueue;
    SharedPreferences preferences;
    Spinner categoria,filial;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);
        id= findViewById(R.id.iduser);
        codigo= findViewById(R.id.codigo);
        filial= (Spinner) findViewById(R.id.Filial);
        categoria=(Spinner) findViewById(R.id.categoria);
        preferences = getSharedPreferences("preferenciasLogin",MODE_PRIVATE);
        String email = preferences.getString("usuario","");;
        codigo.setVisibility(codigo.INVISIBLE);
        validarUsuario("http://192.168.0.15/proyect/base/pages/forms/movil/buscar_user.php?email='"+email+"'");

    }
    private void validarUsuario (String URL){
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id.setText(jsonObject.getString("f_name"));
                        codigo.setText(jsonObject.getString("id"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(New_ticket.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue =Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private void llenarSpinner(String URL)
    {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        id.setText(jsonObject.getString("f_name"));
                        codigo.setText(jsonObject.getString("id"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(New_ticket.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        }
        );
    }

}