package mk.ukim.finki.my_distributor.ui.fragments.customer

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.databinding.FragmentOrderDetailBinding
import mk.ukim.finki.my_distributor.ui.viewmodel.OrderDetailViewModel

class OrderDetailFragment : Fragment() {

    private var _binding: FragmentOrderDetailBinding? = null
    private val binding get() = _binding!!

    private val args: OrderDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val orderId = args.orderId
        binding.orderIdTextView.text = "Order ID: $orderId"
        // TODO: Load additional order details using a ViewModel if needed.
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}