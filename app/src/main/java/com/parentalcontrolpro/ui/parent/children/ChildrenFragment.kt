package com.parentalcontrolpro.ui.parent.children

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.parentalcontrolpro.databinding.FragmentChildrenBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChildrenFragment : Fragment() {
    
    private var _binding: FragmentChildrenBinding? = null
    private val binding get() = _binding!!
    
    private val viewModel: ChildrenViewModel by viewModels()
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildrenBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }
    
    private fun setupUI() {
        binding.fabAddChild.setOnClickListener {
            // TODO: Navigate to add child screen
        }
    }
    
    private fun observeViewModel() {
        // TODO: Observe ViewModel state
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}