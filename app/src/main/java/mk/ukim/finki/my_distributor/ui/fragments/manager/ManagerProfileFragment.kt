package mk.ukim.finki.my_distributor.ui.fragments.manager

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.databinding.FragmentManagerProfileBinding
import mk.ukim.finki.my_distributor.ui.activities.LoginActivity

class ManagerProfileFragment : Fragment() {

    private var _binding: FragmentManagerProfileBinding? = null
    private val binding get() = _binding!!

    private val prefs by lazy { UserPreferences.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs.getUserInfo()?.let { user ->
            binding.nameTextView.text  = user.name
            binding.emailTextView.text = user.email
        }

        binding.pendingDeliveriesButton.setOnClickListener {
            findNavController().navigate(
                mk.ukim.finki.my_distributor.R.id.managerDashboardFragment
            )
        }

        binding.newOrdersButton.setOnClickListener {
            findNavController().navigate(
                mk.ukim.finki.my_distributor.R.id.createDeliveryFragment
            )
        }

        binding.warehouseStatsButton.setOnClickListener {
            Toast.makeText(requireContext(),
                "Warehouse Stats screen not yet implemented",
                Toast.LENGTH_SHORT
            ).show()
        }

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
