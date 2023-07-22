package id.novian.flowablecash.view.report.financial_position

import android.view.LayoutInflater
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentFinancialPositionBinding

@AndroidEntryPoint
class FinancialPosition : BaseFragment<FragmentFinancialPositionBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFinancialPositionBinding
        get() = FragmentFinancialPositionBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false


}