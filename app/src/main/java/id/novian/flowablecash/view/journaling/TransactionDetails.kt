package id.novian.flowablecash.view.journaling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.data.TransactionType
import id.novian.flowablecash.databinding.FragmentTransactionDetailsBinding
import id.novian.flowablecash.domain.models.Daily
import id.novian.flowablecash.viewmodel.TransactionDetails
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class TransactionDetails : Fragment() {
    private var _binding: FragmentTransactionDetailsBinding? = null

    private val binding get() = _binding!!

    private val args: TransactionDetailsArgs by navArgs()
    private lateinit var spinner: AutoCompleteTextView
    private lateinit var daily: Daily

    private val viewModel: TransactionDetails by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTransactionDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSpinner()
        buttonBack()

        transactionDateListener()
        setTransactionType()

        getUserInput()

    }

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_type,
            android.R.layout.simple_dropdown_item_1line
        )
        spinner = binding.spinnerTransactionType
        spinner.setAdapter(adapter)
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
                "01/01/1970"
            }

            val transactionType =
                if (binding.spinnerTransactionType.text.toString() == "Purchase") {
                    TransactionType.PURCHASE
                } else {
                    TransactionType.SALE
                }

            val transactionBalance =
                if (binding.etTransactionBalance.text.toString().isNotEmpty()) {
                    binding.etTransactionBalance.text.toString().toLong()
                } else {
                    0L
                }

            val transactionDescription = binding.etTransactionDesc.text.toString()

            daily = Daily(
                transactionName = transactionName,
                transactionDate = transactionDate,
                transactionType = transactionType,
                total = transactionBalance,
                transactionDescription = transactionDescription
            )

            viewModel.createToast(daily.toString())
        }
    }
}