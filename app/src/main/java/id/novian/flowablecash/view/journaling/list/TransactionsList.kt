package id.novian.flowablecash.view.journaling.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.databinding.CustomDialogForItemClickedBinding
import id.novian.flowablecash.databinding.FragmentTransactionsListBinding
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers

@AndroidEntryPoint
class TransactionsList : Fragment() {
    private var _binding: FragmentTransactionsListBinding? = null
    private val binding get() = _binding!!

    private val args: TransactionsListArgs by navArgs()

    private val viewModel: TransactionListViewModel by viewModels()

    private lateinit var listAdapter: TransactionListAdapter

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

        initRecyclerView()

        showData(
            type = args.type ?: "Default"
        )

        btnBackLogic()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callBack)
    }

    private fun initRecyclerView() {
        binding.rvItem.apply {
            listAdapter = TransactionListAdapter(::showDialog)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = listAdapter
        }
    }

    private fun showData(
        type: String
    ) {

        when (type) {
            "Transactions" -> viewModel.buttonTransactionClicked()
            "Sales" -> viewModel.buttonSaleClicked()
            "Purchases" -> viewModel.buttonPurchaseClicked()
        }

        binding.tvTransactionType.text = type

        viewModel.dataTransactions.observe(viewLifecycleOwner) { data ->
            if (!data.isNullOrEmpty()) {
                listAdapter.submitList(data)
            }
        }

        viewModel.onError.observe(viewLifecycleOwner) { err ->
            if (err) {
                viewModel.createToast("Error Occurred!")
            }
        }
    }

    private fun btnBackLogic() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private val callBack = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            findNavController().popBackStack()
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
            val action =
                TransactionsListDirections.actionTransactionsListToUpdateFragment(details.id)

            findNavController().navigate(action)
        }

        dialogBinding.btnDeleted.setOnClickListener {
            viewModel.deleteTransaction(details)
        }

        dialog.show()
    }

}