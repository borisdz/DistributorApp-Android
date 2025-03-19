package mk.ukim.finki.my_distributor.ui.fragments.customer

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.databinding.FragmentDeliveryDetailBinding
import mk.ukim.finki.my_distributor.databinding.FragmentOrderDetailBinding
import mk.ukim.finki.my_distributor.ui.viewmodel.DeliveryDetailViewModel

class DeliveryDetailFragment : Fragment() {

    private var _binding: FragmentDeliveryDetailBinding? = null
    private val binding get() = _binding!!

    private val deliveryId: Long by lazy {
        arguments?.getLong("deliveryId") ?:0L
    }

    private val viewModel: DeliveryDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TODO: Implement this
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}