package id.novian.flowablecash.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setBottomNavigation()
    }

    private fun setBottomNavigation() {
        val bottomBar = binding.bottomAppBar
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavigationView = binding.bottomNavigationView

        setSupportActionBar(bottomBar)
        setupWithNavController(bottomNavigationView, navController)

        val bar = supportActionBar

        bottomNavigationView.setOnItemSelectedListener { item ->
            NavigationUI.onNavDestinationSelected(item, navController)

            return@setOnItemSelectedListener true
        }

    }
}