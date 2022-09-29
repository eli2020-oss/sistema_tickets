package com.example.sistema_tickets;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

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

public class inbox_Activity extends AppCompatActivity {

    ListView listView;
    Adapter adapter;
    public static ArrayList<ticket>ticketsArrayList= new ArrayList<>();
    String URL="";
    ticket ticket;
    SharedPreferences preferences;
    RequestQueue requestQueue;
    conexion con= new conexion();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        listView=findViewById(R.id.listMostrar);
        adapter=new adapter(this,ticketsArrayList);
        listView.setAdapter((ListAdapter) adapter);

        preferences = getSharedPreferences("preferenciasLogin",MODE_PRIVATE);
        String email = preferences.getString("usuario","");

      ListarDatos(con.ruta+"bandeja_movil.php?email='"+email+"'");
       // ListarDatos("http://192.168.1.205/proyect/base/pages/forms/movil/bandeja_movil.php?email='"+email+"'");
      listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              int numEntero = Integer.parseInt(listView.getItemIdAtPosition(position)+"");
            // Toast.makeText(getApplicationContext(),ticketsArrayList.get(numEntero).getId()+"", Toast.LENGTH_LONG).show();

              Bundle enviaDatos = new Bundle();
              enviaDatos.putString("idticket",ticketsArrayList.get(numEntero).getId()+"");
             Intent intent = new Intent(inbox_Activity.this, chat_Activity.class);
             intent.putExtras(enviaDatos);
             startActivity(intent);
          }
      });
    }

    private void ListarDatos(String URL) {
        // Toast.makeText(getApplicationContext(), URL, Toast.LENGTH_LONG).show();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            // Toast.makeText(getApplicationContext(), "entra 1", Toast.LENGTH_LONG).show();
            @Override
            public void onResponse(JSONArray response) {
                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                       // String id= "1";
                     //   String nombre="Pedro";
                       // String fecha="hoy";
                       String id= jsonObject.getString("ids");
                     String nombre= jsonObject.getString("nombre");
                      String fecha= jsonObject.getString("fecha");
                     //   Toast.makeText(inbox_Activity.this, id, Toast.LENGTH_SHORT).show();
                        ticket=new ticket(id,nombre,fecha);
                        ticketsArrayList.add(ticket);
                       ArrayAdapter<ticket> a = new ArrayAdapter<ticket>(inbox_Activity.this,  android.R.layout.simple_list_item_1,ticketsArrayList);
                      listView.setAdapter(a);
                       //listView.setAdapter((ListAdapter) adapter);
                      // adapter.notifyDataSetChanged();
                       // adapter.notifyAll();
                  //    adapter.getItem(i);
                       // ((adapter) .getAdapter()).notifyDataSetChanged();


                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(inbox_Activity.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        }
        );
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

    public void agregar(View view){
     startActivity(new Intent(getApplicationContext(),New_ticket.class));
    }
}