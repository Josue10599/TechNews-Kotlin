package br.com.alura.technews.ui.viewModel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.viewModel.VizualizaNoticiaViewModel

class VizualizaNoticiaFactory(
    private val repository: NoticiaRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VizualizaNoticiaViewModel(repository) as T
    }

}
