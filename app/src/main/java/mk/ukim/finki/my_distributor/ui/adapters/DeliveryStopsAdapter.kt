package mk.ukim.finki.my_distributor.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemOrderDetailBinding
import mk.ukim.finki.my_distributor.domain.dto.OrderDeliveryDto

class DeliveryStopsAdapter(
    private val onStopClicked: (OrderDeliveryDto) -> Unit
) : ListAdapter<OrderDeliveryDto, DeliveryStopsAdapter.StopVH>(OrderDtoDiffCallback) {
    inner class StopVH(val b: ItemOrderDetailBinding)
        : RecyclerView.ViewHolder(b.root){
        fun bind(o: OrderDeliveryDto){
            b.articleNameTextView.text = "Order #${o.id}"
            b.articleQuantityTextView.text = "Qty: [N/A]"
            b.unitPriceTextView.text = "Sum: $${o.ordSum}"
            b.totalPriceTextView.visibility = View.GONE
            b.root.setOnClickListener { onStopClicked(o) }
        }
    }

    override fun onCreateViewHolder(p: ViewGroup, v: Int) = StopVH(
        ItemOrderDetailBinding.inflate(
            LayoutInflater.from(p.context), p, false
        )
    )

    override fun onBindViewHolder(h: StopVH, pos: Int) = h.bind(getItem(pos))
}