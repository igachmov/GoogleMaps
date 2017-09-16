package com.example.ivan.recyclerviewmap.Gasolino;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ivan.recyclerviewmap.R;
import com.example.ivan.recyclerviewmap.test.IBaseView;
import com.example.ivan.recyclerviewmap.test.TestView;

/**
 * Created by Ivan on 9/13/2017.
 */

public class LoginFragment  extends Fragment implements IBaseView {

    private EditText loginEmail;
    private EditText loginPassword;
    private Button loginBtn;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.login_layout, container, false);
        loginEmail = (EditText) root.findViewById(R.id.login_email);
        loginPassword = (EditText) root.findViewById(R.id.login_password);
        loginBtn = (Button) root.findViewById(R.id.login_button);

        loginEmail.setText( SharedPref.getInstance(getContext()).getString(SharedPref.Key.EMAIL));
        loginPassword.setText( SharedPref.getInstance(getContext()).getString(SharedPref.Key.PASSWORD));

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TestView nextFrag= new TestView();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, nextFrag,"findThisFragment")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return root;
    }



    @Override
    public boolean onBack() {
        return false;
    }
}
