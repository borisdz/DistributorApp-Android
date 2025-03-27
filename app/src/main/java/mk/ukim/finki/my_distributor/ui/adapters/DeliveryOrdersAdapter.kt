package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemOrderDetailBinding
import mk.ukim.finki.my_distributor.domain.dto.ArticleDto
import mk.ukim.finki.my_distributor.domain.dto.OrderDto

object OrderDtoDiffCallback : DiffUtil.ItemCallback<OrderDto>() {
    override fun areItemsTheSame(oldItem: OrderDto, newItem: OrderDto): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: OrderDto, newItem: OrderDto): Boolean {
        return oldItem == newItem
    }
}

class DeliveryOrdersAdapter : ListAdapter<OrderDto, DeliveryOrdersAdapter.OrderViewHolder>(OrderDtoDiffCallback) {

    inner class OrderViewHolder(private val binding: ItemOrderDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(order: OrderDto) {
            binding.articleNameTextView.text = "Order #${order.id}"
            binding.articleQuantityTextView.text = "Qty: [N/A]"
            binding.unitPriceTextView.text = "Unit: $${order.ordSum}"
            binding.totalPriceTextView.text = "Total: $${order.ordSum}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}