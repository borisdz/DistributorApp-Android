package mk.ukim.finki.my_distributor.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.AuthRepository
import mk.ukim.finki.my_distributor.databinding.ActivityLoginBinding
import mk.ukim.finki.my_distributor.ui.viewmodel.AuthViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.AuthViewModelFactory
import mk.ukim.finki.my_distributor.util.decodeJwtToken

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    private val userPreferences by lazy { UserPreferences.getInstance(this) }

    private val authRepository by lazy {
        AuthRepository(
            RetrofitClient.getAuthApiService(
                userPreferences
            )
        )
    }

    private val viewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository, userPreferences)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        viewModel.loginResponse.observe(this) {

            val token = userPreferences.getToken()

            if (token != null) {
                val decoded = decodeJwtToken(token)
                Toast.makeText(
                    this,
                    "Login Successful! Email: ${decoded.email}",
                    Toast.LENGTH_SHORT
                ).show()

                when {
                    decoded.roles.contains("ROLE_CUSTOMER") -> {
                        val intent = Intent(this, CustomerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    decoded.roles.contains("ROLE_MANAGER") -> {
                        val intent = Intent(this, ManagerActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    decoded.roles.contains("ROLE_DRIVER") -> {
                        val intent = Intent(this, DriverActivity::class.java)
                        startActivity(intent)
                        finish()
                    }

                    else -> {
                        Toast.makeText(
                            this,
                            "Unknown user role",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    this,
                    "Token not found",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, "Login error: $errorMsg", Toast.LENGTH_SHORT).show()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

    }
}