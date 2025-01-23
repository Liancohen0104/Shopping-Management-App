package com.example.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.activities.MainActivity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegisterPage#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegisterPage extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    TextView mailTextView;
    TextView phoneTextView;
    TextView passwordTextView;
    TextView repeatPasswordTextView;

    public RegisterPage() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment2.
     */
    // TODO: Rename and change types and number of parameters
    public static RegisterPage newInstance(String param1, String param2) {
        RegisterPage fragment = new RegisterPage();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_page, container, false);

        mailTextView = view.findViewById(R.id.EmailText);
        phoneTextView = view.findViewById(R.id.PhoneText);
        passwordTextView = view.findViewById(R.id.PasswordText);
        repeatPasswordTextView = view.findViewById(R.id.RepeatPasswordText);

        Button button = view.findViewById(R.id.CreateAccountButton);
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                RegisterValidation(view);
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.reg();
                mainActivity.addData();
            }
        });
        return view;
    }

    public void RegisterValidation(View view)
    {
        String mailString = mailTextView.getText().toString();
        if (TextUtils.isEmpty(mailString) || !Patterns.EMAIL_ADDRESS.matcher(mailString).matches())
        {
            mailTextView.setError("Invalid Or Empty Mail");
        }

        String phoneString = phoneTextView.getText().toString();
        if (TextUtils.isEmpty(phoneString) || !phoneString.matches("\\d{10}"))
        {
            phoneTextView.setError("Invalid Or Empty Phone");
        }

        String passwordString = passwordTextView.getText().toString();
        if (TextUtils.isEmpty(passwordString) || passwordString.length() < 6)
        {
            passwordTextView.setError("Invalid Or Empty Password");
        }

        String repeatPasswordString = repeatPasswordTextView.getText().toString();
        if (TextUtils.isEmpty(repeatPasswordString) || !repeatPasswordString.equals(passwordString))
        {
            repeatPasswordTextView.setError("Password Don't Match Or Repeat Password Is Empty");
        }
    }

    /**
     * A simple {@link Fragment} subclass.
     * Use the {@link CartPage#newInstance} factory method to
     * create an instance of this fragment.
     */
    public static class CartPage extends Fragment {

        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";

        // TODO: Rename and change types of parameters
        private String mParam1;
        private String mParam2;

        public CartPage() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartPage.
         */
        // TODO: Rename and change types and number of parameters
        public static CartPage newInstance(String param1, String param2) {
            CartPage fragment = new CartPage();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.cart_page, container, false);
        }
    }
}