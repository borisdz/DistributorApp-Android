package mk.ukim.finki.my_distributor.ui.fragments.driver

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.ui.viewmodel.PastDeliveriesViewModel

class PastDeliveriesFragment : Fragment() {

    companion object {
        fun newInstance() = PastDeliveriesFragment()
    }

    private val viewModel: PastDeliveriesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_past_deliveries, container, false)
    }
}