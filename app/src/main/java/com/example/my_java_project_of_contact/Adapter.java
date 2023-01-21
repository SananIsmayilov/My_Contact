package com.example.my_java_project_of_contact;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_java_project_of_contact.databinding.GorunumBinding;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewRcyc> {
    ArrayList<Contact> contactArrayList1;

    public Adapter(ArrayList<Contact> contactArrayList1) {
        this.contactArrayList1 = contactArrayList1;
    }

    @NonNull
    @Override
    public ViewRcyc onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        GorunumBinding binding=GorunumBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewRcyc(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewRcyc holder, int position) {
    holder.binding.txt.setText(contactArrayList1.get(position).name);
    }

    @Override
    public int getItemCount() {
        return contactArrayList1.size();
    }

    public class ViewRcyc extends RecyclerView.ViewHolder{
        GorunumBinding binding;
        public ViewRcyc(@NonNull GorunumBinding binding ) {
            super(binding.getRoot());
            this.binding=binding;
        }
    }
}
