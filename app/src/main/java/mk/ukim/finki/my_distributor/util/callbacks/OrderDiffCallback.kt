package mk.ukim.finki.my_distributor.util.callbacks

import androidx.recyclerview.widget.DiffUtil
import mk.ukim.finki.my_distributor.domain.dto.OrderDto

class OrderDiffCallback(
    private val oldList: List<OrderDto>,
    private val newList: List<OrderDto>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}