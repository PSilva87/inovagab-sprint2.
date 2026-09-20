package br.com.fiap.inovagab

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val email =
            findViewById<EditText>(R.id.editEmail)

        val senha =
            findViewById<EditText>(R.id.editSenha)

        val btnLogin =
            findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val txtEmail =
                email.text.toString().trim()

            val txtSenha =
                senha.text.toString().trim()

            if (txtEmail.isEmpty() ||
                txtSenha.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Preencha email e senha",
                    Toast.LENGTH_LONG
                ).show()

                return@setOnClickListener
            }

            val body = JSONObject().put("email", txtEmail).put("senha", txtSenha)
            ApiClient.request("POST", "auth/login", body = body, onSuccess = { response ->
                val json = JSONObject(response)
                val role = json.getJSONObject("usuario").getString("role")
                SessionManager(this).save(json.getString("token"), role)
                val destination = when (role) {
                    "OPERADOR" -> HomeActivity::class.java
                    "GESTOR" -> HomeGestorActivity::class.java
                    "LIDER" -> HomeLiderActivity::class.java
                    else -> null
                }
                if (destination == null) {
                    Toast.makeText(this, "Perfil de acesso inválido", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, destination))
                    finish()
                }
            }, onError = { error ->
                Toast.makeText(this, "Erro no login: $error", Toast.LENGTH_LONG).show()
            })
        }
    }
}
