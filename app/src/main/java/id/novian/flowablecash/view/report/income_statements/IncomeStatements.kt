package id.novian.flowablecash.view.report.income_statements

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.layout.BaseFragment
import id.novian.flowablecash.databinding.FragmentIncomeStatementsBinding
import id.novian.flowablecash.helpers.Helpers

@AndroidEntryPoint
class IncomeStatements : BaseFragment<FragmentIncomeStatementsBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentIncomeStatementsBinding
        get() = FragmentIncomeStatementsBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    private val viewModel: IncomeStatementsViewModel by viewModels()

    private lateinit var hppAdapter: HargaPokokPenjualanAdapter
    private lateinit var bebanAdapter: BebanAdapter

    override fun setup() {
        super.setup()
        viewModel.viewModelInitialized()

        observe()

        setBeban()
        setHpp()
    }

    private fun setHpp() {
        hppAdapter = HargaPokokPenjualanAdapter()

        binding.rvHpp.apply {
            adapter = hppAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setBeban() {
        bebanAdapter = BebanAdapter()

        binding.rvBebanBeban.apply {
            adapter = bebanAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observe() {

        val monthInString = Helpers.getMonthName(viewModel.calendarHelper.getMonth())
        binding.tvMonth.text = "Laba Rugi dalam Bulan " + monthInString

        with(viewModel) {

            listOfHpp.observe(viewLifecycleOwner) { hpp ->
                hppAdapter.submitList(hpp)
            }

            listOfBeban.observe(viewLifecycleOwner) { beban ->
                bebanAdapter.submitList(beban)
            }

            totalPenjualan.observe(viewLifecycleOwner) { total ->
                val totalInString = Helpers.formatCurrency(total)

                binding.tvTotalSaldoAkunPenjualan.text = totalInString
                binding.tvCountSaldoPenjualan.text = totalInString
            }

            totalHpp.observe(viewLifecycleOwner) { total ->
                val totalInString = Helpers.formatCurrency(total)

                binding.tvTotalSaldoAkunPembelian.text = totalInString
                binding.tvCountSaldoPembelian.text = totalInString
            }

            totalBeban.observe(viewLifecycleOwner) { total ->
                val totalInString = Helpers.formatCurrency(total)

                binding.tvTotalSaldoAkunBebanBeban.text = totalInString
                binding.tvCountSaldoTotalBeban.text = totalInString
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