package id.novian.flowablecash.view.journaling.purchases_journal

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        itemAdapter = PurchasesJournalAdapter()
        viewModel.getJournal()
        setBtnBack()
    }

    override fun onResume() {
        super.onResume()
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
                Log.d("PurchaseJournal", it)
            }
        }
    }
}