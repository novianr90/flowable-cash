package id.novian.flowablecash.view.journaling.details

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.base.custom.CustomSnackBar
import id.novian.flowablecash.base.custom.CustomSnackBarImpl
import id.novian.flowablecash.base.layout.BaseFragment
import id.novian.flowablecash.databinding.FragmentTransactionDetailsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class SaleRecordFragment :
    BaseFragment<FragmentTransactionDetailsBinding>() {

    private lateinit var snackBar: CustomSnackBar
    private lateinit var paymentSpinner: AutoCompleteTextView

    private val viewModel: RecordHandlerViewModel by viewModels()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTransactionDetailsBinding
        get() = FragmentTransactionDetailsBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override val hasBottomNavigationView: Boolean
        get() = false

    override fun setup() {
        super.setup()

        snackBar = CustomSnackBarImpl(requireNotNull(rootView))

        setPaymentSpinner()

        checkDataIfNull()

        transactionDateListener()

        getUserInput()
        observe()

        buttonBack()
        observeProcess()
    }

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    private fun setPaymentSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.payment_type,
            android.R.layout.simple_dropdown_item_1line
        )

        paymentSpinner = binding.spinnerPaymentType
        paymentSpinner.setAdapter(adapter)
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

            val transactionDate = try {
                val date = dateFormat.parse(binding.etTransactionDate.text.toString()) as Date
                val formattedDate = dateFormat.format(date)
                formattedDate
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

            val transactionTotal = binding.etTransactionBalance.text
            val transactionDescription = binding.etTransactionDesc.text
            val paymentType = binding.spinnerPaymentType.text

            if (!transactionDate.isNullOrEmpty() &&
                !transactionTotal.isNullOrEmpty() &&
                paymentType.isNotEmpty()
            ) {

                viewModel.buttonSavedClicked(
                    name = "Pemasukkan",
                    date = transactionDate,
                    description = transactionDescription.toString(),
                    total = transactionTotal.toString().toInt(),
                    type = "Pemasukkan",
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

    private fun checkDataIfNull() {

        with(binding) {

            etTransactionDate.apply {
                doAfterTextChanged {
                    if (text.isNullOrEmpty()) {
                        txtInputDate.error = "Masukkan Tanggal Transaksi"
                    } else {
                        txtInputDate.error = null
                    }
                }
            }

            etTransactionBalance.apply {
                doAfterTextChanged {
                    if (text.isNullOrEmpty()) {
                        txtInputBalance.error = "Masukkan total transaksi"
                    } else {
                        txtInputBalance.error = null
                    }
                }
            }

            paymentSpinner.apply {
                doAfterTextChanged {
                    if (text.toString().isEmpty()) {
                        txtInputPaymentType.error = "Masukkan jenis pembayaran"
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

    private fun observeProcess() {
        viewModel.onSuccess.observe(viewLifecycleOwner) {
            snackBar.showSnackBar(it)
        }
    }
}