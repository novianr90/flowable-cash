package id.novian.flowablecash.view.journaling

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.datepicker.MaterialDatePicker
import id.novian.flowablecash.R
import id.novian.flowablecash.databinding.FragmentTransactionDetailsBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TransactionDetails : Fragment() {
   private var _binding: FragmentTransactionDetailsBinding? = null

    private val binding get() = _binding!!

    private val args: TransactionDetailsArgs by navArgs()
    private lateinit var spinner: AutoCompleteTextView

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

            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val formattedDate = dateFormat.format(selectedDate)

            binding.etTransactionDate.setText(formattedDate)
        }
    }
}