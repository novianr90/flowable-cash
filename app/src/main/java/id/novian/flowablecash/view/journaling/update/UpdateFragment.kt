package id.novian.flowablecash.view.journaling.update

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
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

    private val args: UpdateFragmentArgs by navArgs()

    override val isNavigationVisible: Boolean
        get() = false

    override fun setup() {
        super.setup()

        snackBar = CustomSnackBarImpl(requireNotNull(rootView))

        setDatePickerListener()
        buttonBack()
    }

    override fun onResume() {
        super.onResume()
        observe()

        if (args.transactionId != 0) {
            setDataTransaction(args.transactionId, args.type)

            getUserInput(args.transactionId, args.type)
        }
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

    private fun setDataTransaction(id: Int, type: String) {
        viewModel.getTransactionById(id, type)

        viewModel.data.observe(viewLifecycleOwner) { data ->
            binding.etTransactionName.setText(data.transactionName)
            binding.etTransactionDate.setText(data.transactionDate)

            binding.etTransactionBalance.setText(Helpers.numberFormatter(data.total))
            binding.etTransactionDesc.setText(data.transactionDescription)
        }
    }

    private fun getUserInput(id: Int, type: String) {
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

            val transactionTotal = binding.etTransactionBalance.text.toString().toInt()
            val transactionDescription = binding.etTransactionDesc.text.toString()

            viewModel.buttonUpdateClicked(
                id = id,
                date = transactionDate,
                total = transactionTotal,
                type = type,
                description = transactionDescription,
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
                    snackBar.showSnackBar(it)
                }
            }

        }
    }
}