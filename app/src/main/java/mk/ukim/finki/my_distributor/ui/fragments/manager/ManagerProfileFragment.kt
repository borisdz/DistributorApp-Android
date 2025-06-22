package mk.ukim.finki.my_distributor.ui.fragments.manager

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.ui.viewmodel.ManagerProfileViewModel

class ManagerProfileFragment : Fragment() {

    companion object {
        fun newInstance() = ManagerProfileFragment()
    }

    private val viewModel: ManagerProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_manager_profile, container, false)
    }
}