package com.example.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.IntentCompat
import com.bumptech.glide.Glide
import com.example.newsapp.ModelNewsContent.Article
import com.example.newsapp.databinding.ContentDetailsActivityBinding

class ContentDetailsActivity : AppCompatActivity(){
    private lateinit var binding: ContentDetailsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val article= IntentCompat.getParcelableExtra(intent,Constants.ARTICLE,Article::class.java)

        val articleContent = intent.getStringExtra(Constants.ARTICLE_CONTENT)
        val articleAuthor = intent.getStringExtra(Constants.ARTICLE_AUTHOR)
        val articleDescription = intent.getStringExtra(Constants.ARTICLE_DESCRIPTION)
        val articleImage = intent.getStringExtra(Constants.ARTICLE_IMAGE_URL)


            binding.apply {
                Glide
                    .with(this@ContentDetailsActivity)
                    .load(articleImage)
                    .into(detalisImvNews)

                tvDetailsContent.text = articleContent
                tvDetailsDescription.text = articleDescription
                tvDetailsAuthor.text = articleAuthor
            }




    }
}