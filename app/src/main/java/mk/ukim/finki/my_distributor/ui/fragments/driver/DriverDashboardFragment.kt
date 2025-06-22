package mk.ukim.finki.my_distributor.ui.fragments.driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository
import mk.ukim.finki.my_distributor.databinding.FragmentDriverDashboardBinding
import mk.ukim.finki.my_distributor.ui.adapters.DriverDeliveriesAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.DriverDashboardViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.DriverDashboardViewModelFactory

class DriverDashboardFragment : Fragment() {

    private var _binding: FragmentDriverDashboardBinding? = null
    private val binding get() = _binding!!

    private val deliveryRepository: DeliveryRepository by lazy {
        DeliveryRepository(
            RetrofitClient.getDeliveryApiService(
                UserPreferences.getInstance(requireContext())
            )
        )
    }

    private val driverDashboardViewModel: DriverDashboardViewModel by viewModels {
        DriverDashboardViewModelFactory(deliveryRepository)
    }

    private lateinit var deliveriesAdapter: DriverDeliveriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDriverDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.deliveriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        deliveriesAdapter = DriverDeliveriesAdapter(emptyList()) { delivery ->
            val action = DriverDashboardFragmentDirections
                .actionDriverDashboardFragmentToDeliveryDetailFragment(delivery.deliveryId)
            findNavController().navigate(action)
        }
        binding.deliveriesRecyclerView.adapter = deliveriesAdapter

        driverDashboardViewModel.deliveries.observe(viewLifecycleOwner) { list ->
            if(list.isEmpty()){
                binding.emptyTextView.visibility=View.VISIBLE
                binding.deliveriesRecyclerView.visibility = View.GONE
            }else {
                binding.emptyTextView.visibility = View.GONE
                binding.deliveriesRecyclerView.visibility = View.VISIBLE
                deliveriesAdapter.updateData(list)
            }
        }

        driverDashboardViewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(
                requireContext(),
                errorMsg,
                Toast.LENGTH_SHORT
            ).show()
        }

        driverDashboardViewModel.loadDriverDeliveries()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}