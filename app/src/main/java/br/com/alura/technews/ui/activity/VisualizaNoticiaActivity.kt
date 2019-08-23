package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.ui.fragment.VizualizaNoticiaFragment
import kotlinx.android.synthetic.main.activity_visualiza_noticia.*

private const val TITULO_APPBAR = "Notícia"

class VisualizaNoticiaActivity : AppCompatActivity() {

    private val noticiaId: Long by lazy {
        intent.getLongExtra(NOTICIA_ID_CHAVE, 0)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visualiza_noticia)
        title = TITULO_APPBAR
        val transaction = supportFragmentManager.beginTransaction()
        val fragment = VizualizaNoticiaFragment()
        val bundle = Bundle()
        bundle.putLong(NOTICIA_ID_CHAVE, noticiaId)
        fragment.arguments = bundle
        transaction.add(activity_vizualiza_noticia_container.id, fragment)
        transaction.commit()
    }

    override fun onAttachFragment(fragment: Fragment?) {
        if (fragment is VizualizaNoticiaFragment) {
            fragment.finalizaActivity = this::finish
            fragment.abreFormularioEdicao = this::abreFormularioEdicao
        }
    }

    private fun abreFormularioEdicao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticiaId)
        startActivity(intent)
    }
}
