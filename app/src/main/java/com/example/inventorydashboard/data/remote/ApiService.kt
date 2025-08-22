package com.example.inventorydashboard.data.remote

import com.example.inventorydashboard.data.remote.dto.BalancesResponseDto
import com.example.inventorydashboard.data.remote.dto.ItemsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("van.dll/getvanalldata")
    suspend fun getItems(
        @Query("cono") cono: Int = 290,
        @Query("strno") strno: Int = 1,
        @Query("case") caseNo: Int = 4
    ): ItemsResponseDto

    @GET("van.dll/getvanalldata")
    suspend fun getBalances(
        @Query("cono") cono: Int = 290,
        @Query("strno") strno: Int = 1,
        @Query("case") caseNo: Int = 9
    ): BalancesResponseDto
}
