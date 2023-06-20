package id.novian.flowablecash.view.journaling.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentRecordTransactionBinding

class RecordTransaction :
    BaseFragment<FragmentRecordTransactionBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecordTransactionBinding
        get() = FragmentRecordTransactionBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        moveToTransactionDetails()
        buttonBack()
    }

    private fun moveToTransactionDetails() {

        val moveToTransaction: (String) -> Unit = { value ->
            findNavController().navigate(
                RecordTransactionDirections.actionRecordTransactionToTransactionDetails(
                    value
                )
            )
        }

        binding.cvAsset.setOnClickListener {
            findNavController().navigate(RecordTransactionDirections.actionRecordTransactionToInsertAssetFragment())
        }

        binding.cvSelling.setOnClickListener {
            moveToTransaction("Selling")
        }

        binding.cvBuying.setOnClickListener {
            moveToTransaction("Buying")
        }
    }

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}