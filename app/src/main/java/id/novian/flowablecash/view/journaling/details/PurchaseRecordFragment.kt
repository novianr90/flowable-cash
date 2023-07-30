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
import id.novian.flowablecash.databinding.FragmentPurchaseRecordBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class PurchaseRecordFragment : BaseFragment<FragmentPurchaseRecordBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentPurchaseRecordBinding
        get() = FragmentPurchaseRecordBinding::inflate

    override val hasBottomNavigationView: Boolean
        get() = false

    override val isNavigationVisible: Boolean
        get() = false

    private lateinit var snackBar: CustomSnackBar
    private lateinit var purchaseTypeSpinner: AutoCompleteTextView
    private lateinit var paymentTypeSpinner: AutoCompleteTextView

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

    private val viewModel: RecordHandlerViewModel by viewModels()

    override fun setup() {
        super.setup()

        snackBar = CustomSnackBarImpl(requireNotNull(rootView))

        purchaseSpinner()
        paymentSpinner()

        datePickerListener()

        getUserInput()

        backButton()
        checkDataIfNull()
        observeProcess()
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

    private fun datePickerListener() {
        binding.etTransactionDate.setOnClickListener {
            showDatePicker()
        }
    }

    private fun purchaseSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.purchase_type,
            android.R.layout.simple_dropdown_item_1line
        )

        purchaseTypeSpinner = binding.spinnerTransactionType
        purchaseTypeSpinner.setAdapter(adapter)
    }

    private fun paymentSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.payment_type,
            android.R.layout.simple_dropdown_item_1line
        )

        paymentTypeSpinner = binding.spinnerPaymentType
        paymentTypeSpinner.setAdapter(adapter)
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
            val purchaseType = binding.spinnerTransactionType.text

            if (
                !transactionDate.isNullOrEmpty() &&
                !transactionTotal.isNullOrEmpty() &&
                paymentType.isNotEmpty() && purchaseType.isNotEmpty()
            ) {
                viewModel.buttonSavedClicked(
                    name = purchaseType.toString(),
                    date = transactionDate.toString(),
                    total = transactionTotal.toString().toInt(),
                    description = transactionDescription.toString(),
                    type = "Pengeluaran",
                    payment = paymentType.toString()
                )
            } else {
                viewModel.createToast("Please input the forms")
            }
        }
    }

    private fun backButton() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
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

            paymentTypeSpinner.apply {
                doAfterTextChanged {
                    if (text.toString().isEmpty()) {
                        txtInputPaymentType.error = "Masukkan jenis pembayaran"
                    } else {
                        txtInputPaymentType.error = null
                    }
                }

                purchaseTypeSpinner.apply {
                    doAfterTextChanged {
                        if (text.toString().isEmpty()) {
                            txtInputType.error = "Masukkan jenis pengeluaran"
                        } else if (paymentTypeSpinner.text.toString() == "Non-Tunai" && purchaseTypeSpinner.text.toString() == "Membayar Hutang") {
                            txtInputType.error = "Pembayaran Hutang harus dalam Tunai"
                        } else {
                            txtInputType.error = null
                        }
                    }
                }
            }
        }
    }

    private fun observeProcess() {
        viewModel.onSuccess.observe(viewLifecycleOwner) {
            snackBar.showSnackBar(it)
        }
    }
}