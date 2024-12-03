package com.example.apphotel.Admin.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apphotel.Admin.Activity.AdminActivity;
import com.example.apphotel.Admin.Entity.User;
import com.example.apphotel.Admin.Fragment.UserDetailFragment;
import com.example.apphotel.R;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    private Context context;
    private List<User> userList;
    private FragmentManager fragmentManager;

    public UserAdapter(Context context, List<User> users, FragmentManager fragmentManager) {
        super(context, 0, users);
        this.context = context;
        this.userList = users;
        this.fragmentManager = fragmentManager;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Inflate the custom item layout if needed
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_view_user, parent, false);
        }

        // Get the current item
        User currentItem = userList.get(position);

        // Bind data to the views
        TextView fullName = convertView.findViewById(R.id.txtFullName);
        TextView txtEmail = convertView.findViewById(R.id.txtEmail);
        Button btnViewUser = convertView.findViewById(R.id.btnViewUser);

        if(currentItem.getFullName() == null || currentItem.getFullName() == "null"){
            fullName.setText("Ten nguoi dung trong");
        }else{
            fullName.setText(currentItem.getFullName());
        }

        txtEmail.setText(currentItem.getEmail());
        btnViewUser.setOnClickListener(v -> {
            // Navigate to UserDetailFragment
            if (fragmentManager != null) {
                Fragment detailsFragment = new UserDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putInt("id", currentItem.getId());
                detailsFragment.setArguments(bundle);

                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, detailsFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Log.e("UserAdapter", "FragmentManager is null!");
            }
        });
        return convertView;
    }
}
