package com.ing.findmyrepo.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ing.findmyrepo.R
import com.ing.findmyrepo.adapter.UserRepoAdapter
import com.ing.findmyrepo.databinding.FragmentUserRepoBinding
import com.ing.findmyrepo.model.response.Repo
import com.ing.findmyrepo.view.activity.MainActivity
import com.ing.findmyrepo.viewmodel.UserRepoViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserRepoFragment : Fragment() {

    private val vm: UserRepoViewModel by viewModel()

    private lateinit var binding: FragmentUserRepoBinding
    private lateinit var adapter: UserRepoAdapter
    private lateinit var mainActivity: MainActivity

    private lateinit var repoList: ArrayList<Repo>

    private var page = 1
    private var totalItemCount = 0
    private var pastVisibleItems = 0
    private var visibleItemCount = 0

    private var canFetchData = true

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        DataBindingUtil.inflate<FragmentUserRepoBinding>(
            inflater,
            R.layout.fragment_user_repo,
            container,
            false
        ).also { fragmentUserRepo ->
            fragmentUserRepo.lifecycleOwner = this
            binding = fragmentUserRepo
            return binding.root
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        repoList = ArrayList()

        vm.repos.observe(mainActivity, userRepoObserver)
        vm.submitClicked.observe(mainActivity, submitObserver)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.vm = vm
        binding.lifecycleOwner = this
        binding.edtUser.doAfterTextChanged { vm.user.value = it.toString() }

        createAdapter()
    }

    private val submitObserver = Observer<Boolean> { isClicked ->

        if (isClicked) {

            mainActivity.showLoading()

            page = 1
            repoList.clear()
            adapter.swapDataSet(repoList)

            vm.getUserRepo(vm.user.value!!, 1)
        }
    }

    private val userRepoObserver = Observer<List<Repo>> { repos ->

        repos?.let {

            mainActivity.hideLoading()
            canFetchData = true
            repoList.addAll(repos)
            adapter.swapDataSet(repoList)
        }
    }

    private fun createAdapter() {

        adapter = UserRepoAdapter(mainActivity, {

            val bundle = bundleOf()
            bundle.putParcelable("repo", it)

            mainActivity.navigationController.navigate(
                R.id.action_userRepoFragment_to_userRepoDetailFragment,
                bundle
            )

        }, repoList)

        val lm = LinearLayoutManager(mainActivity)
        binding.rvUserRepo.adapter = adapter
        binding.rvUserRepo.layoutManager = lm

        binding.rvUserRepo.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (dy > 0) {

                    visibleItemCount = lm.childCount
                    totalItemCount = lm.itemCount
                    pastVisibleItems = lm.findFirstVisibleItemPosition()

                    if (visibleItemCount + pastVisibleItems >= totalItemCount) {

                        if (canFetchData) {

                            mainActivity.showLoading()

                            page += 1
                            vm.getUserRepo(vm.paginationUser.value!!, page)
                            canFetchData = false
                        }
                    }
                }
            }
        })
    }
}