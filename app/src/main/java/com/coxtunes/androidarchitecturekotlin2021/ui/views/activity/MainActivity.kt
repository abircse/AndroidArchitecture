package com.coxtunes.androidarchitecturekotlin2021.ui.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.coxtunes.androidarchitecturekotlin2021.R
import com.coxtunes.androidarchitecturekotlin2021.databinding.ActivityMainBinding
import com.coxtunes.androidarchitecturekotlin2021.ui.uistate.UsersUiState
import com.coxtunes.androidarchitecturekotlin2021.ui.viewmodel.UserViewModel
import com.coxtunes.androidarchitecturekotlin2021.ui.views.adapter.UsersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var binding: ActivityMainBinding? = null

    private val userviewModel: UserViewModel by viewModels()

    private val userItemAdapter: UsersAdapter by lazy {
        UsersAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpViews()
        subscribeQuery()
    }


    private fun subscribeQuery() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                userviewModel.apply {
                    getUsers()
                    uiState.collect {
                        handleUiState(it)
                    }
                }
            }
        }
    }

    private fun handleUiState(UiState: UsersUiState) {
        UiState.apply {
            userItemAdapter.submitCatFactList(userList)
        }
    }

    private fun setUpViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding?.root)
        binding?.apply {
            viewModel = userviewModel
            recyclerview.adapter = userItemAdapter
        }
    }
}