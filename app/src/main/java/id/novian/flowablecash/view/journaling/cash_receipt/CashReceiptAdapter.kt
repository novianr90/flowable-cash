package id.novian.flowablecash.view.journaling.cash_receipt

import android.graphics.Typeface
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import id.novian.flowablecash.base.BaseAdapter
import id.novian.flowablecash.databinding.CashReceiptJournalTableItemBinding
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.helpers.Helpers


class CashReceiptAdapter : BaseAdapter<CashReceiptJournal>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<CashReceiptJournal> {
        val binding = CashReceiptJournalTableItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CashReceiptViewHolder(binding)
    }

    inner class CashReceiptViewHolder(
        private val binding: CashReceiptJournalTableItemBinding
    ) : BaseViewHolder<CashReceiptJournal>(binding.root) {
        override fun onBind(data: CashReceiptJournal, position: Int) {
            with(binding) {
                when (position) {
                    0 -> {
                        tvDate.text = "Date"
                        tvDescriptionReceipt.text = "Description"
                        tvCashReceiptDebit.text = "Debit\n(Kas)"
                        tvCashReceiptCredit.text = "Credit\n(Penjualan)"

                        tvCashReceiptDebit.gravity = Gravity.CENTER
                        tvCashReceiptCredit.gravity = Gravity.CENTER

                        tvDate.setTypeface(null, Typeface.BOLD)
                        tvDescriptionReceipt.setTypeface(null, Typeface.BOLD)
                        tvCashReceiptDebit.setTypeface(null, Typeface.BOLD)
                        tvCashReceiptCredit.setTypeface(null, Typeface.BOLD)
                    }

                    itemCount - 1 -> {

                        tvDate.visibility = View.GONE
                        val layoutParams =
                            tvDescriptionReceipt.layoutParams as LinearLayout.LayoutParams

                        layoutParams.width = 0
                        layoutParams.weight = 3F

                        tvDescriptionReceipt.layoutParams = layoutParams

                        var debit = 0
                        var credit = 0
                        for (i in 0 until currentList.size) {
                            debit += currentList[i].debit
                            credit += currentList[i].credit
                        }

                        tvDescriptionReceipt.text = "Total"
                        tvCashReceiptDebit.text =
                            if (debit != 0) Helpers.numberFormatter(debit) else ""
                        tvCashReceiptCredit.text =
                            if (credit != 0) Helpers.numberFormatter(credit) else ""

                        tvDescriptionReceipt.setTypeface(null, Typeface.BOLD)
                        tvCashReceiptDebit.setTypeface(null, Typeface.NORMAL)
                        tvCashReceiptCredit.setTypeface(null, Typeface.NORMAL)
                    }

                    else -> {
                        val item = currentList[position - 1]
                        tvDate.text = item.date
                        tvDescriptionReceipt.text = item.description
                        tvCashReceiptDebit.text = Helpers.numberFormatter(item.debit)
                        tvCashReceiptCredit.text = Helpers.numberFormatter(item.credit)

                        tvDate.setTypeface(null, Typeface.NORMAL)
                        tvDescriptionReceipt.setTypeface(null, Typeface.NORMAL)
                        tvCashReceiptDebit.setTypeface(null, Typeface.NORMAL)
                        tvCashReceiptCredit.setTypeface(null, Typeface.NORMAL)
                    }
                }
            }
        }
    }
}