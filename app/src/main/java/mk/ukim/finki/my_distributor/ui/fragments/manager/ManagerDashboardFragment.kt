package mk.ukim.finki.my_distributor.ui.fragments.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.ManagerRepository
import mk.ukim.finki.my_distributor.databinding.FragmentManagerDashboardBinding
import mk.ukim.finki.my_distributor.ui.adapters.DeliveriesAdapter
import mk.ukim.finki.my_distributor.ui.adapters.OrdersAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.ManagerDashboardViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.ManagerDashboardViewModelFactory

class ManagerDashboardFragment : Fragment() {

    private var _binding: FragmentManagerDashboardBinding? = null
    private val binding get() = _binding!!

    // Lazy init of prefs and repository
    private val prefs by lazy { UserPreferences.getInstance(requireContext()) }
    private val repository by lazy {
        ManagerRepository(
            RetrofitClient.getManagerApiService(prefs)
        )
    }

    // ViewModel via factory
    private val viewModel: ManagerDashboardViewModel by viewModels {
        ManagerDashboardViewModelFactory(repository, prefs)
    }

    // Adapters for the two lists
    private lateinit var ordersAdapter: OrdersAdapter
    private lateinit var deliveriesAdapter: DeliveriesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentManagerDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) New Orders RecyclerView
        ordersAdapter = OrdersAdapter { /* onOrderClick: implement if needed */ }
        binding.newOrdersRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersAdapter
        }

        // 2) Pending Deliveries RecyclerView
        deliveriesAdapter = DeliveriesAdapter { /* onDeliveryClick: implement if needed */ }
        binding.pendingDelRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = deliveriesAdapter
        }

        // 3) Observe LiveData
        viewModel.dashboard.observe(viewLifecycleOwner) { dto ->
            ordersAdapter.updateData(dto.newOrders)
            deliveriesAdapter.updateData(dto.pendingDeliveries)
        }
        viewModel.error.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }

        // 4) Trigger load
        viewModel.loadDashboard()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}