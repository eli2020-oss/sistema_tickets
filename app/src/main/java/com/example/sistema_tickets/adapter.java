package com.example.sistema_tickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class adapter extends ArrayAdapter<ticket> {

    Context context;
    List<ticket> arrayticket;
    TextView txtid,txtnombre,txtfecha;
    public adapter(@NonNull Context context, List<ticket>arrayticket) {
        super(context, R.layout.list_tickt,arrayticket);
        this.context=context;
        this.arrayticket=arrayticket;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //ticket ticket = getItem(position);
       // RecyclerView.ViewHolder viewHolder; // view lookup cache stored in tag
       // final View result;
         View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tickt,null,false);
        txtid = view.findViewById(R.id.txtid);
         txtnombre = view.findViewById(R.id.txtnombre);
       txtfecha = view.findViewById(R.id.txtfecha);

        txtid.setText(arrayticket.get(position).getId());
        txtnombre.setText(arrayticket.get(position).getNombre());
        txtfecha.setText(arrayticket.get(position).getFecha());
        return view;
    }
}
