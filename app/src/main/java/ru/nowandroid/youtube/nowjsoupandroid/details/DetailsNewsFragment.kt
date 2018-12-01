package ru.nowandroid.youtube.nowjsoupandroid.details

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.details_news_fragment.*

import ru.nowandroid.youtube.nowjsoupandroid.R

class DetailsNewsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsNewsFragment()
    }

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
        // TODO: Use the ViewModel

        link.text = arguments?.getString("link")
    }

}
