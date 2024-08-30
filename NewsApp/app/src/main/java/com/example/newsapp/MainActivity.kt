package com.example.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.newsapp.API.WebService
import com.example.newsapp.Model.SourceResponse
import com.example.newsapp.Model.SourcesItem
import com.example.newsapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        showProgressBar()

        buttonTryAgainListener()

        sendRequestForSourcesAndGetResponse()



    }

    private fun buttonTryAgainListener() {
        binding.btnTryAgain.setOnClickListener()
        {
            clearErrorTextView()
            clearTryAgainButton()
            showProgressBar()
            sendRequestForSourcesAndGetResponse()
        }
    }

    private fun clearTryAgainButton() {
        binding.btnTryAgain.isVisible=false
    }

    private fun clearErrorTextView() {
        binding.tvErrorMessage.isVisible=false
    }

    private fun sendRequestForSourcesAndGetResponse() {
        WebService.getRetrofitService().getNewsSources().enqueue(object: Callback<SourceResponse> {
            override fun onResponse(p0: Call<SourceResponse>, response: Response<SourceResponse>) {
                if(response.isSuccessful)
                {
                    clearProgressBar()
                    response.body()?.sources?.let {
                        showTabs(it)
                    }


                }

            }

            override fun onFailure(p0: Call<SourceResponse>, error: Throwable) {
                showError(error.message)
                showButtonTryAgain()
                clearProgressBar()
            }
        })
    }

    private fun showButtonTryAgain() {
        binding.btnTryAgain.isVisible=true
    }

    private fun showError(message: String?) {
       binding.tvErrorMessage.text= message
       binding.tvErrorMessage.isVisible=true
    }

    private fun showTabs(articles: List<SourcesItem?>) {
        for (element in articles)
            binding.tabLayout.apply{
                addTab(newTab().setText(element?.name))
            }
        binding.tabLayout.isVisible = true
    }

    private fun clearProgressBar() {
        binding.progressBar.isVisible=false
    }

    private fun showProgressBar() {
        binding.progressBar.isVisible = true
    }
}