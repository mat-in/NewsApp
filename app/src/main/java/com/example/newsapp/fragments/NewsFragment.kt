package com.example.newsapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.data.News

class NewsFragment : Fragment() {

    companion object {
        const val ARG_NEWS = "news_item"
    }

    private lateinit var imageView: ImageView
    private lateinit var titleText: TextView
    private lateinit var dateText: TextView
    private lateinit var toggleDescription: TextView
    private lateinit var descriptionText: TextView
    private lateinit var contentText: TextView
    private lateinit var urlText: TextView

    private var news: News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        news = arguments?.getParcelable(ARG_NEWS)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageView = view.findViewById(R.id.news_image)
        titleText = view.findViewById(R.id.news_title)
        dateText = view.findViewById(R.id.news_date)
        toggleDescription = view.findViewById(R.id.toggle_description)
        descriptionText = view.findViewById(R.id.news_description)
        contentText = view.findViewById(R.id.news_content)
        urlText = view.findViewById(R.id.news_url)

        news?.let { item ->
            titleText.text = item.title
            dateText.text = item.publishedAt
            descriptionText.text = item.description ?: "No description available."
            contentText.text = item.content
            urlText.text = item.url

            Glide.with(requireContext())
                .load(item.urlToImage)
                .error(R.drawable.ic_launcher_foreground)             // Optional
                .into(imageView)
        }

        toggleDescription.setOnClickListener {
            if (descriptionText.visibility == View.GONE) {
                descriptionText.visibility = View.VISIBLE
                toggleDescription.text = "Hide Description ▲"
            } else {
                descriptionText.visibility = View.GONE
                toggleDescription.text = "Show Description ▼"
            }
        }
    }
}
