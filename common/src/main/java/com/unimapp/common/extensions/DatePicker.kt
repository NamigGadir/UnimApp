package com.unimapp.common.extensions

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

fun Context.showDatePicker(formatter: String = "dd/MM/yy", onDateSelected: (String) -> Unit) {
    val myCalendar: Calendar = Calendar.getInstance()
    val listener = OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
        myCalendar.set(Calendar.YEAR, year)
        myCalendar.set(Calendar.MONTH, monthOfYear)
        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        val sdf = SimpleDateFormat(formatter, Locale.US)
        onDateSelected(sdf.format(myCalendar.time))
    }
    DatePickerDialog(
        this, listener, myCalendar
            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
        myCalendar.get(Calendar.DAY_OF_MONTH)
    ).show()
}