package id.novian.flowablecash.view.journaling.assets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentInsertAssetBinding
import id.novian.flowablecash.helpers.Helpers
import id.novian.flowablecash.helpers.Result

@AndroidEntryPoint
class InsertAssetFragment :
    BaseFragment<FragmentInsertAssetBinding>() {

    private lateinit var spinner: AutoCompleteTextView

    private val viewModel: AssetViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentInsertAssetBinding
        get() = FragmentInsertAssetBinding::inflate

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

        spinner.setText("Kas", false)
    }

    private fun getUserInput() {
        with(binding) {

            btnSave.setOnClickListener {

                val assetString = spinnerAccountName.text?.toString() ?: "Modal"
                val asset = Helpers.stringToAccountName(assetString)
                val balance = etBalance.text.toString().toInt()
                val balanceInModel = Helpers.debitCreditDeciderForBalanceSheet(asset, balance)


                viewModel.updateBalance(assetString, balanceInModel)

                viewModel.createToast(assetString)
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
                Result.LOADING -> viewModel.createToast("Invoked!")

                else -> { /* Not Implemented */ }
            }
        }
    }
}