package mk.ukim.finki.my_distributor.ui.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.databinding.ActivityLoginBinding
import mk.ukim.finki.my_distributor.ui.viewmodel.AuthViewModel

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        viewModel.loginResponse.observe(this) { loginResponse ->
            Toast.makeText(
                this,
                "Login Successful! Email: ${loginResponse.userEmail}",
                Toast.LENGTH_SHORT
            ).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (email.isNotEmpty() && password.isNotEmpty()){
                viewModel.login(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.loginResponse.observe(this) { loginResponse ->
            Toast.makeText(
                this,
                "Login Successful! Email: ${loginResponse.userEmail}",
                Toast.LENGTH_SHORT
            ).show()

            val userType = loginResponse.clazz_

            when(userType){
                "CUSTOMER" -> {
                    val intent = Intent(this, CustomerActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                "MANAGER" -> {
                    val intent = Intent(this, ManagerActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                "DRIVER" -> {
                    val intent = Intent(this, DriverActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }

        viewModel.error.observe(this) { errorMsg ->
            Toast.makeText(this, "Login error: $errorMsg", Toast.LENGTH_SHORT).show()
        }

    }
}