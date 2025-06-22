package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.R
import mk.ukim.finki.my_distributor.databinding.ItemSelectableOrderBinding
import mk.ukim.finki.my_distributor.domain.dto.OrderDto

class MultiSelectOrdersAdapter(
    private val onSelectionChanged: (List<OrderDto>) -> Unit
) : ListAdapter<OrderDto, MultiSelectOrdersAdapter.OrderViewHolder>(DiffCallback) {

    private val selectedOrders = mutableSetOf<OrderDto>()

    inner class OrderViewHolder(private val binding: ItemSelectableOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(order: OrderDto, isSelected: Boolean) = with(binding) {
            orderIdTextView.text = "Order #${order.id}"
            customerNameTextView.text = "Customer: ${order.customerId}"
            cityTextView.text = "City: ${order.customerId}"
            totalPriceTextView.text = "Total: $${order.ordSum}"
            dateCreatedTextView.text = "Date: ${order.ordDate}"

            root.setBackgroundColor(
                ContextCompat.getColor(
                    root.context,
                    if (isSelected) R.color.blue_500 else android.R.color.transparent
                )
            )

            root.setOnClickListener {
                toggleSelection(order)
            }
        }
    }

    private fun toggleSelection(order: OrderDto) {
        if (selectedOrders.contains(order)) {
            selectedOrders.remove(order)
        } else {
            selectedOrders.add(order)
        }
        notifyItemChanged(currentList.indexOf(order))
        onSelectionChanged(selectedOrders.toList())
    }

    fun getSelectedOrders(): List<OrderDto> = selectedOrders.toList()

    @SuppressLint("NotifyDataSetChanged")
    fun clearSelection() {
        selectedOrders.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemSelectableOrderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        val isSelected = selectedOrders.contains(order)
        holder.bind(order, isSelected)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OrderDto>() {
        override fun areItemsTheSame(oldItem: OrderDto, newItem: OrderDto): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: OrderDto, newItem: OrderDto): Boolean =
            oldItem == newItem
    }
}