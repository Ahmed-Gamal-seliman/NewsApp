package com.example.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.databinding.ContentDetailsActivityBinding

class ContentDetailsActivity : AppCompatActivity(){
    private lateinit var binding: ContentDetailsActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContentDetailsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}