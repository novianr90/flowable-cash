package id.novian.flowablecash.view.report.rekap_hpp

import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import id.novian.flowablecash.base.layout.BaseTableAdapter
import id.novian.flowablecash.base.layout.VIEW_TYPE_FOOTER
import id.novian.flowablecash.base.layout.VIEW_TYPE_HEADER
import id.novian.flowablecash.databinding.BalanceSheetTableItemBinding
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers

class TrialBalanceAdapter : BaseTableAdapter<TransactionDomain>() {

    inner class HeaderViewHolder(
        private val binding: BalanceSheetTableItemBinding
    ) : BaseTableViewHolder<TransactionDomain>(binding.root) {
        override fun onBindHeader(position: Int) {
            with(binding) {
                if (position == 0) {
                    tvAccountName.gravity = Gravity.CENTER

                    tvAccountNo.text = "Tanggal"
                    tvAccountName.text = "Nama Transaksi"
                    tvAccountBalanceTotal.text = "Total"

                    tvAccountNo.setTypeface(null, Typeface.BOLD)
                    tvAccountName.setTypeface(null, Typeface.BOLD)
                    tvAccountBalanceTotal.setTypeface(null, Typeface.BOLD)
                }
            }
        }
    }

    inner class FooterViewHolder(
        private val binding: BalanceSheetTableItemBinding
    ) : BaseTableViewHolder<TransactionDomain>(binding.root) {
        override fun onBindFooter(data: List<TransactionDomain>, position: Int) {
            with(binding) {
                if (position == itemCount - 1) {
                    tvAccountNo.visibility = View.GONE

                    val layoutParams = tvAccountName.layoutParams as LinearLayout.LayoutParams

                    layoutParams.width = 0
                    layoutParams.weight = 4F

                    tvAccountName.layoutParams = layoutParams

                    var debit = 0
                    for (i in 0 until currentList.size) {
                        debit += data[i].total
                    }

                    val total = Helpers.numberFormatter(debit)

                    tvAccountName.text = "Total"
                    tvAccountBalanceTotal.text = total

                    tvAccountName.setTypeface(null, Typeface.BOLD)
                    tvAccountBalanceTotal.setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    inner class ItemViewHolder(
        private val binding: BalanceSheetTableItemBinding
    ) : BaseTableViewHolder<TransactionDomain>(binding.root) {
        override fun onBindItem(data: TransactionDomain, position: Int) {
            with(binding) {
                tvAccountNo.visibility = View.VISIBLE

                val layoutParams = tvAccountName.layoutParams as LinearLayout.LayoutParams

                layoutParams.width = 0
                layoutParams.weight = 3F

                val total = data.total

                tvAccountNo.text = data.transactionName
                tvAccountName.text = data.transactionDescription
                tvAccountBalanceTotal.text = Helpers.numberFormatter(total)

                tvAccountNo.setTypeface(null, Typeface.NORMAL)
                tvAccountName.setTypeface(null, Typeface.NORMAL)
                tvAccountBalanceTotal.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseTableViewHolder<TransactionDomain> {
        val binding = BalanceSheetTableItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return when (viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(binding)
            VIEW_TYPE_FOOTER -> FooterViewHolder(binding)
            else -> ItemViewHolder(binding)
        }
    }
}