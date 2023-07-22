package id.novian.flowablecash.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import id.novian.flowablecash.base.BaseAdapter
import id.novian.flowablecash.databinding.TransactionItemBinding
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers

class TransactionListAdapter(
    private val onClick: (item: TransactionDomain) -> Unit
) : BaseAdapter<TransactionDomain>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<TransactionDomain> {
        val binding = TransactionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return TransactionListViewHolder(binding)
    }

    inner class TransactionListViewHolder(private val binding: TransactionItemBinding) :
        BaseViewHolder<TransactionDomain>(binding.root) {

        override fun onBind(data: TransactionDomain) {
            with(binding) {

                tvTransactionDesc.text = data.transactionDescription
                tvTransactionDate.text =
                    Helpers.dateFormatFromNonStringToString(data.transactionDate)
                tvTransactionTotal.text = Helpers.numberFormatter(data.total)
                tvTransactionType.text = Helpers.transactionTypeChanger(data.transactionType)

                cvItemData.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }
}