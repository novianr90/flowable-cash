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
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.helpers.Helpers

class TrialBalanceAdapter: BaseTableAdapter<AccountDomain>() {

    inner class HeaderViewHolder(
        private val binding: BalanceSheetTableItemBinding
    ): BaseTableViewHolder<AccountDomain>(binding.root) {
        override fun onBindHeader(position: Int) {
            with(binding) {
                if (position == 0) {
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
            }
        }
    }

    inner class FooterViewHolder(
        private val binding: BalanceSheetTableItemBinding
    ): BaseTableViewHolder<AccountDomain>(binding.root) {
        override fun onBindFooter(data: List<AccountDomain>, position: Int) {
            with(binding) {
                if (position == itemCount - 1) {
                    tvAccountNo.visibility = View.GONE

                    val layoutParams = tvAccountName.layoutParams as LinearLayout.LayoutParams

                    layoutParams.width = 0
                    layoutParams.weight = 3F

                    tvAccountName.layoutParams = layoutParams

                    var debit = 0
                    var credit = 0
                    for (i in 0 until currentList.size) {
                        debit += data[i].balance.debit
                        credit += data[i].balance.credit
                    }

                    val result = if (debit == credit) {
                        "Balance"
                    } else "Not Balance"

                    tvAccountName.text = result
                    tvAccountBalanceDebit.text = Helpers.numberFormatter(debit)
                    tvAccountBalanceCredit.text = Helpers.numberFormatter(credit)

                    tvAccountName.setTypeface(null, Typeface.BOLD)
                    tvAccountBalanceDebit.setTypeface(null, Typeface.NORMAL)
                    tvAccountBalanceCredit.setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    inner class ItemViewHolder(
        private val binding: BalanceSheetTableItemBinding
    ): BaseTableViewHolder<AccountDomain>(binding.root) {
        override fun onBindItem(data: AccountDomain, position: Int) {
            with(binding) {
                tvAccountNo.visibility = View.VISIBLE

                val layoutParams = tvAccountName.layoutParams as LinearLayout.LayoutParams

                layoutParams.width = 0
                layoutParams.weight = 2F

                tvAccountNo.text = data.accountNo
                tvAccountName.text = Helpers.accountNameToString(data.accountName)
                tvAccountBalanceDebit.text = Helpers.numberFormatter(data.balance.debit)
                tvAccountBalanceCredit.text = Helpers.numberFormatter(data.balance.credit)

                tvAccountNo.setTypeface(null, Typeface.NORMAL)
                tvAccountName.setTypeface(null, Typeface.NORMAL)
                tvAccountBalanceDebit.setTypeface(null, Typeface.NORMAL)
                tvAccountBalanceCredit.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseTableViewHolder<AccountDomain> {
        val binding = BalanceSheetTableItemBinding.inflate(
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