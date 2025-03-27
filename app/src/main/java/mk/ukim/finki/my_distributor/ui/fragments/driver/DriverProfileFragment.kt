package mk.ukim.finki.my_distributor.ui.fragments.driver

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.ui.viewmodel.DriverProfileViewModel

class DriverProfileFragment : Fragment() {

    companion object {
        fun newInstance() = DriverProfileFragment()
    }

    private val viewModel: DriverProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_driver_profile, container, false)
    }
}