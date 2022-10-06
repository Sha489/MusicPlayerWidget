package com.app.widgetsapp

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class MusicWidget : AppWidgetProvider() {

    private val ACTION_PLAYPAUSE =  "com.app.widgetsapp.action.ACTION_WIDGET_CLICK"
    private val ACTION_NEXT =  "com.app.widgetsapp.action.ACTION_NEXT_CLICK"
    private val ACTION_PREVIOUS =  "com.app.widgetsapp.action.ACTION_PREVIOUS_CLICK"

    companion object {
        var isPlaying: Boolean = false
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent!!.action.equals(ACTION_PLAYPAUSE)) {
            playSong(context!!)
        } else if(intent.action.equals(ACTION_NEXT)) {
            playNextSong(context!!)
        } else if(intent.action.equals(ACTION_PREVIOUS)) {
            playPreviousSong(context!!)
        }
    }

    fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {

        val widgetText = context.getString(R.string.appwidget_text)
        // Construct the RemoteViews object
        val views = RemoteViews(context.packageName, R.layout.music_widget)
        views.setOnClickPendingIntent(R.id.play, getPendingSelfIntent(context, ACTION_PLAYPAUSE))
        views.setOnClickPendingIntent(R.id.nextBtn, getPendingSelfIntent(context, ACTION_NEXT))
        views.setOnClickPendingIntent(R.id.previousBtn, getPendingSelfIntent(context, ACTION_PREVIOUS))

        views.setImageViewResource(R.id.play, R.drawable.play)
        views.setImageViewResource(R.id.songImage, R.drawable.moana_poster)

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }

    private fun getPendingSelfIntent(context: Context, action: String): PendingIntent? {
        val intent = Intent(context, javaClass) // An intent directed at the current class (the "self").
        intent.action = action
        return PendingIntent.getBroadcast(context, 0, intent, 0)
    }

    private fun playSong(context: Context) {
        val views = RemoteViews(context.packageName, R.layout.music_widget)
        val localComponentName = ComponentName(context, MusicWidget::class.java)
        if(!isPlaying) {
            views.setImageViewResource(R.id.play, R.drawable.pause)
            isPlaying = true
            MainActivity.musicPlayer.start()
        } else {
            views.setImageViewResource(R.id.play, R.drawable.play)
            isPlaying = false
            MainActivity.musicPlayer.pause()
        }

        AppWidgetManager.getInstance(context).updateAppWidget(localComponentName, views)
    }

    private fun playNextSong(context: Context) {
        val views = RemoteViews(context.packageName, R.layout.music_widget)
        val localComponentName = ComponentName(context, MusicWidget::class.java)
        views.setImageViewResource(R.id.songImage, R.drawable.perfect_image)
        views.setTextViewText(R.id.title, "Perfect")
        views.setTextViewText(R.id.description, "Ed Sheeran")
        views.setImageViewResource(R.id.play, R.drawable.pause)
        AppWidgetManager.getInstance(context).updateAppWidget(localComponentName, views)

        MainActivity.musicPlayer.reset()
        MainActivity.musicPlayer = MediaPlayer.create(context, MainActivity.songList[1])
        MainActivity.musicPlayer.start()
    }

    private fun playPreviousSong(context: Context) {
        val views = RemoteViews(context.packageName, R.layout.music_widget)
        val localComponentName = ComponentName(context, MusicWidget::class.java)
        views.setImageViewResource(R.id.songImage, R.drawable.moana_poster)
        views.setTextViewText(R.id.title, "How far i'll go.")
        views.setTextViewText(R.id.description, "Auli'i Cravalho")
        views.setImageViewResource(R.id.play, R.drawable.pause)
        AppWidgetManager.getInstance(context).updateAppWidget(localComponentName, views)

        MainActivity.musicPlayer.reset()
        MainActivity.musicPlayer = MediaPlayer.create(context, MainActivity.songList[0])
        MainActivity.musicPlayer.start()
    }
}


