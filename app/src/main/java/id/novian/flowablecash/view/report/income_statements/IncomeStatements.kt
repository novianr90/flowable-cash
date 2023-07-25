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

            totalPenjualan.observe(viewLifecycleOwner) { total ->
                val totalInString = Helpers.formatCurrency(total)

                binding.tvSaldoAkunPenjualan.text = totalInString
                binding.tvTotalSaldoAkunPenjualan.text = totalInString
                binding.tvCountSaldoPenjualan.text = totalInString
            }

            totalPembelian.observe(viewLifecycleOwner) { total ->
                val totalInString = Helpers.formatCurrency(total)

                binding.tvSaldoAkunPembelian.text = totalInString
                binding.tvTotalSaldoAkunPembelian.text = totalInString
                binding.tvCountSaldoPembelian.text = totalInString
            }

            akunBebanOngkos.observe(viewLifecycleOwner) { total ->
                val totalInString = Helpers.formatCurrency(total)
                binding.tvTotalSaldoAkunBebanPenjualan.text = totalInString
                binding.tvCountSaldoBebanOngkos.text = totalInString
            }

            akunBebanPengemasan.observe(viewLifecycleOwner) { total ->
                val totalInString = Helpers.formatCurrency(total)
                binding.tvTotalSaldoAkunBebanPembelian.text = totalInString
                binding.tvCountSaldoBebanPengemasan.text = totalInString
            }

            akunBebanOperasional.observe(viewLifecycleOwner) { total ->
                val totalInString = Helpers.formatCurrency(total)
                binding.tvTotalSaldoAkunBebanOperasional.text = totalInString
                binding.tvCountSaldoBebanOperasional.text = totalInString
            }

            akunBebanLainnya.observe(viewLifecycleOwner) { total ->
                val totalInString = Helpers.formatCurrency(total)
                binding.tvTotalSaldoAkunBebanLainnya.text = totalInString
                binding.tvCountSaldoBebanLainnya.text = totalInString
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