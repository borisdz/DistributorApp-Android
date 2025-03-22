package mk.ukim.finki.my_distributor.util.callbacks

import androidx.recyclerview.widget.DiffUtil
import mk.ukim.finki.my_distributor.domain.dto.OrderItem

object OrderItemDiffCallback : DiffUtil.ItemCallback<OrderItem>() {
    override fun areItemsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem.article.id == newItem.article.id
    }

    override fun areContentsTheSame(oldItem: OrderItem, newItem: OrderItem): Boolean {
        return oldItem == newItem
    }
}