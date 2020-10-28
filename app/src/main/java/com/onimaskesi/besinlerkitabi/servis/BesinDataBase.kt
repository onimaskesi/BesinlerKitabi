package com.onimaskesi.besinlerkitabi.servis

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.onimaskesi.besinlerkitabi.model.Besin

@Database(entities = arrayOf(Besin::class),version = 1)
abstract class BesinDataBase : RoomDatabase() {

    abstract  fun besinDao() : BesinDAO

    //Singleton (heryerden erişebilmek için)
    companion object {

        // instance değişkeni database örneği var mı diye kontrol amaçlı oluşturduk
        // Volatile tüm threatler tarafından görünür olmasını sağlar
        @Volatile private var instance : BesinDataBase? = null

        //instance oluşturulduysa onu döndür oluşturulmadıysa database oluştur ve instance değişkenine eşitle
        private val lock = Any()
        operator fun invoke(context: Context) = instance ?: synchronized(lock){
            instance ?: databaseOlustur(context).also {
                instance = it
            }
        }

        private fun databaseOlustur(context : Context)
                = Room.databaseBuilder(
            context.applicationContext,
            BesinDataBase::class.java,
            "besindatabase"
        ).build()

    }

}