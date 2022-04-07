package com.englizya.select_seat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.englizya.model.model.Seat
import com.englizya.select_seat.SeatAdapter.SeatViewHolder
import com.englizya.select_seat.databinding.CardViewSeatBinding

class SeatAdapter(
    private val seatList: List<Seat>,
    private val onItemClicked: (Seat) -> Unit,
) : RecyclerView.Adapter<SeatViewHolder>() {

    private val seatIterator = seatList.iterator()

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            in (0..4) -> if (position % 5 == 0) 1 else if (position % 5 == 4) 3 else 2
            in (5..59) -> if (position % 5 == 2) 2 else 4
            else -> 4
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SeatViewHolder {
        return SeatViewHolder(
            CardViewSeatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            viewType = viewType,
            seat = if (viewType == 4 && seatIterator.hasNext())
                seatIterator.next()
            else
                null
        )
    }

    override fun onBindViewHolder(holder: SeatViewHolder, position: Int) {}

    override fun getItemCount(): Int {
        return 65
    }

    inner class SeatViewHolder(
        private val binding: CardViewSeatBinding,
        private var isSelected: Boolean = false,
        private val seat: Seat? = null,
        private val viewType: Int,
    ) : ViewHolder(binding.root) {

        init {
            when (viewType) {
                1 -> setImage(R.drawable.ic_driver_steering_wheel)
                3 -> setImage(R.drawable.ic_exit)
                4 -> updateUI()
            }
        }

        private fun setupListener() {
            binding.root.setOnClickListener {
                onItemClicked(seat!!)
                select()
            }
        }

        private fun setImage(icDriverSteeringWheel: Int) {
            binding.seatImage.setImageResource(icDriverSteeringWheel)
        }

        private fun select() {
            if (seat!!.closeFlag == 0) {
                if (isSelected) {
                    binding.seatImage.setImageResource(R.drawable.ic_seat_available)
                } else {
                    binding.seatImage.setImageResource(R.drawable.ic_seat_selected)
                }
                isSelected = isSelected.not()
            }
        }

        private fun updateUI() {
            if (seat!!.closeFlag == 0 && seat.source != null) {
                binding.seatImage.setImageResource(R.drawable.ic_seat_semi_booked)
            } else if (seat.closeFlag == 0) {
                binding.seatImage.setImageResource(R.drawable.ic_seat_available)
            } else {
                binding.seatImage.setImageResource(R.drawable.ic_seat_booked)
            }
        }
    }
}
