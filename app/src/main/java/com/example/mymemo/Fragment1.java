package com.example.mymemo;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


public class Fragment1 extends Fragment {

    EditText editDiary;
    Button btnWrite;

    String fileName; //
    String str; //



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_1, container, false);

        DatePicker dp = v.findViewById(R.id.datePicker);
        editDiary = v.findViewById(R.id.editDiary);
        btnWrite = v.findViewById(R.id.btnWrite);

        int year = dp.getYear();
        int month = dp.getMonth();
        int day = dp.getDayOfMonth();

        //아무것도 선택하지 않아도 filename은 존재해야 함
        fileName = year + "_" + (month+1) + "_" + day + ".txt";

        dp.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                fileName = year + "_" + (month+1) + "_" + day + ".txt";
                Log.d("Today: ", fileName);

                str = readDiary(fileName);
                editDiary.setText(str);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //file open
                    FileOutputStream outFs = getContext().openFileOutput(fileName, Context.MODE_PRIVATE);

                    //wirte
                    str = editDiary.getText().toString();
                    outFs.write(str.getBytes());
                    outFs.close();
                    //close
                    Toast.makeText(getContext(), fileName+ " on save ", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


        return v;
    }

    private String readDiary(String fileName) {
        //file open, read and close
        try {
            FileInputStream inFs = getContext().openFileInput(fileName);

            byte[] txt = new byte[500];
            inFs.read(txt);
            inFs.close();
            //read byte, diary(txt) change to String(str) and return
            str = new String(txt);

        } catch (IOException e) {
            str = "" ;
            editDiary.setHint("No plan");
        }
        return  str;
    }
}