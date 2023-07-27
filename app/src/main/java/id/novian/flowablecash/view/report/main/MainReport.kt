package id.novian.flowablecash.view.report.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import id.novian.flowablecash.R
import id.novian.flowablecash.base.layout.BaseFragment
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
            val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_spacing)
            val includeEdge = false
            addItemDecoration(GridSpacingItemDecoration(2, spacingInPixels, includeEdge))
        }
        reportAdapter.submitList(menuReportItems)
    }

    private fun moveTo(destination: Int) {
        findNavController().navigate(destination)
    }

    private fun onClickOnItem(journalType: String) {
        when (journalType) {
            "Jurnal Penjualan" -> moveTo(R.id.action_mainReport_to_cashReceiptJournal)
            "Jurnal Pembelian" -> moveTo(R.id.action_mainReport_to_purchasesJournal)
            "Neraca Saldo" -> moveTo(R.id.action_mainReport_to_balanceSheetFragment)
            "Laporan Laba Rugi" -> moveTo(R.id.action_mainReport_to_incomeStatements)
            "Laporan Posisi Keuangan" -> moveTo(R.id.action_mainReport_to_financialPosition)
        }
    }
}