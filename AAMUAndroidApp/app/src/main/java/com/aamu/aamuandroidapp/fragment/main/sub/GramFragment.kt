package com.aamu.aamuandroidapp.fragment.main.sub

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelProvider
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
import com.aamu.aamuandroidapp.fragment.main.MainViewModel
import com.aamu.aamuandroidapp.fragment.main.MainViewModelFactory
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

    private lateinit var viewModel : AAMUgramViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navControllerHost = findNavController()
        viewModel = ViewModelProvider(requireActivity(),
            AAMUgramViewModelFactory(requireContext())
        )[AAMUgramViewModel::class.java]
        binding.gramCompose.setContent {
            ComposeCookBookTheme {
                AAMUgramHome(
                    viewModel = viewModel,
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