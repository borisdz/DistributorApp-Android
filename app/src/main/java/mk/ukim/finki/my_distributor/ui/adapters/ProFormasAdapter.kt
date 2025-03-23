package mk.ukim.finki.my_distributor.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import mk.ukim.finki.my_distributor.databinding.ItemProformaBinding
import mk.ukim.finki.my_distributor.domain.dto.ProFormaDto
import mk.ukim.finki.my_distributor.util.callbacks.ProFormaDiffCallback

class ProFormasAdapter(
    private var proFormas: List<ProFormaDto>,
    private val onItemClicked: (ProFormaDto) -> Unit
) : RecyclerView.Adapter<ProFormasAdapter.ProFormaViewHolder>() {

    inner class ProFormaViewHolder(private val binding: ItemProformaBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(proForma: ProFormaDto) {
            binding.proFormaTitle.text = proForma.customerName
            binding.proFormaSummary.text = proForma.statusName
            binding.root.setOnClickListener { onItemClicked(proForma) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProFormaViewHolder {
        val binding = ItemProformaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProFormaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProFormaViewHolder, position: Int) {
        holder.bind(proFormas[position])
    }

    override fun getItemCount(): Int {
        return proFormas.size
    }

    fun updateData(newProFormas: List<ProFormaDto>) {
        val diffCallback = ProFormaDiffCallback(this.proFormas, newProFormas)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.proFormas = newProFormas
        diffResult.dispatchUpdatesTo(this)
    }
}