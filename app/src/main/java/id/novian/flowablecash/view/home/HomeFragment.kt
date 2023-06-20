package id.novian.flowablecash.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentHomeBinding
import id.novian.flowablecash.helpers.Result

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var balanceSheetAdapter: BalanceSheetAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addRecordTransaction()

        viewModel.getBalanceSheet()
        balanceSheetAdapter = BalanceSheetAdapter()

        dataToNavigate()

        observe()
    }

    override fun onResume() {
        super.onResume()
        observeBalanceSheetData()
        setBalanceSheetTable()
    }

    private fun addRecordTransaction() {
        binding.fabRecord.setOnClickListener {
            it.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToRecordTransaction())
        }
    }

    private fun dataToNavigate() {

        binding.cvTransactions.setOnClickListener {
            navigateToTransactionList("Transactions")
        }

        binding.cvPurchase.setOnClickListener {
            navigateToTransactionList("Purchases")
        }

        binding.cvSale.setOnClickListener {
            navigateToTransactionList("Sales")
        }
    }

    private fun navigateToTransactionList(
        type: String
    ) {

        val action = HomeFragmentDirections.actionHomeFragmentToTransactionsList(type = type)

        findNavController().navigate(action)
    }

    private fun setBalanceSheetTable() {
        with(binding) {
            rvItemBalanceSheet.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = balanceSheetAdapter
            }
        }
    }

    private fun observeBalanceSheetData() {
        viewModel.dataBalanceSheet.observe(viewLifecycleOwner) {
            balanceSheetAdapter.submitList(it)
        }
    }

    private fun observe() {
        viewModel.onResult.observe(viewLifecycleOwner) {
            when (it) {
                Result.FAILED -> viewModel.createToast("Failed!")
                Result.SUCCESS -> viewModel.createToast("Success")
                else -> {}
            }
        }
    }
}