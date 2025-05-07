package com.example.newsapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.data.News
import com.example.newsapp.data.NewsInterface
import com.example.newsapp.data.NewsResponse
import com.example.newsapp.data.RetrofitInstance
import com.example.newsapp.fragments.NewsFragment
import com.example.newsapp.view.MyAdapter
import com.example.newsapp.viewmodel.NewsViewModel
import kotlinx.coroutines.launch
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    val newsViewModel = NewsViewModel()
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )

        val adapter = MyAdapter(arrayListOf()) { news ->
            recyclerView.visibility = View.GONE // hide list
            findViewById<FrameLayout>(R.id.fragment_container).visibility = View.VISIBLE // show fragment

            val fragment = NewsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(NewsFragment.ARG_NEWS, news)
                }
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }


        recyclerView.adapter = adapter

        newsViewModel.newsLiveData.observe(this) {news->
            adapter.itemsList.clear()
            adapter.itemsList.addAll(news)
            adapter.notifyDataSetChanged()
        }

        newsViewModel.errorLiveData.observe(this) {error->
            Log.v("Error", error)
        }

        newsViewModel.getNews()
    }
    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
            recyclerView.visibility = View.VISIBLE
            findViewById<FrameLayout>(R.id.fragment_container).visibility = View.GONE
        } else {
            super.onBackPressed()
        }
    }
}