package id.novian.flowablecash.view.report.balance_sheet

import android.graphics.Typeface
import android.text.Layout.Alignment
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import id.novian.flowablecash.base.BaseAdapter
import id.novian.flowablecash.databinding.BalanceSheetTableItemBinding
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.helpers.Helpers

class BalanceSheetAdapter: BaseAdapter<BalanceSheetDomain>() {

    inner class BalanceSheetViewHolder(
        private val binding: BalanceSheetTableItemBinding
    ): BaseViewHolder<BalanceSheetDomain>(binding.root) {
        override fun onBind(data: BalanceSheetDomain, position: Int) {
            Log.d("BalanceSheetAdapter", "data is $data in position $position")
            with(binding) {

                when(position) {

                    0 -> {

                        tvAccountName.gravity = Gravity.CENTER

                        tvAccountNo.text = "No Akun"
                        tvAccountName.text = "Nama Akun"
                        tvAccountBalanceDebit.text = "Debit"
                        tvAccountBalanceCredit.text = "Kredit"

                        tvAccountNo.setTypeface(null, Typeface.BOLD)
                        tvAccountName.setTypeface(null, Typeface.BOLD)
                        tvAccountBalanceDebit.setTypeface(null, Typeface.BOLD)
                        tvAccountBalanceCredit.setTypeface(null, Typeface.BOLD)
                    }

                    else -> {

                        tvAccountNo.visibility = View.VISIBLE

                        val layoutParams = tvAccountName.layoutParams as LinearLayout.LayoutParams

                        layoutParams.width = 0
                        layoutParams.weight = 2F

                        val item = currentList[position - 1]

                        tvAccountNo.text = item.accountNo
                        tvAccountName.text = Helpers.accountNameToString(item.accountName)
                        tvAccountBalanceDebit.text = Helpers.numberFormatter(item.balance.debit)
                        tvAccountBalanceCredit.text = Helpers.numberFormatter(item.balance.credit)

                        tvAccountNo.setTypeface(null, Typeface.NORMAL)
                        tvAccountName.setTypeface(null, Typeface.NORMAL)
                        tvAccountBalanceDebit.setTypeface(null, Typeface.NORMAL)
                        tvAccountBalanceCredit.setTypeface(null, Typeface.NORMAL)
                    }

                }

            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BalanceSheetDomain> {
        val binding = BalanceSheetTableItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BalanceSheetViewHolder(binding)
    }
}