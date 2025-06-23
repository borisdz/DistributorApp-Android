package mk.ukim.finki.my_distributor.util.callbacks

import androidx.recyclerview.widget.DiffUtil
import mk.ukim.finki.my_distributor.domain.dto.OrderSimpleDto

class OrderDiffCallback(
    private val oldList: List<OrderSimpleDto>,
    private val newList: List<OrderSimpleDto>
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