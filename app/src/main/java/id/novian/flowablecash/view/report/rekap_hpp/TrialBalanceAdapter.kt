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
                    tvAccountBalanceDesc.text = "Deskripsi"
                    tvAccountBalanceTotal.text = "Total"

                    tvAccountNo.setTypeface(null, Typeface.BOLD)
                    tvAccountName.setTypeface(null, Typeface.BOLD)
                    tvAccountBalanceDesc.setTypeface(null, Typeface.BOLD)
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
                    layoutParams.weight = 3F

                    tvAccountName.layoutParams = layoutParams

                    tvAccountBalanceDesc.visibility = View.GONE
                    val totalLayout =
                        tvAccountBalanceTotal.layoutParams as LinearLayout.LayoutParams
                    totalLayout.width = 0
                    totalLayout.weight = 3F

                    tvAccountBalanceTotal.layoutParams = totalLayout

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
                tvAccountBalanceDesc.visibility = View.VISIBLE

                val dateLayoutParams = tvAccountNo.layoutParams as LinearLayout.LayoutParams
                val nameLayoutParams = tvAccountName.layoutParams as LinearLayout.LayoutParams
                val descLayoutParams =
                    tvAccountBalanceDesc.layoutParams as LinearLayout.LayoutParams
                val totalLayoutParams =
                    tvAccountBalanceTotal.layoutParams as LinearLayout.LayoutParams

                0.apply {
                    dateLayoutParams.width = this
                    nameLayoutParams.width = this
                    descLayoutParams.width = this
                    totalLayoutParams.width = this
                }

                150.apply {
                    dateLayoutParams.height = this
                    nameLayoutParams.height = this
                    descLayoutParams.height = this
                    totalLayoutParams.height = this
                }

                dateLayoutParams.weight = 1f
                nameLayoutParams.weight = 2f
                descLayoutParams.weight = 2f
                totalLayoutParams.weight = 1f

                tvAccountNo.layoutParams = dateLayoutParams
                tvAccountName.layoutParams = nameLayoutParams
                tvAccountBalanceDesc.layoutParams = descLayoutParams
                tvAccountBalanceTotal.layoutParams = totalLayoutParams

                val total = data.total

                tvAccountNo.text = data.transactionDate
                tvAccountName.text = data.transactionName
                tvAccountBalanceDesc.text = data.transactionDescription
                tvAccountBalanceTotal.text = Helpers.numberFormatter(total)

                tvAccountNo.setTypeface(null, Typeface.NORMAL)
                tvAccountName.setTypeface(null, Typeface.NORMAL)
                tvAccountBalanceDesc.setTypeface(null, Typeface.NORMAL)
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