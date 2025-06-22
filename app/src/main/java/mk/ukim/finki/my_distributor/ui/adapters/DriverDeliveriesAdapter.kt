package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemDriverDeliveryBinding
import mk.ukim.finki.my_distributor.domain.dto.DeliverySimpleDto

class DriverDeliveriesAdapter(
    private var deliveries: List<DeliverySimpleDto>,
    private val onDeliveryClicked: (DeliverySimpleDto) -> Unit
) : RecyclerView.Adapter<DriverDeliveriesAdapter.DeliveryViewHolder>() {

    inner class DeliveryViewHolder(private val binding: ItemDriverDeliveryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(delivery: DeliverySimpleDto) {
            binding.deliveryDateTextView.text = "Delivery Date: ${delivery.deliveryDate}"
            binding.statusTextView.text = "Status: ${delivery.deliveryStatusName}"
            binding.routeInfoTextView.text = "Route estimate"
            binding.root.setOnClickListener {
                onDeliveryClicked(delivery)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = ItemDriverDeliveryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DeliveryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DeliveryViewHolder, position: Int) {
        holder.bind(deliveries[position])
    }

    override fun getItemCount(): Int {
        return deliveries.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newDelivereis: List<DeliverySimpleDto>) {
        deliveries = newDelivereis
        notifyDataSetChanged()
    }
}