package com.crisvillamil.tvmazeapp.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.HORIZONTAL
import com.crisvillamil.tvmazeapp.OnItemSelected
import com.crisvillamil.tvmazeapp.R
import com.crisvillamil.tvmazeapp.databinding.ActivityMainBinding
import com.crisvillamil.tvmazeapp.model.Show
import com.crisvillamil.tvmazeapp.view.recyclerview.ShowsAdapter
import com.crisvillamil.tvmazeapp.view.recyclerview.ShowsPagingAdapter
import com.crisvillamil.tvmazeapp.viewmodel.MainActivityViewModel
import java.util.concurrent.Executor


class MainActivity : AppCompatActivity() {

    companion object {
        const val SHOW_ITEM_KEY = "ShowItemKey"
        private const val SHARE_ELEMENT_TRANSITION_NAME = "imageShow"
    }

    private val staggeredGridRowsCount = 2
    private lateinit var viewModel: MainActivityViewModel
    private lateinit var mainActivityBinding: ActivityMainBinding
    private lateinit var showsPagingAdapter: ShowsPagingAdapter

    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    private val onShowClicked = object : OnItemSelected<Show> {
        override fun onItemSelected(item: Show, sharedElementTransition: View) {
            navigateToShowDetail(item, sharedElementTransition)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupActivity()
    }

    private fun setupActivity() {
        viewModel = ViewModelProvider(this).get(MainActivityViewModel::class.java)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        checkBiometricSecurity()
    }

    private fun checkBiometricSecurity() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence
                ) {
                    super.onAuthenticationError(errorCode, errString)
                    val errorMessage = getString(R.string.authentication_error_message, errString)
                    Toast.makeText(
                        applicationContext,
                        errorMessage, Toast.LENGTH_SHORT
                    )
                        .show()
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    onAuthenticationSuccess()
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    val errorMessage = getString(R.string.authentication_failed)
                    Toast.makeText(
                        applicationContext, errorMessage,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            })


        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle(resources.getString(R.string.biometric_title))
            .setSubtitle(resources.getString(R.string.biometric_message))
            .setDeviceCredentialAllowed(true)
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    private fun onAuthenticationSuccess() {
        initShowsPagingAdapter()
        initRecyclerView()
        initViewModelEvents()
        viewModel.fetchShows()
        setupSearchInput()
        initSearchObserver()
    }

    private fun initSearchObserver() {
        mainActivityBinding.searchInput.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrBlank()) {
                viewModel.fetchShows()
                hideKeyBoard()
            }
        }
    }

    private fun setupSearchInput() {
        mainActivityBinding.searchInput.setOnEditorActionListener { textView, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                val textToSearch = textView.text.toString()
                onKeyboardSearchButton(textToSearch)
                hideKeyBoard()
                true
            } else {
                false
            }
        }
    }

    private fun onKeyboardSearchButton(textToSearch: String) {
        mainActivityBinding.loader.visibility = View.VISIBLE
        if (textToSearch.isBlank()) {
            viewModel.fetchShows()
        } else {
            viewModel.searchShows(textToSearch)
        }
    }

    private fun hideKeyBoard() {
        val inputMethodManager: InputMethodManager =
            this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            mainActivityBinding.searchInput.windowToken,
            0
        )
    }


    private fun initViewModelEvents() {
        viewModel.showListLiveData.observe(this, {
            mainActivityBinding.loader.visibility = View.GONE
            mainActivityBinding.seriesRecyclerView.adapter = showsPagingAdapter
            showsPagingAdapter.submitData(this.lifecycle, it)
        })
        viewModel.showListFindLiveData.observe(this, {
            mainActivityBinding.loader.visibility = View.GONE
            mainActivityBinding.seriesRecyclerView.adapter = ShowsAdapter(it, onShowClicked)
        })
    }

    private fun initShowsPagingAdapter() {
        showsPagingAdapter = ShowsPagingAdapter(onShowClicked)
    }

    private fun navigateToShowDetail(showItem: Show, sharedElementTransition: View) {
        val intent = Intent(this@MainActivity, ShowDetailActivity::class.java)
        intent.putExtra(SHOW_ITEM_KEY, showItem)
        val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this@MainActivity,
            sharedElementTransition,
            SHARE_ELEMENT_TRANSITION_NAME
        )
        startActivity(intent, options.toBundle())
    }

    private fun initRecyclerView() {
        mainActivityBinding.seriesRecyclerView.apply {
            adapter = showsPagingAdapter
            layoutManager = StaggeredGridLayoutManager(staggeredGridRowsCount, HORIZONTAL)
        }
    }
}