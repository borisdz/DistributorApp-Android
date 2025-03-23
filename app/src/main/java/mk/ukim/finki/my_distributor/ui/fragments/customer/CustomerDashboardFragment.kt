package mk.ukim.finki.my_distributor.ui.fragments.customer

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.DatabaseProvider
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.CustomerRepository
import mk.ukim.finki.my_distributor.databinding.FragmentCustomerDashboardBinding
import mk.ukim.finki.my_distributor.ui.adapters.DeliveriesAdapter
import mk.ukim.finki.my_distributor.ui.adapters.OrdersAdapter
import mk.ukim.finki.my_distributor.ui.adapters.ProFormasAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.CustomerDashboardViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.CustomerDashboardViewModelFactory
import mk.ukim.finki.my_distributor.util.Resource

class CustomerDashboardFragment : Fragment() {

    private var _binding: FragmentCustomerDashboardBinding? = null
    private val binding get() = _binding!!

    private lateinit var customerRepository: CustomerRepository

    private val viewModel: CustomerDashboardViewModel by viewModels {
        CustomerDashboardViewModelFactory(customerRepository)
    }

    private lateinit var ordersAdapter: OrdersAdapter
    private lateinit var deliveriesAdapter: DeliveriesAdapter
    private lateinit var proFormasAdapter: ProFormasAdapter

    override fun onAttach(context: Context) {
        super.onAttach(context)
        customerRepository = CustomerRepository(
            RetrofitClient.getDashboardApiService(userPreferences = UserPreferences(context)),
            DatabaseProvider.getDatabase(requireContext()).dashboardDataDao(),
            Gson()
        )
    }

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
        ordersAdapter = OrdersAdapter(emptyList()) { order ->
            val action = CustomerDashboardFragmentDirections
                .actionCustomerDashboardFragmentToOrderDetailFragment(order.id)
            findNavController().navigate(action)
        }
        binding.ordersRecyclerView.adapter = ordersAdapter

        binding.deliveriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        deliveriesAdapter = DeliveriesAdapter(emptyList()) { delivery ->
            val action = CustomerDashboardFragmentDirections
                .actionCustomerDashboardFragmentToDeliveryDetailFragment(delivery.id)
            findNavController().navigate(action)
        }
        binding.deliveriesRecyclerView.adapter = deliveriesAdapter

        binding.proFormasRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        proFormasAdapter = ProFormasAdapter(emptyList()) { proForma ->
            val action = CustomerDashboardFragmentDirections
                .actionCustomerDashboardFragmentToProFormaDetailFragment(proForma.id)
            findNavController().navigate(action)
        }
        binding.proFormasRecyclerView.adapter = proFormasAdapter

        viewModel.dashboardData.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    // Optionally show a loading spinner
                }

                is Resource.Success -> {
                    val data = resource.data
                    ordersAdapter.updateData(data.orders)
                    deliveriesAdapter.updateData(data.deliveries)
                    proFormasAdapter.updateData(data.proFormas)
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