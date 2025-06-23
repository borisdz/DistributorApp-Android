package mk.ukim.finki.my_distributor.ui.fragments.customer

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.databinding.FragmentProfileBinding
import mk.ukim.finki.my_distributor.ui.activities.LoginActivity

class CustomerProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val prefs by lazy { UserPreferences.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) Populate user name
        prefs.getUserInfo()?.let { user ->
            binding.profileName.text = user.name
        }

        // 2) “My Orders”
        binding.ordersButton.setOnClickListener {

        }

        // 3) “My Deliveries”
        binding.deliveriesButton.setOnClickListener {

        }

        // 4) “My Pro-Formas”
        binding.proFormasButton.setOnClickListener {

        }

        // 5) “Settings”
        binding.settingsButton.setOnClickListener {

        }

        // 6) “Log Out”
        binding.logoutButton.setOnClickListener {
            prefs.clearToken()
            prefs.clearUserInfo()
            val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
