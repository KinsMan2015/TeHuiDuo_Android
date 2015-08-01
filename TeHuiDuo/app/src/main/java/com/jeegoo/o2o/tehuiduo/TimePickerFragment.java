package com.jeegoo.o2o.tehuiduo;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.TimePicker;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by xintong on 2015/7/20.
 */
public class TimePickerFragment extends DialogFragment {
    public static final String EXTRA_TIME = "com.jeegoo.tehuiduo.time";
    private Date mDate;

    public static TimePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TIME, date);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    private void sendResult(int resultCode) {
        if (getTargetFragment() == null) {
            return;
        }
        Intent i = new Intent();
        i.putExtra(EXTRA_TIME, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        mDate = (Date) getArguments().getSerializable(EXTRA_TIME);
        View v = getActivity().getLayoutInflater().inflate(R.layout.dialog_time, null);
        final TimePicker timePicker = (TimePicker) v.findViewById(R.id.dialog_time_picker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                mDate = new GregorianCalendar(0,0,0,hourOfDay,minute).getTime();
                getArguments().putSerializable(EXTRA_TIME, mDate);
            }
        });
        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.time_picker_title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendResult(Activity.RESULT_OK);
            }
        }).create();
    }
}
