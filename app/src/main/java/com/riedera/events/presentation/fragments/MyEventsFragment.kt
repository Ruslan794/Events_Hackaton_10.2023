package com.riedera.events.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.riedera.events.R
import com.riedera.events.domain.models.Event
import com.riedera.events.presentation.adapters.EventListAdapter
import com.riedera.events.presentation.viewModels.MyEventsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyEventsFragment : Fragment(), EventListAdapter.OnEventClickListener {

    private val vm: MyEventsViewModel by viewModel()

    private var listener: MyEventsFragmentListener? = null

    private lateinit var nothingHereText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_my_events, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.checklist_fragment_recycler_view)
        nothingHereText = view.findViewById<TextView>(R.id.nothing_here_text)

        val adapter = vm.eventsAdapter
        adapter.setOnEventClickListener(this)
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this.context)

        changeEmptyListTextState(adapter.isEmpty())

        vm.eventsList.observe(viewLifecycleOwner) {
            adapter.updateData(it)
            changeEmptyListTextState(adapter.isEmpty())
        }


        return view
    }

    private fun changeEmptyListTextState(isEmpty: Boolean) {
        nothingHereText.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }

    interface MyEventsFragmentListener {
        fun onEventClick(event: Event)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MyEventsFragmentListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement MyFragmentListener")
        }
    }

    override fun onEventClick(event: Event) {
        listener?.onEventClick(event)
    }
}
