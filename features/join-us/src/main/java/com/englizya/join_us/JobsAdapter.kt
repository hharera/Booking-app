package com.englizya.join_us

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.englizya.join_us.databinding.JobItemBinding

class JobsAdapter(
    private val jobItems: List<JobItem>,
    private val onItemClicked: (JobItem) -> Unit,
) : RecyclerView.Adapter<JobsAdapter.JobItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JobItemViewHolder {
        val bind = JobItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return JobItemViewHolder(binding = bind)
    }

    override fun onBindViewHolder(holder: JobItemViewHolder, position: Int) {
        holder.updateUI(jobItems.get(position))
        holder.itemView.setOnClickListener {
            onItemClicked(jobItems.get(position))
        }
    }

    override fun getItemCount(): Int {
        return jobItems.size
    }

    class JobItemViewHolder(private val binding: JobItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun updateUI(jobItem: JobItem) {
            binding.imageView9.setImageResource(jobItem.itemIconRes)
            binding.itemId.setText(jobItem.itemTitleRes)
        }

    }
}
