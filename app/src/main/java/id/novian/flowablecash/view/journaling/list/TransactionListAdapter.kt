package id.novian.flowablecash.view.journaling.list

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

                tvItemNameDetails.text = data.transactionName
                tvItemDateDetails.text = data.transactionDate
                tvItemTypeDetails.text = Helpers.transactionTypeChanger(data.transactionType)
                tvItemTotalDetails.text = Helpers.numberFormatter(data.total)
                tvItemDescDetails.text = data.transactionDescription
                tvItemFeeTypeDetails.text = Helpers.feeTypeChanger(data.feeType)
                tvItemFeeDetails.text = Helpers.numberFormatter(data.fee)

                cvItemData.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }
}