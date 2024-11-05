package com.example.apphotel.Login.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.apphotel.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ForgotPasswordBottomSheetFragment extends BottomSheetDialogFragment {
    private EditText editText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.forgot_password_fragment_login, container, false);
        Button button=view.findViewById(R.id.send_code_fragment_login);
            try {
                Bundle arg = getArguments();
                if (arg != null) {
                    String email = arg.getString("email");
                    editText=view.findViewById(R.id.frament_email_forgotPassword);
                    editText.setText(email);
                }
            }catch (Exception e){

            }
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CreatePasswordBottomSheetFragment createPasswordBottomSheetFragment=new CreatePasswordBottomSheetFragment();
                    createPasswordBottomSheetFragment.show(getFragmentManager(), createPasswordBottomSheetFragment.getTag());
                    dismiss();
                }
            });


        return view;
    }
}
