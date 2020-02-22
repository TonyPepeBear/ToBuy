package com.tonypepe.tobuy.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.tonypepe.tobuy.R
import com.tonypepe.tobuy.logd

class SettingsFragment : Fragment(R.layout.fragment_settings) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logd("onViewCreated")

    }
}
