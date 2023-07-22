package id.novian.flowablecash.view.report.income_statements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentIncomeStatementsBinding
import id.novian.flowablecash.helpers.Helpers

@AndroidEntryPoint
class IncomeStatements : BaseFragment<FragmentIncomeStatementsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIncomeStatementsBinding
        get() = FragmentIncomeStatementsBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    private val viewModel: IncomeStatementsViewModel by viewModels()

    override fun setup() {
        super.setup()
        viewModel.viewModelInitialized()

        observe()
    }

    private fun observe() {

        binding.tvMonth.text = viewModel.calendarHelper.getMonth().toString()

        with(viewModel) {

            cashReceipt.observe(viewLifecycleOwner) { list ->
                val cashInTotal = list.sumOf { it.credit }
                val totalInString = Helpers.formatCurrency(cashInTotal)
                
                binding.tvSaldoAkunPenjualan.text = totalInString
                binding.tvTotalSaldoAkunPenjualan.text = totalInString
                binding.tvCountSaldoPenjualan.text = totalInString
            }

            purchasesJournal.observe(viewLifecycleOwner) { list ->
                val purchaseInTotal = list.sumOf { it.debit }
                val totalInString = Helpers.formatCurrency(purchaseInTotal)
                
                binding.tvSaldoAkunPembelian.text = totalInString
                binding.tvTotalSaldoAkunPembelian.text = totalInString
                binding.tvCountSaldoPembelian.text = totalInString
            }

            akunBebanPenjualan.observe(viewLifecycleOwner) { list ->
                val beban = list.sumOf { it.balance.debit }
                val totalInString = Helpers.formatCurrency(beban)
                binding.tvTotalSaldoAkunBebanPenjualan.text = totalInString
                binding.tvCountSaldoBebanPenjualan.text = totalInString
            }

            akunBebanPembelian.observe(viewLifecycleOwner) { list ->
                val beban = list.sumOf { it.balance.debit }
                val totalInString = Helpers.formatCurrency(beban)
                binding.tvTotalSaldoAkunBebanPembelian.text = totalInString
                binding.tvCountSaldoBebanPembelian.text = totalInString
            }

            labaKotor.observe(viewLifecycleOwner) {
                binding.tvCountSaldoLabaKotor.text = Helpers.formatCurrency(it)
            }

            labaBersih.observe(viewLifecycleOwner) {
                binding.tvCountSaldoLabaBersih.text = Helpers.formatCurrency(it)
            }

        }

    }

}