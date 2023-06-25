package id.novian.flowablecash.view.report.ledger

import android.view.LayoutInflater
import android.view.ViewGroup
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentLedgerBinding

class Ledger : BaseFragment<FragmentLedgerBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentLedgerBinding
        get() = FragmentLedgerBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false
}