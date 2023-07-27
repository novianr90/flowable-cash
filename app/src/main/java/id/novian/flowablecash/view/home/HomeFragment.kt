package id.novian.flowablecash.view.home

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.base.custom.CustomSnackBar
import id.novian.flowablecash.base.custom.CustomSnackBarImpl
import id.novian.flowablecash.base.layout.BaseFragment
import id.novian.flowablecash.databinding.CustomDialogForItemClickedBinding
import id.novian.flowablecash.databinding.FragmentHomeBinding
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers
import id.novian.flowablecash.helpers.Result

@AndroidEntryPoint
class HomeFragment :
    BaseFragment<FragmentHomeBinding>() {

    private lateinit var snackBar: CustomSnackBar

    private var isExecuted = false

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: HomeViewModel by viewModels()

    // TransactionList Dependencies
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinnerSortDate: Spinner
    private lateinit var transactionListAdapter: TransactionListAdapter
    private lateinit var arrayAdapter: ArrayAdapter<CharSequence>

    private val transactionList = mutableListOf<TransactionDomain>()
    private val filteredList = mutableListOf<TransactionDomain>()

    override fun setup() {
        super.setup()

        snackBar = CustomSnackBarImpl(
            requireNotNull(rootView),
            requireActivity().findViewById(R.id.bottom_app_bar)
        )

        if (!isExecuted) {
            viewModel.viewModelInitialized()
            isExecuted = true
        }

        getTransactions()

        // RecyclerView Logic
        setListAdapter()
        setupRecyclerView()

        // Sort Use case
        setSpinnerSortDate()

        // Observe All the Data
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        isExecuted = false
    }

    private fun setListAdapter() {
        transactionListAdapter = TransactionListAdapter(::showDialog)
    }

    private fun setSpinnerSortDate() {
        spinnerSortDate = binding.spinnerSortDate
        arrayAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_date_sort,
            android.R.layout.simple_dropdown_item_1line,
        )
        spinnerSortDate.adapter = arrayAdapter
        spinnerSortDate.onItemSelectedListener = spinnerSortedDateListener()
    }

    private fun spinnerSortedDateListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                filteredList.clear()

                when (p0?.getItemAtPosition(p2).toString()) {
                    "Hari Ini" -> {
                        filteredList.clear()
                        filteredList.addAll(transactionList.filter { it.transactionDate == viewModel.calendarHelper.getCurrentDate() })
                        transactionListAdapter.submitList(filteredList)
                    }

                    "Kemarin" -> {
                        filteredList.clear()
                        filteredList.addAll(transactionList.filter { it.transactionDate == viewModel.calendarHelper.getYesterdayDate() })
                        transactionListAdapter.submitList(filteredList)
                    }

                    "7 Hari Lalu" -> {
                        filteredList.clear()
                        filteredList.addAll(Helpers.filterTransactionsByDateRange(
                            transactionList,
                            viewModel.calendarHelper.getLast7DaysRange()
                        ))
                        transactionListAdapter.submitList(filteredList)
                    }
                    "30 Hari Lalu" -> {
                        filteredList.clear()
                        filteredList.addAll(Helpers.filterTransactionsByDateRange(
                            transactionList,
                            viewModel.calendarHelper.getLast30DaysRange()
                        ))
                        transactionListAdapter.submitList(filteredList)
                    }
                    "Bulan Ini" -> {
                        filteredList.clear()
                        filteredList.addAll(Helpers.filterTransactionsByDateRange(
                            transactionList,
                            viewModel.calendarHelper.getCurrentMonthRange()
                        ))
                        transactionListAdapter.submitList(filteredList)
                    }
                    "Bulan Lalu" -> {
                        filteredList.clear()
                        filteredList.addAll(Helpers.filterTransactionsByDateRange(
                            transactionList,
                            viewModel.calendarHelper.getLastMonthRange()
                        ))
                        transactionListAdapter.submitList(filteredList)
                    }
                }
                recyclerView.scrollToPosition(0)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                filteredList.clear()
                filteredList.addAll(transactionList.filter { it.transactionDate == viewModel.calendarHelper.getCurrentDate() })
                transactionListAdapter.submitList(filteredList)
                recyclerView.scrollToPosition(0)
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.rvItemTransactionList

        recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = transactionListAdapter
        }
    }

    private fun observe() {
        with(viewModel) {

            dataTransactions.observe(viewLifecycleOwner) { newList ->
                transactionList.clear()
                transactionList.addAll(newList)

                setSpinnerSortDate()
            }

            errMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
//                    viewModel.createToast(it)
                    snackBar.showSnackBar(it)
                }
            }

            onResult.observe(viewLifecycleOwner) {
                when (it) {
                    Result.SUCCESS -> {
                        snackBar.showSnackBar("Success!")
                    }

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

    private fun showDialog(details: TransactionDomain) {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())

        val dialogBinding: CustomDialogForItemClickedBinding =
            CustomDialogForItemClickedBinding.inflate(inflater)

        dialogBinding.tvItemNameDetails.text = details.transactionName
        dialogBinding.tvItemDateDetails.text = details.transactionDate
        dialogBinding.tvItemTypeDetails.text = details.transactionType
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