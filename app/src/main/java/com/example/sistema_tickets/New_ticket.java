package com.example.sistema_tickets;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

import java.util.ArrayList;

public class New_ticket extends AppCompatActivity {

    EditText id;
    TextView codigo;
    RequestQueue requestQueue;
    SharedPreferences preferences;
    Spinner cate, fili;
    private LocationManager ubicacion;
   // String[] frutas={"peras","Uvas"};
   ArrayList<categoria> lista = new ArrayList<categoria>();
    ArrayList<filial> listfilial = new ArrayList<filial>();
  // private AsyncHttpClient cliente;
    Button btnenviar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        id = findViewById(R.id.iduser);
        codigo = findViewById(R.id.codigo);
        fili = (Spinner) findViewById(R.id.Filial);
        cate = (Spinner) findViewById(R.id.cate);
        btnenviar = findViewById(R.id.btnEnviar);
        preferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        String email = preferences.getString("usuario", "");


      // cate.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,lista));
        codigo.setVisibility(codigo.INVISIBLE);

        //validarUsuario("http://192.168.0.10/proyect/base/pages/forms/movil/buscar_user.php?email='" + email + "'");
        validarUsuario("http://192.168.1.205/proyect/base/pages/forms/movil/buscar_user.php?email='" + email + "'");

        localizacion();
        llenarSpinner("http://192.168.0.10/proyect/base/pages/forms/movil/spinner_categoria.php");
       // llenarSpinner("http://192.168.1.205/proyect/base/pages/forms/movil/spinner_categoria.php");
        llenarfilial("http://192.168.0.10/proyect/base/pages/forms/movil/spinner_filial.php");
       // llenarfilial("http://192.168.1.205/proyect/base/pages/forms/movil/spinner_filial.php");
        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // cate.getItemAtPosition(cate.getSelectedItemPosition()).toString();
                int pos=cate.getSelectedItemPosition();
                if(pos>=0)
                {

                    categoria c= lista.get(pos);
                    Toast.makeText(getApplicationContext(), c.getCate_id(), Toast.LENGTH_LONG).show();

                }
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
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    private void llenarSpinner(String URL) {
       // Toast.makeText(getApplicationContext(), "entra 1", Toast.LENGTH_LONG).show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {

                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        jsonObject = response.getJSONObject(i);


                        String id=jsonObject.getString("id_categoria");
                        String descrip=jsonObject.getString("t_descripcion");
                        String estado=jsonObject.getString("cate_estado");
                        categoria c= new categoria(id,descrip,estado);
                       // c.setCate_id(jsonObject.getString("id_categoria"));
                       // c.setT_categoria(jsonObject.getString("t_descripcion"));
                        lista.add(c);

                }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
               ArrayAdapter<categoria> a = new ArrayAdapter<categoria>(New_ticket.this,  android.R.layout.simple_list_item_1, lista);
             //  cate.setAdapter(new ArrayAdapter<categoria>(this, android.R.layout.simple_spinner_item,lista));
               cate.setAdapter(a);

             //cate.setSelection(1);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(New_ticket.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private void llenarfilial(String URL) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {

            @Override
            public void onResponse(JSONArray response) {

                try {

                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        jsonObject = response.getJSONObject(i);


                        String up=jsonObject.getString("id_filial");
                        int id = Integer.parseInt(up);
                        String descrip=jsonObject.getString("nombre");

                       filial f= new filial(id,descrip);
                        listfilial.add(f);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<filial> a = new ArrayAdapter<filial>(New_ticket.this,  android.R.layout.simple_list_item_1, listfilial);
                //  cate.setAdapter(new ArrayAdapter<categoria>(this, android.R.layout.simple_spinner_item,lista));
                fili.setAdapter(a);

                //cate.setSelection(1);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(New_ticket.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
    private void localizacion() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
          ActivityCompat.requestPermissions(this,new String[]{
                  Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION
          },1000);
        }
        try {
            ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
          Location cog = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
             if(ubicacion!=null) {
                Log.d("Latitud", String.valueOf(cog.getLatitude()));
                 Log.d("Longitud", String.valueOf(cog.getLongitude()));
             }
        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
       // ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
       /// Location cog = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       /// if(ubicacion!=null) {
       ///     Log.d("Latitud", String.valueOf(cog.getLatitude()));
      //      Log.d("Longitud", String.valueOf(cog.getLongitude()));
      //  }

    }

}