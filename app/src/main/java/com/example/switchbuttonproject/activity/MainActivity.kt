package com.example.switchbuttonproject.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.switchbuttonproject.R
import com.example.switchbuttonproject.databinding.ActivityMainBinding
import com.example.switchbuttonproject.fragment.FragmentFour
import com.example.switchbuttonproject.fragment.FragmentOne
import com.example.switchbuttonproject.fragment.FragmentThree
import com.example.switchbuttonproject.fragment.FragmentTwo
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var isEgoEnabled: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupSwitches()
        setupBottomNavigationBar()
    }

    private fun setupSwitches() {
        // "Ego" switch is unchecked initially
        binding.toggleEgo.isChecked = false
        toggleOtherSwitches(true) // Enable other switches as Ego is unchecked
        binding.bottomNavigationView.menu.clear() // Start with an empty BottomNavigationView
        binding.bottomNavigationView.visibility = BottomNavigationView.VISIBLE

        // Set up listeners for the switches
        binding.toggleEgo.setOnCheckedChangeListener { _, isChecked ->
            isEgoEnabled = isChecked
            if (isChecked) {
                // Ego is enabled, clear BottomNavigationView and disable other switches
                toggleOtherSwitches(false)
                binding.bottomNavigationView.menu.clear()
                binding.bottomNavigationView.visibility = BottomNavigationView.GONE
            } else {
                // Ego is disabled, allow other switches to be enabled
                toggleOtherSwitches(true)
                binding.bottomNavigationView.visibility = BottomNavigationView.VISIBLE
            }
        }

        // Add or remove icons based on the state of each switch
        binding.toggleTeamSpirit.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) addIconToBottomNavigationBar(R.id.toggleTeamSpirit) else removeIconFromBottomNavigationBar(R.id.toggleTeamSpirit)
        }

        binding.toggleConcentration.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) addIconToBottomNavigationBar(R.id.toggleConcentration) else removeIconFromBottomNavigationBar(R.id.toggleConcentration)
        }

        binding.toggleLoyalty.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) addIconToBottomNavigationBar(R.id.toggleLoyalty) else removeIconFromBottomNavigationBar(R.id.toggleLoyalty)
        }

        binding.toggleDiscipline.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) addIconToBottomNavigationBar(R.id.toggleDiscipline) else removeIconFromBottomNavigationBar(R.id.toggleDiscipline)
        }
    }

    // Only enable or disable the switches without affecting their visibility
    private fun toggleOtherSwitches(enable: Boolean) {
        binding.toggleTeamSpirit.isEnabled = enable
        binding.toggleConcentration.isEnabled = enable
        binding.toggleLoyalty.isEnabled = enable
        binding.toggleDiscipline.isEnabled = enable
    }

    private fun setupBottomNavigationBar() {
        // Set up navigation when items are selected
        binding.bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.toggleTeamSpirit -> navigateToFragment(FragmentOne())
                R.id.toggleConcentration -> navigateToFragment(FragmentTwo())
                R.id.toggleLoyalty -> navigateToFragment(FragmentThree())
                R.id.toggleDiscipline -> navigateToFragment(FragmentFour())
            }
            true
        }
    }

    // Dynamically add items to the BottomNavigationView
    private fun addIconToBottomNavigationBar(itemId: Int) {
        val menu = binding.bottomNavigationView.menu

        // Prevent adding more than 5 items
        if (menu.size() >= 5) {
            return
        }

        when (itemId) {
            R.id.toggleTeamSpirit -> {
                if (menu.findItem(R.id.toggleTeamSpirit) == null) { // Prevent duplicate items
                    menu.add(0, R.id.toggleTeamSpirit, 0, "Team Spirit").setIcon(R.drawable.icon_home)
                }
            }
            R.id.toggleConcentration -> {
                if (menu.findItem(R.id.toggleConcentration) == null) {
                    menu.add(0, R.id.toggleConcentration, 0, "Concentration").setIcon(R.drawable.icon_headphone)
                }
            }
            R.id.toggleLoyalty -> {
                if (menu.findItem(R.id.toggleLoyalty) == null) {
                    menu.add(0, R.id.toggleLoyalty, 0, "Loyalty").setIcon(R.drawable.icon_man)
                }
            }
            R.id.toggleDiscipline -> {
                if (menu.findItem(R.id.toggleDiscipline) == null) {
                    menu.add(0, R.id.toggleDiscipline, 0, "Discipline").setIcon(R.drawable.icon_star)
                }
            }
        }
    }

    // Dynamically remove items from the BottomNavigationView
    private fun removeIconFromBottomNavigationBar(itemId: Int) {
        binding.bottomNavigationView.menu.removeItem(itemId)
    }

    // Navigate to a specific fragment when a menu item is selected
    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
