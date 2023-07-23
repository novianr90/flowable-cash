package id.novian.flowablecash.view.report.financial_position

import android.view.LayoutInflater
import android.view.ViewGroup
import id.novian.flowablecash.base.BaseAdapter
import id.novian.flowablecash.databinding.ItemFinancialPositionChildBinding
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.helpers.Helpers

class FinancialPositionChildAdapter : BaseAdapter<AccountDomain>() {

    inner class ChildViewHolder(
        private val binding: ItemFinancialPositionChildBinding
    ) : BaseViewHolder<AccountDomain>(binding.root) {
        override fun onBind(data: AccountDomain) {
            with(binding) {

                val balance = if (data.balance.debit == 0) {
                    data.balance.credit
                } else {
                    data.balance.debit
                }

                val accountName = Helpers.accountNameToString(data.accountName)

                tvAccountName.text = accountName
                tvAccountBalance.text = Helpers.formatCurrency(balance)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<AccountDomain> {
        val binding = ItemFinancialPositionChildBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ChildViewHolder(binding)
    }
}