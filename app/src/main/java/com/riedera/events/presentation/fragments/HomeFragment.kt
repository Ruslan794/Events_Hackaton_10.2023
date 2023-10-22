package com.riedera.events.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabItem
import com.google.android.material.tabs.TabLayout
import com.riedera.events.R
import com.riedera.events.domain.models.Club
import com.riedera.events.domain.models.Event
import com.riedera.events.presentation.adapters.ClubListAdapter
import com.riedera.events.presentation.adapters.EventListAdapter
import com.riedera.events.presentation.viewModels.HomeViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment(), EventListAdapter.OnEventClickListener,
    ClubListAdapter.OnClubClickListener {

    private val vm: HomeViewModel by viewModel()

    private var listener: HomeFragmentListener? = null

    private lateinit var nothingHereText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        nothingHereText = view.findViewById<TextView>(R.id.nothing_here_text)
        val rv = view.findViewById<RecyclerView>(R.id.home_fragment_recycler_view)

        val tabLayout = view.findViewById<TabLayout>(R.id.tab_layout)
        val eventsTab = view.findViewById<TabItem>(R.id.events_tab)
        val clubsTab = view.findViewById<TabItem>(R.id.clubs_tab)
        val search = view.findViewById<SearchView>(R.id.search_view)

        val eventsAdapter = vm.eventsAdapter
        val clubsAdapter = vm.clubsAdapter
        clubsAdapter.setOnClubClickListener(this)
        eventsAdapter.setOnEventClickListener(this)
        rv.adapter = eventsAdapter
        rv.layoutManager = LinearLayoutManager(this.context)

        changeEmptyListTextState(eventsAdapter.isEmpty())

        vm.eventsList.observe(viewLifecycleOwner) {
            eventsAdapter.updateData(it)
            changeEmptyListTextState(eventsAdapter.isEmpty())
        }

        vm.clubsList.observe(viewLifecycleOwner) {
            clubsAdapter.updateData(it)
            changeEmptyListTextState(eventsAdapter.isEmpty())
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> {
                        rv.adapter = eventsAdapter
                    }

                    1 -> {
                        rv.adapter = clubsAdapter
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })


        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (rv.adapter is EventListAdapter) {
                    val filteredList =
                        vm.eventsList.value?.filter { it.name.lowercase().contains(newText?.trim()?.lowercase().toString()) }
                    if (filteredList != null) {
                        eventsAdapter.updateData(filteredList)
                    }
                    rv.adapter = eventsAdapter
                }

                if (rv.adapter is ClubListAdapter) {
                    val filteredList =
                        vm.clubsList.value?.filter { it.name.lowercase().contains(newText?.trim()?.lowercase().toString()) }
                    if (filteredList != null) {
                        clubsAdapter.updateData(filteredList)
                    }
                    rv.adapter = clubsAdapter
                }

                return false
            }
        })


        return view
    }


    private fun changeEmptyListTextState(isEmpty: Boolean) {
        nothingHereText.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is HomeFragmentListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement MyFragmentListener")
        }
    }


    interface HomeFragmentListener {
        fun onEventClick(event: Event)
        fun onClubClick(club: Club)
    }

    override fun onEventClick(event: Event) {
        listener?.onEventClick(event)
    }

    override fun onClubClick(club: Club) {
        listener?.onClubClick(club)
    }

}


