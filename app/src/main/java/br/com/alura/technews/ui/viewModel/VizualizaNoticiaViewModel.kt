package br.com.alura.technews.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.repository.Resource

class VizualizaNoticiaViewModel(
    private val repository: NoticiaRepository
) : ViewModel() {
    fun remove(noticia: Noticia): LiveData<Resource<Void?>> = repository.remove(noticia)
    fun buscaPorId(noticiaId: Long): LiveData<Noticia?> = repository.buscaPorId(noticiaId)
}
