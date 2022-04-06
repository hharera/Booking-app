package com.englizya.select_seat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.englizya.model.model.ReservationSeat
import com.englizya.select_seat.databinding.CardViewSeatBinding

class SeatAdapter(
    private val seatList: List<ReservationSeat>,
    private val onItemClicked: (ReservationSeat) -> Unit,
) : RecyclerView.Adapter<SeatAdapter.BaseViewHolder>() {

    private val seatIterator = seatList.iterator()

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            in (0..4) -> if (position % 5 == 0) 1 else if (position % 5 == 4) 3 else 2
            in (5..seatList.lastIndex - 5) -> if (position % 5 == 2) 2 else 4
            else -> 4
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (viewType) {
            1 -> DriverViewHolder(
                CardViewSeatBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            2 -> NoneViewHolder(
                CardViewSeatBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            3 -> DoorViewHolder(
                CardViewSeatBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> SeatViewHolder(
                CardViewSeatBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                seat = seatIterator.next()
            )
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.updateUI()
    }

    override fun getItemCount(): Int {
        return seatList.size + 16
    }

    abstract class BaseViewHolder(
        binding: View
    ) : ViewHolder(binding) {
        abstract fun updateUI()
    }

    class DriverViewHolder(
        private val binding: CardViewSeatBinding
    ) : BaseViewHolder(binding.root) {

        override fun updateUI() {
            binding.seatImage.setImageResource(R.drawable.ic_driver_steering_wheel)
        }
    }

    class DoorViewHolder(
        private val binding: CardViewSeatBinding
    ) : BaseViewHolder(binding.root) {

        override fun updateUI() {
            binding.seatImage.setImageResource(R.drawable.ic_exit)
        }
    }

    class NoneViewHolder(
        binding: CardViewSeatBinding
    ) : BaseViewHolder(binding.root) {

        override fun updateUI() {}
    }

    inner class SeatViewHolder(
        private val binding: CardViewSeatBinding,
        private var isSelected: Boolean = false,
        private val seat: ReservationSeat,
    ) : BaseViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener {
                onItemClicked(seat)
                select()
            }
        }

        private fun select() {
            if (seat.closeFlag == 0) {
                if (isSelected) {
                    binding.seatImage.setImageResource(R.drawable.ic_seat_available)
                } else {
                    binding.seatImage.setImageResource(R.drawable.ic_seat_selected)
                }
                isSelected = isSelected.not()
            }
        }

        override fun updateUI() {
            if (seat.closeFlag == 0) {
                binding.seatImage.setImageResource(R.drawable.ic_seat_available)
            } else {
                binding.seatImage.setImageResource(R.drawable.ic_seat_booked)
            }
        }
    }
}
