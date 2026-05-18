package com.osamu.existentialjournal.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class ReminderManager(private val context: Context) {

    private val prefs = context.getSharedPreferences("reminders_prefs", Context.MODE_PRIVATE)

    fun scheduleReminder(hour: Int, minute: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (before(Calendar.getInstance())) {
                add(Calendar.DAY_OF_MONTH, 1)
            }
        }

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        saveSettings(true, hour, minute)
    }

    fun cancelReminder() {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        saveSettings(false, 0, 0)
    }

    private fun saveSettings(enabled: Boolean, hour: Int, minute: Int) {
        prefs.edit().apply {
            putBoolean("enabled", enabled)
            putInt("hour", hour)
            putInt("minute", minute)
            apply()
        }
    }

    fun isEnabled(): Boolean = prefs.getBoolean("enabled", false)
    fun getHour(): Int = prefs.getInt("hour", 20)
    fun getMinute(): Int = prefs.getInt("minute", 0)
}
