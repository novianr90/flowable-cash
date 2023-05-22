package id.novian.flowablecash.view.journaling

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.DatePicker
import android.widget.EditText
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import id.novian.flowablecash.R
import id.novian.flowablecash.databinding.FragmentTransactionDetailsBinding
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TransactionDetails : Fragment() {
   private var _binding: FragmentTransactionDetailsBinding? = null

    private val binding get() = _binding!!

    private val args: TransactionDetailsArgs by navArgs()
    private lateinit var spinner: AutoCompleteTextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
    }

    private fun setSpinner() {
        val adapter = ArrayAdapter.createFromResource(requireContext(), R.array.transaction_type, android.R.layout.simple_dropdown_item_1line)
        spinner = binding.spinnerTransactionType
        spinner.setAdapter(adapter)
    }

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun getTransactionDetails() {
        val argsValue = args.transactionType

    }

    private fun transactionDateListener() {
        binding.etTransactionDate.apply {
            setOnClickListener {
                showDatePicker()
            }

            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun afterTextChanged(p0: Editable?) {
                    if (!p0.isNullOrEmpty() && p0.length == 8) {
                        val formattedDate = formatDate(p0.toString())
                        binding.etTransactionDate.setText(formattedDate)
                        binding.etTransactionDate.setSelection(formattedDate.length)
                    }
                }
            })
        }
    }

    private fun formatDate(date: String): String {
        val sb = StringBuilder(date)
        sb.insert(2, "/")
        sb.insert(5, "/")
        return sb.toString()
    }

    private fun showDatePicker() {
        val calendar = Calendar.getInstance()

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, month, dayOfMonth)

                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val formattedDate = dateFormat.format(selectedDate.time)
                binding.etTransactionDate.setText(formattedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }
}