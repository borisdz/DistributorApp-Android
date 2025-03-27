package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemOrderDetailBinding
import mk.ukim.finki.my_distributor.domain.dto.ArticleDto

class DeliveryOrdersAdapter(
    private val onItemClicked: (ArticleDto) -> Unit
) : ListAdapter<ArticleDto, DeliveryOrdersAdapter.DeliveryOrderViewHolder>(ArticleDiffCallback) {

    inner class DeliveryOrderViewHolder(private val binding: ItemOrderDetailBinding)
        : RecyclerView.ViewHolder(binding.root){
        @SuppressLint("SetTextI18n")
        fun bind(article: ArticleDto){
            binding.articleNameTextView.text = article.name
            binding.articleQuantityTextView.text = "Qty: ${article.quantity}"
            binding.unitPriceTextView.text = "Unit: $${article.price}"
            val totalPrice = article.price.toDouble() * article.quantity
            binding.totalPriceTextView.text = "Total: $${"%.2f".format(totalPrice)}"
            binding.root.setOnClickListener { onItemClicked(article) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryOrderViewHolder {
        val binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DeliveryOrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryOrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}