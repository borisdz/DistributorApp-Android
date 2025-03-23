package mk.ukim.finki.my_distributor.ui.fragments.customer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import mk.ukim.finki.my_distributor.databinding.FragmentProFormaDetailBinding

class ProFormaDetailFragment : Fragment() {

    private var _binding: FragmentProFormaDetailBinding? = null
    private val binding get() = _binding!!

    private val args: ProFormaDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProFormaDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val proFormaId = args.proFormaId
        binding.proFormaIdTextView.text = "Pro Forma ID: $proFormaId"
        // TODO: Load additional pro forma details if needed
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}