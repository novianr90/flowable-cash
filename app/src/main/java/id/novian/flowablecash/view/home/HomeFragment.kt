package id.novian.flowablecash.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.databinding.FragmentHomeBinding

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addRecordTransaction()

        dataToNavigate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addRecordTransaction() {
        binding.fabRecord.setOnClickListener {
            it.findNavController()
                .navigate(HomeFragmentDirections.actionHomeFragmentToRecordTransaction())
        }
    }

    private fun dataToNavigate() {

        binding.cvTransactions.setOnClickListener {
            navigateToTransactionList("Transactions")
        }

        binding.cvPurchase.setOnClickListener {
            navigateToTransactionList("Purchases")
        }

        binding.cvSale.setOnClickListener {
            navigateToTransactionList("Sales")
        }
    }

    private fun navigateToTransactionList(
        type: String
    ) {

        val action = HomeFragmentDirections.actionHomeFragmentToTransactionsList(type = type)

        findNavController().navigate(action)
    }
}