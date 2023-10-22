package com.riedera.events.presentation.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputLayout
import com.riedera.events.R
import com.riedera.events.domain.MyDateTimeFormatters
import com.riedera.events.presentation.viewModels.AddEventViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.Calendar

class AddEventFragment : Fragment() {

    private val vm: AddEventViewModel by viewModel()

    private var dueDate: LocalDate = LocalDate.now()
    private var dueTime: LocalTime = LocalTime.of(0, 0)
    private var deadlineDate: LocalDate? = null
    private var deadlineTime: LocalTime = LocalTime.of(0, 0)


    interface OnEventSavedListener {
        fun onEventSaved(result: Boolean)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_event, container, false)


        val nameTextInput = view.findViewById<TextInputLayout>(R.id.name_text_input)
        val nameEditText = nameTextInput.editText
        val descriptionTextInput = view.findViewById<TextInputLayout>(R.id.description_text_input)
        val descriptionEditText = descriptionTextInput.editText

        val dueDatetimePickerLayout = view.findViewById<LinearLayout>(R.id.datetime_picker_layout)

        val dueDateEditText = view.findViewById<EditText>(R.id.date_edit_text)
        val dueTimeEditText = view.findViewById<EditText>(R.id.time_edit_text)


        val dueDateTextInput = view.findViewById<TextInputLayout>(R.id.date_text_input)

        val textInputLayouts = listOf(nameTextInput, descriptionTextInput, dueDateTextInput)



        dueDateEditText.setOnClickListener {
            dueDateTextInput.isErrorEnabled = false
            showDatePicker { selectedDate ->
                dueDate = selectedDate
                dueDateEditText.setText(selectedDate.format(MyDateTimeFormatters.dateShort))
            }
        }

        dueTimeEditText.setOnClickListener {
            showTimePicker { selectedTime ->
                dueTime = selectedTime
                dueTimeEditText.setText(selectedTime.format(MyDateTimeFormatters.time))
            }
        }



        vm.savingTriggered.observe(viewLifecycleOwner) { isSavingTriggered ->
            if (isSavingTriggered) {

                var name = nameEditText?.text?.trim().toString()
                val description = descriptionEditText?.text?.trim().toString()

                val nameIsEmpty = name.isEmpty()
                val descriptionIsEmpty = description.isEmpty()
                val dueDateIsEmpty = dueDateEditText.text.isNullOrEmpty()
                val dueTimeIsEmpty =    dueTimeEditText.text.isNullOrEmpty()

                val isValid = !nameIsEmpty && !descriptionIsEmpty && !dueDateIsEmpty


                if (isValid) {
                    vm.saveEvent(
                        name,
                        LocalDateTime.of(dueDate, dueTime),
                        description,)
                }else {
                    val errorMessage = "This field cannot be empty!"

                    if (nameIsEmpty) {
                        nameTextInput.error = errorMessage
                        nameTextInput.isErrorEnabled = true
                    }
                    if (descriptionIsEmpty) {
                        descriptionTextInput.error = errorMessage
                        descriptionTextInput.isErrorEnabled = true
                    }

                    if (dueDateIsEmpty) {
                        dueDateTextInput.error = errorMessage
                        dueDateTextInput.isErrorEnabled = true
                    }
                    if (dueTimeIsEmpty) {
                        dueDateTextInput.error = errorMessage
                        dueDateTextInput.isErrorEnabled = true
                    }
                }



                descriptionEditText?.setOnFocusChangeListener { v, hasFocus ->
                    descriptionTextInput.isErrorEnabled = false
                }


            }
        }
        return view
    }

    private fun showDatePicker(onDateSelected: (LocalDate) -> Unit) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDay ->
                val selectedDateTime = LocalDate.of(selectedYear, selectedMonth + 1, selectedDay)
                onDateSelected(selectedDateTime)
            },
            year,
            month,
            day
        )

        datePickerDialog.show()
    }

    private fun showTimePicker(
        onTimeSelected: (LocalTime) -> Unit
    ) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                val selectedDateTime = LocalTime.of(selectedHour, selectedMinute)
                onTimeSelected(selectedDateTime)
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    private fun clearErrorWarnings(textInputLayouts: List<TextInputLayout>) {
        textInputLayouts.forEach {
            it.isErrorEnabled = false
        }
    }

    fun triggerInfoItemSaving() {
        vm.triggerSaving()
        vm.savedSuccessfully.observe(viewLifecycleOwner) { result ->
            (activity as? OnEventSavedListener)?.onEventSaved(result)
        }
    }
}