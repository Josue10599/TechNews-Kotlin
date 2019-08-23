package br.com.alura.technews.ui.activity.extensions

import android.app.Activity
import android.widget.Toast
import br.com.alura.technews.repository.Resource

fun Activity.mostraErro(mensagem: String) {
    Toast.makeText(
        this,
        mensagem,
        Toast.LENGTH_LONG
    ).show()
}

fun Activity.verificaErro(it: Resource<Void?>, texto: String) {
    if (it.erro == null) finish()
    else mostraErro(texto)
}