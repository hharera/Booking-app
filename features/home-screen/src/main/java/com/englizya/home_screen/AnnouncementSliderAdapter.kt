package com.englizya.home_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import com.englizya.home_screen.databinding.CardViewAnnouncementBinding
import com.englizya.model.model.Announcement
import com.smarteist.autoimageslider.SliderViewAdapter
import com.squareup.picasso.Picasso

class AnnouncementSliderAdapter(
    private var announcements: List<Announcement>,
) : SliderViewAdapter<AnnouncementSliderAdapter.Holder>() {

    inner class Holder(private val binding: CardViewAnnouncementBinding) :
        SliderViewAdapter.ViewHolder(binding.root) {
        fun updateUI(announcement: Announcement) {
            Picasso.get().load(announcement.announcementImageUrl).into(binding.announcementImage)
        }

    }

    override fun getCount(): Int {
        return announcements.size
    }


    fun setAnnouncements(list: List<Announcement>) {
        announcements = (list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup?): Holder {
        val binding = CardViewAnnouncementBinding.inflate(
            LayoutInflater.from(parent?.context),
            parent,
            false
        )
        return Holder(binding = binding)
    }

    override fun onBindViewHolder(viewHolder: Holder?, position: Int) {
        viewHolder?.updateUI(announcements[position])
//        viewHolder.
    }
}
