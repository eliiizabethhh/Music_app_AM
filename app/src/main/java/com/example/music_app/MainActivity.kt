package com.example.music_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etCorreo : EditText
    private lateinit var etPass : EditText
    private lateinit var btnInicio : Button
    private lateinit var dbHelper : DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

       val tvCrearCuenta = findViewById<TextView>(R.id.tvCrearCuenta)

        etCorreo = findViewById(R.id.etCorreo)
        etPass = findViewById(R.id.etPassword)
        btnInicio = findViewById(R.id.btnIngresar)

        dbHelper = DatabaseHelper(this)

        //EVENTO PARA INICIAR SESIÓN
        btnInicio.setOnClickListener {

            //TODO: AGREGAR METODO DE INICIO SE SESION
            iniciarSesión()
        }

        tvCrearCuenta.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
         //MÉTODO PARA INICIAR SESIÓN
         private fun iniciarSesión(){

             //ACCEDER A LA INFORMACIÓN QUE ESCRIBE EL USUARIO
             val correo = etCorreo.text.toString().trim()
             val pass = etPass.text.toString().trim()

             if (correo.isEmpty() ||pass.isEmpty()) {
                 Toast.makeText(this,"Completa todos los campos" , Toast.LENGTH_SHORT).show()
                 return
             }

             //LLAMAR AL MÉTODO DE INICIO DE SESIÓN EN LA BD
            val resultado =  dbHelper.iniciarSesion(correo, pass)

             if(resultado){
                 //Si la sesión es correcta
                 Toast.makeText(this,"Bienvenid@ (:" , Toast.LENGTH_SHORT).show()

                 val intent = Intent(this, HomeActivity::class.java)
                 startActivity(intent)
                 //Cerrar la pantalla actual
                 finish()


             } else {
                 Toast.makeText(this,"Datos Incorrectos" , Toast.LENGTH_SHORT).show()

             }


         }


}