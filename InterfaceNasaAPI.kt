package com.interview.mywallst_assignment.ui

import com.interview.mywallst_assignment.data_payload.MeteorsApiResponse
import retrofit2.Response
import retrofit2.http.GET

interface InterfaceNasaAPI {

    // Suspend function to be invoked within the coroutine
    @GET("y77d-th95.json")
    suspend fun getAPIResponse() : Response<MeteorsApiResponse>
}