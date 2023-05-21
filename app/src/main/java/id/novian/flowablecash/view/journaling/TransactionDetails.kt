package id.novian.flowablecash.view.journaling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import id.novian.flowablecash.R
import id.novian.flowablecash.databinding.FragmentTransactionDetailsBinding

class TransactionDetails : Fragment() {
   private var _binding: FragmentTransactionDetailsBinding? = null

    private val binding get() = _binding!!

    private val args: TransactionDetailsArgs by navArgs()

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

        val argumentValue = args.transactionType
    }

}