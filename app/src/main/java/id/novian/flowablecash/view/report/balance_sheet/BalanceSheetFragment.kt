package id.novian.flowablecash.view.report.balance_sheet

import android.view.LayoutInflater
import android.view.ViewGroup
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentBalanceSheetBinding

class BalanceSheetFragment : BaseFragment<FragmentBalanceSheetBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBalanceSheetBinding
        get() = FragmentBalanceSheetBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

}