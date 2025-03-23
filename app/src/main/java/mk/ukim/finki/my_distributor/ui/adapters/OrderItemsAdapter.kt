package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemOrderDetailBinding
import mk.ukim.finki.my_distributor.domain.dto.OrderItem

class OrderItemsAdapter :
    ListAdapter<OrderItem, OrderItemsAdapter.OrderItemViewHolder>(OrderItemDiffCallback) {

    inner class OrderItemViewHolder(private val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: OrderItem) {
            binding.articleNameTextView.text = item.article.name
            binding.articleQuantityTextView.text = "Qty: ${item.quantity}"
            binding.unitPriceTextView.text = "Unit: $${item.article.price}"
            val totalPrice = item.article.price * item.quantity.toBigDecimal()
            binding.totalPriceTextView.text = "Total: $${"%.2f".format(totalPrice)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val binding =
            ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}