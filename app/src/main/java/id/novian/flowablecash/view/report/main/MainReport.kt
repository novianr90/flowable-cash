package id.novian.flowablecash.view.report.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import id.novian.flowablecash.R
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentMainReportBinding

class MainReport : BaseFragment<FragmentMainReportBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainReportBinding
        get() = FragmentMainReportBinding::inflate

    private lateinit var reportAdapter: MenuReportAdapter

    override fun setup() {
        super.setup()
        setAdapter()
    }

    override fun onResume() {
        super.onResume()
        setRecyclerView()
    }

    private fun setAdapter() {
        reportAdapter = MenuReportAdapter(::onClickOnItem)
    }

    private fun setRecyclerView() {
        binding.rvItemMainReport.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = reportAdapter
            setHasFixedSize(true)
        }
        reportAdapter.submitList(menuReportItems)
    }

    private fun moveTo(destination: Int) {
        findNavController().navigate(destination)
    }

    private fun onClickOnItem(journalType: String) {
        when (journalType) {
            "Cash Receipt Journal" -> moveTo(R.id.action_mainReport_to_cashReceiptJournal)
            "Purchases Journal" -> moveTo(R.id.action_mainReport_to_purchasesJournal)
        }
    }
}