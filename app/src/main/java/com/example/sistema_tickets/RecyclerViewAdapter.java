package com.example.sistema_tickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    List<Message> mData;
    private LayoutInflater mInflater;
    Context context;
    private RecyclerView.ViewHolder holder;
    private int position;

    public RecyclerViewAdapter(List<Message> itemList,Context context)
    {
        this.mInflater=LayoutInflater.from(context);
        this.context =context;
        this.mData = itemList;
    }
    public int getItemCount() {return mData.size();}
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup paren,int viewType)
    {
        View view= mInflater.inflate(R.layout.message_design,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.bindData(mData.get(position));
    }


    public void setItems(List<Message> items){mData=items;}
    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iconImage;
        TextView user_email,user_menssage,user_menssage_date_time;
       ViewHolder(View itemView){
           super(itemView);
           user_email = itemView.findViewById(R.id.user_email);
           user_menssage = itemView.findViewById(R.id.user_menssage);
           user_menssage_date_time= itemView.findViewById(R.id.user_menssage_date_time);

       }

       void bindData(final Message item )
       {
           user_email.setText(item.getD_user());
           user_menssage.setText(item.d_descrip);
           user_menssage_date_time.setText(item.fecha);
       }


    }


}
