package id.novian.flowablecash.view.report.balance_sheet

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.layout.BaseFragment
import id.novian.flowablecash.databinding.FragmentBalanceSheetBinding
import id.novian.flowablecash.helpers.Result

@AndroidEntryPoint
class TrialBalanceFragment : BaseFragment<FragmentBalanceSheetBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentBalanceSheetBinding
        get() = FragmentBalanceSheetBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    private val viewModel: TrialBalanceViewModel by viewModels()

    private lateinit var sheetAdapter: TrialBalanceAdapter

    override fun setup() {
        super.setup()
        getBalanceSheet()
        setAdapter()
        setRecyclerView()
        observeData()
        observeError()

        buttonBack()
    }

    private fun getBalanceSheet() {
        viewModel.getJournal()
    }

    private fun setAdapter() {
        sheetAdapter = TrialBalanceAdapter()
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

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}