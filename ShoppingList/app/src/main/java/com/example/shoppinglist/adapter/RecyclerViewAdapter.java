package com.example.shoppinglist.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.shoppinglist.MainActivity;
import com.example.shoppinglist.R;
import com.example.shoppinglist.data.DatabaseHandler;
import com.example.shoppinglist.model.Item;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Item> itemList;
    private Context context;

    public RecyclerViewAdapter(List<Item> itemList, Context context) {
        this.itemList = itemList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_list,parent,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Item item = itemList.get(position);
        String itemNameText=context.getResources().getString(R.string.item_text) + " " + item.getItemName();
        String itemBrandText=context.getResources().getString(R.string.brand_text) + " " + item.getItemBrand();
        String itemQualityText=context.getResources().getString(R.string.quality_text) + " " + item.getItemQuality();
        String itemQuantityText=context.getResources().getString(R.string.quantity_text) + " " + item.getItemQuantity();
        holder.itemNameTextView.setText(itemNameText);
        holder.itemBrandTextView.setText(itemBrandText);
        holder.itemQualityTextView.setText(itemQualityText);
        holder.itemQuantityTextView.setText(itemQuantityText);
        holder.itemDateTextView.setText(item.getItemAdded());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView itemNameTextView;
        public TextView itemBrandTextView;
        public TextView itemQualityTextView;
        public TextView itemQuantityTextView;
        public TextView itemDateTextView;
        public Button editButton;
        public Button deleteButton;
        AlertDialog.Builder builder;
        AlertDialog dialog;
        EditText itemNameEditText;
        EditText itemBrandEditText;
        EditText itemQualityEditText;
        EditText itemQuantityEditText;
        TextView titleTextView;
        Button saveButton;

        public ViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            itemNameTextView = itemView.findViewById(R.id.item_name_textView);
            itemBrandTextView = itemView.findViewById(R.id.item_brand_textView);
            itemQualityTextView = itemView.findViewById(R.id.item_quality_textView);
            itemQuantityTextView = itemView.findViewById(R.id.item_quantity_textView);
            itemDateTextView = itemView.findViewById(R.id.item_date_textView);
            editButton = itemView.findViewById(R.id.edit_button);
            deleteButton = itemView.findViewById(R.id.delete_button);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.edit_button:
                    editItem();
                    break;
                case R.id.delete_button:
                    Item item = itemList.get(getAdapterPosition());
                    itemList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                        Snackbar.make(v,R.string.item_removed,Snackbar.LENGTH_LONG).setAction(R.string.undo, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                itemList.add(item);
                                notifyDataSetChanged();

                            }
                        }).addCallback(new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                            @Override
                            public void onDismissed(Snackbar transientBottomBar, int event) {
                                super.onDismissed(transientBottomBar, event);
                                if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT || event == Snackbar.Callback.DISMISS_EVENT_CONSECUTIVE) {
                                    deleteItem(item.getId());
                                }
                            }
                        }).show();

                    break;
                default:break;
            }
        }

        private void editItem() {
            builder = new AlertDialog.Builder(context);
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.popup_layout,null);
            itemNameEditText = view.findViewById(R.id.item_name_editText);
            itemBrandEditText = view.findViewById(R.id.item_brand_editText);
            itemQualityEditText = view.findViewById(R.id.item_quality_editText);
            itemQuantityEditText = view.findViewById(R.id.item_quantity_editText);
            titleTextView = view.findViewById(R.id.title_textView);
            saveButton = view.findViewById(R.id.save_button);

            Item item = itemList.get(getAdapterPosition());
            titleTextView.setText(R.string.edit_item);
            itemNameEditText.setText(item.getItemName());
            itemBrandEditText.setText(item.getItemBrand());
            itemQualityEditText.setText(item.getItemQuality());
            itemQuantityEditText.setText(item.getItemQuantity());

            builder.setView(view);
            dialog = builder.create();
            dialog.show();

            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(!itemNameEditText.getText().toString().isEmpty() && !itemBrandEditText.getText().toString().isEmpty() && !itemQualityEditText.getText().toString().isEmpty() && !itemQuantityEditText.getText().toString().isEmpty()){
                        item.setItemName(itemNameEditText.getText().toString());
                        item.setItemBrand(itemBrandEditText.getText().toString());
                        item.setItemQuality(itemQualityEditText.getText().toString());
                        item.setItemQuantity(itemQuantityEditText.getText().toString());
                        DatabaseHandler db = new DatabaseHandler(context);
                        db.updateItem(item);
                        notifyDataSetChanged();
                        dialog.dismiss();
                    }else {
                        Toast.makeText(context, "Field cannot be empty!", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }

        private void deleteItem(int id) {

                DatabaseHandler db = new DatabaseHandler(context);
                db.deleteItem(id);

        }
    }
}
