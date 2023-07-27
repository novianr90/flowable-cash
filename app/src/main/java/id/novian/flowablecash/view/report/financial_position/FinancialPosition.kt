package id.novian.flowablecash.view.report.financial_position

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.layout.BaseFragment
import id.novian.flowablecash.databinding.FragmentFinancialPositionBinding
import id.novian.flowablecash.domain.models.AccountDomain

@AndroidEntryPoint
class FinancialPosition : BaseFragment<FragmentFinancialPositionBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFinancialPositionBinding
        get() = FragmentFinancialPositionBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    private val viewModel: FinancialPositionViewModel by viewModels()

    private val listOfFinancialPosData = mutableListOf<FinancialPositionParentData>()
    private lateinit var parentAdapter: FinancialPositionParentAdapter

    override fun setup() {
        super.setup()
        viewModel.viewModelInitialized()
        observe()

        setRecyclerView()
        buttonBack()
    }

    private fun setRecyclerView() {
        parentAdapter = FinancialPositionParentAdapter()
        binding.apply {
            rvFinancialPosition.adapter = parentAdapter
            rvFinancialPosition.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observe() {
        viewModel.dataAllAccounts.observe(viewLifecycleOwner) { list ->
            val asetLancar = accountSeparator(list, "Aset Lancar", "1-1")
            val asetTetap = accountSeparator(list, "Aset Tetap", "1-2")
            val liability = accountSeparator(list, "Liabilitas", "2-")
            val ekuitas = accountSeparator(list, "Ekuitas", "3-")

            listOfFinancialPosData.add(asetLancar)
            listOfFinancialPosData.add(asetTetap)
            listOfFinancialPosData.add(liability)
            listOfFinancialPosData.add(ekuitas)

            parentAdapter.submitList(listOfFinancialPosData)
        }
    }

    private fun accountSeparator(
        list: List<AccountDomain>,
        title: String,
        conditions: String
    ): FinancialPositionParentData {
        val listData = list
            .filter { it.accountNo.startsWith(conditions) }

        Log.i("Separator", "Data is for $title with list of $listData")

        val totalDebit = listData.sumOf { it.balance.debit }
        val totalCredit = listData.sumOf { it.balance.credit }
        val totalBalance = totalDebit + totalCredit

        return FinancialPositionParentData(
            title,
            listData,
            totalBalance
        )
    }

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}