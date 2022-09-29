package com.example.sistema_tickets;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class New_ticket extends AppCompatActivity implements LocationListener {

    EditText id,titulo,mensaje;
    TextView codigo;
    RequestQueue requestQueue;
    SharedPreferences preferences;
    Spinner cate, fili;
    int posc, posf;
    categoria c;
    filial f;
    LocationManager ubicacion;
    String latitud,longitud;
    ArrayList<categoria> lista = new ArrayList<categoria>();
    ArrayList<filial> listfilial = new ArrayList<filial>();
    Button btnenviar;
    conexion con= new conexion();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_ticket);

        id = findViewById(R.id.iduser);
        codigo = findViewById(R.id.codigo);
        titulo= findViewById(R.id.titulo);
        mensaje = findViewById(R.id.mensaje);

        fili = (Spinner) findViewById(R.id.Filial);
        cate = (Spinner) findViewById(R.id.cate);
        btnenviar = findViewById(R.id.btnEnviar);
        preferences = getSharedPreferences("preferenciasLogin", MODE_PRIVATE);
        String email = preferences.getString("usuario", "");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION
            }, 100);
        }

        // cate.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,lista));
        codigo.setVisibility(codigo.INVISIBLE);


        validarUsuario(con.ruta+"buscar_user.php?email='" + email + "'");

        localizacion();

         llenarSpinner(con.ruta+"spinner_categoria.php");

         llenarfilial(con.ruta+"spinner_filial.php");
        btnenviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // cate.getItemAtPosition(cate.getSelectedItemPosition()).toString();
                posc = cate.getSelectedItemPosition();
                posf = fili.getSelectedItemPosition();
                if (posc >= 0) {

                    c = lista.get(posc);
                    f = listfilial.get(posf);
                    ejecutarServicio(con.ruta+"insert_ticket.php");
                  //  Toast.makeText(getApplicationContext(), c.getCate_id(), Toast.LENGTH_LONG).show();

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


                        String id = jsonObject.getString("id_categoria");
                        String descrip = jsonObject.getString("t_descripcion");
                        String estado = jsonObject.getString("cate_estado");
                        categoria c = new categoria(id, descrip, estado);
                        // c.setCate_id(jsonObject.getString("id_categoria"));
                        // c.setT_categoria(jsonObject.getString("t_descripcion"));
                        lista.add(c);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<categoria> a = new ArrayAdapter<categoria>(New_ticket.this, android.R.layout.simple_list_item_1, lista);
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


                        String up = jsonObject.getString("id_filial");
                        int id = Integer.parseInt(up);
                        String descrip = jsonObject.getString("nombre");

                        filial f = new filial(id, descrip);
                        listfilial.add(f);

                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
                ArrayAdapter<filial> a = new ArrayAdapter<filial>(New_ticket.this, android.R.layout.simple_list_item_1, listfilial);
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


    @SuppressLint("MissingPermission")
    private void localizacion() {

        try {


            ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            ubicacion.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5, New_ticket.this);

        }catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
      //  Toast.makeText(getApplicationContext(),""+location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_LONG).show();
       latitud= location.getLatitude()+"";
       longitud= location.getLongitude()+"";
       try{
           Geocoder geocoder= new Geocoder(New_ticket.this, Locale.getDefault());
           List<Address> addresses = geocoder.getFromLocation(location.getLatitude(),location.getLongitude(),1);
       String address = addresses.get(0).getAddressLine(0);
           Toast.makeText(getApplicationContext(),"Resultado "+address, Toast.LENGTH_LONG).show();
       }catch(Exception e){
           e.printStackTrace();
       }
    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
    private void ejecutarServicio(String URL)
    {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "Operacion realizada con exito!!", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros= new HashMap<String,String>();
                parametros.put("id",codigo.getText().toString());
                parametros.put("titulo",titulo.getText().toString());
                parametros.put("categoria",c.getCate_id()+"");
                parametros.put("filial",f.getId_filial()+"");
                parametros.put("mensaje",mensaje.getText().toString());
                parametros.put("latitud",latitud.toString());
                parametros.put("longitud",longitud.toString());
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}