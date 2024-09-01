package com.example.newsapp

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.newsapp.ModelNewsContent.Article
import com.example.newsapp.databinding.NewsItemBinding

class NewsAdapter(private val newsList:ArrayList<Article?>?):Adapter<NewsAdapter.NewsViewHolder>() {

    var onItemClicked: ItemOnClickListener? =null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context))
        return NewsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
            holder.bind(newsList!![position])
    }

    override fun getItemCount(): Int = newsList?.size ?: 0

    inner class NewsViewHolder(private val binding: NewsItemBinding): ViewHolder(binding.root)
    {
            fun bind(article:Article?)
            {
                binding.tvAuthorName.text = article?.author
                Log.e("imageUrl",article?.urlToImage.toString())
                Glide
                    .with(binding.imvNews)
                    .load(article?.urlToImage)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.imvNews)
                binding.tvDescription.text = article?.description

                binding.root.setOnClickListener()
                {
                    onItemClicked?.onItemClick()
                }

            }
    }

    fun interface ItemOnClickListener{
        fun onItemClick()
    }
}