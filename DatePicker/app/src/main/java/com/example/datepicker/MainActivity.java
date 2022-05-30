package com.example.datepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TextView textView;
    Button button;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    DatePickerDialog finalPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.textView);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();

                int currentDay=calendar.get(Calendar.DAY_OF_MONTH);
                int currentMonth=calendar.get(Calendar.MONTH);
                int currentYear=calendar.get(Calendar.YEAR);

                datePickerDialog = DatePickerDialog.newInstance(
                        MainActivity.this,
                        currentYear,
                        currentMonth,
                        currentDay
                );

                datePickerDialog.showYearPickerFirst(true);

                datePickerDialog.show(getSupportFragmentManager(),"datePicker");

            }
        });
        finalPicker = (DatePickerDialog) getSupportFragmentManager().findFragmentByTag("datePicker");

        if (finalPicker!=null){
            finalPicker.setOnDateSetListener(this);
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        String day = 10>dayOfMonth? "0"+dayOfMonth:""+dayOfMonth;
        String mon = 10>monthOfYear? "0"+(monthOfYear+1):""+(monthOfYear+1);

        String date = day+"/"+mon+"/"+year;

        textView.setText(date);

    }
}