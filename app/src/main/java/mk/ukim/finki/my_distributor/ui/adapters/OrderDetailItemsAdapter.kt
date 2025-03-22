package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemOrderDetailBinding
import mk.ukim.finki.my_distributor.domain.dto.ArticleDto

class OrderDetailItemsAdapter: ListAdapter<ArticleDto, OrderDetailItemsAdapter.OrderItemViewHolder>(ArticleDiffCallback) {

    inner class OrderItemViewHolder(private val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(article: ArticleDto) {
            binding.articleNameTextView.text = article.name
            binding.articleManufacturerNameTextView.text = article.manufacturer
            binding.articleQuantityTextView.text = "Qty: ${article.quantity}"
            binding.unitPriceTextView.text = "Unit: $${article.price}"
            val totalPrice = article.price.toDouble() * article.quantity
            binding.totalPriceTextView.text = "Total: $${"%.2f".format(totalPrice)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

object ArticleDiffCallback : DiffUtil.ItemCallback<ArticleDto>() {
    override fun areItemsTheSame(oldItem: ArticleDto, newItem: ArticleDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: ArticleDto, newItem: ArticleDto): Boolean {
        return oldItem == newItem
    }
}