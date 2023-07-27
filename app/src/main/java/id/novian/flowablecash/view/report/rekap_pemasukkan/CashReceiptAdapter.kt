package id.novian.flowablecash.view.report.rekap_pemasukkan

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import id.novian.flowablecash.base.layout.BaseTableAdapter
import id.novian.flowablecash.base.layout.VIEW_TYPE_FOOTER
import id.novian.flowablecash.base.layout.VIEW_TYPE_HEADER
import id.novian.flowablecash.databinding.JournalTableItemBinding
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.helpers.Helpers


class CashReceiptAdapter : BaseTableAdapter<CashReceiptJournal>() {

    inner class HeaderViewHolder(
        private val binding: JournalTableItemBinding
    ): BaseTableViewHolder<CashReceiptJournal>(binding.root) {
        override fun onBindHeader(position: Int) {
            with(binding) {
                if (position == 0) {
                    val dateLayoutParams = tvDate.layoutParams as LinearLayout.LayoutParams
                    val descriptionLayoutParams =
                        tvDescriptionReceipt.layoutParams as LinearLayout.LayoutParams
                    val totalLayoutParams =
                        tvCashReceiptTotal.layoutParams as LinearLayout.LayoutParams


                    200.apply {
                        dateLayoutParams.height = this
                        descriptionLayoutParams.height = this
                        totalLayoutParams.height = this
                    }

                    tvDate.text = "Date"
                    tvDescriptionReceipt.text = "Description"
                    tvCashReceiptTotal.text = "Total"

                    tvDate.layoutParams = dateLayoutParams
                    tvDescriptionReceipt.layoutParams = descriptionLayoutParams
                    tvCashReceiptTotal.layoutParams = totalLayoutParams

                    tvDate.setTypeface(null, Typeface.BOLD)
                    tvDescriptionReceipt.setTypeface(null, Typeface.BOLD)
                    tvCashReceiptTotal.setTypeface(null, Typeface.BOLD)
                }
            }
        }
    }

    inner class FooterViewHolder(
        private val binding: JournalTableItemBinding
    ): BaseTableViewHolder<CashReceiptJournal>(binding.root) {
        override fun onBindFooter(data: List<CashReceiptJournal>, position: Int) {
            with(binding) {
                if (position == itemCount - 1) {
                    tvDate.visibility = View.GONE
                    val layoutParams =
                        tvDescriptionReceipt.layoutParams as LinearLayout.LayoutParams

                    layoutParams.width = 0
                    layoutParams.weight = 4F

                    tvDescriptionReceipt.layoutParams = layoutParams

                    var debit = 0
                    var credit = 0
                    for (i in 0 until currentList.size) {
                        debit += data[i].debit
                        credit += data[i].credit
                    }

                    val total = if (debit != 0) debit else credit

                    tvDescriptionReceipt.text = "Total"
                    tvCashReceiptTotal.text = Helpers.numberFormatter(total)

                    tvDescriptionReceipt.setTypeface(null, Typeface.BOLD)
                    tvCashReceiptTotal.setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    inner class ItemViewHolder(
        private val binding: JournalTableItemBinding
    ): BaseTableViewHolder<CashReceiptJournal>(binding.root) {
        override fun onBindItem(data: CashReceiptJournal, position: Int) {
            with(binding) {

                tvDate.visibility = View.VISIBLE

                val layoutParams =
                    tvDescriptionReceipt.layoutParams as LinearLayout.LayoutParams

                layoutParams.width = 0
                layoutParams.weight = 3F

                val total = if (data.debit != 0) data.debit else data.credit

                tvDate.text = data.date
                tvDescriptionReceipt.text = data.description
                tvCashReceiptTotal.text = Helpers.numberFormatter(total)

                tvDate.setTypeface(null, Typeface.NORMAL)
                tvDescriptionReceipt.setTypeface(null, Typeface.NORMAL)
                tvCashReceiptTotal.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseTableViewHolder<CashReceiptJournal> {
        val binding = JournalTableItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return when(viewType) {
            VIEW_TYPE_HEADER -> HeaderViewHolder(binding)
            VIEW_TYPE_FOOTER -> FooterViewHolder(binding)
            else -> ItemViewHolder(binding)
        }
    }
}