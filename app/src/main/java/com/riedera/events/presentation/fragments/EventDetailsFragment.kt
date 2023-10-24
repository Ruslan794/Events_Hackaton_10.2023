package com.riedera.events.presentation.fragments

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.riedera.events.R
import com.riedera.events.domain.MyDateTimeFormatters
import com.riedera.events.presentation.viewModels.EventDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.w3c.dom.Text

class EventDetailsFragment : Fragment() {


    private val vm: EventDetailsViewModel by viewModel()


    private var infoItemId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            infoItemId = requireArguments().getString(EVENT_ID)?.toLong() ?: -1
        }
        vm.getEvent(infoItemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_event_details, container, false)

        val image = view.findViewById<ImageView>(R.id.poster)
        val name = view.findViewById<TextView>(R.id.name)
        val date = view.findViewById<TextView>(R.id.date)
        val time = view.findViewById<TextView>(R.id.time)
        val location = view.findViewById<TextView>(R.id.location)
        val description = view.findViewById<TextView>(R.id.description)
        val btn = view.findViewById<Button>(R.id.sub_btn)

        vm.event.observe(viewLifecycleOwner){
            image.load(it.image)
            name.text = it.name
            location.text = "Location: ${it.location}"
            date.text = "Date: ${it.dateAndTime?.format(MyDateTimeFormatters.dateShort)}"
            time.text = "Time: ${it.dateAndTime?.format(MyDateTimeFormatters.time)}"
            description.text = it.description
        }

        btn.setOnClickListener {
            btn.text = "Unsubscribe"
            btn.isEnabled = false
            btn.setBackgroundResource(R.drawable.subscribe_button_shape)
            savedEventId = vm.event.value?.id
        }


        return view
    }

    companion object{
        const val EVENT_ID = "event_id"
        var savedEventId : Long? = null
    }

}