package com.riedera.events.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.riedera.events.R
import com.riedera.events.domain.models.Club

class ClubListAdapter(private var clubList: MutableList<Club>) :
    RecyclerView.Adapter<ClubListAdapter.ItemViewHolder>() {

    interface OnClubClickListener {
        fun onClubClick(club: Club)
    }

    fun getList(): List<Club> = clubList.toList()

    private var onClubClickListener: OnClubClickListener? = null


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        val poster = itemView.findViewById<ImageView>(R.id.poster)
        val name = itemView.findViewById<TextView>(R.id.name)
        val btn = itemView.findViewById<Button>(R.id.btn_vh)
        val participantsNum = itemView.findViewById<TextView>(R.id.participants_number)

        fun bind(item: Club) {
            name.text = item.name
            poster.load(item.image)
            participantsNum.text = item.participants.toString()

            btn.setOnClickListener {
                btn.text = "Unsubscribe"
                btn.isEnabled = false
                btn.setBackgroundResource(R.drawable.subscribe_button_shape)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.club_view_holder, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return clubList.size
    }

    fun isEmpty(): Boolean = clubList.isEmpty()

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = clubList[position]

        holder.cardView.setOnClickListener {
            onClubClickListener?.onClubClick(currentItem)
        }

        holder.bind(currentItem)
    }


    fun setOnClubClickListener(listener: OnClubClickListener) {
        this.onClubClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Club>) {
        clubList = newData.toMutableList()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        clubList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(0, clubList.size)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: Club, position: Int) {
        clubList.add(position, item)
        notifyItemInserted(position)
        notifyItemRangeChanged(0, clubList.size)
        notifyDataSetChanged()
    }

    fun updateItem(position: Int, newItem: Club) {
        clubList[position] = newItem
        notifyItemChanged(position)
        notifyDataSetChanged()
    }
}