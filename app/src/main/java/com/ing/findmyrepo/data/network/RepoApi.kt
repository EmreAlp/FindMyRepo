package com.ing.findmyrepo.data.network

import com.ing.findmyrepo.model.response.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RepoApi {

    @GET("users/{user}/repos")
    suspend fun getUserRepo(
        @Path("user") user: String,
        @Query("page") page: Int
    ): Response<List<Repo>>
}