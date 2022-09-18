package com.example.sistema_tickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class adapter extends ArrayAdapter<ticket> {

    Context context;
    List<ticket> arrayticket;
    public adapter(@NonNull Context context, List<ticket>arrayticket) {
        super(context, R.layout.list_tickt,arrayticket);
        this.context=context;
        this.arrayticket=arrayticket;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tickt,null,true);
        TextView txtid = view.findViewById(R.id.txtid);
        TextView txtnombre = view.findViewById(R.id.txtnombre);

        txtid.setText(arrayticket.get(position).getId());
        txtnombre.setText(arrayticket.get(position).getNombre());
        return view;
    }
}
