package br.com.alura.technews.repository

class Resource<T>(
    val dado: T?,
    val erro: String? = null
)

fun <T> criaResourceDeFalha(
    resourceAtual: Resource<T?>?,
    erro: String?
): Resource<T> = if (resourceAtual != null) Resource(resourceAtual.dado, erro) else Resource(null, erro)