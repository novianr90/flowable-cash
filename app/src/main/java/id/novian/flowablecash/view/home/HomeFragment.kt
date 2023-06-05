package id.novian.flowablecash.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.databinding.FragmentHomeBinding
import id.novian.flowablecash.domain.models.DomainData
import id.novian.flowablecash.domain.models.PurchaseDomain
import id.novian.flowablecash.domain.models.SaleDomain
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.viewmodel.HomeViewModel

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addRecordTransaction()

        dataToNavigate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addRecordTransaction() {
        binding.fabRecord.setOnClickListener {
            it.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToRecordTransaction())
        }
    }

    private fun dataToNavigate() {

        viewModel.onLoading.observe(viewLifecycleOwner) { loading ->
            if (loading) {
                viewModel.createToast(message = "Currently loading")
            }
        }

        binding.cvTransactions.setOnClickListener {
            viewModel.buttonTransactionsClicked()

            viewModel.dataTransactions.observe(viewLifecycleOwner) { transactions ->
                transactions?.let { transactionDomainList ->
                    navigateToTransactionList(
                        type = "Transactions",
                        transactions = transactionDomainList,
                    )
                }
            }
        }

        binding.cvPurchase.setOnClickListener {
            viewModel.buttonPurchaseClicked()

            viewModel.dataPurchase.observe(viewLifecycleOwner) { purchases ->
                purchases?.let { list ->
                    navigateToTransactionList(
                        type = "Purchases",
                        purchases = list,
                    )
                }
            }
        }

        binding.cvSale.setOnClickListener {
            viewModel.buttonSaleClicked()

            viewModel.dataSales.observe(viewLifecycleOwner) { sales ->
                sales?.let { list ->
                    navigateToTransactionList(
                        type = "Sales",
                        sales = list,
                    )
                }
            }
        }
    }

    private fun navigateToTransactionList(
        type: String,
        transactions: List<TransactionDomain>? = null,
        sales: List<SaleDomain>? = null,
        purchases: List<PurchaseDomain>? = null,
    ) {

        val params = DomainData(
            listOfTransactions = transactions,
            listOfSales = sales,
            listOfPurchases = purchases
        )

        val action = HomeFragmentDirections.actionHomeFragmentToTransactionsList(
            type = type,
            dataToSend = params
        )

        findNavController().navigate(action)
    }
}