package com.ing.findmyrepo.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ing.findmyrepo.R
import com.ing.findmyrepo.databinding.FragmentDetailUserRepoBinding
import com.ing.findmyrepo.model.response.Repo
import com.ing.findmyrepo.utils.Event
import com.ing.findmyrepo.view.activity.MainActivity
import com.ing.findmyrepo.viewmodel.UserRepoDetailViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserRepoDetailFragment : Fragment() {

    private val vm: UserRepoDetailViewModel by viewModel()

    private lateinit var binding: FragmentDetailUserRepoBinding
    private lateinit var mainActivity: MainActivity
    private lateinit var repo: Repo
    private var dbRepo: Repo? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DataBindingUtil.inflate<FragmentDetailUserRepoBinding>(
            inflater,
            R.layout.fragment_detail_user_repo,
            container,
            false
        ).also { fragmentDetailUserRepo ->
            fragmentDetailUserRepo.lifecycleOwner = this
            binding = fragmentDetailUserRepo
            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm
        binding.lifecycleOwner = this

        repo = arguments?.getParcelable("repo")!!

        dbRepo = mainActivity.map?.get(repo.id.toString())
        dbRepo?.let { repo.isFavorite = true }

        vm.repo.value = repo

        vm.isBackClicked.observe(mainActivity, backClickedObserver)

        binding.ivFavorite.setOnClickListener {

            dbRepo?.let { // if repo isn't a favorite

                mainActivity.removeRepo(it)
                repo.isFavorite = false
                vm.repo.value = repo

            } ?: run { // if repo is a favorite

                mainActivity.addRepo(repo)
                repo.isFavorite = true
                vm.repo.value = repo
            }
        }
    }

    private val backClickedObserver = Observer<Event<Boolean>> { isClicked ->

        isClicked?.getContentIfNotHandledOrReturnNull()?.let {
            mainActivity.onBackPressed()
        }
    }
}