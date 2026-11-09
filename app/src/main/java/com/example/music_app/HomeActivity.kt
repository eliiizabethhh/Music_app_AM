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
    private lateinit var tvPop: TextView
    private lateinit var imgPop: ImageView
    private lateinit var tvRock: TextView
    private lateinit var imgRock: ImageView
    private lateinit var tvReggaeton: TextView
    private lateinit var imgReggaeton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        tvPop = findViewById(R.id.tvPop)
        imgPop = findViewById(R.id.imgPop)

        tvRock = findViewById(R.id.tvRock)
        imgRock = findViewById(R.id.imgRock)

        tvReggaeton = findViewById(R.id.tvReggaeton)
        imgReggaeton = findViewById(R.id.imgReggaeton)


        obtenerAlbumGenero("pop", imgPop, tvPop)

        obtenerAlbumGenero("rock", imgRock, tvRock)

        obtenerAlbumGenero("reggaeton", imgReggaeton, tvReggaeton)
    }


    // METODO PARA CONSULTAR ALBUMES POR GENERO

    private fun obtenerAlbumGenero(
        genero: String,
        imagen: ImageView,
        resultado: TextView
    ) {

        RetrofitClient.api.obtenerAlbumGenero(
            genero = genero,
            pais = "MX",
            media = "music",
            entidad = "album",
            limite = 10
        ).enqueue(object : Callback<ITunesResponse> {

            // METODO PARA RECIBIR LA RESPUESTA DEL API

            override fun onResponse(
                call: Call<ITunesResponse>,
                response: Response<ITunesResponse>
            ) {

                if (response.isSuccessful) {

                    val albumes = response.body()?.results

                    if (!albumes.isNullOrEmpty()) {

                        // OBTENER EL PRIMER ALBUM DE LA RESPUESTA

                        val album = albumes[0]

                        // MOSTRAR LA INFORMACION DEL ALBUM

                        resultado.text =
                            "Álbum: ${album.collectionName}\n" +
                                    "Artista: ${album.artistName}\n" +
                                    "Género: ${album.primaryGenreName}"

                        // CARGAR LA PORTADA CON GLIDE

                        Glide.with(this@HomeActivity)
                            .load(album.obtenerImagenGrande())
                            .into(imagen)

                    } else {

                        resultado.text = "No se encontraron álbumes de $genero"

                    }

                } else {

                    resultado.text = "Error en la consulta: ${response.code()}"

                }
            }


            // METODO PARA IDENTIFICAR ERRORES DE CONEXION

            override fun onFailure(
                call: Call<ITunesResponse>,
                t: Throwable
            ) {

                resultado.text = "Error de conexión al consultar $genero"

                Toast.makeText(
                    this@HomeActivity,
                    "Error en el API",
                    Toast.LENGTH_SHORT
                ).show()

            }

        })

    }

}