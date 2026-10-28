package com.example.music_app

import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.music_app.model.ITunesResponse
import com.example.music_app.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeActivity : AppCompatActivity() {

    private lateinit var tvResultado : TextView
    private lateinit var imgAlbum : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvResultado = findViewById(R.id.tvResultado)
        imgAlbum = findViewById(R.id.imgAlbum)

        obtenerAlbum()
    }


    //MÉTODO PARA CARGAR LOS ALBUMES
    private fun obtenerAlbum(){

        RetrofitClient.api.obtenerAlbumGenero(
            genero = "pop",
            pais = "MX",
            media = "music",
            entidad = "album",
            limite = 1,
        ).enqueue(object : Callback<ITunesResponse>{
            //MÉTODO PARA VERIFICAR QUE LA RESPUESTA SEA CORRECTA
            override fun onResponse(
                call: Call<ITunesResponse>,
                response: Response<ITunesResponse>
            ) {

                //variable que obtenga los resultados del appi
                val albumes = response.body()?.results

                if(!albumes.isNullOrEmpty()){
                    //VARIABLE QUE GUARDE LA INFORMACIÓN DE CADA RESPUESTA OBTENIDA DEL API
                    val album = albumes[0]
                    //MOSTRAR INFORMACION DEL API
                    tvResultado.text="Nombre del album: ${album.collectionName} \n\n" +
                                     "Artista: ${album.artistName}"
                    //MOSTRAR IMAGEN EN EL API
                    Glide.with(this@HomeActivity)
                        .load(album.obtenerImagenGrande())
                        .into(imgAlbum)
                } else {
                    Toast.makeText(this@HomeActivity, "No hay resultados", Toast.LENGTH_SHORT).show()
                }


            }
             //MÉTODO PARA IDENTIFICAR SI NO HAY RESPUESTA
            override fun onFailure(call: Call<ITunesResponse>, t: Throwable) {
                Toast.makeText(this@HomeActivity, "Error en el API", Toast.LENGTH_SHORT).show()
            }

        })
    }

}