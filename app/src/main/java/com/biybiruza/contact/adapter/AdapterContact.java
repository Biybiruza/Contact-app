package com.biybiruza.contact.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.biybiruza.contact.R;
import com.biybiruza.contact.data.ContactModels;

import java.util.List;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ViewHolder> {

    public List<ContactModels> list;

    public AdapterContact(List<ContactModels> list) {
        this.list = list;
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView phoneNumber;

        public ViewHolder(View itemView){
            super(itemView);

            name = itemView.findViewById(R.id.tv_name);
            phoneNumber = itemView.findViewById(R.id.tv_phone);
        }
    }
}
