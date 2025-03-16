package mk.ukim.finki.my_distributor.ui.fragments.customer

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateOrderBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.articlesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        articlesAdapter = ArticlesAdapter(emptyList()) { article ->
            Toast.makeText(
                requireContext(),
                "Clicked: ${article.name}",
                Toast.LENGTH_SHORT
            ).show()
        }
        binding.articlesRecyclerView.adapter = articlesAdapter

        viewModel.articles.observe(viewLifecycleOwner) { articles ->
            articlesAdapter.updateData(articles)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMsg ->
            Toast.makeText(
                requireContext(),
                "Error: $errorMsg",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.searchArticles(s?.toString() ?: "")
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        binding.reviewOrderButton.setOnClickListener {
            Toast.makeText(
                requireContext(),
                "Review order clicked",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}