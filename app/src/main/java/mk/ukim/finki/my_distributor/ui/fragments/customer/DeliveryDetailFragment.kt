package mk.ukim.finki.my_distributor.ui.fragments.customer

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository
import mk.ukim.finki.my_distributor.databinding.FragmentDeliveryDetailBinding
import mk.ukim.finki.my_distributor.ui.adapters.OrderInDeliveryAdapter
import mk.ukim.finki.my_distributor.ui.fragments.driver.DeliveryDetailFragmentDirections
import mk.ukim.finki.my_distributor.ui.viewmodel.DeliveryDetailViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.DeliveryDetailViewModelFactory

class DeliveryDetailFragment : Fragment() {
    private var _binding: FragmentDeliveryDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DeliveryDetailFragmentArgs by navArgs()

    private val repo by lazy {
        DeliveryRepository(
            RetrofitClient.getDeliveryApiService(
                UserPreferences.getInstance(requireContext())
            )
        )
    }

    private val viewModel: DeliveryDetailViewModel by viewModels {
        DeliveryDetailViewModelFactory(repo)
    }

    private lateinit var adapter: OrderInDeliveryAdapter

    override fun onCreateView(i: LayoutInflater, c: ViewGroup?, s: Bundle?) =
        FragmentDeliveryDetailBinding.inflate(i, c, false).also { _binding = it }.root

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = OrderInDeliveryAdapter()
        binding.stopsRv.layoutManager = LinearLayoutManager(requireContext())
        binding.stopsRv.adapter = adapter

        viewModel.detail.observe(viewLifecycleOwner) { dto ->
            binding.deliveryDateTv.text = "Date: ${dto?.delivery?.delDate.toString()}"
            binding.deliveryStatusTv.text = "Status: ${dto?.delivery?.delStatus}"
            adapter.submitList(dto?.orders)
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }

        viewModel.load(args.deliveryId)

        binding.startDeliveryBtn.setOnClickListener {
            val action = DeliveryDetailFragmentDirections
                .actionDeliveryDetailFragmentToStartDeliveryFragment(args.deliveryId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView(); _binding = null
    }
}