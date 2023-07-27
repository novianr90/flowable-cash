package id.novian.flowablecash.base.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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

    open val hasBottomNavigationView: Boolean
        get() = true

    var rootView: View? = null

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
        rootView = view.rootView
        setup()
    }

    open fun setup() {
        setBottomNavigationView(isNavigationVisible)
    }

    private fun setBottomNavigationView(isVisible: Boolean) {
        if (hasBottomNavigationView) {
            requireActivity().findViewById<BottomAppBar>(R.id.bottom_app_bar).isVisible = isVisible
            requireActivity().findViewById<FloatingActionButton>(R.id.fab_record).isVisible =
                isVisible
        }
    }
}