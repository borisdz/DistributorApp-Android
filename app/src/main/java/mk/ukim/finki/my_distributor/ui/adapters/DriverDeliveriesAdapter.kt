package mk.ukim.finki.my_distributor.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemDriverDeliveryBinding
import mk.ukim.finki.my_distributor.domain.dto.DeliveryDto

class DriverDeliveriesAdapter(
    private var deliveries: List<DeliveryDto>,
    private val onDeliveryClicked: (DeliveryDto) -> Unit
) : RecyclerView.Adapter<DriverDeliveriesAdapter.DeliveryViewHolder>() {

    inner class DeliveryViewHolder(private val binding: ItemDriverDeliveryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(delivery: DeliveryDto) {
            binding.deliveryDateTextView.text = "Delivery Date: ${delivery.delDate}"
            binding.statusTextView.text = "Status: ${delivery.delStatus}"
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

    fun updateData(newDelivereis: List<DeliveryDto>){
        deliveries = newDelivereis
        notifyDataSetChanged()
    }
}