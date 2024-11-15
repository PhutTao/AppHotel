package com.example.apphotel.Homescreen.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.apphotel.Homescreen.Activity.Homescreen_changepassword;
import com.example.apphotel.Homescreen.Activity.Homescreen_myprofile;
import com.example.apphotel.Homescreen.Activity.Homescreen_notifications;
import com.example.apphotel.Homescreen.HomescreenActivity;
import com.example.apphotel.Login.Activity.LoginActivity;
import com.example.apphotel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;




public class Homescreen_setting extends Fragment {
    BottomNavigationView bottomNavigationView;
    ImageButton btn_back;
    LinearLayout setting_logout,setting_editprofile,setting_changepassword,setting_notifications, setting_language;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homescreen_fragment_setting, container, false);
        // back home
        btn_back = (ImageButton) view.findViewById(R.id.myprofile_btn_back);
        bottomNavigationView = getActivity().findViewById(R.id.homescreen_bottom_navigation);

        // Set a click listener for the button
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add this line to go back to the previous fragment
                getFragmentManager().popBackStack();
                // Update the selected item in the BottomNavigationView
                bottomNavigationView.setSelectedItemId(((HomescreenActivity)getActivity()).getPreviousFragmentId());
            }

        });
        //logout
        setting_logout = (LinearLayout) view.findViewById(R.id.setting_btn_logout);
        setting_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSignOutDialog();
            }
        });

        //edit profile
        setting_editprofile = (LinearLayout) view.findViewById(R.id.setting_btn_editprofile);
        setting_changepassword = (LinearLayout) view.findViewById(R.id.setting_btn_changepassword);
        setting_notifications = (LinearLayout) view.findViewById(R.id.setting_btn_notifications);
        setting_language = (LinearLayout) view.findViewById(R.id.setting_btn_language);
        setting_editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Homescreen_myprofile.class);
                startActivity(intent);
            }
        });
        setting_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Homescreen_changepassword.class);
                startActivity(intent);
            }
        });
        setting_notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Homescreen_notifications.class);
                startActivity(intent);
            }
        });
        setting_language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLanguageDialog();
            }
        });



        return view;

    }
    public void showSignOutDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.homescreen_arlert_empty, null);
        builder.setView(view);

        builder.setTitle("Logout of your account?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void showLanguageDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getLayoutInflater().inflate(R.layout.homescreen_arlert_empty, null);
        builder.setView(view);

        // Set the title with a custom font size
        TextView title = new TextView(getActivity());
        title.setText("Other languages will be updated later");
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
        title.setTypeface(null, Typeface.BOLD);
        title.setGravity(Gravity.CENTER);


        builder.setCustomTitle(title);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }



}