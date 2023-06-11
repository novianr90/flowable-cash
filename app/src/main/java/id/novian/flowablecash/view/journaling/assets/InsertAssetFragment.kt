package id.novian.flowablecash.view.journaling.assets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.databinding.FragmentInsertAssetBinding
import id.novian.flowablecash.helpers.Result

@AndroidEntryPoint
class InsertAssetFragment : Fragment() {

    private var _binding: FragmentInsertAssetBinding? = null
    private val binding get() = _binding!!

    private lateinit var spinner: AutoCompleteTextView

    private val viewModel: AssetViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInsertAssetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setSpinner()
        getBack()

        getUserInput()
        observe()
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

    private fun getUserInput() {
        with(binding) {
            btnSave.setOnClickListener {

                val accountName = binding.spinnerAccountName.text.toString()
                val balance = binding.etBalance.text.toString().toInt()

                viewModel.createBalanceSheet(accountName, balance)
            }

        }
    }

    private fun getBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun observe() {
        viewModel.onSuccess.observe(viewLifecycleOwner) {
            when(it) {
                Result.SUCCESS -> viewModel.createToast("Success!")
                Result.FAILED -> viewModel.createToast("Failed!")

                else -> { /* Not Implemented */ }
            }
        }
    }
}