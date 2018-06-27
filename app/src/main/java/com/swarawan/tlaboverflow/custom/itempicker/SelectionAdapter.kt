package com.swarawan.tlaboverflow.custom.itempicker

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import com.swarawan.corelibrary.extensions.goneIf
import com.swarawan.tlaboverflow.R
import io.reactivex.subjects.PublishSubject
import kotlinx.android.synthetic.main.item_selection.view.*

/**
 * Created by rioswarawan on 4/13/18.
 */
class SelectionAdapter(private val items: MutableList<Selection>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var publisher: PublishSubject<List<Selection>> = PublishSubject.create()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_selection, parent, false)
        return SelectionViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val categoryHolder = holder as SelectionViewHolder
        categoryHolder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    private fun updateListDisplay(position: Int) {
        items.forEach { it.isSelected = false }
        items[position].isSelected = true
        notifyDataSetChanged()

        publisher.onNext(items.filter { it.isSelected })
    }

    inner class SelectionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(category: Selection) {
            with(itemView) {
                textName.text = category.name
                imgCheck.goneIf(!category.isSelected)

                RxView.clicks(itemView).subscribe { updateListDisplay(adapterPosition) }
            }
        }
    }
}