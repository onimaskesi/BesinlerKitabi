package com.onimaskesi.besinlerkitabi.servis

import com.onimaskesi.besinlerkitabi.model.Besin
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET

interface BesinAPI {

    // GET (veriyi çekmek için) , POST (Veri göndermek için) ...

    //URL => https://raw.githubusercontent.com/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    //BASE_URL => https://raw.githubusercontent.com/
    //atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json

    @GET("/atilsamancioglu/BTK20-JSONVeriSeti/master/besinler.json")
    fun getBesinler() : Single<ArrayList<Besin>> //Single => RXJAVA (veriyi tek seferde çekip başka işlem yapmaz)
    //fun getBesinler() : Call<ArrayList<Besin>> //Call => RETROFİT2
    //fun getBesinler() : Observable<ArrayList<Besin>> //Observable => RXJAVA (veriyi çeker, hata veya başarı mesajı döndürür)
}