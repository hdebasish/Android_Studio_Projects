package com.example.timepicker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.date.DatePickerController;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    TextView textView;
    Button button;
    Calendar calendar;
    TimePickerDialog timePickerDialog;
    TimePickerDialog finalPicker;
    Boolean is24hour = true;

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
                int currentHour = calendar.get(Calendar.HOUR);
                int currentMinute = calendar.get(Calendar.MINUTE);
                int currentSecond = calendar.get(Calendar.SECOND);
                timePickerDialog = TimePickerDialog.newInstance(
                        MainActivity.this,
                        currentHour,
                        currentMinute,
                        currentSecond,
                        is24hour
                );

                timePickerDialog.setAccentColor(getResources().getColor(R.color.colorPrimary));
                timePickerDialog.setTitle("Time Picker");
                timePickerDialog.setThemeDark(false);
                timePickerDialog.dismissOnPause(false);
                timePickerDialog.setOkText("SET");
                timePickerDialog.setCancelText("DISMISS");
                timePickerDialog.enableSeconds(true);
                timePickerDialog.show(getSupportFragmentManager(),"timePicker");
                timePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        Toast.makeText(getApplicationContext(),"Time is not set!",Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });

            finalPicker = (TimePickerDialog) getSupportFragmentManager().findFragmentByTag("timePicker");

            if (finalPicker!=null){
                finalPicker.setOnTimeSetListener(this);
            }


    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

        String hr = 10>hourOfDay? "0"+hourOfDay:""+hourOfDay;
        String min = 10>minute? "0"+minute:""+minute;
        String sec = 10>second? "0"+second:""+second;

        String time = hr+":"+min+":"+sec;

        textView.setText(time);

    }
}