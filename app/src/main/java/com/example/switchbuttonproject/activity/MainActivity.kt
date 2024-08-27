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
        // "Ego" switch is checked initially
        binding.toggleEgo.isChecked = false

        binding.toggleEgo.setOnCheckedChangeListener { _, isChecked ->
            isEgoEnabled = isChecked
            if (isChecked) {
                toggleOtherSwitches(false)
                binding.bottomNavigationView.menu.clear()
                binding.bottomNavigationView.visibility = BottomNavigationView.GONE
            } else {
                toggleOtherSwitches(true)
                binding.bottomNavigationView.visibility = BottomNavigationView.VISIBLE
                addInitialIconToBottomNavigationBar()
            }
        }

        // Setup other switches (they should remain visible but be enabled/disabled)
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


    private fun addInitialIconToBottomNavigationBar() {
        val menu = binding.bottomNavigationView.menu

        // Add initial icons, ensuring you do not exceed the 5-item limit
        if (menu.size() < 5) {
            menu.add(0, R.id.itemOne, 0, "Main").setIcon(R.drawable.icon_home)
        }
        if (menu.size() < 5) {
            menu.add(0, R.id.itemTwo, 1, "Music").setIcon(R.drawable.icon_headphone)
        }
        if (menu.size() < 5) {
            menu.add(0, R.id.itemThree, 2, "Profile").setIcon(R.drawable.icon_man)
        }
        if (menu.size() < 5) {
            menu.add(0, R.id.itemFour, 3, "Favorites").setIcon(R.drawable.icon_star)
        }
    }


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


    private fun removeIconFromBottomNavigationBar(itemId: Int) {
        binding.bottomNavigationView.menu.removeItem(itemId)
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
