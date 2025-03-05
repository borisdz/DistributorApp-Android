package mk.ukim.finki.my_distributor.ui.fragments.customer

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.databinding.FragmentCustomerDashboardBinding
import mk.ukim.finki.my_distributor.domain.OrderDto
import mk.ukim.finki.my_distributor.ui.adapters.OrdersAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.CustomerDashboardViewModel
import mk.ukim.finki.my_distributor.util.Resource

class CustomerDashboardFragment : Fragment() {

    private var _binding: FragmentCustomerDashboardBinding? = null
    private val binding get() = _binding!!

    private val viewModel: CustomerDashboardViewModel by viewModels { viewModelFactory }

    private lateinit var ordersAdapter: OrdersAdapter
//    private lateinit var deliveriesAdapter: DeliveriesAdapter
//    private lateinit var proFormasAdapter: ProFormasAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomerDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.ordersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
//        ordersAdapter = OrdersAdapter(emptyList()) {
//            // Handle order click - navigate to details.
//        }
        binding.ordersRecyclerView.adapter = ordersAdapter

        viewModel.dashboardData.observe(viewLifecycleOwner) { resource ->
            when(resource) {
                is Resource.Loading -> {
                    // Optionally show a loading spinner
                }
                is Resource.Success -> {
                    val data = resource.data
                    ordersAdapter.updateData(data.orders)
                }
                is Resource.Error -> {
                    Toast.makeText(requireContext(), resource.message, Toast.LENGTH_SHORT).show()
                }
            }

        }

        viewModel.loadDashboardData(1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}