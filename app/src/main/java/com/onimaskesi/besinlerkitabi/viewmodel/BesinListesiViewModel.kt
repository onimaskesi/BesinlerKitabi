package com.onimaskesi.besinlerkitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onimaskesi.besinlerkitabi.model.Besin
import com.onimaskesi.besinlerkitabi.servis.BesinAPIServis
import com.onimaskesi.besinlerkitabi.servis.BesinDAO
import com.onimaskesi.besinlerkitabi.servis.BesinDataBase
import com.onimaskesi.besinlerkitabi.util.OzelSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class BesinListesiViewModel(application: Application) : BaseViewModel(application) {

    val besinler = MutableLiveData<List<Besin>>()
    val besinHata = MutableLiveData<Boolean>()
    val besinYukleniyor = MutableLiveData<Boolean>()

    val besinApiServis = BesinAPIServis()
    val disposable = CompositeDisposable() //kullan at yapısını sağlar, isteği bitirmeyi sağlar

    val ozelSharedPreferences = OzelSharedPreferences(getApplication())

    private val guncellemeZamani = 10 * 60 * 1000 * 1000 * 1000L // 10dk (nano cinsinden)



    fun refreshData(){

        val kaydedilmeZamani = ozelSharedPreferences?.zamaniAl()
        if(kaydedilmeZamani != null &&
            kaydedilmeZamani != 0L &&
            (System.nanoTime() - kaydedilmeZamani) <= guncellemeZamani){

            verileriSQLitetanAl()

        }else {

            verileriInternettenAl()

        }

    }

    private fun verileriSQLitetanAl(){

        besinYukleniyor.value = true

        launch {

            val dao = BesinDataBase(getApplication()).besinDao()
            besinleriGoster(dao.getAllBesin())
            //Toast.makeText(getApplication(), "Besinleri Room'dan aldık", Toast.LENGTH_LONG).show()

        }

    }

    fun refreshFromInternet(){
        verileriInternettenAl()
    }

    private fun verileriInternettenAl(){

        besinYukleniyor.value = true

        //IO thread => veri alış verişinde kullanılır
        //Default thread => yoğun cpu görsel işlemlerde kullanılır
        //UI thread => kullanıcının arayüzüyle ilgili işlemlerde kullanılır
        disposable.add(

            besinApiServis.getData()
                .subscribeOn(Schedulers.newThread()) //işlemin asenkron çalışmasını sağlıyoruz (yeni bir thread ile)
                .observeOn(AndroidSchedulers.mainThread()) //veriyi nerede gözlemleyeceğimizi belirtiyoruz (mainthread kullanıcının kullandığı kısım)
                .subscribeWith(
                    // DisposableSingleObserver abstract class(soyut sınıf) olduğu için object ile tanımladık
                    object : DisposableSingleObserver<ArrayList<Besin>>(){
                        override fun onSuccess(t: ArrayList<Besin>) {

                            SQLiteSakla(t)
                            besinleriGoster(t)
                            //Toast.makeText(getApplication(), "Besinleri İnternetten aldık", Toast.LENGTH_LONG).show()

                        }

                        override fun onError(e: Throwable) {

                            besinHata.value = true
                            besinYukleniyor.value = false
                            e.printStackTrace()

                        }

                    }
                )
        )

    }

    fun besinleriGoster(besinlerListesi : List<Besin>){

        besinler.value = besinlerListesi
        besinHata.value = false
        besinYukleniyor.value = false

    }

    fun SQLiteSakla(besinlerListesi : List<Besin>){

        launch {

            val dao = BesinDataBase(getApplication()).besinDao()
            dao.deleteAll()
            val uuidListesi = dao.insertAll(*besinlerListesi.toTypedArray())


            for( i in 0 until besinlerListesi.size){

                besinlerListesi[i].uuid = uuidListesi[i].toInt()

            }

        }
        ozelSharedPreferences.zamaniKaydet(System.nanoTime())

    }


}