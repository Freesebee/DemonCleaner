package com.example.demoncleaner.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Date;

public abstract class DayChangedBroadcastReceiver extends BroadcastReceiver {

    private Date date = new Date();

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        Date currentDate = new Date();

        if ((action.equals(Intent.ACTION_TIME_CHANGED)
                || action.equals(Intent.ACTION_TIMEZONE_CHANGED)
                || action.equals(Intent.ACTION_DATE_CHANGED))
            && !isSameDay(currentDate)) {

            date = currentDate;
            onDayChanged();
        }
    }

    protected abstract void onDayChanged();

    private boolean isSameDay(Date currentDate) {
        return currentDate == date;
    }
}