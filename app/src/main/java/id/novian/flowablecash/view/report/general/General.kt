package id.novian.flowablecash.view.report.general

import android.view.LayoutInflater
import android.view.ViewGroup
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentGeneralBinding

class General : BaseFragment<FragmentGeneralBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentGeneralBinding
        get() = FragmentGeneralBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false
}