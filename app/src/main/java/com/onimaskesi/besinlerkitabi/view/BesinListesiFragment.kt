package com.onimaskesi.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.onimaskesi.besinlerkitabi.R
import com.onimaskesi.besinlerkitabi.adapter.BesinListesiRecyclerAdapter
import com.onimaskesi.besinlerkitabi.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi.*

class BesinListesiFragment : Fragment() {

    lateinit var viewModel : BesinListesiViewModel
    var recyclerAdapter = BesinListesiRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        besinListesiRecyclerView.layoutManager = LinearLayoutManager(this.context)
        besinListesiRecyclerView.adapter = recyclerAdapter

        swipeRefreshLayout.setOnRefreshListener {

            hataTextView.visibility = View.GONE
            besinListesiRecyclerView.visibility = View.GONE
            swipeRefreshLayout.isRefreshing = false
            yukleniyorView.visibility = View.VISIBLE

            viewModel.refreshFromInternet()

        }

        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.besinler.observe(viewLifecycleOwner, Observer{ besinler ->

            besinler?.let{
                besinListesiRecyclerView.visibility = View.VISIBLE
                recyclerAdapter.besinListesiGuncelle(it)
            }

        })

        viewModel.besinHata.observe(viewLifecycleOwner, Observer{hata ->

            if(hata){
                besinListesiRecyclerView.visibility = View.INVISIBLE
                hataTextView.visibility = View.VISIBLE
            }else{
                besinListesiRecyclerView.visibility = View.VISIBLE
                hataTextView.visibility = View.GONE
            }

        })

        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer{yukleniyor ->

            if(yukleniyor){
                besinListesiRecyclerView.visibility = View.INVISIBLE
                hataTextView.visibility = View.GONE
                yukleniyorView.visibility = View.VISIBLE
            }else{
                yukleniyorView.visibility = View.GONE
            }

        })
    }

}
