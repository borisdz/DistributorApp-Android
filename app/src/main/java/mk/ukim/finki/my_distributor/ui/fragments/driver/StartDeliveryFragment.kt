package mk.ukim.finki.my_distributor.ui.fragments.driver

import android.content.Intent
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository
import mk.ukim.finki.my_distributor.databinding.FragmentStartDeliveryBinding
import mk.ukim.finki.my_distributor.ui.adapters.DeliveryOrdersAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.StartDeliveryViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.StartDeliveryViewModelFactory
import androidx.core.net.toUri
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class StartDeliveryFragment : Fragment() {

    private var _binding: FragmentStartDeliveryBinding? = null
    private val binding get() = _binding!!

    private val deliveryRepository: DeliveryRepository by lazy {
        DeliveryRepository(
            RetrofitClient.getDeliveryApiService(UserPreferences.getInstance(requireContext()))
        )
    }

    private val viewModel: StartDeliveryViewModel by viewModels {
        StartDeliveryViewModelFactory(deliveryRepository)
    }

    private lateinit var deliveryOrdersAdapter: DeliveryOrdersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStartDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //TODO: Fix this so it isn't hardcoded
        val deliveryId = 1L
        viewModel.loadDelivery(deliveryId)

        binding.deliveryOrdersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        deliveryOrdersAdapter = DeliveryOrdersAdapter()
        binding.deliveryOrdersRecyclerView.adapter = deliveryOrdersAdapter

        viewModel.deliveryWithOrders.observe(viewLifecycleOwner) { dto ->
            dto?.let { deliveryDto ->
                deliveryOrdersAdapter.submitList(deliveryDto.orders)
            }
        }

        binding.startDeliveryButton.setOnClickListener {
            val startKmStr = binding.startKmEditText.text.toString()
            val startKm = startKmStr.toIntOrNull()
            if(startKm!=null && startKm > 0){
                viewModel.startDelivery(startKm)

                viewModel.firstOrderLocation.observe(viewLifecycleOwner) { location ->
                    location?.let {
                        val gmmIntentUri =
                            "google.nvigation:q=${it.latitude},${it.longitude}".toUri()
                        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                        mapIntent.setPackage("com.google.android.apps.maps")
                        if(mapIntent.resolveActivity(requireActivity().packageManager) != null) {
                            startActivity(mapIntent)
                        }else {
                            Toast.makeText(
                                requireContext(),
                                "Google Maps not installed",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter a valid starting kilometer value.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPos = viewHolder.adapterPosition
                val toPos = target.adapterPosition
                viewModel.reorderOrders(fromPos,toPos)
                deliveryOrdersAdapter.notifyItemMoved(fromPos,toPos)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                TODO("Not yet implemented")
            }
        })
        itemTouchHelper.attachToRecyclerView(binding.deliveryOrdersRecyclerView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}