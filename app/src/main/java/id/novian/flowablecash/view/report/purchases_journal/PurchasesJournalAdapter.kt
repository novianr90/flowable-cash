package id.novian.flowablecash.view.report.purchases_journal

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import id.novian.flowablecash.base.BaseAdapter
import id.novian.flowablecash.databinding.JournalTableItemBinding
import id.novian.flowablecash.domain.models.PurchasesJournal
import id.novian.flowablecash.helpers.Helpers

class PurchasesJournalAdapter : BaseAdapter<PurchasesJournal>() {

    inner class PurchasesJournalViewHolder(
        private val binding: JournalTableItemBinding
    ) : BaseViewHolder<PurchasesJournal>(binding.root) {
        override fun onBind(data: PurchasesJournal, position: Int) {

            with(binding) {

                when (position) {

                    0 -> {

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
                        tvCashReceiptDebit.text = "Debit\n(Persediaan Barang Dagang)"
                        tvCashReceiptCredit.text = "Credit\n(Kas)"

                        tvDate.layoutParams = dateLayoutParams
                        tvDescriptionReceipt.layoutParams = descriptionLayoutParams
                        tvCashReceiptDebit.layoutParams = debitLayoutParams
                        tvCashReceiptCredit.layoutParams = creditLayoutParams

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

                        tvDate.visibility = View.VISIBLE

                        val layoutParams =
                            tvDescriptionReceipt.layoutParams as LinearLayout.LayoutParams

                        layoutParams.width = 0
                        layoutParams.weight = 2F

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

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<PurchasesJournal> {
        val binding = JournalTableItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return PurchasesJournalViewHolder(binding)
    }

}