package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemManagerDeliveryBinding
import mk.ukim.finki.my_distributor.domain.dto.DeliverySimpleDto

class ManagerDeliveriesAdapter(
    private var items: List<DeliverySimpleDto>,
    private val onClick: (DeliverySimpleDto) -> Unit
) : RecyclerView.Adapter<ManagerDeliveriesAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemManagerDeliveryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(del: DeliverySimpleDto) = with(binding) {
            deliveryDateTextView.text  = "Date: ${del.deliveryDate}"
            statusTextView.text        = "Status: ${del.deliveryStatusName}"
            root.setOnClickListener { onClick(del) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemManagerDeliveryBinding.inflate(
            LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(items[position])

    override fun getItemCount() = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newItems: List<DeliverySimpleDto>) {
        items = newItems
        notifyDataSetChanged()
    }
}