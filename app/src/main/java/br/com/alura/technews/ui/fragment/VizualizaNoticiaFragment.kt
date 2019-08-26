package br.com.alura.technews.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import br.com.alura.technews.R
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.ui.activity.NOTICIA_ID_CHAVE
import br.com.alura.technews.ui.fragment.extensions.mostraErro
import br.com.alura.technews.ui.viewModel.VizualizaNoticiaViewModel
import kotlinx.android.synthetic.main.fragment_vizualiza_noticia.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val MENSAGEM_FALHA_REMOCAO = "Não foi possível remover notícia"
private const val NOTICIA_NAO_ENCONTRADA = "Notícia não encontrada"
private const val TITULO_APPBAR = "Notícia"

class VizualizaNoticiaFragment : Fragment() {
    private val noticiaId: Long by lazy {
        arguments?.getLong(NOTICIA_ID_CHAVE) ?: throw IllegalArgumentException("Id inválido")
    }
    private lateinit var noticia: Noticia
    private val viewModel: VizualizaNoticiaViewModel by viewModel()

    var finalizaActivity: () -> Unit = {}
    var abreFormularioEdicao: (noticiaSelecionada: Noticia) -> Unit = {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        verificaIdDaNoticia()
        buscaNoticiaSelecionada()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.title = TITULO_APPBAR
        return inflater.inflate(R.layout.fragment_vizualiza_noticia, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.visualiza_noticia_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.visualiza_noticia_menu_edita -> abreFormularioEdicao(noticia)
            R.id.visualiza_noticia_menu_remove -> remove()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun buscaNoticiaSelecionada() {
        viewModel.buscaPorId(noticiaId).observe(this, Observer {
            it?.let {
                this.noticia = it
                preencheCampos(it)
            }
        })
    }

    private fun verificaIdDaNoticia() {
        if (noticiaId == 0L) {
            mostraErro(NOTICIA_NAO_ENCONTRADA)
            finalizaActivity
        }
    }

    private fun preencheCampos(noticia: Noticia) {
        fragment_visualiza_noticia_titulo.text = noticia.titulo
        fragment_visualiza_noticia_texto.text = noticia.texto
    }

    private fun remove() {
        if (::noticia.isInitialized)
            viewModel.remove(noticia)
            .observe(this, Observer {
                if (it.erro == null) finalizaActivity
                else mostraErro(MENSAGEM_FALHA_REMOCAO)
            })
    }
}