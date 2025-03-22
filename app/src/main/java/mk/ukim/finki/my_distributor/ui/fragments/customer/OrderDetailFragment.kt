package mk.ukim.finki.my_distributor.ui.fragments.customer

import android.annotation.SuppressLint
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.ArticlesRepository
import mk.ukim.finki.my_distributor.data.repository.OrderRepository
import mk.ukim.finki.my_distributor.databinding.FragmentOrderDetailBinding
import mk.ukim.finki.my_distributor.ui.adapters.OrderDetailItemsAdapter
import mk.ukim.finki.my_distributor.ui.adapters.OrderItemsAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.OrderDetailViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.OrderDetailViewModelFactory

class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!

    private val args: OrderDetailFragmentArgs by navArgs()

    private lateinit var orderItemsAdapter: OrderDetailItemsAdapter

    private val orderRepository: OrderRepository by lazy {
        OrderRepository(
            RetrofitClient.getOrderApiService(UserPreferences.getInstance(requireContext()))
        )
    }

    private val viewModel: OrderDetailViewModel by viewModels {
        OrderDetailViewModelFactory(orderRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = args.orderId
        binding.orderIdTextView.text = "Order ID: $orderId"
        binding.orderItemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.orderItemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        orderItemsAdapter = OrderDetailItemsAdapter()
        binding.orderItemsRecyclerView.adapter = orderItemsAdapter
        binding.totalSumTextView
        // Load the order details.
        viewModel.loadOrderDetail(orderId)

        // Observe order details.
        viewModel.orderDetail.observe(viewLifecycleOwner) { detail ->
            detail?.let {
                // Update the UI for the order.
                binding.orderDateTextView.text = "Order Date: ${it.order.ordDate}"
                binding.orderSumTextView.text = "Order Sum: $${it.order.ordSum}"
                binding.paymentDateTextView.text = "Payment Date: ${it.order.ordFulfillmentDate ?: "N/A"}"
                binding.orderCommentTextView.text = "Comment: ${it.order.ordComment ?: "None"}"
                binding.orderStatusTextView.text = "Status: ${it.order.oStatusId}"
                binding.deliveryStatusTextView.text = "Delivery: ${it.order.deliveryId ?: "Not assigned"}"
                if (it.order.deliveryId != null) {
                    val deliveryId = it.order.deliveryId
                    binding.deliveryStatusTextView.setOnClickListener {
                        val action = OrderDetailFragmentDirections
                            .actionOrderDetailFragmentToDeliveryDetailFragment(deliveryId)
                        findNavController().navigate(action)
                    }
                }
                // Populate the RecyclerView with order items.
                orderItemsAdapter.submitList(it.items)
            }
        }


        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            binding.orderCommentTextView.text = errorMsg
        }

        binding.proFormaButton.setOnClickListener {
            // Trigger pro forma generation/download logic
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}