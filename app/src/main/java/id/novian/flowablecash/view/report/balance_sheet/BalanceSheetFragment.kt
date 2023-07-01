package id.novian.flowablecash.view.report.balance_sheet

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentBalanceSheetBinding
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.helpers.Helpers
import id.novian.flowablecash.helpers.Result

@AndroidEntryPoint
class BalanceSheetFragment : BaseFragment<FragmentBalanceSheetBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBalanceSheetBinding
        get() = FragmentBalanceSheetBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    private val viewModel: BalanceSheetViewModel by viewModels()

    private lateinit var sheetAdapter: BalanceSheetAdapter

    override fun setup() {
        super.setup()
        getBalanceSheet()
        setAdapter()
        setRecyclerView()
        observeData()
        observeError()
    }

    private fun getBalanceSheet() {
        viewModel.getBalanceSheetJournal()
    }

    private fun setAdapter() {
        sheetAdapter = BalanceSheetAdapter()
    }

    private fun setRecyclerView() {
        binding.rvItemBalanceSheetJournal.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = sheetAdapter
        }
    }

    private fun observeData() {
        with(viewModel) {

            dataBalanceSheet.observe(viewLifecycleOwner) {
                val sortData = it.sortedBy { data -> data.accountNo }
                sheetAdapter.submitList(sortData)

                setTotalOfBalanceSheet(sortData)

                Log.d("BalanceSheetFragment", "Data is: $it")
            }

            onProcess.observe(viewLifecycleOwner) { process ->
                when(process) {
                    Result.LOADING -> {}
                    Result.SUCCESS -> {}
                    Result.FAILED -> {}
                }
            }

        }
    }

    private fun observeError() {
        viewModel.errMessage.observe(viewLifecycleOwner) {
            Log.d("BalanceSheetFragment", "Error Occurred! Message: $it")
        }
    }

    private fun setTotalOfBalanceSheet(data: List<BalanceSheetDomain>) {
        var debit = 0
        var credit = 0

        for (i in data.indices) {
            debit += data[i].balance.debit
            credit += data[i].balance.credit
        }

        binding.tvDebit.text = Helpers.numberFormatter(debit)
        binding.tvCredit.text = Helpers.numberFormatter(credit)
    }

}