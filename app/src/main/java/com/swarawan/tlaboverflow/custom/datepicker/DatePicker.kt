package com.swarawan.tlaboverflow.custom.datepicker

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.widget.DatePicker
import java.util.Calendar

/**
 * Created by rioswarawan on 4/12/18.
 */
class DatePicker : DialogFragment(), DatePickerDialog.OnDateSetListener {

    companion object {
        fun newInstance() = DatePicker()
    }

    lateinit var listener: OnDatePickListener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val dialog = DatePickerDialog(activity, this@DatePicker, year, month, day)
        dialog.datePicker.maxDate = calendar.timeInMillis

        return dialog
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        val formattedDate = "$dayOfMonth / ${(month + 1)} / $year"
        val selectedCalendar = Calendar.getInstance().apply {
            set(year, month, dayOfMonth)
        }

        val timeInMilis = (selectedCalendar.timeInMillis / 1000).toString()
        listener.onDatePicked(formattedDate, timeInMilis)
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        try {
            manager?.let {
                val transaction = it.beginTransaction()
                transaction.add(this@DatePicker, tag)
                transaction.commit()
            }
        } catch (ex: IllegalStateException) {
        }
    }

    interface OnDatePickListener {
        fun onDatePicked(dateString: String, timeInMilis: String)
    }
}