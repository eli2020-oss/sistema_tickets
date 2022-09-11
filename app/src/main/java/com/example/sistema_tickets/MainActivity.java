package com.example.sistema_tickets;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    EditText edtUsuario,edtPassword;
    Button btnLogin;
    String usuario,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edtUsuario=findViewById(R.id.edtUsuario);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
        recuperarPreferencias();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                usuario=edtUsuario.getText().toString();
                password= edtPassword.getText().toString();
                if(!usuario.isEmpty() && !password.isEmpty())
                {
                    validarUsuario("http://192.168.0.13/proyect/base/pages/forms/movil/validar_usuario.php");
                }else
                {
                    Toast.makeText(MainActivity.this,"No se permite campos vacios", Toast.LENGTH_LONG).show();

                }

                }
        });
    }
    private void validarUsuario (String URL){
        StringRequest stringRequest= new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(!response.isEmpty()){
                    guardarpreferencias();
                    Intent intent= new Intent(getApplicationContext(),Principal.class);
                    startActivity(intent);
                    finish();
                }else
                {

                    Toast.makeText(MainActivity.this,"Usuario o password es incorrecta", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              Toast.makeText(MainActivity.this,error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String, String>();
                parametros.put("usuario",usuario);
                parametros.put("password",password);

                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    private void guardarpreferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("usuario",usuario);
        editor.putString("password",password);
        editor.putBoolean("session",true);
        editor.commit();
    }
    private void recuperarPreferencias(){
        SharedPreferences preferences=getSharedPreferences("preferenciasLogin", Context.MODE_PRIVATE);
        edtUsuario.setText(preferences.getString("usuario","micorreoelectronico@gmail.com"));
        edtPassword.setText(preferences.getString("password","123456"));
    }


}