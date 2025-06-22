package mk.ukim.finki.my_distributor.ui.fragments.driver

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.DeliveryRepository
import mk.ukim.finki.my_distributor.databinding.FragmentStartDeliveryBinding
import mk.ukim.finki.my_distributor.ui.adapters.OrderInDeliveryAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.StartDeliveryViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.StartDeliveryViewModelFactory

class StartDeliveryFragment : Fragment() {
    private var _binding: FragmentStartDeliveryBinding? = null
    private val binding get() = _binding!!
    private val args: StartDeliveryFragmentArgs by navArgs()

    private val repo by lazy {
        DeliveryRepository(
            RetrofitClient.getDeliveryApiService(UserPreferences.getInstance(requireContext()))
        )
    }
    private val viewModel: StartDeliveryViewModel by viewModels {
        StartDeliveryViewModelFactory(repo)
    }
    private lateinit var adapter: OrderInDeliveryAdapter

    // 1) Register an ActivityResultLauncher for Google Maps
    private val mapsLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        // Called when Maps closes
        Toast.makeText(requireContext(),
            "Returned from navigation", Toast.LENGTH_SHORT).show()
        viewModel.markCurrentStopDone()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ) = FragmentStartDeliveryBinding.inflate(inflater, container, false)
        .also { _binding = it }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView + ListAdapter
        adapter = OrderInDeliveryAdapter()
        binding.stopsRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.stopsRecyclerView.adapter = adapter

        // Observe the stops list
        viewModel.orders.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list.toList()) // submit an immutable copy
        }
        viewModel.error.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
        viewModel.loadDeliveryWithOrders(args.deliveryId)

        // 2) Hook up drag-and-drop using ItemTouchHelper
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) = makeMovementFlags(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN,
                0
            )

            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val from = vh.adapterPosition
                val to   = target.adapterPosition
                viewModel.reorderOrders(from, to)
                return true
            }

            override fun onSwiped(vh: RecyclerView.ViewHolder, direction: Int) {
                // no-op
            }
        }).attachToRecyclerView(binding.stopsRecyclerView)

        // 3) Begin navigation button
        binding.beginNavigationButton.setOnClickListener {
            val km = binding.startKmEditText.text.toString().toIntOrNull()
            if (km == null || km <= 0) {
                Toast.makeText(requireContext(),
                    "Enter valid kilometers", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.startDelivery(km)

            // Get the *current* first stop from VM and launch Maps
            viewModel.currentStopLocation.value?.let { loc ->
                val uri = "google.navigation:q=${loc.latitude},${loc.longitude}".toUri()
                val intent = Intent(Intent.ACTION_VIEW, uri)
                    .setPackage("com.google.android.apps.maps")
                mapsLauncher.launch(intent)
            } ?: Toast.makeText(requireContext(),
                "No stops available", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
