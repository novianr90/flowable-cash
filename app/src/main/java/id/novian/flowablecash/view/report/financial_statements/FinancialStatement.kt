package id.novian.flowablecash.view.report.financial_statements

import android.view.LayoutInflater
import android.view.ViewGroup
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.layout.BaseFragment
import id.novian.flowablecash.databinding.FragmentFinancialStatementBinding

@AndroidEntryPoint
class FinancialStatement : BaseFragment<FragmentFinancialStatementBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFinancialStatementBinding
        get() = FragmentFinancialStatementBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false


}