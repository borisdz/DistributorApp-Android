package mk.ukim.finki.my_distributor.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemDeliveryBinding
import mk.ukim.finki.my_distributor.domain.dto.DeliveryDto

class DeliveriesAdapter(
    private var deliveries: List<DeliveryDto>,
    private val onItemClicked: (DeliveryDto)->Unit
) : RecyclerView.Adapter<DeliveriesAdapter.DeliveryViewHolder>(){

    inner class DeliveryViewHolder(private val binding: ItemDeliveryBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(delivery: DeliveryDto){
                    binding.deliveryTitle.text = delivery.delDate.toString()
                    binding.deliverySummary.text = delivery.driverName
                    binding.root.setOnClickListener { onItemClicked(delivery) }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryViewHolder {
        val binding = ItemDeliveryBinding.inflate(
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

    fun updateData(newDeliveries: List<DeliveryDto>){
        deliveries = newDeliveries
        notifyDataSetChanged()
    }
}