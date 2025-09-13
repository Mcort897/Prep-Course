package com.parentalcontrolpro.ui.child.apps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.parentalcontrolpro.databinding.FragmentChildAppsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppsFragment : Fragment() {
    
    private var _binding: FragmentChildAppsBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: AppsViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildAppsBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        // TODO: Setup apps UI
    }
    
    private fun observeViewModel() {
        // TODO: Observe ViewModel state
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}