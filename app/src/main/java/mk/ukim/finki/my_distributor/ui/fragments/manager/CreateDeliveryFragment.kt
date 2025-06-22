package mk.ukim.finki.my_distributor.ui.fragments.manager

import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.ManagerRepository
import mk.ukim.finki.my_distributor.databinding.FragmentCreateDeliveryBinding
import mk.ukim.finki.my_distributor.ui.adapters.MultiSelectOrdersAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.CreateDeliveryViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.CreateDeliveryViewModelFactory
import java.time.LocalDate

class CreateDeliveryFragment : Fragment() {

    private var _binding: FragmentCreateDeliveryBinding? = null
    private val binding get() = _binding!!

    private val prefs by lazy { UserPreferences.getInstance(requireContext()) }
    private val repository by lazy {
        ManagerRepository(
            RetrofitClient.getManagerApiService(prefs)
        )
    }

    private val viewModel: CreateDeliveryViewModel by viewModels {
        CreateDeliveryViewModelFactory(repository)
    }

    private lateinit var ordersAdapter: MultiSelectOrdersAdapter
    private lateinit var vehicleSpinnerAdapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateDeliveryBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ordersAdapter = MultiSelectOrdersAdapter { orderId ->
            viewModel.toggleOrderSelection(orderId)
        }
        binding.ordersRv.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ordersAdapter
        }

        binding.sortGroup.setOnCheckedChangeListener { _, checkedId ->
            val list = viewModel.unassignedOrders.value ?: return@setOnCheckedChangeListener
            val sorted = when (checkedId) {
                R.id.sortCity -> list.sortedBy { it.customerId } // needs to be city
                else           -> list.sortedBy { it.ordDate }
            }
            ordersAdapter.submitList(sorted)
        }

        viewModel.vehicles.observe(viewLifecycleOwner) { vehicles ->
            val labels = vehicles.map { it.plateNumber }
            vehicleSpinnerAdapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                labels
            ).also { a ->
                a.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
            binding.vehicleSpinner.adapter = vehicleSpinnerAdapter

            binding.vehicleSpinner.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        viewModel.selectedVehicle.value = vehicles[position]
                    }
                    override fun onNothingSelected(parent: AdapterView<*>) {
                        viewModel.selectedVehicle.value = null
                    }
                }
        }

        binding.datePickerBtn.setOnClickListener {
            val today = LocalDate.now()
            DatePickerDialog(
                requireContext(),
                { _, year, month, day ->
                    // month is 0-based
                    val isoDate = "%04d-%02d-%02d".format(year, month + 1, day)
                    viewModel.deliveryDate.value = isoDate
                    binding.datePickerBtn.text = isoDate
                },
                today.year,
                today.monthValue - 1,
                today.dayOfMonth
            ).show()
        }

        binding.createDeliveryBtn.setOnClickListener {
            viewModel.createDelivery()
        }

        viewModel.unassignedOrders.observe(viewLifecycleOwner) { list ->
            ordersAdapter.submitList(list)
        }
        viewModel.error.observe(viewLifecycleOwner) { message ->
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        }
        viewModel.result.observe(viewLifecycleOwner) { response ->
            Toast.makeText(
                requireContext(),
                "Created delivery #${response.deliveryId}",
                Toast.LENGTH_LONG
            ).show()
            viewModel.loadData()
        }

        viewModel.loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}