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
import mk.ukim.finki.my_distributor.domain.dto.OrderSimpleDto

class MultiSelectOrdersAdapter(
    private val onSelectionChanged: (OrderSimpleDto) -> Unit
) : ListAdapter<OrderSimpleDto, MultiSelectOrdersAdapter.OrderViewHolder>(DiffCallback) {

    private val selectedOrders = mutableSetOf<OrderSimpleDto>()

    inner class OrderViewHolder(private val binding: ItemSelectableOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(order: OrderSimpleDto, isSelected: Boolean) = with(binding) {
            orderIdTextView.text        = "Order #${order.id}"
            customerNameTextView.text   = "Customer: ${order.customerId}"
            cityTextView.text           = "City: ${order.customerId}"
            totalPriceTextView.text     = "Total: $${order.ordSum}"
            dateCreatedTextView.text    = "Date: ${order.ordDate}"

            root.setBackgroundColor(
                ContextCompat.getColor(
                    root.context,
                    if (isSelected) R.color.blue_500 else android.R.color.transparent
                )
            )

            root.setOnClickListener {
                if (!selectedOrders.remove(order)) {
                    selectedOrders.add(order)
                }
                notifyItemChanged(currentList.indexOf(order))
                onSelectionChanged(order)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderViewHolder(
            ItemSelectableOrderBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = getItem(position)
        holder.bind(order, selectedOrders.contains(order))
    }

    companion object DiffCallback : DiffUtil.ItemCallback<OrderSimpleDto>() {
        override fun areItemsTheSame(o1: OrderSimpleDto, o2: OrderSimpleDto) = o1.id == o2.id
        override fun areContentsTheSame(o1: OrderSimpleDto, o2: OrderSimpleDto) = o1 == o2
    }
}