package com.ing.findmyrepo.data.repository

import com.ing.findmyrepo.data.network.RepoApi
import com.ing.findmyrepo.model.response.Repo
import com.ing.findmyrepo.data.network.SafeApiRequest

class UserRepository(private val api: RepoApi) : SafeApiRequest() {

    suspend fun getUserRepos(user: String, page: Int): List<Repo> {
        return apiRequest { api.getUserRepo(user, page) }
    }

}