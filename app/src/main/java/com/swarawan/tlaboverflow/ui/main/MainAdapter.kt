package com.swarawan.tlaboverflow.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.swarawan.tlaboverflow.R
import com.swarawan.tlaboverflow.common.convertDate
import com.swarawan.tlaboverflow.network.response.ArticleData
import kotlinx.android.synthetic.main.item_article.view.*

/**
 * Created by Rio Swarawn on 6/27/18.
 */
class MainAdapter(private val articles: MutableList<ArticleData>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewHolder = holder as ArticleViewHolder
        viewHolder.bind(articles[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(view)
    }

    override fun getItemCount(): Int = articles.size

    fun newList(loans: MutableList<ArticleData>) {
        this.articles.clear()
        this.articles.addAll(loans)
        this.notifyDataSetChanged()
    }

    fun updateList(loans: MutableList<ArticleData>) {
        this.articles.addAll(loans)
        this.notifyDataSetChanged()
    }

    fun getItem(position: Int): ArticleData = articles[position]

    inner class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(article: ArticleData) {
            itemView.textTitle.text = article.title
            itemView.textDisplayName.text = "by ${article.owner.displayName}"
            itemView.textCreation.text = (article.creationDate * 1000).convertDate()

            Glide.with(itemView.context)
                    .load(article.owner.profileImage)
                    .priority(Priority.IMMEDIATE)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .dontAnimate()
                    .into(itemView.imageProfile)
        }
    }
}