package com.ing.findmyrepo.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ing.findmyrepo.model.response.Repo
import com.ing.findmyrepo.utils.Event

class UserRepoDetailViewModel : ViewModel() {

    var repo: MutableLiveData<Repo> = MutableLiveData()

    private val _isBackClicked: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val isBackClicked: LiveData<Event<Boolean>> get() = _isBackClicked

    fun onBackPressed(view: View) {
        _isBackClicked.value = Event(true)
    }
}