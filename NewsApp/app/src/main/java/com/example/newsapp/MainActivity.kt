package com.example.newsapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsapp.API.ApiManager
import com.example.newsapp.ModelNewsSource.SourceResponse
import com.example.newsapp.ModelNewsSource.SourcesItem
import com.example.newsapp.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    lateinit var newsFragment:NewsFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        showProgressBar()

        

        buttonTryAgainListener()

        sendRequestForSourcesAndGetResponse()



    }

    private fun pushFragment(fragment: Fragment, id: String?) {

        val bundle:Bundle = Bundle()
        id?.let {
            bundle.putString("soruceId", it)
            fragment.arguments = bundle
        }

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
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
        ApiManager.getRetrofitService().getNewsSources().enqueue(object: Callback<SourceResponse> {
            override fun onResponse(p0: Call<SourceResponse>, response: Response<SourceResponse>) {
                if(response.isSuccessful)
                {
                    clearProgressBar()
                    response.body()?.sources?.let {
                    showTabs(it)
                    onTabsClickListener(it)
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

    private fun onTabsClickListener(sources: List<SourcesItem?>) {
        binding.tabLayout.setOnTabSelectedListener(object:TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {

                if (tab != null) {
                    pushFragment(NewsFragment(),sources[tab.position]?.id)
                }


            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    pushFragment(NewsFragment(),sources[tab.position]?.id)
                }
            }
        })
        binding.tabLayout.getTabAt(0)?.select()
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