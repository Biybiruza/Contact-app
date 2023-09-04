package com.biybiruza.contact.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biybiruza.contact.R;
import com.biybiruza.contact.data.ContactModels;

import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ViewHolder> {

    public List<ContactModels> list;
    private OnItemClickListener onClickListener;
    private OnCallClickListener onCallClickListener;
    public int positions = 0;

    public AdapterContact(List<ContactModels> list, OnItemClickListener onClickListener,
                          OnCallClickListener onCallClickListener) {
        this.list = list;
        this.onClickListener = onClickListener;
        this.onCallClickListener = onCallClickListener;
    }

    public AdapterContact(List<ContactModels> list) {
        this.list = list;
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public interface OnCallClickListener{
        void onCallClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_contact,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(String.format("%s %s", list.get(position).getName(), list.get(position).getSurname()));
        holder.phoneNumber.setText("+998"+list.get(position).getPhoneNumber());

        positions = position;
        //item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickListener.onItemClick(position);
            }
        });

        //call click
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onCallClickListener.onCallClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView phoneNumber;
        ImageView call;

        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            phoneNumber = itemView.findViewById(R.id.tv_phone);
            call = itemView.findViewById(R.id.iv_callBtn);
        }
    }
}
