package com.example.sistema_tickets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;

import java.util.ArrayList;

public class inbox_Activity extends AppCompatActivity {

    ListView listView;
    Adapter adapter;
    public static ArrayList<ticket>ticketsArrayList= new ArrayList<>();
    String url="";
    ticket ticket;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
    }
    public void agregar(View view){
     startActivity(new Intent(getApplicationContext(),New_ticket.class));
    }
}