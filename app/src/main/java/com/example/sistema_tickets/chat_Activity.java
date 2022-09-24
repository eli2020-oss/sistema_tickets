package com.example.sistema_tickets;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class chat_Activity extends AppCompatActivity {
    TextView ver;
    List<Message> elements;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
       // ver= findViewById(R.id.ver);
      // Bundle recibeDatos = getIntent().getExtras();
      // String info= recibeDatos.getString("idticket");
     //  ver.setText(info);

        init();
    }
    public void init(){
        elements =new ArrayList<>();
        elements.add(new Message("1212122" ,"Mensaje","Fecha"));
        elements.add(new Message("2222" ,"MMMM","hola"));
        elements.add(new Message("123231232" ,"Mefsdfsdsaje","Fdfgdgecha"));
        RecyclerViewAdapter listAdapter = new RecyclerViewAdapter(elements,this);
        RecyclerView recyclerView= findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(listAdapter);
    }

}