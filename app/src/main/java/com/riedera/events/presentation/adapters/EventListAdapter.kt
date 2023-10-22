package com.riedera.events.presentation.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.riedera.events.R
import com.riedera.events.domain.MyDateTimeFormatters
import com.riedera.events.domain.models.Event

class EventListAdapter(private var eventList: MutableList<Event>) :
    RecyclerView.Adapter<EventListAdapter.ItemViewHolder>() {

    interface OnEventClickListener {
        fun onEventClick(event: Event)
    }



    fun getList(): List<Event> = eventList.toList()

    private var onEventClickListener: OnEventClickListener? = null


    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val cardView = itemView.findViewById<CardView>(R.id.card_view)
        val poster = itemView.findViewById<ImageView>(R.id.poster)
        val isVerified = itemView.findViewById<ImageView>(R.id.is_verified)
        val creator = itemView.findViewById<TextView>(R.id.creator)
        val name = itemView.findViewById<TextView>(R.id.name)
        val date = itemView.findViewById<TextView>(R.id.date)
        val participantsNumber = itemView.findViewById<TextView>(R.id.participants_number)

        fun bind(item: Event) {

            if (!item.verified) isVerified.visibility = View.INVISIBLE
            creator.text = item.creator
            name.text = item.name
            date.text = item.dateAndTime.format(MyDateTimeFormatters.dateShort)
            poster.load(item.image)
            participantsNumber.text = item.participants.toString()

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.event_view_holder, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return eventList.size
    }

    fun isEmpty(): Boolean = eventList.isEmpty()

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = eventList[position]


        holder.cardView.setOnClickListener {
            onEventClickListener?.onEventClick(currentItem)
        }

        holder.bind(currentItem)
    }

    fun setOnEventClickListener(listener: OnEventClickListener) {
        this.onEventClickListener = listener
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<Event>) {
        eventList = newData.toMutableList()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun removeItem(position: Int) {
        eventList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(0, eventList.size)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItem(item: Event, position: Int) {
        eventList.add(position, item)
        notifyItemInserted(position)
        notifyItemRangeChanged(0, eventList.size)
        notifyDataSetChanged()
    }

    fun updateItem(position: Int, newItem: Event) {
        eventList[position] = newItem
        notifyItemChanged(position)
        notifyDataSetChanged()
    }
}