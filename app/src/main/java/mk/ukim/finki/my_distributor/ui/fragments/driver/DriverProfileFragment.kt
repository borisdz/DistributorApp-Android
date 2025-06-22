package mk.ukim.finki.my_distributor.ui.fragments.driver

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.databinding.FragmentDriverProfileBinding
import mk.ukim.finki.my_distributor.ui.activities.LoginActivity

class DriverProfileFragment : Fragment() {

    private var _binding: FragmentDriverProfileBinding? = null
    private val binding get() = _binding!!

    private val prefs by lazy { UserPreferences.getInstance(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentDriverProfileBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs.getUserInfo()?.let { user ->
            binding.nameTextView.text = user.name
            binding.emailTextView.text = user.email
        } ?: run {
            binding.nameTextView.text = "Unknown User"
            binding.emailTextView.text = ""
        }

        binding.viewPastButton.setOnClickListener {
            findNavController().navigate(
                DriverProfileFragmentDirections
                    .actionDriverProfileFragmentToPastDeliveriesFragment()
            )
        }

        binding.editProfileButton.setOnClickListener {
        }

        binding.logoutButton.setOnClickListener {
            prefs.clearToken()
            prefs.clearUserInfo()

            val intent = Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)

            requireActivity().finish()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
