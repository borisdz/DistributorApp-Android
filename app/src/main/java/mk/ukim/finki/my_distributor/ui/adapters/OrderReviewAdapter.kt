package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemOrderReviewBinding
import mk.ukim.finki.my_distributor.domain.dto.OrderItem
import mk.ukim.finki.my_distributor.util.callbacks.OrderReviewDiffCallback

class OrderReviewAdapter : ListAdapter<OrderItem, OrderReviewAdapter.OrderReviewViewHolder>(OrderItemDiffCallback) {

    inner class OrderReviewViewHolder(private val binding: ItemOrderReviewBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(orderItem: OrderItem) {
            binding.articleName.text = orderItem.article.name
            binding.articleQuantity.text = "Qty: ${orderItem.quantity}"
            binding.articlePrice.text = "$${orderItem.article.price}"
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderReviewViewHolder {
        val binding = ItemOrderReviewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderReviewViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: OrderReviewViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

}

object OrderItemDiffCallback : DiffUtil.ItemCallback<OrderItem>() {
    override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem.article.id == newItem.article.id
    }

    override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem == newItem
    }
}
