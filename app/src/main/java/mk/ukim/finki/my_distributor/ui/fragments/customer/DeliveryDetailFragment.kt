package mk.ukim.finki.my_distributor.ui.fragments.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import mk.ukim.finki.my_distributor.databinding.FragmentDeliveryDetailBinding

class DeliveryDetailFragment : Fragment() {

    private var _binding: FragmentDeliveryDetailBinding? = null
    private val binding get() = _binding!!

    private val args: DeliveryDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDeliveryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deliveryId = args.deliveryId
        binding.deliveryIdTextView.text = "Delivery ID: $deliveryId"
        // TODO: Load delivery details if needed
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}