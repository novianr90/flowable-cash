package id.novian.flowablecash.view.journaling.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import id.novian.flowablecash.databinding.FragmentRecordTransactionBinding

class RecordTransaction : Fragment() {

    private var _binding: FragmentRecordTransactionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentRecordTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

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