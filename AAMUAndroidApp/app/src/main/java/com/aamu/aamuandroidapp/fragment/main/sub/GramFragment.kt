package com.aamu.aamuandroidapp.fragment.main.sub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.aamu.aamuandroidapp.R
import com.aamu.aamuandroidapp.components.gram.AAMUgramHome
import com.aamu.aamuandroidapp.components.gram.AAMUgramViewModel
import com.aamu.aamuandroidapp.components.gram.AAMUgramViewModelFactory
import com.aamu.aamuandroidapp.data.DemoDataProvider
import com.aamu.aamuandroidapp.databinding.FragmentGramBinding
import com.aamu.aamuandroidapp.fragment.main.MainFragmentDirections
import com.aamu.aamuandroidapp.ui.theme.ComposeCookBookTheme

class GramFragment : Fragment() {

    private lateinit var binding: FragmentGramBinding
    private lateinit var navControllerHost : NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGramBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControllerHost = findNavController()

        binding.gramCompose.setContent {
            ComposeCookBookTheme {
                val viewModel : AAMUgramViewModel = viewModel(
                    factory = AAMUgramViewModelFactory(LocalContext.current)
                )
                AAMUgramHome(
                    onLikeClicked = {
                        viewModel.getGramLike(it)
                    },
                    onCommentsClicked = { lno ->
                        val action = MainFragmentDirections.actionMainFragmentToGramDetailFragment(lno)
                        navControllerHost.navigate(action)
                    },
                    onSendClicked = {},
                    onPostingClicked = {navControllerHost.navigate(R.id.action_mainFragment_to_gramPostingFragment)},
                    onMessagingClicked = {navControllerHost.navigate(R.id.action_mainFragment_to_chatlistFragment)}
                )
            }
        }
    }
}