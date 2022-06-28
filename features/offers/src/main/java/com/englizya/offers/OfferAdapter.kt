package com.englizya.offers

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.model.model.Offer
import com.englizya.offers.databinding.OfferItemBinding
import com.squareup.picasso.Picasso

class OfferAdapter(
    private var offers: List<Offer>,
    ) : RecyclerView.Adapter<OfferAdapter.NavigationItemViewHolder>() {

    inner class NavigationItemViewHolder(private val binding: OfferItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(offer: Offer) {
            binding.offerTitle.text = offer.offerTitle
            binding.offerDescription.text = offer.offerDescription
            binding.offerEndDate.text = offer.endDate
            binding.offerStartDate.text = offer.startDate
            binding.offerDiscount.text = offer.discount.toString()
            Picasso.get().load(offer.offerImageUrl).into(binding.offerImage)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NavigationItemViewHolder {
        val binding = OfferItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationItemViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.updateUI(offers[position])
    }

    override fun getItemCount(): Int {
        return offers.size
    }

    fun setOffers(list: List<Offer>) {
        offers = (list)
        notifyDataSetChanged()
    }
}
