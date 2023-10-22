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
import com.riedera.events.domain.models.Club
import com.riedera.events.presentation.adapters.ClubListAdapter
import com.riedera.events.presentation.viewModels.AccountViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class AccountFragment : Fragment(), ClubListAdapter.OnClubClickListener {

    private val vm: AccountViewModel by viewModel()

    private var listener: AccountFragmentListener? = null

    private lateinit var nothingHereText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_account, container, false)

        val rv = view.findViewById<RecyclerView>(R.id.account_fragment_recycler_view)
        nothingHereText = view.findViewById<TextView>(R.id.nothing_here_text)
        val name = view.findViewById<TextView>(R.id.name)

        val adapter = vm.clubsAdapter
        adapter.setOnClubClickListener(this)

        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(this.context)

        changeEmptyListTextState(adapter.isEmpty(), nothingHereText)


        vm.clubsList.observe(viewLifecycleOwner){
            adapter.updateData(it)
            changeEmptyListTextState(adapter.isEmpty(), nothingHereText)
        }

        return view
    }


    private fun changeEmptyListTextState(isEmpty: Boolean, textview: TextView) {
        if (isEmpty) {
            textview.visibility = View.VISIBLE
        } else {
            textview.visibility = View.GONE
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AccountFragmentListener) {
            listener = context
        } else {
            throw ClassCastException("$context must implement MyFragmentListener")
        }
    }


    interface AccountFragmentListener {
        fun onClubClick(club: Club)
    }

    override fun onClubClick(club: Club) {
        listener?.onClubClick(club)
    }
}
