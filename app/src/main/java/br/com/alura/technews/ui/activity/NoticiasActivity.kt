package br.com.alura.technews.ui.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.extensions.fragmentTransaction
import br.com.alura.technews.ui.fragment.ListaNoticiasFragment
import br.com.alura.technews.ui.fragment.VizualizaNoticiaFragment
import kotlinx.android.synthetic.main.activity_noticias.*

private const val TAG_VIZUALIZA_NOTICIA_FRAGMENT = "VizualizaNoticiaFragment"

class ListaNoticiasActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noticias)
        if (savedInstanceState == null) abreListaNoticias()
        else {
            supportFragmentManager.findFragmentByTag(TAG_VIZUALIZA_NOTICIA_FRAGMENT)?.let {
                val novoFragment = VizualizaNoticiaFragment()
                val argumentsAntigos = it.arguments
                novoFragment.arguments = argumentsAntigos
                fragmentTransaction { remove(it) }
                supportFragmentManager.popBackStack()
                abreVisualizaNoticia(novoFragment)
            }
        }
    }

    override fun onAttachFragment(fragment: Fragment?) {
        super.onAttachFragment(fragment)
        when (fragment) {
            is ListaNoticiasFragment -> configuraListaNoticias(fragment)
            is VizualizaNoticiaFragment -> configuraVizualizaNoticia(fragment)
        }
    }

    private fun configuraVizualizaNoticia(fragment: VizualizaNoticiaFragment) {
        fragment.finalizaActivity = this::finish
        fragment.abreFormularioEdicao = this::abreFormularioEdicao
    }

    private fun configuraListaNoticias(fragment: ListaNoticiasFragment) {
        fragment.abreFormularioModoCriacao = this::abreFormularioModoCriacao
        fragment.abreVisualizadorNoticia = this::abreVisualizadorNoticia
    }

    private fun abreVisualizadorNoticia(noticia: Noticia) {
        val fragment = VizualizaNoticiaFragment()
        val bundle = Bundle()
        bundle.putLong(NOTICIA_ID_CHAVE, noticia.id)
        fragment.arguments = bundle
        abreVisualizaNoticia(fragment)
    }

    private fun abreListaNoticias() {
        fragmentTransaction {
            replace(R.id.frame_layout_principal, ListaNoticiasFragment())
        }
    }

    private fun abreVisualizaNoticia(fragment: VizualizaNoticiaFragment) {
        if (frame_layout_secundario == null)
            fragmentTransaction {
                addToBackStack(TAG_VIZUALIZA_NOTICIA_FRAGMENT)
                replace(R.id.frame_layout_principal, fragment, TAG_VIZUALIZA_NOTICIA_FRAGMENT)
            }
        else {
            fragmentTransaction {
                replace(R.id.frame_layout_secundario, fragment, TAG_VIZUALIZA_NOTICIA_FRAGMENT)
            }
        }
    }

    private fun abreFormularioEdicao(noticia: Noticia) {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, noticia.id)
        startActivity(intent)
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }
}
