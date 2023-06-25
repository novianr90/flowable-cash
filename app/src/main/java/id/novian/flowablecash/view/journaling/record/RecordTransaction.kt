package id.novian.flowablecash.view.journaling.record

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import id.novian.flowablecash.base.BaseFragment
import id.novian.flowablecash.databinding.FragmentRecordTransactionBinding

class RecordTransaction :
    BaseFragment<FragmentRecordTransactionBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentRecordTransactionBinding
        get() = FragmentRecordTransactionBinding::inflate

    override val isNavigationVisible: Boolean
        get() = false

    override val hasBottomNavigationView: Boolean
        get() = false

    override fun setup() {
        super.setup()
        moveToTransactionDetails()
        buttonBack()
    }

    private fun moveToTransactionDetails() {

        binding.cvAsset.setOnClickListener {
            findNavController().navigate(RecordTransactionDirections.actionRecordTransactionToInsertAssetFragment())
        }

        binding.cvSelling.setOnClickListener {
            findNavController().navigate(
                RecordTransactionDirections.actionRecordTransactionToTransactionDetails(
                    transactionType = "Selling"
                )
            )
        }

        binding.cvBuying.setOnClickListener {
            findNavController().navigate(
                RecordTransactionDirections.actionRecordTransactionToTransactionDetails(
                    transactionType = "Buying"
                )
            )
        }
    }

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}