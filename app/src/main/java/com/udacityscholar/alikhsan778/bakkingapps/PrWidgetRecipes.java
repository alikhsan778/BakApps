package com.udacityscholar.alikhsan778.bakkingapps;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

/**
 * Alikhsan778
 */
public class PrWidgetRecipes extends AppWidgetProvider {


    public static final String EXTRA_INGREDIENT_TAG = "EXTRA_INGREDIENT";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        Intent intent = new Intent(context,SvWidgetRecipe.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        intent.setData(Uri.parse(intent.toUri(Intent.URI_INTENT_SCHEME)));


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.pr_widget_recipes);
        views.setRemoteAdapter(R.id.widget_stackView,intent);


        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

}

