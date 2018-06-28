package ru.spcm.apps.mtgpro.repository.bounds

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.support.annotation.MainThread
import android.support.annotation.WorkerThread
import ru.spcm.apps.mtgpro.model.tools.ApiResponse
import ru.spcm.apps.mtgpro.model.tools.Resource
import ru.spcm.apps.mtgpro.tools.AppExecutors
import ru.spcm.apps.mtgpro.tools.Logger


abstract class NetworkBound<R, Q>
@MainThread
internal constructor(private val appExecutors: AppExecutors) {

    internal val result = MediatorLiveData<Resource<R>>()

    open fun create() : NetworkBound<R, Q> {
        result.value = Resource.loading(null)
        val savedData = loadSaved()
        result.addSource(savedData) { data ->
            if(shouldFetch(data)){
                result.removeSource(savedData)
                result.value = Resource.loading(data)
                fetchFromNetwork(data)
            }else{
                result.value = Resource.success(data)
            }
        }
        return this
    }

    private fun fetchFromNetwork(data : R?) {
        val apiResponse = createCall()
        result.addSource<ApiResponse<Q>>(apiResponse) { response ->
            result.removeSource<ApiResponse<Q>>(apiResponse)
            if (response?.body != null) {
                appExecutors.networkIO().execute {
                    saveCallResult(processResponse(response))
                    appExecutors.mainThread().execute {
                        result.addSource(loadSaved()) {
                            newData -> result.setValue(Resource.success(newData))
                        }
                    }
                }
            } else {
                onFetchFailed(response?.errorMessage)
                result.setValue(Resource.error(response?.errorMessage ?: "Ошибка получения данных", data))
            }
        }
    }

    fun asLiveData(): LiveData<Resource<R>> {
        return result
    }

    private fun onFetchFailed(message: String?) {
        Logger.e(message)
    }

    @WorkerThread
    private fun processResponse(response: ApiResponse<Q>?): Q? {
        return response?.body
    }

    @WorkerThread
    protected abstract fun saveCallResult(data: Q?)

    @MainThread
    protected abstract fun shouldFetch(data: R?): Boolean

    @MainThread
    protected abstract fun loadSaved(): LiveData<R>

    @MainThread
    protected abstract fun createCall(): LiveData<ApiResponse<Q>>

}