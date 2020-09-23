package com.ing.findmyrepo.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import com.ing.findmyrepo.R
import com.ing.findmyrepo.databinding.ActivityMainBinding
import com.ing.findmyrepo.model.response.Repo
import com.ing.findmyrepo.utils.Helper.toast
import io.paperdb.Paper
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    lateinit var navigationController: NavController

    var map: HashMap<String, Repo>? = null

    private val error: MutableLiveData<String> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        createMap()

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navigationController = Navigation.findNavController(this, R.id.nav_fragment)

        error.observe(this, errorObserver)
    }

    fun showLoading() {
        binding.progressBar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        binding.progressBar.visibility = View.GONE
    }

    fun addRepo(repo: Repo) {
        map?.put(repo.id.toString(), repo)
        Paper.book().write("map", map)
    }

    fun removeRepo(repo: Repo) {
        map?.remove(repo.id.toString())
        Paper.book().write("map", map)
    }

    private fun createMap() {

        map = Paper.book().read<HashMap<String, Repo>>("map")

        if (map == null) {
            map = hashMapOf()
            Paper.book().write("map", map)
        }
    }

    private val errorObserver = Observer<String> { error ->

        error?.let {
            if (error.isNotEmpty()) {
                hideLoading()
                toast(error)
            }
        }
    }
}