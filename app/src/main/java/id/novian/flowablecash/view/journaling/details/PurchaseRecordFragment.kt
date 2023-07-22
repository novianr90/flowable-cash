package id.novian.flowablecash.view.journaling.details

import android.view.LayoutInflater
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentPurchaseRecordBinding

@AndroidEntryPoint
class PurchaseRecordFragment: BaseFragment<FragmentPurchaseRecordBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPurchaseRecordBinding
        get() = FragmentPurchaseRecordBinding::inflate

    override val hasBottomNavigationView: Boolean
        get() = false

    override val isNavigationVisible: Boolean
        get() = false


}