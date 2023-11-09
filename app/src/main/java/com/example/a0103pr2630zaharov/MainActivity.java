package com.example.a0103pr2630zaharov;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.a0103pr2630zaharov.data.Results;
import com.example.a0103pr2630zaharov.data_base.DataBaseManager;
import com.example.a0103pr2630zaharov.databinding.ActivityMainBinding;
import com.example.a0103pr2630zaharov.fragments.ResultsFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    Double res;
    String str;
    DataBaseManager dataBaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataBaseManager = new DataBaseManager(MainActivity.this);
        dataBaseManager.openDB();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new ResultsFragment()).commit();
        binding.buttonBackNum.setText("<-");
        binding.textView.setText("0");
        binding.buttonBackNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.textView.getText().toString().equals("")) {
                    binding.textView.setText(binding.textView.getText().toString().substring(0, binding.textView.getText().toString().length() - 1));
                }
            }
        });
        binding.buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView.setText("");
            }
        });
        binding.buttonEq.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                str = binding.textView.getText().toString();
                res = eval(str.replace(" ", ""));
                if (String.valueOf(res).split("\\.")[1].length() == 1 || String.valueOf(res).split("\\.")[1].equals("0")) {
                    binding.textViewRes.setText(String.valueOf(res).split("\\.")[0]);
                    Results result = new Results(str, String.valueOf(res).split("\\.")[0]);
                    dataBaseManager.addResult(result);
                } else {
                    binding.textViewRes.setText(res + "");
                    Results result = new Results(str, String.valueOf(res));
                    dataBaseManager.addResult(result);
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerView, new ResultsFragment()).commit();
            }
        });
        binding.buttonClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView.setText("");
                binding.textViewRes.setText("");
                res = 0.0;
                tempOp = 0;
            }
        });
        binding.buttonDot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.textView.append(binding.buttonDot.getText().toString());
            }
        });
        binding.buttonResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!toglResults) {
                    toglResults = true;
                    binding.fragmentContainerView.setVisibility(View.VISIBLE);
                } else {
                    toglResults = false;
                    binding.fragmentContainerView.setVisibility(View.INVISIBLE);
                }

            }
        });
    }

    boolean toglResults;

    public void onClickDigits(View view) {
        Button btn = (Button) view;
        if (!binding.textViewRes.getText().toString().equals("")) {
            binding.textView.setText("");
            binding.textViewRes.setText("");
            res = 0.0;
            tempOp = 0;
        }
        binding.textView.append(btn.getText().toString());
    }

    int tempOp = 0;

    @SuppressLint("SetTextI18n")
    public void onClickOperations(View view) {
        Button btn = (Button) view;
        if (!binding.textViewRes.getText().toString().equals("")) {
            if (tempOp == 0) {
                tempOp += 1;
                binding.textView.setText(binding.textViewRes.getText().toString() + " " + btn.getText().toString() + " ");
            }
        } else
            binding.textView.append(" " + btn.getText().toString() + " ");
    }

    //Дальше бога нет
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (; ; ) {
                    if (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (; ; ) {
                    if (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dataBaseManager.closeDB();
    }
}