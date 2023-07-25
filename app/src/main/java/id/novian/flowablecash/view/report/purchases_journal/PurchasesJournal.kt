package id.novian.flowablecash.view.report.purchases_journal

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentPurchasesJournalBinding

@AndroidEntryPoint
class PurchasesJournal : BaseFragment<FragmentPurchasesJournalBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPurchasesJournalBinding
        get() = FragmentPurchasesJournalBinding::inflate

    private val viewModel: PurchasesJournalViewModel by viewModels()
    private lateinit var itemAdapter: PurchasesJournalAdapter

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()
        itemAdapter = PurchasesJournalAdapter()
        viewModel.getJournal()
        setBtnBack()

        observe()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        binding.rvItemPurchasesJournal.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = itemAdapter
        }
    }

    private fun setBtnBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observe() {
        with(viewModel) {

            dataPurchaseJournal.observe(viewLifecycleOwner) {
                itemAdapter.submitList(it)
            }

            errMessage.observe(viewLifecycleOwner) {
                createToast(it)
                Log.d("PurchaseJournal", "Error Occurred! Message: $it")
            }
        }
    }
}