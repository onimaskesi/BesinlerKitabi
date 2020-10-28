package com.onimaskesi.besinlerkitabi.servis

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.onimaskesi.besinlerkitabi.model.Besin

// veri tabanına (SQLite) erişim sağlayan obje (Data Access Object)
@Dao
interface BesinDAO {

    // vararg => birden fazla parametre olarak besin öğesi alınmasını sağlar
    // Long => insertAll fonksiyonu primary keyleri döndürür ( uuid )
    // suspend => coroutine scope
    // Insert => Room, insert into
    @Insert
    suspend fun insertAll( vararg besin : Besin ) : List<Long>

    @Query("SELECT * FROM Besin")
    suspend fun getAllBesin() : List<Besin>

    @Query("SELECT * FROM Besin WHERE uuid = :besinId")
    suspend fun getBesin(besinId : Int) : Besin

    @Query("DELETE FROM Besin")
    suspend fun deleteAll()

    @Query("DELETE FROM Besin WHERE uuid = :besinId")
    suspend fun deleteBesin(besinId : Int)

}