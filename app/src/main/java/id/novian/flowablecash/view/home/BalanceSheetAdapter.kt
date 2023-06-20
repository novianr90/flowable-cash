package id.novian.flowablecash.view.home

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import id.novian.flowablecash.base.BaseAdapter
import id.novian.flowablecash.databinding.BalanceSheetTableItemBinding
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.helpers.Helpers

class BalanceSheetAdapter : BaseAdapter<BalanceSheetDomain>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BalanceSheetDomain> {
        val binding = BalanceSheetTableItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BalanceSheetJournalViewHolder(binding)
    }

    inner class BalanceSheetJournalViewHolder(
        private val binding: BalanceSheetTableItemBinding
    ) : BaseViewHolder<BalanceSheetDomain>(binding.root) {

        override fun onBind(data: BalanceSheetDomain, position: Int) {
            when (position) {
                0 -> {
                    binding.apply {

                        tvAccountNo.text = "Acc No."
                        tvAccountName.text = "Account Name"
                        tvAccountBalanceDebit.text = "Debit"
                        tvAccountBalanceCredit.text = "Credit"

                        tvAccountNo.setTypeface(null, Typeface.BOLD)
                        tvAccountName.setTypeface(null, Typeface.BOLD)
                        tvAccountBalanceDebit.setTypeface(null, Typeface.BOLD)
                        tvAccountBalanceCredit.setTypeface(null, Typeface.BOLD)
                    }
                }

                itemCount - 1 -> {
                    with(binding) {
                        tvAccountNo.visibility = View.GONE

                        val layoutParams = tvAccountName.layoutParams as LinearLayout.LayoutParams

                        layoutParams.width = 0
                        layoutParams.weight = 3F
                        tvAccountName.layoutParams = layoutParams

                        tvAccountName.text = "Total"

                        var debit = 0
                        var credit = 0

                        for (i in 0 until currentList.size) {
                            debit += currentList[i].balance.debit
                            credit += currentList[i].balance.credit
                        }

                        tvAccountBalanceDebit.text =
                            if (debit != 0) Helpers.numberFormatter(debit) else ""
                        tvAccountBalanceCredit.text =
                            if (credit != 0) Helpers.numberFormatter(credit) else ""
                    }
                }

                else -> {
                    val item = currentList[position - 1]
                    with(binding) {

                        tvAccountNo.visibility = View.VISIBLE

                        val layoutParams = tvAccountName.layoutParams as LinearLayout.LayoutParams

                        layoutParams.width = 0
                        layoutParams.weight = 2F
                        tvAccountName.layoutParams = layoutParams

                        tvAccountNo.setTypeface(null, Typeface.NORMAL)
                        tvAccountName.setTypeface(null, Typeface.NORMAL)
                        tvAccountBalanceDebit.setTypeface(null, Typeface.NORMAL)
                        tvAccountBalanceCredit.setTypeface(null, Typeface.NORMAL)

                        tvAccountNo.text = item.accountNo
                        tvAccountName.text = Helpers.accountNameToString(item.accountName)
                        tvAccountBalanceDebit.text = Helpers.numberFormatter(item.balance.debit)
                        tvAccountBalanceCredit.text = Helpers.numberFormatter(item.balance.credit)
                    }
                }
            }
        }
    }
}