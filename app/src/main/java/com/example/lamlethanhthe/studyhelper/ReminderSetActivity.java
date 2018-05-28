package com.example.lamlethanhthe.studyhelper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.lamlethanhthe.studyhelper.AlgoModules.BasicAlgos;
import com.example.lamlethanhthe.studyhelper.Global.Flags;
import com.example.lamlethanhthe.studyhelper.Global.TimeInterval;

import java.util.Calendar;

public class ReminderSetActivity extends AppCompatActivity {
    TextView txtTime, txtDate, txtRep;
    ImageView imgDate, imgTime;
    CheckBox chkRep;
    RadioButton rbDay, rbMonth;
    RadioGroup rgRep;

    Calendar c, at;
    TimeInterval rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder_set);

        attachViews();
        initComponents();
    }

    private void initComponents() {
        Bundle extras = getIntent().getExtras();
        c = (Calendar)extras.getSerializable(Flags.transCalendar);

        rep = TimeInterval.none;
        at = Calendar.getInstance();

        final DatePickerDialog datePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                at.set(year, month, dayOfMonth);
                txtDate.setText(BasicAlgos.DatetoString(getResources(), at));
            }
        }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH));
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);

        imgDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });

        final TimePickerDialog timePicker = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                at.set(Calendar.HOUR_OF_DAY, hourOfDay);
                at.set(Calendar.MINUTE, minute);
                txtTime.setText(hourOfDay + " : " + minute + "'");
            }
        }, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), true);

        imgTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show();
            }
        });

        chkRep.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    rgRep.setVisibility(View.VISIBLE);
                    rbDay.setChecked(true);
                } else {
                    rep = TimeInterval.none;
                    rgRep.setVisibility(View.INVISIBLE);
                }
            }
        });

        rbDay.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    rep = TimeInterval.day;
            }
        });
        
        rbMonth.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    rep = TimeInterval.week;
            }
        });

        chkRep.setChecked(false);
    }

    private void attachViews() {
        txtTime = (TextView)findViewById(R.id.txtRemTime);
        txtDate = (TextView)findViewById(R.id.txtRemDate);
        txtRep = (TextView)findViewById(R.id.txtRemRepeat);
        imgDate = (ImageView)findViewById(R.id.imgRemDate);
        imgTime = (ImageView)findViewById(R.id.imgRemTime);
        chkRep = (CheckBox)findViewById(R.id.chkRemRepeat);
        rbDay = (RadioButton)findViewById(R.id.rbRepDay);
        rbMonth = (RadioButton)findViewById(R.id.rbRepWeek);
        rgRep = (RadioGroup)findViewById(R.id.rgRep);
    }

    public void confirm(View view) {
        Intent resIntent = new Intent();
        resIntent.putExtra(Flags.transCalendar, at);
        resIntent.putExtra(Flags.transInterval, rep);
        setResult(RESULT_OK, resIntent);
        finish();
    }
}
