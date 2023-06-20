package id.novian.flowablecash.view.journaling.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentTransactionDetailsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class TransactionDetails :
    BaseFragment<FragmentTransactionDetailsBinding>() {

    private val args: TransactionDetailsArgs by navArgs()
    private lateinit var feeSpinner: AutoCompleteTextView
    private lateinit var spinner: AutoCompleteTextView

    private val viewModel: TransactionDetailsViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTransactionDetailsBinding
        get() = FragmentTransactionDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinner()
        setSpinnerForFeeType()
        buttonBack()

        transactionDateListener()
        setTransactionType()

        getUserInput()
        observe()
    }

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_type,
            android.R.layout.simple_dropdown_item_1line
        )
        spinner = binding.spinnerTransactionType
        spinner.setAdapter(adapter)
    }

    private fun setSpinnerForFeeType() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_type,
            android.R.layout.simple_dropdown_item_1line
        )

        feeSpinner = binding.spinnerFeeType
        feeSpinner.setAdapter(adapter)
    }

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setTransactionType() {
        val type = when (args.transactionType) {
            "Selling" -> "Sale"
            "Buying" -> "Purchase"
            else -> ""
        }

        binding.spinnerTransactionType.setText(type, false)
        binding.spinnerFeeType.setText(type, false)
    }

    private fun transactionDateListener() {
        binding.etTransactionDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun showDatePicker() {

        val builder = MaterialDatePicker.Builder.datePicker()

        builder.setTitleText("Select your date")
        builder.setSelection(MaterialDatePicker.todayInUtcMilliseconds())

        val materialDatePicker = builder.build()

        materialDatePicker.show(parentFragmentManager, "DATE_PICKER")

        materialDatePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDateInMillis = selection as Long
            val selectedDate = Date(selectedDateInMillis)

            val formattedDate = dateFormat.format(selectedDate)

            binding.etTransactionDate.setText(formattedDate)
        }
    }

    private fun getUserInput() {
        binding.btnSave.setOnClickListener {
            val transactionName = binding.etTransactionName.text.toString()

            val transactionDate = try {
                val date = dateFormat.parse(binding.etTransactionDate.text.toString()) as Date
                val formattedDate = dateFormat.format(date)
                formattedDate
            } catch (e: Exception) {
                e.printStackTrace()
                "01-01-1970"
            }

            val transactionType = binding.spinnerTransactionType.text.toString()
            val feeType = binding.spinnerFeeType.text.toString()
            val fee = binding.etFeeBalance.text.toString().toInt()
            val transactionTotal = binding.etTransactionBalance.text.toString().toInt()
            val transactionDescription = binding.etTransactionDesc.text.toString()

            viewModel.buttonSavedClicked(
                name = transactionName,
                date = transactionDate,
                description = transactionDescription,
                total = transactionTotal,
                type = transactionType,
                feeType = feeType,
                fee = fee
            )
        }
    }

    private fun observe() {
        viewModel.onSuccess.observe(viewLifecycleOwner) {

            if (it == "Success") {
                viewModel.createToast(it)
            }

        }
    }
}