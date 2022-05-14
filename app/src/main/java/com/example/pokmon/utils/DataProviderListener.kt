package com.example.pokmon.utils



interface DataProviderListener {

    fun onSuccess(result: Any)

    fun onEmpty()

    fun onError(exception: Throwable, message: String = exception.localizedMessage!!)
}