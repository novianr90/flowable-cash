package id.novian.flowablecash.view.report.cash_receipt

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import id.novian.flowablecash.base.BaseTableAdapter
import id.novian.flowablecash.base.VIEW_TYPE_FOOTER
import id.novian.flowablecash.base.VIEW_TYPE_HEADER
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
                    val debitLayoutParams =
                        tvCashReceiptDebit.layoutParams as LinearLayout.LayoutParams
                    val creditLayoutParams =
                        tvCashReceiptCredit.layoutParams as LinearLayout.LayoutParams

                    200.apply {
                        dateLayoutParams.height = this
                        descriptionLayoutParams.height = this
                        debitLayoutParams.height = this
                        creditLayoutParams.height = this
                    }

                    tvDate.text = "Date"
                    tvDescriptionReceipt.text = "Description"
                    tvCashReceiptDebit.text = "Debit"
                    tvCashReceiptCredit.text = "Credit"

                    tvDate.layoutParams = dateLayoutParams
                    tvDescriptionReceipt.layoutParams = descriptionLayoutParams
                    tvCashReceiptDebit.layoutParams = debitLayoutParams
                    tvCashReceiptCredit.layoutParams = creditLayoutParams

                    tvDate.setTypeface(null, Typeface.BOLD)
                    tvDescriptionReceipt.setTypeface(null, Typeface.BOLD)
                    tvCashReceiptDebit.setTypeface(null, Typeface.BOLD)
                    tvCashReceiptCredit.setTypeface(null, Typeface.BOLD)
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
                    layoutParams.weight = 3F

                    tvDescriptionReceipt.layoutParams = layoutParams

                    var debit = 0
                    var credit = 0
                    for (i in 0 until currentList.size) {
                        debit += data[i].debit
                        credit += data[i].credit
                    }

                    tvDescriptionReceipt.text = "Total"
                    tvCashReceiptDebit.text = Helpers.numberFormatter(debit)
                    tvCashReceiptCredit.text = Helpers.numberFormatter(credit)

                    tvDescriptionReceipt.setTypeface(null, Typeface.BOLD)
                    tvCashReceiptDebit.setTypeface(null, Typeface.NORMAL)
                    tvCashReceiptCredit.setTypeface(null, Typeface.NORMAL)
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
                layoutParams.weight = 2F

                tvDate.text = data.date
                tvDescriptionReceipt.text = data.description
                tvCashReceiptDebit.text = Helpers.numberFormatter(data.debit)
                tvCashReceiptCredit.text = Helpers.numberFormatter(data.credit)

                tvDate.setTypeface(null, Typeface.NORMAL)
                tvDescriptionReceipt.setTypeface(null, Typeface.NORMAL)
                tvCashReceiptDebit.setTypeface(null, Typeface.NORMAL)
                tvCashReceiptCredit.setTypeface(null, Typeface.NORMAL)

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