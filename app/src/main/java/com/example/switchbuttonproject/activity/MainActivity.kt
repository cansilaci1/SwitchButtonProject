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
        binding.toggleEgo.isChecked = true
        toggleOtherSwitches(false)

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

        binding.toggleTeamSpirit.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) addIconToBottomNavigationBar(R.id.team_spirit) else removeIconFromBottomNavigationBar(R.id.team_spirit)
        }

        binding.toggleConcentration.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) addIconToBottomNavigationBar(R.id.concentration) else removeIconFromBottomNavigationBar(R.id.concentration)
        }

        // Similarly for other switches like toggleLoyalty, toggleDiscipline, etc.
    }

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
        // Add the initial icon for the main screen to the BottomNavigationBar
        binding.bottomNavigationView.menu.add(0, R.id.main, 0, "Main").setIcon(R.drawable.ic_home)
    }

    private fun addIconToBottomNavigationBar(itemId: Int) {
        when (itemId) {
            R.id.team_spirit -> binding.bottomNavigationView.menu.add(0, R.id.team_spirit, 0, "Team Spirit").setIcon(R.drawable.ic_team)
            R.id.concentration -> binding.bottomNavigationView.menu.add(0, R.id.concentration, 0, "Concentration").setIcon(R.drawable.ic_concentration)
            // Similarly for other items
        }
    }

    private fun removeIconFromBottomNavigationBar(itemId: Int) {
        binding.bottomNavigationView.menu.removeItem(itemId)
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit()
    }
}
