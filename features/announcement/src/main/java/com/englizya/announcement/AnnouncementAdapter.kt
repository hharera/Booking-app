package com.englizya.announcement

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.announcement.databinding.AnnnouncementItemBinding
import com.englizya.model.model.Announcement
import com.squareup.picasso.Picasso

class AnnouncementAdapter(
    private var announcements :List<Announcement>,
    ) : RecyclerView.Adapter<AnnouncementAdapter.NavigationItemViewHolder>() {

    inner class NavigationItemViewHolder(private val binding:AnnnouncementItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun updateUI(announcement: Announcement) {
            Picasso.get().load(announcement.announcementImageUrl).into(binding.announcementImage)
            itemView.setOnClickListener{
                NavigateToAnnouncementDetails(announcement.announcementId)
            }
        }

        private fun NavigateToAnnouncementDetails(announcementId: Int) {

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NavigationItemViewHolder {
        val binding = AnnnouncementItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NavigationItemViewHolder(binding = binding)
    }

    override fun onBindViewHolder(holder: NavigationItemViewHolder, position: Int) {
        holder.updateUI(announcements[position])
    }

    override fun getItemCount(): Int {
        return announcements.size
    }

    fun setAnnouncements(list: List<Announcement>) {
        announcements = (list)
        notifyDataSetChanged()
    }
}
