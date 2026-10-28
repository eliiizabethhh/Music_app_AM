package com.example.music_app.network

import com.example.music_app.model.ITunesResponse
import retrofit2.http.GET
import retrofit2.http.Query

//ARCHIVO QUE PERMITE GENERAR LA PETICIÓN AL API

interface ITunesApi {

    @GET("search")

    //MÉTODO PARA BUSCAR EL ALBÚM POR GÉNERO
    fun obtenerAlbumGenero(
        @Query("term") genero : String,
        @Query("country") pais : String,
        @Query("media") media : String,
        @Query("entity") entidad : String,
        @Query("limit") limite : Int,
    ) : retrofit2.Call<ITunesResponse>


}