package id.novian.flowablecash.view.home

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.CustomDialogForItemClickedBinding
import id.novian.flowablecash.databinding.FragmentHomeBinding
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers
import id.novian.flowablecash.helpers.Result

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var transactionListAdapter: TransactionListAdapter

    override fun setup() {
        super.setup()
        getTransactions()
        setListOfTransaction()

        observe()
    }

    override fun onResume() {
        super.onResume()
        setupRecyclerView()
    }

    private fun observe() {
        with(viewModel) {
            errMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    viewModel.createToast(it)
                }
            }

            onResult.observe(viewLifecycleOwner) {
                when (it) {
                    Result.SUCCESS -> {}
                    Result.FAILED -> {}
                    Result.LOADING -> {}
                    else -> {}
                }
            }
        }
    }

    private fun getTransactions() {
        viewModel.getListOfTransactions()
    }

    private fun setListOfTransaction() {
        viewModel.dataTransactions.observe(viewLifecycleOwner) {
            transactionListAdapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {
        binding.rvItemTransactionList.apply {
            transactionListAdapter = TransactionListAdapter(::showDialog)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionListAdapter
        }
    }

    private fun showDialog(details: TransactionDomain) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())

        val dialogBinding: CustomDialogForItemClickedBinding =
            CustomDialogForItemClickedBinding.inflate(inflater)

        dialogBinding.tvItemNameDetails.text = details.transactionName
        dialogBinding.tvItemDateDetails.text = details.transactionDate
        dialogBinding.tvItemTypeDetails.text =
            Helpers.transactionTypeChanger(details.transactionType)
        dialogBinding.tvItemTotalDetails.text = Helpers.numberFormatter(details.total)
        dialogBinding.tvItemCreatedAtDetails.text = details.createdAt
        dialogBinding.tvItemUpdatedAtDetails.text = details.updatedAt

        dialogBuilder.setView(dialogBinding.root)

        val dialog = dialogBuilder.create()

        dialogBinding.btnUpdated.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment()
            findNavController().navigate(action)
            dialog.dismiss()
        }

        dialogBinding.btnDeleted.setOnClickListener {
            viewModel.deleteTransaction(details)
            dialog.dismiss()
        }

        dialog.show()
    }
}