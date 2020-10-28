package com.onimaskesi.besinlerkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.onimaskesi.besinlerkitabi.R
import com.onimaskesi.besinlerkitabi.databinding.FragmentBesinDetayiBinding
import com.onimaskesi.besinlerkitabi.util.gorselIndir
import com.onimaskesi.besinlerkitabi.util.placeholderYap
import com.onimaskesi.besinlerkitabi.viewmodel.BesinDetayiViewModel
import kotlinx.android.synthetic.main.fragment_besin_detayi.*
import java.security.Provider

class BesinDetayiFragment : Fragment() {

    lateinit var viewModel : BesinDetayiViewModel
    private var besinId = 0
    private lateinit var dataBinding : FragmentBesinDetayiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_besin_detayi,container,false)
        //return inflater.inflate(R.layout.fragment_besin_detayi, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let{
            besinId = BesinDetayiFragmentArgs.fromBundle(it).besinId
        }

        viewModel = ViewModelProviders.of(this).get(BesinDetayiViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)

        observeLiveData()
    }

    fun observeLiveData(){
        viewModel.besinLiveData.observe(this, Observer { besin ->

            besin?.let{

                dataBinding.besin = besin

                /*
                besinIsimTextView.text = besin.besinIsim
                besinKaloriTextView.text = besin.besinKalori
                besinKarbonhidratTextView.text = besin.besinKarbonhidrat
                besinProteinTextView.text = besin.besinProtein
                besinYagTextView.text = besin.besinYag
                besinDetayImageView.gorselIndir(besin.besinGorsel, placeholderYap(context!!))
                 */
            }
        })
    }

}
