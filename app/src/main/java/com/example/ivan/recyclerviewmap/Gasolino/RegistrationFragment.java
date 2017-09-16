package com.example.ivan.recyclerviewmap.Gasolino;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ivan.recyclerviewmap.MainActivity;
import com.example.ivan.recyclerviewmap.R;
import com.example.ivan.recyclerviewmap.test.IBaseView;


public class RegistrationFragment extends Fragment implements IBaseView {

    private EditText firstName;
    private EditText secondName;
    private EditText email;
    private EditText password;
    private EditText password2;
    private EditText age;
    private EditText fuel;
    private Button registration;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.registration_layout, container, false);
        firstName = (EditText) root.findViewById(R.id.first_name);
        secondName = (EditText) root.findViewById(R.id.second_name);
        email = (EditText) root.findViewById(R.id.email);
        password = (EditText) root.findViewById(R.id.password);
        password2 = (EditText) root.findViewById(R.id.confirm_password);
        age = (EditText) root.findViewById(R.id.age);
        fuel = (EditText) root.findViewById(R.id.prefered_fuel);
        registration = (Button) root.findViewById(R.id.register_btn);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPref.getInstance(getActivity()).put(SharedPref.Key.FIRST_NAME,firstName.getText().toString());
                SharedPref.getInstance(getActivity()).put(SharedPref.Key.SECOND_NAME,secondName.getText().toString());
                SharedPref.getInstance(getActivity()).put(SharedPref.Key.EMAIL,email.getText().toString());
                SharedPref.getInstance(getActivity()).put(SharedPref.Key.PASSWORD,password.getText().toString());
                SharedPref.getInstance(getActivity()).put(SharedPref.Key.AGE,age.getText().toString());
                SharedPref.getInstance(getActivity()).put(SharedPref.Key.FUEL,fuel.getText().toString());
                ((MainActivity)getActivity()).hideLoading();

            }
        });

        return root ;
    }




    @Override
    public boolean onBack() {
        return false;
    }
}
