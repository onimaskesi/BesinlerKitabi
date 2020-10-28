package com.onimaskesi.besinlerkitabi.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onimaskesi.besinlerkitabi.model.Besin
import com.onimaskesi.besinlerkitabi.servis.BesinDataBase
import kotlinx.coroutines.launch

class BesinDetayiViewModel(application: Application) : BaseViewModel(application) {

    val besinLiveData = MutableLiveData<Besin>()

    fun roomVerisiniAl(uuid : Int){

        launch {

            val dao = BesinDataBase(getApplication()).besinDao()
            val besin = dao.getBesin(uuid)
            besinLiveData.value = besin

        }

    }

}