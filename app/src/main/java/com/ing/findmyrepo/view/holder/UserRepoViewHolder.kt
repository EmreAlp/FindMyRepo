package com.ing.findmyrepo.view.holder

import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.ing.findmyrepo.databinding.AdapterUserRepoItemBinding
import com.ing.findmyrepo.model.response.Repo
import com.ing.findmyrepo.view.activity.MainActivity

class UserRepoViewHolder(
    private val binding: AdapterUserRepoItemBinding,
    private val openDetail: (Repo) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var repo: Repo
    private var activity: MainActivity = binding.root.context as MainActivity

    init {
        binding.holder = this
    }

    fun setItem(repo: Repo) {

        val dbRepo = activity.map?.get(repo.id.toString())
        dbRepo?.let { repo.isFavorite = true } ?: run { repo.isFavorite = false }

        this.repo = repo
        binding.setVariable(BR.repo, repo)
        binding.executePendingBindings()
    }

    fun openDetailRepo(view: View) {
        openDetail(repo)
    }
}