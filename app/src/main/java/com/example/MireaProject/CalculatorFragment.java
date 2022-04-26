package com.example.MireaProject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalculatorFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalculatorFragment extends Fragment {

    Button buttonPlus;
    Button buttonMinus;
    Button buttonMultiply;
    Button buttonShare;
    EditText textNumber1;
    EditText textNumber2;
    TextView answer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalculatorFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalculatorFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalculatorFragment newInstance(String param1, String param2) {
        CalculatorFragment fragment = new CalculatorFragment();
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
        View inflatedView = inflater.inflate(R.layout.fragment_calculator, container, false);

        textNumber1 = (EditText) inflatedView.findViewById(R.id.editTextFirstNumber);
        textNumber2 = (EditText) inflatedView.findViewById(R.id.editTextSecondNumber);
        answer = (TextView) inflatedView.findViewById(R.id.textViewAnswer);


        buttonPlus = (Button) inflatedView.findViewById(R.id.buttonPlus);
        buttonPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCalculation("+");
            }
        });

        buttonMinus = (Button) inflatedView.findViewById(R.id.buttonMinus);
        buttonMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCalculation("-");
            }
        });

        buttonMultiply = (Button) inflatedView.findViewById(R.id.buttonMultiply);
        buttonMultiply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCalculation("*");
            }
        });

        buttonShare = (Button) inflatedView.findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doCalculation("/");
            }
        });

        return inflatedView;
    }

    public void doCalculation(String action) {

        float number1 = Integer.parseInt(textNumber1.getText().toString());
        float number2 = Integer.parseInt(textNumber2.getText().toString());
        switch (action) {
            case "+":
                answer.setText(String.valueOf(number1+number2));
                break;
            case "-":
                answer.setText(String.valueOf(number1-number2));
                break;
            case "*":
                answer.setText(String.valueOf(number1*number2));
                break;
            case "/":
                answer.setText(String.valueOf(number1/number2));
                break;
            default:
                break;
        }
    }
}