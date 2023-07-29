package id.novian.flowablecash.view.report.income_statements

import android.view.LayoutInflater
import android.view.ViewGroup
import id.novian.flowablecash.base.layout.BaseAdapter
import id.novian.flowablecash.databinding.ItemProfitlossBinding
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers

class BebanAdapter : BaseAdapter<TransactionDomain>() {

    inner class BebanBaseViewHolder(
        private val binding: ItemProfitlossBinding
    ) : BaseViewHolder<TransactionDomain>(binding.root) {
        override fun onBind(data: TransactionDomain) {
            with(binding) {
                tvNamaTransaksi.text = data.transactionName
                tvTotalAkun.text = Helpers.formatCurrency(data.total)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TransactionDomain> {
        val binding = ItemProfitlossBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return BebanBaseViewHolder(binding)
    }
}