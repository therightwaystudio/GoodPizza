package com.example.romanovsky_m.testproject.Fragments;

import android.app.Dialog;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.romanovsky_m.testproject.Activities.GoodPizzaActivity;
import com.example.romanovsky_m.testproject.R;
import com.example.romanovsky_m.testproject.Services.LoadingService;

import com.github.rahatarmanahmed.cpv.CircularProgressView;


public class LoadingFragment extends DialogFragment {
    private CircularProgressView circularProgressView;
    MyBroadcastReceiver mMyBroadcastReceiver;
    Intent intentMyIntentService;
    TextView mTextView;
    AlertDialog alertDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        intentMyIntentService = new Intent(getActivity(), LoadingService.class);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_dialog_loadaing,null);
        circularProgressView = (CircularProgressView) view.findViewById(R.id.progress_view);
        mTextView = (TextView) view.findViewById(R.id.load_text_view);

        alertDialog = new AlertDialog.Builder(getActivity()).
        setView(view).
        setPositiveButton("Попробовать ещё", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).
        setTitle(R.string.loading_date).
        create();

        return alertDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        getActivity().startService(intentMyIntentService);
        getDialog().setCanceledOnTouchOutside(false);
        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).setVisibility(View.INVISIBLE);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if(i==android.view.KeyEvent.KEYCODE_BACK){
                    return true;
                }
                else {
                    return false;
                }
            }
        });
        mMyBroadcastReceiver = new MyBroadcastReceiver();
        IntentFilter intentFilter = new IntentFilter(LoadingService.ACTION_LOADINGSERVICE);
        intentFilter.addCategory(Intent.CATEGORY_DEFAULT);
        getActivity().registerReceiver(mMyBroadcastReceiver, intentFilter);
    }

    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mMyBroadcastReceiver);
    }

    public class MyBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String result = intent.getStringExtra(LoadingService.EXTRA_KEY_OUT);
            switch (result) {
                case LoadingService.RESPONSE_OK:
                    ((GoodPizzaActivity) getActivity()).LOADED_FLAG = true;
                    getDialog().dismiss();
                    FragmentManager fragmentManager = getFragmentManager();
                    Fragment fragment = new CategoriesListFragment();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,fragment)
                            .commit();
                    break;
                case LoadingService.RESPONSE_NETWORK_ERROR:
                    circularProgressView.setVisibility(View.INVISIBLE);
                    mTextView.setText(R.string.no_network_connection);
                    mTextView.setVisibility(View.VISIBLE);
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            circularProgressView.setVisibility(View.VISIBLE);
                            mTextView.setVisibility(View.INVISIBLE);
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.INVISIBLE);
                            getActivity().startService(intentMyIntentService);
                        }
                    });

                    break;
                case LoadingService.RESPONSE_SERVER_ERROR:
                    circularProgressView.setVisibility(View.INVISIBLE);
                    mTextView.setText(R.string.connection_fail);
                    mTextView.setVisibility(View.VISIBLE);
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.VISIBLE);
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            circularProgressView.setVisibility(View.VISIBLE);
                            mTextView.setVisibility(View.INVISIBLE);
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setVisibility(View.INVISIBLE);
                            getActivity().startService(intentMyIntentService);
                        }
                    });
                    break;
                default:
                    break;
            }
        }
    }
}
