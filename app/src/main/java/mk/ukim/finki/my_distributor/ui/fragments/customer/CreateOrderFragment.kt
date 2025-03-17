package mk.ukim.finki.my_distributor.ui.fragments.customer


import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.data.api.RetrofitClient
import mk.ukim.finki.my_distributor.data.local.UserPreferences
import mk.ukim.finki.my_distributor.data.repository.ArticlesRepository
import mk.ukim.finki.my_distributor.databinding.FragmentCreateOrderBinding
import mk.ukim.finki.my_distributor.domain.dto.ArticleDto
import mk.ukim.finki.my_distributor.domain.dto.OrderItem
import mk.ukim.finki.my_distributor.ui.adapters.ArticlesAdapter
import mk.ukim.finki.my_distributor.ui.viewmodel.CreateOrderViewModel
import mk.ukim.finki.my_distributor.ui.viewmodel.CreateOrderViewModelFactory

class CreateOrderFragment : Fragment() {

    private var _binding: FragmentCreateOrderBinding? = null
    private val binding get() = _binding!!

    private val articlesRepository: ArticlesRepository by lazy {
        ArticlesRepository(
            RetrofitClient.getArticleApiService(UserPreferences.getInstance(requireContext()))
        )
    }

    private lateinit var articlesAdapter: ArticlesAdapter

    private val orderItems = mutableListOf<OrderItem>()

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
            showQuantityDialog(article)
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
            findNavController().navigate(R.id.action_createOrderFragment_to_orderReviewFragment)
        }
    }

    private fun showQuantityDialog(article: ArticleDto) {
        val dialogView = LayoutInflater
            .from(requireContext())
            .inflate(R.layout.dialog_add_article, null)
        val quantityEditText = dialogView.findViewById<EditText>(R.id.quantityEditText)
        val confirmButton = dialogView.findViewById<Button>(R.id.confirmQuantityButton)

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Add ${article.name}")
            .setView(dialogView)
            .setCancelable(true)
            .create()

        confirmButton.setOnClickListener {
            val quantityStr = quantityEditText.text.toString()
            val quantity = quantityStr.toIntOrNull()
            if (quantity != null && quantity > 0){
                orderItems.add(OrderItem(article,quantity))
                Toast.makeText(
                    requireContext(),
                    "Added ${article.name} x $quantity",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter a valid quantity",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        dialog.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}