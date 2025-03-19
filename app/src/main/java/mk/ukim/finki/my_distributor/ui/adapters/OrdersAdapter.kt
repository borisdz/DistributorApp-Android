package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemOrderBinding
import mk.ukim.finki.my_distributor.domain.dto.OrderDto
import mk.ukim.finki.my_distributor.util.callbacks.ArticleDiffCallback
import mk.ukim.finki.my_distributor.util.callbacks.OrderDiffCallback

class OrdersAdapter(
    private var orders: List<OrderDto>,
    private val onItemClicked: (OrderDto) -> Unit
) : RecyclerView.Adapter<OrdersAdapter.OrderViewHolder>() {

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(order: OrderDto) {
            binding.orderTitle.text = order.customerId.toString()
            binding.orderSummary.text = order.ordSum.toString()
            binding.root.setOnClickListener { onItemClicked(order) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int {
        return orders.size
    }

    fun updateData(newOrders: List<OrderDto>) {
        val diffCallback = OrderDiffCallback(this.orders, newOrders)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.orders = newOrders
        diffResult.dispatchUpdatesTo(this)
    }
}