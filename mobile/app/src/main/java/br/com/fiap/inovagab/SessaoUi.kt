package br.com.fiap.inovagab

import android.app.Activity
import android.content.Intent

fun Activity.encerrarSessao() {
    SessionManager(this).clear()
    startActivity(Intent(this, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
    finishAffinity()
}
