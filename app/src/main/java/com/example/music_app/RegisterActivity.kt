package com.example.music_app

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText

    private lateinit var btnRegistrar: Button

    //CREAR OBJETO DE LA CLASE DE LA BASE DE DATOS
    private lateinit var dbHelper : DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        btnRegistrar = findViewById(R.id.btnRegistrar)

        //INICIAR EL OBJETO DE LA BASE DE DATOS
        dbHelper = DatabaseHelper(this)

        //EVENTO PARA REGISTRAR UN USUARIO
        btnRegistrar.setOnClickListener {
            //LLAMAR AL MÉTODO DE REGISTRAR USUARIO
            registrarUsuario()

        }

    }

    //MÉTODO PARA REGISTRAR UN USUARIO
    private fun registrarUsuario (){

        //OBTENER LA INFORMACION QUE ESCRIBE AL USUARIO
        val nombre = etNombre.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val password = etPassword.text.toString().trim()

        //VALIDAR QUE LOS CAMPOS TENGAN INFORMACIÓN
        if(nombre.isEmpty() || correo.isEmpty() || password.isEmpty() ){
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT). show()
            return
        }

        //LLAMAR A NUESTRA BASE DE DATOS Y GUARDAR EL RESULTADO DE LA INSERCIÓN
        val resultado = dbHelper.registrarUsuario(nombre,correo,password)

        if(resultado){
            //SI EL RESULTADO ES VERDADERO
            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT). show()
            //REGRESAR AL USUARIO A LA PANTALLA ANTERIOR
            finish()
        } else
            Toast.makeText(this, "Registro fallido", Toast.LENGTH_SHORT). show()

    }
}