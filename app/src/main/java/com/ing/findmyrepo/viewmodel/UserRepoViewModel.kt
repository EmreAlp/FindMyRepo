package com.ing.findmyrepo.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ing.findmyrepo.data.network.ApiException
import com.ing.findmyrepo.data.repository.UserRepository
import com.ing.findmyrepo.model.response.Repo
import kotlinx.coroutines.launch

class UserRepoViewModel(
    private val repository: UserRepository,
    private val error: MutableLiveData<String>
) : ViewModel() {

    private val _repos: MutableLiveData<List<Repo>> = MutableLiveData()
    val repos: LiveData<List<Repo>> get() = _repos

    private val _submitClicked: MutableLiveData<Boolean> = MutableLiveData(false)
    val submitClicked: LiveData<Boolean> get() = _submitClicked

    val paginationUser: MutableLiveData<String> = MutableLiveData("")
    val user: MutableLiveData<String> = MutableLiveData("")

    fun getUserRepo(name: String, page: Int) {

        viewModelScope.launch {

            try {
                _repos.value = repository.getUserRepos(name, page)
            } catch (apiException: ApiException) {
                error.value = apiException.message
            }
        }
    }

    fun submitRepo(view: View) {
        paginationUser.value = user.value
        _submitClicked.value = true
    }
}