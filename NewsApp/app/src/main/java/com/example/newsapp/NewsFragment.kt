package com.example.newsapp

import android.content.Context
import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.newsapp.API.ApiManager
import com.example.newsapp.ModelNewsContent.Article
import com.example.newsapp.ModelNewsContent.SourceResponse
import com.example.newsapp.databinding.FragmentNewsContentBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsFragment: Fragment() {

    lateinit var binding: FragmentNewsContentBinding
    var articles:List<Article?>? =null
    var newsAdapter:NewsAdapter?=null


    override fun onAttach(context: Context) {
        super.onAttach(context)


    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewsContentBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        showProgressBar()

        /* intialize Adapter and recycler View*/
        initViews()


        /* Call Api */
        arguments?.getString(Constants.SOURCE_ID)?.let {
            val id: String = it
            sendNewsContentRequestAndGetResponse(id)
        }




        newsAdapter?.onItemClicked = NewsAdapter.ItemOnClickListener {
            val intent: Intent = Intent(context,ContentDetailsActivity::class.java)

            /* Crach here when passing article as parcelable object */
//            intent.putExtra(Constants.ARTICLE,it)

            intent.putExtra(Constants.ARTICLE_IMAGE_URL,it.urlToImage)
            intent.putExtra(Constants.ARTICLE_AUTHOR,it.author)
            intent.putExtra(Constants.ARTICLE_CONTENT,it.content)
            intent.putExtra(Constants.ARTICLE_DESCRIPTION,it.description)

            startActivity(intent)

        }

    }

    private fun initViews() {
        newsAdapter = NewsAdapter(null)
        binding.rvNewsContent.adapter=newsAdapter
    }

    private fun showProgressBar() {
        binding.framgentProgressBar.isVisible = true
    }
    private fun clearProgressBar() {
        binding.framgentProgressBar.isVisible = false
    }

    private fun sendNewsContentRequestAndGetResponse(id: String) {
        ApiManager.getRetrofitService().getNewsContent(id).enqueue(object:
            Callback<SourceResponse> {
            override fun onResponse(
                p0: Call<SourceResponse>,
                response: Response<SourceResponse>
            ) {

                if(response.isSuccessful)
                {
                    Log.e("send news","called")
                    clearProgressBar()

                    changeList(response.body()?.articles)




                }

            }

            override fun onFailure(
                p0: Call<SourceResponse>,
                error: Throwable
            ) {
                //showError(error.message)
                clearProgressBar()
            }
        })
    }

    private fun changeList(articles: List<Article?>?) {
        newsAdapter?.changeData(articles)
    }
}