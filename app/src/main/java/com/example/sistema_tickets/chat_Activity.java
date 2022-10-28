package com.example.sistema_tickets;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chat_Activity extends AppCompatActivity {

    String info;
    List<Message> elements;
    ArrayList<Message> items;
    SharedPreferences preferences;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    RecyclerViewAdapter listAdapter;
    conexion con= new conexion();
    TextInputEditText mensaje;
    FloatingActionButton enviar;
    String codigo;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        recyclerView= findViewById(R.id.recyclerview);
        mensaje = findViewById(R.id.mensaje);
        enviar =findViewById(R.id.fab_send);
        // items = new ArrayList<Message>();
        swipeRefreshLayout =findViewById(R.id.swipeRefreshLayout);
        preferences = getSharedPreferences("preferenciasid",MODE_PRIVATE);
        codigo = preferences.getString("codigo","");

      Bundle recibeDatos = getIntent().getExtras();
      info= recibeDatos.getString("idticket");
        elements =new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadItems(con.ruta+"obtenerMensaje.php?tickes_id='"+info+"'");
         enviar.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
               //
                if(mensaje.getText().toString().isEmpty())
                {
                    Toast.makeText(chat_Activity.this,"MENSAJE EN BLANCO",Toast.LENGTH_LONG).show();
                }else
                {
                    //Toast.makeText(chat_Activity.this,mensaje.getText().toString(),Toast.LENGTH_LONG).show();
                    ejecutarServicio(con.ruta+"envio_mensaje.php");
                   //
                 //   loadItems(con.ruta+"obtenerMensaje.php?tickes_id='"+info+"'");
                }
                 //
                 //mensaje.setText("");
             }
         });
         swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
             @Override
             public void onRefresh() {
                 if(mensaje.getText().toString().isEmpty())
                 {
                 Toast.makeText(chat_Activity.this,"MENSAJE EN BLANCO",Toast.LENGTH_LONG).show();
             }else
              {
                  Message envio= new Message("",mensaje.getText().toString(),"Ahora");
                  elements.add(envio);
                  listAdapter = new RecyclerViewAdapter(elements,chat_Activity.this);
                  recyclerView.setAdapter(listAdapter);
              }
                 mensaje.setText("");


             }
         });
    }

    private void loadItems(String MENSAJE_URL){
        //Toast.makeText(chat_Activity.this, "Entra 1", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest=new StringRequest(Request.Method.GET, MENSAJE_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray item = new JSONArray(response);
                    for(int i=0; i<item.length(); i++)
                    {
                      //  Toast.makeText(chat_Activity.this, "Entra", Toast.LENGTH_SHORT).show();
                        JSONObject itemObject = item.getJSONObject(i);
                        String id= itemObject.getString("id");
                        String nombre= itemObject.getString("descri");
                        String fecha= itemObject.getString("fecha");
                       // items.add(new Message(id+"",nombre+"",fecha+""));
                        Message mensaje= new Message(id+"",nombre+"",fecha+"");
                        elements.add(mensaje);
                    }
                    listAdapter = new RecyclerViewAdapter(elements,chat_Activity.this);
                    recyclerView.setAdapter(listAdapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(chat_Activity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        Volley.newRequestQueue(this).add(stringRequest);
    }
    private void ListarDatos(String URL) {
       //  Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_LONG).show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            // Toast.makeText(getApplicationContext(), "entra 1", Toast.LENGTH_LONG).show();
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
               // Toast.makeText(chat_Activity.this, "entra", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);

                        String id= jsonObject.getString("id");
                        String nombre= jsonObject.getString("descri");
                        String fecha= jsonObject.getString("fecha");

                     //  elements.add(new Message(id+"",nombre+"",fecha+""));
                      //  items.add(new Message(id+"",nombre+"",fecha+""));

                     //Toast.makeText(chat_Activity.this, items+"", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }

             ///   recyclerView.setHasFixedSize(true);
          //     recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            //    recyclerView.setAdapter(listAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(chat_Activity.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);

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
                parametros.put("tickes_id",info);
                parametros.put("d_user",codigo);
                parametros.put("d_descrip",mensaje.getText().toString());
                //Toast.makeText(getApplicationContext(),"Ejecuto",Toast.LENGTH_LONG).show();
                return parametros;
            }
        };
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}