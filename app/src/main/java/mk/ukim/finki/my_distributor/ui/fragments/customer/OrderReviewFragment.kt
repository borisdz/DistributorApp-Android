package mk.ukim.finki.my_distributor.ui.fragments.customer

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.OrderRepository
import mk.ukim.finki.my_distributor.databinding.FragmentOrderReviewBinding
import mk.ukim.finki.my_distributor.domain.dto.OrderItem
import mk.ukim.finki.my_distributor.domain.enumerations.PaymentMethod
import mk.ukim.finki.my_distributor.ui.adapters.OrderReviewAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.OrderViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.OrderViewModelFactory

class OrderReviewFragment : Fragment() {

    private var _binding: FragmentOrderReviewBinding? = null
    private val binding get() = _binding!!

    private lateinit var orderReviewAdapter: OrderReviewAdapter

    private val orderRepository: OrderRepository by lazy {
        OrderRepository(
            RetrofitClient.getOrderApiService(
                userPreferences = UserPreferences(
                    requireContext()
                )
            )
        )
    }

    private val orderViewModel: OrderViewModel by activityViewModels {
        OrderViewModelFactory(orderRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderReviewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.orderItemsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        orderReviewAdapter = OrderReviewAdapter(
            onEditClicked = { orderItem ->
                showEditQuantityDialog(orderItem)
            },
            onDeleteClicked = { orderItem ->
                orderViewModel.removeItem(orderItem)
            }
        )
        binding.orderItemsRecyclerView.adapter = orderReviewAdapter

        orderViewModel.orderItems.observe(viewLifecycleOwner) { orderItems ->
            orderReviewAdapter.submitList(orderItems.toList())
        }

        binding.editOrderButton.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Edit Order clicked",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_orderReviewFragment_to_createOrderFragment)
        }

        binding.cancelOrderButton.setOnClickListener {
            orderViewModel.clearOrder()
            Toast.makeText(
                requireContext(),
                "Order cancelled",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.completeOrderButton.setOnClickListener {
            val paymentMethod = when (binding.paymentRadioGroup.checkedRadioButtonId) {
                R.id.radioProForma -> PaymentMethod.PRO_FORMA
                R.id.radioCash -> PaymentMethod.CASH
                else -> PaymentMethod.CASH
            }

            orderViewModel.completeOrder(paymentMethod)
            orderViewModel.orderSubmissionResult.observe(viewLifecycleOwner) { result ->
                result.onSuccess {
                    Toast.makeText(
                        requireContext(),
                        "Order completed successfully",
                        Toast.LENGTH_LONG
                    ).show()
                }.onFailure { exception ->
                    Toast.makeText(
                        requireContext(),
                        "Order completion failed: ${exception.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }


    private fun showEditQuantityDialog(orderItem: OrderItem) {
        val dialogView =
            LayoutInflater.from(requireContext()).inflate(R.layout.dialog_add_article, null)
        val quantityEditText = dialogView.findViewById<EditText>(R.id.quantityEditText)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmQuantityButton)

        quantityEditText.setText(orderItem.quantity.toString())

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Edit ${orderItem.article.name}")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        confirmButton.setOnClickListener {
            val quantityStr = quantityEditText.text.toString()
            val quantity = quantityStr.toIntOrNull()
            if (quantity!=null && quantity>0) {
                orderViewModel.updateItem(orderItem.copy(quantity=quantity))
                Toast.makeText(
                    requireContext(),
                    "Updated ${orderItem.article.name} to $quantity",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }else{
                Toast.makeText(
                    requireContext(),
                    "Please enter a valid quantity",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}