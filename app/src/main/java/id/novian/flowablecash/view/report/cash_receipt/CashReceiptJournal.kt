package id.novian.flowablecash.view.report.cash_receipt

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentCashReceiptJournalBinding

@AndroidEntryPoint
class CashReceiptJournal :
    BaseFragment<FragmentCashReceiptJournalBinding>() {

    private val viewModel: CashReceiptJournalViewModel by viewModels()

    private lateinit var itemAdapter: CashReceiptAdapter

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentCashReceiptJournalBinding
        get() = FragmentCashReceiptJournalBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        viewModel.getCashReceiptJournal()
        itemAdapter = CashReceiptAdapter()
        observe()
        setButtonBack()
        setRecyclerView()
        getJournal()
    }

    private fun setRecyclerView() {
        binding.rvItemCashReceiptJournal.adapter = itemAdapter
        binding.rvItemCashReceiptJournal.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun getJournal() {
        viewModel.dataCashReceiptJournal.observe(viewLifecycleOwner) {
            itemAdapter.submitList(it)
        }
    }

    private fun observe() {
        with(viewModel) {
            errMessage.observe(viewLifecycleOwner) {
                viewModel.createToast(it)
                Log.d("CashReceipt", "Error Occurred! Message $it")
            }
        }
    }

    private fun setButtonBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}