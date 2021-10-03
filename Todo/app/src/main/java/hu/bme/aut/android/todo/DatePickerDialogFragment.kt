package hu.bme.aut.android.todo

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerDialogFragment : DialogFragment() {

    companion object {
        const val TAG = "DatePickerDialog"
    }

    private val calSelectedDate = Calendar.getInstance()

    private lateinit var listener: DateListener

    private val dateSetListener = DatePickerDialog.OnDateSetListener { datePicker, year, monthOfYear, dayOfMonth ->
        calSelectedDate.set(Calendar.YEAR, year)
        calSelectedDate.set(Calendar.MONTH, monthOfYear)
        calSelectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

        listener.onDateSelected(buildDateText())

        dismiss()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        calSelectedDate.time = Date(System.currentTimeMillis())
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return DatePickerDialog(
            requireContext(),
            dateSetListener,
            calSelectedDate.get(Calendar.YEAR),
            calSelectedDate.get(Calendar.MONTH),
            calSelectedDate.get(Calendar.DAY_OF_MONTH)
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        try {
            listener = if (targetFragment != null) {
                targetFragment as DateListener
            } else {
                activity as DateListener
            }
        } catch (e: ClassCastException) {
            throw RuntimeException(e)
        }
    }

    private fun buildDateText(): String {
        val dateString = StringBuilder()

        dateString.append(calSelectedDate.get(Calendar.YEAR))
        dateString.append(".")
        dateString.append((calSelectedDate.get(Calendar.MONTH) + 1).toString().padStart(2, '0'))
        dateString.append(".")
        dateString.append(calSelectedDate.get(Calendar.DAY_OF_MONTH).toString().padStart(2, '0'))
        dateString.append(".")

        return dateString.toString()
    }

    interface DateListener {
        fun onDateSelected(date: String)
    }

}
