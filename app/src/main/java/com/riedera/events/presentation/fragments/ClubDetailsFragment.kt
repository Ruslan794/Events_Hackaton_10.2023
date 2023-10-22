package com.riedera.events.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.riedera.events.R
import com.riedera.events.domain.models.Event
import com.riedera.events.presentation.adapters.EventListAdapter
import com.riedera.events.presentation.viewModels.ClubDetailsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ClubDetailsFragment : Fragment(), EventListAdapter.OnEventClickListener {


    private val vm: ClubDetailsViewModel by viewModel()

    private var infoItemId: Long = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            infoItemId = requireArguments().getString(CLUB_ID)?.toLong() ?: -1
        }
        vm.getClub(infoItemId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_club_details, container, false)

        val name = view.findViewById<TextView>(R.id.name)
        val poster = view.findViewById<ImageView>(R.id.poster)
        val description = view.findViewById<TextView>(R.id.description)
        val subscribeBtn = view.findViewById<Button>(R.id.subscribe_btn)
        val rv = view.findViewById<RecyclerView>(R.id.club_details_rv)

        val eventsAdapter = vm.eventsAdapter
        eventsAdapter.setOnEventClickListener(this)
        rv.adapter = eventsAdapter
        rv.layoutManager = LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)


        vm.club.observe(viewLifecycleOwner) {
            name.text = it.name
            poster.load(it.image)
            description.text = it.description
        }

        vm.eventsList.observe(viewLifecycleOwner) {
            eventsAdapter.updateData(it)
        }

        subscribeBtn.setOnClickListener {
            subscribeBtn.text = "Unsubscribe"
            subscribeBtn.isEnabled = false
            subscribeBtn.setBackgroundResource(R.drawable.subscribe_button_shape)
            savedClubId = vm.club.value?.id
        }

        return view
    }

    companion object {
        const val CLUB_ID = "club_id"
        var savedClubId: Long? = null
    }

    private var listener: ClubDetailsFragmentListener? = null

    interface ClubDetailsFragmentListener {
        fun onEventClick(event: Event)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is ClubDetailsFragmentListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement MyFragmentListener")
        }
    }


    override fun onEventClick(event: Event) {
        listener?.onEventClick(event)
    }

}