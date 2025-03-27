package mk.ukim.finki.my_distributor.ui.fragments.driver

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.ui.viewmodel.StartDeliveryViewModel

class StartDeliveryFragment : Fragment() {

    companion object {
        fun newInstance() = StartDeliveryFragment()
    }

    private val viewModel: StartDeliveryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_start_delivery, container, false)
    }
}