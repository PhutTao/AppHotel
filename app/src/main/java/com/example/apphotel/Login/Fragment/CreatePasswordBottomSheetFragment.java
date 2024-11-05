package com.example.apphotel.Login.Fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.apphotel.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class CreatePasswordBottomSheetFragment extends BottomSheetDialogFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_password_fragment_login, container, false);
        Button button=view.findViewById(R.id.change_password);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        ImageView  passwordImageView = view.findViewById(R.id.signup_password_icon);
        EditText passwordText=view.findViewById(R.id.signup_password_text);
        passwordImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable currentDrawable = passwordImageView.getDrawable();
                if (currentDrawable != null && !currentDrawable.getConstantState().equals(getResources().getDrawable(R.drawable.signup_password_hide).getConstantState())) {
                    passwordText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordImageView.setImageResource(R.drawable.signup_password_hide);
                } else {
                    passwordText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordImageView.setImageResource(R.drawable.signup_visible_icon);
                }

            }
        });

        ImageView  passwordImageView2 = view.findViewById(R.id.signup_confirm_password_icon);
        EditText passwordText2=view.findViewById(R.id.signup_confirm_password_text);
        passwordImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable currentDrawable = passwordImageView2.getDrawable();
                if (currentDrawable != null && !currentDrawable.getConstantState().equals(getResources().getDrawable(R.drawable.signup_password_hide).getConstantState())) {
                    passwordText2.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    passwordImageView2.setImageResource(R.drawable.signup_password_hide);
                } else {
                    passwordText2.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    passwordImageView2.setImageResource(R.drawable.signup_visible_icon);
                }

            }
        });
        return view;
    }
}
