package com.example.bigproject;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

/**
 * The type Solvent view holders.
 */
public class SolventViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{

    /**
     * The Country name.
     */
    public TextView countryName;
    /**
     * The Country photo.
     */
    public ImageView countryPhoto;
    /**
     * The Zametka.
     */
    public Zametka zametka;
    /**
     * The Context.
     */
    public Context context;
    /**
     * The Button.
     */
    Button button;

    /**
     * Instantiates a new Solvent view holders.
     *
     * @param itemView the item view
     * @param context  the context
     */
    public SolventViewHolders(View itemView, Context context) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.context=context;
        countryName =  itemView.findViewById(R.id.country_name);
        countryPhoto =  itemView.findViewById(R.id.country_photo);
    }


    @Override
    public void onClick(View view) {
        AddDialogFragment addDialogFragment = new AddDialogFragment(zametka);
        addDialogFragment.show(((AppCompatActivity) context).getSupportFragmentManager(), "addDialog");
    }


}
