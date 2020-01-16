package com.alp.myapplication.network

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import retrofit2.HttpException
import retrofit2.Response

class CallHandler<DATA : Any> {
    lateinit var client: Deferred<Response<DATA>>

    @Suppress("UNCHECKED_CAST")
    fun makeCall(): MutableLiveData<Resource<DATA>> {
        val result = MutableLiveData<Resource<DATA>>()
        result.value = Resource.loading(null)

        GlobalScope.launch {
            try {
                val await = client.await()
                if (await.isSuccessful && await.code() == 200 && await.body() != null
                    && !TextUtils.isEmpty(await.body().toString())) {
                    val response = client.awaitResult().getOrThrow()
                    withContext(Dispatchers.Main) {
                        result.value = Resource.success(response)
                    }
                } else if (await.isSuccessful && await.code() == 200) {
                    withContext(Dispatchers.Main) {
                        result.value = Resource.success(null)
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        throw HttpException(await)
                    }
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.Main) {
                    if (e is HttpException) result.value =
                        Resource.error("${e.message} | code ${e.response()?.code()}", 0, e)
                    else result.value = Resource.error("${e.message}", 0, e)
                }
                e.printStackTrace()
            }
        }
        return result
    }
}

fun <DATA : Any> networkCall(block: CallHandler<DATA>.() -> Unit): MutableLiveData<Resource<DATA>> =
    CallHandler<DATA>().apply(block).makeCall()