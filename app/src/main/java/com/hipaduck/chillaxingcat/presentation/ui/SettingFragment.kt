package com.hipaduck.chillaxingcat.presentation.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hipaduck.base.common.BaseBindingFragment
import com.hipaduck.base.util.showToast
import com.hipaduck.chillaxingcat.R
import com.hipaduck.chillaxingcat.databinding.FragmentSettingBinding
import com.hipaduck.chillaxingcat.presentation.viewmodel.SettingViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class SettingFragment : BaseBindingFragment<FragmentSettingBinding>() {
    @LayoutRes
    override fun getLayoutResId() = R.layout.fragment_setting

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)
        binding.vm = getViewModel()
        binding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.settingToolbar.basicToolbarBack.setOnClickListener { findNavController().navigateUp() }
        binding.settingRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.settingRecyclerview.setHasFixedSize(true)

        binding.settingToolbar.basicToolbarBack.setOnClickListener { findNavController().navigateUp() }

        binding.vm?.actionEvent?.observe(viewLifecycleOwner) { event->
            event.getContentIfNotHandled()?.let { action ->
                when (action) {
                    is SettingViewModel.Action.DialogAction -> {
                        when(action.type) {

                        }
                    }
                    is SettingViewModel.Action.ToastAction -> {
                        showToast(action.msg)
                    }
                }
            }
        }
    }
}