package com.example.contactsql.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contactsql.ContactDetailsActivity;
import com.example.contactsql.MainActivity;
import com.example.contactsql.R;
import com.example.contactsql.model.Contact;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    public static final String NAME = "name" ;
    public static final String PHONE_NUMBER = "phone_number";

    private List<Contact> contactList;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Contact> contactList) {
        this.context = context;
        this.contactList = contactList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(contactView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {

        Contact contact = contactList.get(position);

        holder.nameTextView.setText(contact.getName());
        holder.phoneNumberTextView.setText(contact.getPhoneNumber());
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView nameTextView;
        public TextView phoneNumberTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView=itemView.findViewById(R.id.name_textView);
            phoneNumberTextView=itemView.findViewById(R.id.phone_number_textView);
            itemView.setOnClickListener(this);
            nameTextView.setOnClickListener(this);
            phoneNumberTextView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

            switch (v.getId()){
                case R.id.name_textView:
                    Log.i("ClickedOn","Name");
                    break;
                case R.id.phone_number_textView:
                    Log.i("ClickedOn","Phone Number");
                    break;
                default:
                    break;
            }
            Log.i("ClickedOn",String.valueOf(getAdapterPosition()));

            Intent intent = new Intent(context,ContactDetailsActivity.class);
            intent.putExtra(NAME,contactList.get(getAdapterPosition()).getName());
            intent.putExtra(PHONE_NUMBER,contactList.get(getAdapterPosition()).getPhoneNumber());
            context.startActivity(intent);



        }
    }
}
