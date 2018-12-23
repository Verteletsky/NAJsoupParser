package ru.nowandroid.youtube.nowjsoupandroid.details

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.details_news_fragment.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import ru.nowandroid.youtube.nowjsoupandroid.R
import java.io.IOException
import kotlin.coroutines.CoroutineContext

class DetailsNewsFragment : Fragment(), CoroutineScope {

  private var job = Job()
  override val coroutineContext: CoroutineContext = Dispatchers.Main + job

  private lateinit var viewModel: DetailsNewsViewModel

  override fun onCreateView(
      inflater: LayoutInflater, container: ViewGroup?,
      savedInstanceState: Bundle?
                           ): View? {
    return inflater.inflate(R.layout.details_news_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this).get(DetailsNewsViewModel::class.java)

    job = launch(Dispatchers.IO) {
      getData()
    }
  }

  private fun getData() {
    try {
      val document = Jsoup.connect(arguments?.getString("link")).get()
      val elements = document.select("div[id=bt_center]")

      val title = elements.select("h1[class=title]").text()

      val description = elements.select("div[id=n_n]").select("p").text()

      val linkImage = document.baseUri() +
                      elements.select("div[class=mainfoto]").select("img").attr("src")

      job = launch {
        det_title.text = title.toString()
        det_description.text = description.toString()
        Picasso.with(activity)
            .load(linkImage)
            .into(det_main_photo)
      }
    } catch (e: IOException) {
      Log.e("TEST) ", e.message.toString())
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    job.cancel()
  }

}
