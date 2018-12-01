package ru.nowandroid.youtube.nowjsoupandroid.list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_news_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import ru.nowandroid.youtube.nowjsoupandroid.R
import java.io.IOException

class ListNewsFragment : Fragment() {

    private val url = "https://www.volzsky.ru/index.php?wx=16"
    private val listNews = mutableListOf<News>()
    private lateinit var adapter: DataAdapter

    companion object {
        fun newInstance() = ListNewsFragment()
    }

    private lateinit var viewModel: ListNewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.list_news_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ListNewsViewModel::class.java)
        // TODO: Use the ViewModel

        adapter = DataAdapter()
        val llm = LinearLayoutManager(this.context)
        rv.layoutManager = llm
        rv.adapter = adapter

        GlobalScope.launch {
            getData()
        }
    }

    private fun getData() {
        try {
            val document = Jsoup.connect(url).get()
            val elements = document.select("div[class=btc_block-1]")

            for (i in 0 until elements.size) {
                val title = elements.select("div[class=btc_block-1_1 btc_h]")
                    .select("a")
                    .eq(i)
                    .text()

                val description =
                    elements.select("div[class=btc_block-1_2]")
                        .select("p[class=btc_p]")
                        .eq(i)
                        .text()

                val linkImage =
                    document.baseUri() +
                            elements.select("div[class=btc_block-1_2]")
                                .select("img")
                                .eq(i)
                                .attr("src")

                val additionalInfo = elements.select("div[class=btc_block-1_2]")
                    .select("p[class=btc_p btc_inf]")
                    .eq(i)
                    .text()

                val linkDetails =
                    document.baseUri() +
                    elements.select("div[class=btc_block-1_2]")
                        .eq(i)
                        .select("a")
                        .attr("href")

                listNews.add(News(title, description, linkImage, additionalInfo, linkDetails))
            }
            GlobalScope.launch(Dispatchers.Main) {
                adapter.set(listNews)
            }
        } catch (e: IOException) {
            Log.e("TEST) exception", e.message.toString())
        }
    }

}
