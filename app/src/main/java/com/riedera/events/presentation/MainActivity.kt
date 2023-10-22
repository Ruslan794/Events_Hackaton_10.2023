package com.riedera.events.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.riedera.events.R
import com.riedera.events.domain.models.Club
import com.riedera.events.domain.models.Event
import com.riedera.events.presentation.fragments.AccountFragment
import com.riedera.events.presentation.fragments.AddEventFragment
import com.riedera.events.presentation.fragments.ClubDetailsFragment
import com.riedera.events.presentation.fragments.EventDetailsFragment
import com.riedera.events.presentation.fragments.HomeFragment
import com.riedera.events.presentation.fragments.MyEventsFragment
import org.koin.android.ext.android.get

class MainActivity : AppCompatActivity(), HomeFragment.HomeFragmentListener,
    MyEventsFragment.MyEventsFragmentListener, AccountFragment.AccountFragmentListener,
    AddEventFragment.OnEventSavedListener, ClubDetailsFragment.ClubDetailsFragmentListener {

    private lateinit var addEventFragment: AddEventFragment
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var fab: FloatingActionButton
    private var isOnAddInfoItemScreen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val statusBarColor = ContextCompat.getColor(this, R.color.neutral_light)
        window.statusBarColor = statusBarColor

        fab = findViewById<FloatingActionButton>(R.id.fab)
        bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            if (bottomNavigationView.selectedItemId == menuItem.itemId) {
                return@setOnItemSelectedListener true
            }
            when (menuItem.itemId) {
                R.id.navigation_home -> showFragment(get<HomeFragment>())
                R.id.navigation_my_events -> showFragment(get<MyEventsFragment>())
                R.id.navigation_account -> showFragment(get<AccountFragment>())
            }
            if (isOnAddInfoItemScreen) {
                isOnAddInfoItemScreen = false
                fab.setImageResource(R.drawable.baseline_add_24)
            }
            true
        }
        if (savedInstanceState == null) {
            showFragment(get<HomeFragment>())
        }

        fab.setOnClickListener {
            if (!isOnAddInfoItemScreen) {
                addEventFragment = get<AddEventFragment>()
                showFragment(addEventFragment)
                bottomNavigationView.selectedItemId = R.id.navigation_placeholder
                fab.setImageResource(R.drawable.baseline_check_24)
                isOnAddInfoItemScreen = true
            } else {
                addEventFragment.triggerInfoItemSaving()
            }
        }

    }

    private fun showFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()

        transaction.replace(
            R.id.fragment_container, fragment
        ).commit()
    }

    override fun onEventClick(event: Event) {
        val bundle = Bundle()
        bundle.putString(EventDetailsFragment.EVENT_ID, event.id.toString())

        val eventDetailsFragment = get<EventDetailsFragment>()
        eventDetailsFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, eventDetailsFragment).commit()


        bottomNavigationView.selectedItemId = R.id.navigation_placeholder

    }

    override fun onClubClick(club: Club) {
        val bundle = Bundle()
        bundle.putString(ClubDetailsFragment.CLUB_ID, club.id.toString())

        val clubDetailsFragment = get<ClubDetailsFragment>()
        clubDetailsFragment.arguments = bundle

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, clubDetailsFragment).commit()


        bottomNavigationView.selectedItemId = R.id.navigation_placeholder
    }

    override fun onEventSaved(result: Boolean) {
        showFragment(get<HomeFragment>())
        bottomNavigationView.selectedItemId = R.id.navigation_home
        fab.setImageResource(R.drawable.baseline_add_24)
        isOnAddInfoItemScreen = false
        if (result) {
            Toast.makeText(applicationContext, "Saved successfully!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Something went wrong!", Toast.LENGTH_SHORT).show()
        }
    }

}
