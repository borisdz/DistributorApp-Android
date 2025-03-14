package mk.ukim.finki.my_distributor.ui.fragments.customer

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Database
import com.google.gson.Gson
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.DatabaseProvider
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.ArticlesRepository
import mk.ukim.finki.my_distributor.databinding.FragmentCreateOrderBinding
import mk.ukim.finki.my_distributor.ui.adapters.ArticlesAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.CreateOrderViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.CreateOrderViewModelFactory

class CreateOrderFragment : Fragment() {

    private var _binding: FragmentCreateOrderBinding? = null
    private val binding get() = _binding!!

    private lateinit var articlesRepository: ArticlesRepository

    private lateinit var articlesAdapter: ArticlesAdapter

    private val viewModel: CreateOrderViewModel by viewModels {
        CreateOrderViewModelFactory(articlesRepository)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
}