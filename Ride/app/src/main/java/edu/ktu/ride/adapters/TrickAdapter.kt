package edu.ktu.ride.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import edu.ktu.ride.database.Trick
import edu.ktu.ride.databinding.TrickItemBinding

class TrickAdapter(val clickListener: TrickClickListener) :
    ListAdapter<Trick, TrickAdapter.ViewHolder>(TrickDiffCallBack()) {

    class ViewHolder(val binding: TrickItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(trick: Trick, clickListener: TrickClickListener) {
                    binding.trick = trick
                    binding.clickListener = clickListener
                }
            }

    class TrickDiffCallBack : DiffUtil.ItemCallback<Trick>() {
        override fun areItemsTheSame(oldItem: Trick, newItem: Trick): Boolean {
            return oldItem.trickId == newItem.trickId
        }

        override fun areContentsTheSame(oldItem: Trick, newItem: Trick): Boolean {
            return oldItem == newItem
        }
    }

    class TrickClickListener(val clickListener: (trick: Trick) -> Unit) {
        fun onClick(trick: Trick) {
            clickListener(trick)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            TrickItemBinding
                .inflate(
                    LayoutInflater
                        .from(parent.context)
                )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }
}