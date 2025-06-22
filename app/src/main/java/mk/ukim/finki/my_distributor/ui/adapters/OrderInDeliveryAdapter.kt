package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemOrderInDeliveryBinding
import mk.ukim.finki.my_distributor.domain.dto.OrderDeliveryDto

object OrderDiffCallback : DiffUtil.ItemCallback<OrderDeliveryDto>() {
    override fun areItemsTheSame(o1: OrderDeliveryDto, o2: OrderDeliveryDto) = o1.id == o2.id
    override fun areContentsTheSame(o1: OrderDeliveryDto, o2: OrderDeliveryDto) = o1 == o2
}
class OrderInDeliveryAdapter :
    ListAdapter<OrderDeliveryDto, OrderInDeliveryAdapter.VH>(OrderDiffCallback) {

    inner class VH(private val binding: ItemOrderInDeliveryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(o: OrderDeliveryDto) {
            binding.orderIdTextView.text = "Order #${o.id}"
            binding.totalPriceTextView.text = "Total: $${o.ordSum}"
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, v: Int) = VH(
        ItemOrderInDeliveryBinding.inflate(
            LayoutInflater.from(p.context), p, false
        )
    )

    override fun onBindViewHolder(h: VH, pos: Int) = h.bind(getItem(pos))
}