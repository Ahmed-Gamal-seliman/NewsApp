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
import androidx.fragment.app.Fragment
import com.example.newsapp.ModelNewsContent.Article
import com.example.newsapp.databinding.FragmentNewsContentBinding

class NewsFragment: Fragment() {

    lateinit var binding: FragmentNewsContentBinding
    var articles:List<Article?>? =null
    var newsAdapter:NewsAdapter? = null


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
        /* Set adapter */
        Log.e("on view created","called")
        if(arguments == null)
        {
            Log.e("arguments","null")
        }

        val articleList:ArrayList<Article?>? = arguments?.getSerializable("articleList") as ArrayList<Article?>?
        newsAdapter = NewsAdapter(articleList)
        binding.rvNewsContent.adapter = newsAdapter
        binding.rvNewsContent.adapter?.notifyDataSetChanged()


        newsAdapter?.onItemClicked = NewsAdapter.ItemOnClickListener {
            val intent: Intent = Intent(context,ContentDetailsActivity::class.java)

            startActivity(intent)
            Toast.makeText(requireContext(),"item clicked",Toast.LENGTH_SHORT).show()
        }



        Log.e("size",articleList?.size.toString())
//        for(i in 0..articleList?.size!!)
//            Log.e("author", articleList[i]?.author.toString())
    }


}