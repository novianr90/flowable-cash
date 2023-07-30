package id.novian.flowablecash.view.journaling.assets

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.base.custom.CustomSnackBar
import id.novian.flowablecash.base.custom.CustomSnackBarImpl
import id.novian.flowablecash.base.layout.BaseFragment
import id.novian.flowablecash.data.remote.models.balancesheet.AccountBalance
import id.novian.flowablecash.databinding.FragmentInsertAssetBinding
import id.novian.flowablecash.helpers.Result

@AndroidEntryPoint
class InsertAssetFragment :
    BaseFragment<FragmentInsertAssetBinding>() {

    private lateinit var snackBar: CustomSnackBar
    private lateinit var spinner: AutoCompleteTextView
    private lateinit var paymentSpinner: AutoCompleteTextView

    private val viewModel: AssetViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInsertAssetBinding
        get() = FragmentInsertAssetBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override val hasBottomNavigationView: Boolean
        get() = false

    override fun setup() {
        super.setup()

        snackBar = CustomSnackBarImpl(requireNotNull(rootView))

        setSpinner()
        setPaymentSpinner()
        getBack()

        getUserInput()
        observe()

        observeDataIfNull()
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.account_name,
            android.R.layout.simple_dropdown_item_1line
        )

        spinner = binding.spinnerAccountName
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

    private fun getUserInput() {
        with(binding) {

            btnSave.setOnClickListener {

                val assetString = spinnerAccountName.text?.toString()
                val balanceString = etBalance.text
                val payment = spinnerPaymentType.text

                if (!assetString.isNullOrEmpty() && !balanceString.isNullOrEmpty() && payment.isNotEmpty()) {
                    val balance = balanceString.toString().toInt()
                    val queryBalance = AccountBalance(
                        debit = balance, credit = balance
                    )

                    viewModel.processData(assetString, queryBalance, payment.toString())
                }

                viewModel.onSuccess.value?.name?.let { it1 -> viewModel.createToast(it1) }
            }

        }
    }

    private fun getBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observe() {

        with(viewModel) {
            onSuccess.observe(viewLifecycleOwner) {
                when (it) {
                    Result.SUCCESS -> {
                        snackBar.showSnackBar("Success!")
                    }

                    Result.FAILED -> {}
                    Result.LOADING -> {}

                    else -> { /* Not Implemented */
                    }
                }
            }

            errMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
//                    viewModel.createToast(it)
                    snackBar.showSnackBar(it ?: "Error Occurred!")
                }
            }
        }
    }

    private fun observeDataIfNull() {
        with(binding) {
            spinnerAccountName.apply {
                doAfterTextChanged {

                    spinnerPaymentType.doAfterTextChanged {
                        if (spinnerPaymentType.toString() == "Non-Tunai" && text.toString() == "Kas") {
                            txtInputAccountName.error = "Kas harusnya dalam Tunai"
                        }
                    }

                    if (text.toString().isEmpty()) {
                        txtInputAccountName.error = "Masukkan Nama Akun"
                    } else {
                        txtInputAccountName.error = null
                    }
                }
            }

            spinnerPaymentType.apply {
                doAfterTextChanged {
                    if (text.toString().isEmpty()) {
                        txtInputPaymentType.error = "Masukkan pembayaran saldo ini"
                    } else {
                        txtInputPaymentType.error = null
                    }
                }
            }

            etBalance.apply {
                doAfterTextChanged {
                    if (text.toString().isEmpty()) {
                        txtInputName.error = "Masukkan saldo akun ini"
                    } else {
                        txtInputName.error = null
                    }
                }
            }
        }
    }
}