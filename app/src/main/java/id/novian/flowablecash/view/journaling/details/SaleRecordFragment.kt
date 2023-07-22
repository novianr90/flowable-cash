package id.novian.flowablecash.view.journaling.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentTransactionDetailsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class SaleRecordFragment :
    BaseFragment<FragmentTransactionDetailsBinding>() {

    private lateinit var feeSpinner: AutoCompleteTextView
    private lateinit var spinner: AutoCompleteTextView
    private lateinit var paymentSpinner: AutoCompleteTextView

    private val viewModel: TransactionDetailsViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTransactionDetailsBinding
        get() = FragmentTransactionDetailsBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override val hasBottomNavigationView: Boolean
        get() = false

    override fun setup() {
        super.setup()
//        setSpinner()
        setSpinnerForFeeType()
        setPaymentSpinner()

        checkDataIfNull()

        transactionDateListener()

        getUserInput()
        observe()

        buttonBack()
    }

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_type,
            android.R.layout.simple_dropdown_item_1line
        )
//        spinner = binding.spinnerTransactionType
        spinner.setAdapter(adapter)
    }

    private fun setPaymentSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.payment_type,
            android.R.layout.simple_dropdown_item_1line
        )

        paymentSpinner = binding.spinnerPaymentType
        paymentSpinner.setAdapter(adapter)
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
            val transactionName = binding.etTransactionName.text

            val transactionDate = try {
                val date = dateFormat.parse(binding.etTransactionDate.text.toString()) as Date
                val formattedDate = dateFormat.format(date)
                formattedDate
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

//            val transactionType = binding.spinnerTransactionType.text
            val feeType = binding.spinnerFeeType.text
            val fee = binding.etFeeBalance.text
            val transactionTotal = binding.etTransactionBalance.text
            val transactionDescription = binding.etTransactionDesc.text
            val paymentType = binding.spinnerPaymentType.text

            if (!transactionName.isNullOrEmpty() &&
                !transactionDate.isNullOrEmpty() &&
                feeType.isNotEmpty() &&
                !fee.isNullOrEmpty() &&
                !transactionTotal.isNullOrEmpty() &&
                paymentType.isNotEmpty()) {

                viewModel.buttonSavedClicked(
                    name = transactionName.toString(),
                    date = transactionDate,
                    description = transactionDescription.toString(),
                    total = transactionTotal.toString().toInt(),
                    type = "Penjualan",
                    feeType = feeType.toString(),
                    fee = fee.toString().toInt(),
                    payment = paymentType.toString()
                )
            } else {
                viewModel.createToast("Please fill all the form")
            }
        }
    }

    private fun observe() {

        with(viewModel) {
            onSuccess.observe(viewLifecycleOwner) {

                if (it == "Success") {
                    viewModel.createToast(it)
                }

            }

            errMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
                    viewModel.createToast(it)
                }
            }
        }
    }

    private fun transactionTypeSpinnerListener(): AdapterView.OnItemSelectedListener {
        return object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                feeSpinner.setText(p0?.getItemAtPosition(p2).toString(), false)

                if (p0?.getItemAtPosition(p2).toString() == "Penjualan" && paymentSpinner.text.toString() == "Hutang") {
                    paymentSpinner.error = "Penjualan dengan pembayaran hutang tidak diizinkan"
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }
    }

    private fun checkDataIfNull() {

        with(binding) {

            etTransactionName.apply {
                doAfterTextChanged {
                    if (text.isNullOrEmpty()) {
                        txtInputName.error = "Please input transaction name"
                    } else {
                        txtInputName.error = null
                    }
                }
            }

            etTransactionDate.apply {
                doAfterTextChanged {
                    if (text.isNullOrEmpty()) {
                        txtInputDate.error = "Please input Transaction Date"
                    } else {
                        txtInputDate.error = null
                    }
                }
            }

            etTransactionBalance.apply {
                doAfterTextChanged {
                    if (text.isNullOrEmpty()) {
                        txtInputBalance.error = "Please input total transaction"
                    } else {
                        txtInputBalance.error = null
                    }
                }
            }

            spinner.doAfterTextChanged { _ ->
                feeSpinner.setText(spinner.text.toString(), false)

                paymentSpinner.doAfterTextChanged {
                    if (spinner.text.toString() == "Penjualan" && paymentSpinner.text.toString() == "Hutang") {
                        txtInputPaymentType.error = "Maaf, saat ini penjualan tidak menerima pembayaran Hutang"
                    } else {
                        txtInputPaymentType.error = null
                    }
                }
            }
        }
    }

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}