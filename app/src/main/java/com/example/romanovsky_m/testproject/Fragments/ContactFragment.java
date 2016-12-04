package com.example.romanovsky_m.testproject.Fragments;

import android.app.Dialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.net.Uri;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;

import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageButton;

import com.example.romanovsky_m.testproject.R;


public class ContactFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_contact,null);

        ImageButton buttonMob = (ImageButton) v.findViewById(R.id.buttonMob);
        ImageButton buttonEmail = (ImageButton) v.findViewById(R.id.contact_email);
        ImageButton buttonMap = (ImageButton) v.findViewById(R.id.contact_map);

        PackageManager packageManager = getActivity().getPackageManager();

        boolean telephonySupported = packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY);
        final boolean gsmSupported = packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_GSM);
        boolean cdmaSupported = packageManager.hasSystemFeature(PackageManager.FEATURE_TELEPHONY_CDMA);
        if(telephonySupported || gsmSupported || cdmaSupported){
            buttonMob.setEnabled(true);
        }
        else {
            buttonMob.setEnabled(false);
        }

        buttonMob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:+375292959954"));
                    startActivity(intent);
            }
        });

        buttonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"therightwaystudio@gmail.com"});
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Вопрос");
                emailIntent.setType("text/plain");
                startActivity(emailIntent);
            }
        });

        buttonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                Fragment fragment = new MyMapFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.fragment_container,fragment)
                        .addToBackStack(null)
                        .commit();
                dismiss();

            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.contacts)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).create();
    }
}
