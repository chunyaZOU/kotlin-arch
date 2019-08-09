package com.cy.kotlinarch.utils

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import android.widget.Toast
import com.cy.kotlinarch.App

class AppUtil {

    companion object {
        fun isNoUseOption(): Boolean {

            val pkgManager = App.sCtx.packageManager
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            val resolveInfoList = pkgManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            return resolveInfoList.size > 0
        }


        fun isUseAppOpen(): Boolean {
            val ct = System.currentTimeMillis()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val usageStatsManager = App.sCtx.getSystemService(Context.USAGE_STATS_SERVICE) as? UsageStatsManager

                val usageStatsList = usageStatsManager?.queryUsageStats(UsageStatsManager.INTERVAL_BEST, ct, 0)
                return !(usageStatsList == null || usageStatsList.isEmpty())
            }
            Toast.makeText(App.sCtx, "SDK_INT < 21", Toast.LENGTH_LONG).show()
            return false
        }
    }
}