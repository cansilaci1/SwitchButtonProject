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
        binding.toggleEgo.isChecked = false
        toggleOtherSwitches(true)
        binding.bottomNavigationView.menu.clear()
        binding.bottomNavigationView.visibility = BottomNavigationView.VISIBLE

        binding.toggleEgo.setOnCheckedChangeListener { _, isChecked ->
            isEgoEnabled = isChecked
            if (isChecked) {
                clearOtherSwitches()
                clearFragmentContainer()
                binding.bottomNavigationView.menu.clear()
                binding.bottomNavigationView.visibility = BottomNavigationView.GONE
            } else {
                toggleOtherSwitches(true)
                binding.bottomNavigationView.visibility = BottomNavigationView.VISIBLE
            }
        }

        binding.toggleTeamSpirit.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.toggleEgo.isChecked = false
                addIconToBottomNavigationBar(R.id.toggleTeamSpirit)
            } else removeIconFromBottomNavigationBar(R.id.toggleTeamSpirit)
        }

        binding.toggleConcentration.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.toggleEgo.isChecked = false
                addIconToBottomNavigationBar(R.id.toggleConcentration)
            } else removeIconFromBottomNavigationBar(R.id.toggleConcentration)
        }

        binding.toggleLoyalty.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.toggleEgo.isChecked = false
                addIconToBottomNavigationBar(R.id.toggleLoyalty)
            } else removeIconFromBottomNavigationBar(R.id.toggleLoyalty)
        }

        binding.toggleDiscipline.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.toggleEgo.isChecked = false
                addIconToBottomNavigationBar(R.id.toggleDiscipline)
            } else removeIconFromBottomNavigationBar(R.id.toggleDiscipline)
        }
    }

    private fun toggleOtherSwitches(enable: Boolean) {
        binding.toggleTeamSpirit.isEnabled = enable
        binding.toggleConcentration.isEnabled = enable
        binding.toggleLoyalty.isEnabled = enable
        binding.toggleDiscipline.isEnabled = enable
    }

    private fun clearOtherSwitches() {
        binding.toggleTeamSpirit.isChecked = false
        binding.toggleConcentration.isChecked = false
        binding.toggleLoyalty.isChecked = false
        binding.toggleDiscipline.isChecked = false
    }

    private fun clearFragmentContainer() {
        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        fragment?.let {
            supportFragmentManager.beginTransaction().remove(it).commit()
        }
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

    private fun addIconToBottomNavigationBar(itemId: Int) {
        val menu = binding.bottomNavigationView.menu

        if (menu.size() >= 5) {
            return
        }

        when (itemId) {
            R.id.toggleTeamSpirit -> {
                if (menu.findItem(R.id.toggleTeamSpirit) == null) {
                    menu.add(0, R.id.toggleTeamSpirit, 0, "Team Spirit").setIcon(R.drawable.icon_home)
                    navigateToFragment(FragmentOne())
                }
            }
            R.id.toggleConcentration -> {
                if (menu.findItem(R.id.toggleConcentration) == null) {
                    menu.add(0, R.id.toggleConcentration, 0, "Concentration").setIcon(R.drawable.icon_headphone)
                    navigateToFragment(FragmentTwo())
                }
            }
            R.id.toggleLoyalty -> {
                if (menu.findItem(R.id.toggleLoyalty) == null) {
                    menu.add(0, R.id.toggleLoyalty, 0, "Loyalty").setIcon(R.drawable.icon_man)
                    navigateToFragment(FragmentThree())
                }
            }
            R.id.toggleDiscipline -> {
                if (menu.findItem(R.id.toggleDiscipline) == null) {
                    menu.add(0, R.id.toggleDiscipline, 0, "Discipline").setIcon(R.drawable.icon_star)
                    navigateToFragment(FragmentFour())
                }
            }
        }
    }

    private fun removeIconFromBottomNavigationBar(itemId: Int) {
        val menu = binding.bottomNavigationView.menu
        menu.removeItem(itemId)

        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if (currentFragment != null) {
            when (itemId) {
                R.id.toggleTeamSpirit -> if (currentFragment is FragmentOne) clearFragmentContainer()
                R.id.toggleConcentration -> if (currentFragment is FragmentTwo) clearFragmentContainer()
                R.id.toggleLoyalty -> if (currentFragment is FragmentThree) clearFragmentContainer()
                R.id.toggleDiscipline -> if (currentFragment is FragmentFour) clearFragmentContainer()
            }
        }
    }

    private fun navigateToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}
