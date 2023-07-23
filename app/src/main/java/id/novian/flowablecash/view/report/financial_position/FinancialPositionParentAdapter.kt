package id.novian.flowablecash.view.report.financial_position

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import id.novian.flowablecash.base.BaseAdapter
import id.novian.flowablecash.databinding.ItemFinancialPositionParentBinding
import id.novian.flowablecash.helpers.Helpers

class FinancialPositionParentAdapter : BaseAdapter<FinancialPositionParentData>() {

    inner class ParentViewHolder(
        private val binding: ItemFinancialPositionParentBinding
    ) : BaseViewHolder<FinancialPositionParentData>(binding.root) {
        override fun onBind(data: FinancialPositionParentData) {
            with(binding) {
                tvTitleAkun.text = data.title
                tvTotalAkun.text = Helpers.formatCurrency(data.total)

                val adapter = FinancialPositionChildAdapter()

                rvItemFinancialPosition.adapter = adapter
                rvItemFinancialPosition.layoutManager = LinearLayoutManager(itemView.context)
                adapter.submitList(data.data)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<FinancialPositionParentData> {
        val binding = ItemFinancialPositionParentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ParentViewHolder(binding)
    }
}