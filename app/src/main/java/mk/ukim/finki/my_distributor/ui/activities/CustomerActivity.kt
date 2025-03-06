package mk.ukim.finki.my_distributor.ui.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.databinding.ActivityCustomerBinding
import mk.ukim.finki.my_distributor.ui.fragments.customer.CreateOrderFragment
import mk.ukim.finki.my_distributor.ui.fragments.customer.CustomerDashboardFragment
import mk.ukim.finki.my_distributor.ui.fragments.customer.ProfileFragment

class CustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCustomerBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        // TODO: Implement customer activity logic

        val bottomNav = findViewById<BottomNavigationView>(binding.bottomNavigation.id)

        bottomNav.setOnItemSelectedListener { item->
            val selectedFragment = when (item.itemId) {
                R.id.nav_dashboard -> CustomerDashboardFragment()
                R.id.nav_create_order -> CreateOrderFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> CustomerDashboardFragment()
            }
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container,selectedFragment)
                .commit()
            true
        }

        if (savedInstanceState == null){
            bottomNav.selectedItemId = R.id.nav_dashboard
        }
    }
}