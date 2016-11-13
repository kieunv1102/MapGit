package com.kieunv.mapgit;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePickerActivity extends AppCompatActivity implements View.OnClickListener {
    TextView fromDate, toDate;
    SimpleDateFormat dateFormatter;
    int myear, mmonth, mday;
    Calendar newCalendar;
    boolean checkfromdate = false;
    boolean checktodate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);
        fromDate = (TextView) findViewById(R.id.fromDate);
        toDate = (TextView) findViewById(R.id.toDate);
        fromDate.setOnClickListener(this);
        toDate.setOnClickListener(this);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        newCalendar = Calendar.getInstance();
        myear = newCalendar.get(Calendar.YEAR);
        mmonth = newCalendar.get(Calendar.MONTH);
        mday = newCalendar.get(Calendar.DAY_OF_MONTH);

//        newCalendar.add(Calendar.DATE, 3);
//        newCalendar.add(Calendar.DATE, -6);

    }

    private void DPDialog(final View textcccc) {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                myear = year;
                mmonth = monthOfYear;
                mday = dayOfMonth;
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                ((TextView) textcccc).setText(dateFormatter.format(newDate.getTime()));
            }

        }, myear, mmonth, mday);

//        dialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
//        dialog.getDatePicker().setMinDate(newCalendar.getTimeInMillis());
        dialog.show();
    }

    public void ddd(final View text) {
        class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
//                final Calendar calendar = Calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH);
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, myear, mmonth, mday);

                newCalendar.add(Calendar.DATE, 1);
                dpd.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());
                newCalendar.add(Calendar.DATE, -2);
                dpd.getDatePicker().setMinDate(newCalendar.getTimeInMillis());


                return dpd;
            }

            public void onDateSet(DatePicker view, int year, int month, int day) {
//            TextView tv = (TextView) getActivity().findViewById(R.id.tv);
                myear = year;
                mmonth = month;
                mday = day;
                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(0);
                cal.set(year, month, day, 0, 0, 0);
                Date chosenDate = cal.getTime();

                DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US);
                String formattedDate = df.format(chosenDate);

                ((TextView) text).setText(formattedDate);
            }
        }
        DialogFragment dFragment = new DatePickerFragment();
        dFragment.show(getFragmentManager(), "Date Picker");
    }

    public void ddd2(final View text) {
        class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                DatePickerDialog dpd = new DatePickerDialog(getActivity(), AlertDialog.THEME_HOLO_LIGHT, this, myear, mmonth, mday);
//                if (checkfromdate) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.DATE, 3);
                dpd.getDatePicker().setMaxDate(cal.getTimeInMillis());
                cal.add(Calendar.DATE, -3);
                dpd.getDatePicker().setMinDate(cal.getTimeInMillis());
//                }
                return dpd;
            }

            public void onDateSet(DatePicker view, int year, int month, int day) {

                myear = year;
                mmonth = month;
                mday = day;
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, day);
                ((TextView) text).setText(dateFormatter.format(newDate.getTime()));
                checkfromdate = true;
            }
        }
        DialogFragment dFragment = new DatePickerFragment();
        dFragment.show(getFragmentManager(), "Date Picker");
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fromDate:
                ddd(fromDate);
//                checkfromdate = true;
//                checktodate=false;
//                DPDialog(fromDate);
                break;
            case R.id.toDate:
                ddd(toDate);
//                checktodate = true;
//                checkfromdate=false;
//                DPDialog(toDate);
                break;
            default:
                break;
        }
    }
}
