package id.novian.flowablecash.view.journaling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.databinding.FragmentTransactionsListBinding
import id.novian.flowablecash.domain.models.DomainData
import id.novian.flowablecash.viewmodel.TransactionListViewModel

@AndroidEntryPoint
class TransactionsList : Fragment() {
    private var _binding: FragmentTransactionsListBinding? = null
    private val binding get() = _binding!!

    private val args: TransactionsListArgs by navArgs()

    private val viewModel: TransactionListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionsListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showData(
            type = args.type ?: "Transactions",
            data = args.dataToSend
        )
    }

    private fun showData(
        type: String,
        data: DomainData?
    ) {

        val transactions = data?.listOfTransactions
        val sales = data?.listOfSales
        val purchases = data?.listOfPurchases

        binding.tvTransactionType.text = type

        if (!transactions.isNullOrEmpty()) {
            viewModel.createToast(transactions[0].transactionName)
        } else if (!sales.isNullOrEmpty()) {
            viewModel.createToast(sales[0].transactionName)
        } else if (!purchases.isNullOrEmpty()) {
            viewModel.createToast(purchases[0].transactionName)
        }

    }

}