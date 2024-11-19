package com.example.todolist

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginText = findViewById<EditText>(R.id.txtLogin) // ajuste o id para o seu botão
        val passwordText = findViewById<EditText>(R.id.txtPassword) // ajuste o id para o seu botão
        val loginButton = findViewById<Button>(R.id.btnLogin) // ajuste o id para o seu botão

        loginButton.setOnClickListener {
            // Aqui você pode adicionar a lógica de autenticação
            if (loginText.text.toString() != "teste" || passwordText.text.toString() != "teste") {
                Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
            } else {
                // Supondo que a autenticação seja bem-sucedida:
                val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
                with(sharedPref.edit()) {
                    putBoolean("is_logged_in", true) // Salva que o usuário está logado
                    apply()
                }

                // Inicia a MainActivity e fecha a LoginActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
