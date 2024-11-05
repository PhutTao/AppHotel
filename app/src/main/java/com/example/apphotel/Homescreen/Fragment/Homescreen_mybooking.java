package com.example.apphotel.Homescreen.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ImageButton;

import com.example.apphotel.Homescreen.HomescreenActivity;
import com.example.apphotel.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;


public class Homescreen_mybooking extends Fragment {
    BottomNavigationView bottomNavigationView;
    ImageButton mybooking_btn_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.homescreen_fragment_mybooking, container, false);

        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.mybooking_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Booked"));
        tabLayout.addTab(tabLayout.newTab().setText("Favorite"));

        final LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.mybooking_container);


        Fragment fragment = new Homescreen_mybooking_booked();
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.mybooking_container, fragment).commit();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                if (tab.getPosition() == 0) {
                    fragment = new Homescreen_mybooking_booked();
                }
                if (tab.getPosition() == 1) {
                    fragment = new Homescreen_mybooking_history();
                }
                if (fragment != null) {
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.mybooking_container, fragment).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // TODO: Implement this method
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // TODO: Implement this method
            }
        });



        // back home
        mybooking_btn_back = (ImageButton) view.findViewById(R.id.myprofile_btn_back);
        bottomNavigationView = getActivity().findViewById(R.id.homescreen_bottom_navigation);

        // Set a click listener for the button
        mybooking_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                // Update the selected item in the BottomNavigationView
                bottomNavigationView.setSelectedItemId(((HomescreenActivity)getActivity()).getPreviousFragmentId());
            }

        });
        return view;
    }
}
