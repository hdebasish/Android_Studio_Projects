package com.bawp.virustracker;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.Data;
import androidx.work.WorkInfo;

import com.bawp.virustracker.workers.VirusViewModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.bawp.virustracker.Constants.DATA_OUTPUT;

/**
 * Implementation of App Widget functionality.
 */
public class VirusWidget extends AppWidgetProvider {

    private static VirusViewModel virusViewModel;
    private static String newCases;
    private static String newDeath;
    private static String recoveredNum;
    private static Observer<List<WorkInfo>> widgetObserver;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        virusViewModel =
                new ViewModelProvider.AndroidViewModelFactory(
                        (Application) context.getApplicationContext())
                        .create(VirusViewModel.class);

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        widgetObserver = new Observer<List<WorkInfo>>() {
            @Override
            public void onChanged(List<WorkInfo> workInfos) {
                if (workInfos == null || workInfos.isEmpty()) {
                    return;
                }
                WorkInfo workInfo = workInfos.get(0);
                boolean finished = workInfo.getState().isFinished();
                if (!finished) {
                    //show progress
                }else {
                    Data outputData = workInfo.getOutputData();
                    String outputDataString = outputData.getString(DATA_OUTPUT);
                    if (!TextUtils.isEmpty(outputDataString) ){
                        virusViewModel.setOutputData(outputDataString);
                    }
                    setUI(context);
                }
            }
        };

        virusViewModel.getOutputWorkInfo().observeForever(widgetObserver);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.virus_widget);
        views.setTextViewText(R.id.widget_title, widgetText);
        views.setTextViewText(R.id.new_cases, newCases);
        views.setTextViewText(R.id.new_deaths, newDeath);
        views.setTextViewText(R.id.total_recovered, recoveredNum);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    private static void setUI(Context context) {

        try {
            String outputData = virusViewModel.getOutputData();
            JSONObject jsonObject = new JSONObject(outputData);
            Log.i("jsonWidget", "populateUI: " + jsonObject.getString("cases"));
            newCases=String.format(context.getString(R.string.todayCases), jsonObject.getLong("todayCases"));
            newDeath=String.format(context.getString(R.string.todayDeaths), jsonObject.getLong("todayDeaths"));
            recoveredNum=String.format(context.getString(R.string.recovered), jsonObject.getLong("recovered"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        virusViewModel.getOutputWorkInfo().removeObserver(widgetObserver);
    }
}