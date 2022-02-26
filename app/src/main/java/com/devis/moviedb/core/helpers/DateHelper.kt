package com.devis.moviedb.core.helpers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by devisevianus on 25/02/22
 */

object DateHelper {
    const val FORMAT_YYYY_MM_DD = "yyyy-mm-dd"
    const val FORMAT_DD_MMM_YYYY = "dd MMM yyyy"

    @SuppressLint("SimpleDateFormat")
    fun String.changeFormatDate(oldFormat: String, newFormat: String): String? {
        val df = SimpleDateFormat(oldFormat)
        val date = df.parse(this)
        df.timeZone = TimeZone.getDefault()
        df.applyPattern(newFormat)
        return try {
            df.format(date!!)
        } catch (e: NullPointerException) {
            e.printStackTrace()
            null
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}