package com.ing.findmyrepo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ing.findmyrepo.R
import com.ing.findmyrepo.databinding.AdapterUserRepoItemBinding
import com.ing.findmyrepo.model.response.Repo
import com.ing.findmyrepo.view.activity.MainActivity
import com.ing.findmyrepo.view.holder.UserRepoViewHolder

class UserRepoAdapter(
    private val mainActivity: MainActivity,
    private val openDetail: (Repo) -> Unit,
    mDataSet: MutableList<Repo>
) : BaseRecyclerAdapter<UserRepoViewHolder, Repo>(mDataSet) {

    override fun createView(view: ViewGroup, viewType: Int): UserRepoViewHolder {

        val layoutInflater =
            mainActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val binding = DataBindingUtil.inflate<AdapterUserRepoItemBinding>(
            layoutInflater,
            R.layout.adapter_user_repo_item,
            view,
            false
        )
        return UserRepoViewHolder(binding,openDetail)
    }

    override fun bindView(view: UserRepoViewHolder, position: Int) {

        val character = getItem(position)
        view.setItem(character)
    }
}