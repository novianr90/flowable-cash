package id.novian.flowablecash.view.journaling.update

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.base.custom.CustomSnackBar
import id.novian.flowablecash.base.custom.CustomSnackBarImpl
import id.novian.flowablecash.base.layout.BaseFragment
import id.novian.flowablecash.databinding.FragmentUpdateBinding
import id.novian.flowablecash.helpers.Helpers
import id.novian.flowablecash.helpers.Result
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class UpdateFragment :
    BaseFragment<FragmentUpdateBinding>() {

    private lateinit var snackBar: CustomSnackBar
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentUpdateBinding
        get() = FragmentUpdateBinding::inflate

    private val viewModel: UpdateViewModel by viewModels()
    private lateinit var spinner: AutoCompleteTextView
    private lateinit var feeSpinner: AutoCompleteTextView

    private val args: UpdateFragmentArgs by navArgs()

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()

        snackBar = CustomSnackBarImpl(requireNotNull(rootView))

        setSpinner()
        setDatePickerListener()
        buttonBack()
    }

    override fun onResume() {
        super.onResume()
        observe()

        if (args.transactionId != 0) {
            setDataTransaction(args.transactionId)

            getUserInput(args.transactionId)
        }
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.transaction_type,
            android.R.layout.simple_dropdown_item_1line
        )
        spinner = binding.spinnerTransactionType

        feeSpinner = binding.spinnerFeeType

        feeSpinner.setAdapter(adapter)
        spinner.setAdapter(adapter)
    }

    private fun setDatePickerListener() {
        binding.etTransactionDate.setOnClickListener {
            showDatePicker()
        }
    }

    private val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

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

    private fun setDataTransaction(id: Int) {
        viewModel.getTransactionById(id)

        viewModel.data.observe(viewLifecycleOwner) { data ->
            binding.etTransactionName.setText(data.transactionName)
            binding.etTransactionDate.setText(data.transactionDate)

            binding.spinnerTransactionType.setText(
                Helpers.transactionTypeChanger(data.transactionType),
                false
            )
            binding.etTransactionBalance.setText(Helpers.numberFormatter(data.total))
            binding.etTransactionDesc.setText(data.transactionDescription)
            binding.spinnerFeeType.setText(data.feeType, false)
            binding.etFeeBalance.setText(Helpers.numberFormatter(data.fee))
        }
    }

    private fun getUserInput(id: Int) {
        binding.btnUpdate.setOnClickListener { _ ->

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
            val transactionFee = binding.etFeeBalance.text.toString().toInt()
            val transactionTotal = binding.etTransactionBalance.text.toString().toInt()
            val transactionDescription = binding.etTransactionDesc.text.toString()

            viewModel.buttonUpdateClicked(
                id = id,
                name = transactionName,
                date = transactionDate,
                description = transactionDescription,
                total = transactionTotal,
                type = transactionType,
                fee = transactionFee,
                feeType = feeType
            )
        }
    }

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observe() {
        with(viewModel) {

            onSuccess.observe(viewLifecycleOwner) { status ->
                when (status) {
                    Result.SUCCESS -> snackBar.showSnackBar("Success!")
                    Result.FAILED -> {}
                    Result.LOADING -> {}
                }
            }

            errMessage.observe(viewLifecycleOwner) {
                if (it.isNotEmpty()) {
//                    viewModel.createToast(it)
                    snackBar.showSnackBar(it)
                }
            }

        }
    }
}