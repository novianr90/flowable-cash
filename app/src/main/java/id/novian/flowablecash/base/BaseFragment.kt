package id.novian.flowablecash.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import id.novian.flowablecash.R

abstract class BaseFragment<VB : ViewBinding> : Fragment() {
    private var _binding: ViewBinding? = null

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = _binding as VB

    open val isNavigationVisible: Boolean
        get() = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(_binding).root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    open fun setup() {
        setBottomNavigationView(isNavigationVisible)
        setFabRecord(isNavigationVisible)
    }

    private fun setBottomNavigationView(isVisible: Boolean) {
        requireActivity().findViewById<BottomAppBar>(R.id.bottom_app_bar).isVisible = isVisible
        requireActivity().findViewById<FloatingActionButton>(R.id.fab_record).isVisible = isVisible
    }

    private fun setFabRecord(isVisible: Boolean) {
        if (isVisible) {
            requireActivity().findViewById<FloatingActionButton>(R.id.fab_record)
                .setOnClickListener {
                    findNavController().navigate(R.id.fab_record)
                }
        }
    }
}