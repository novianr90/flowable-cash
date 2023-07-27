package id.novian.flowablecash.base.layout

import androidx.recyclerview.widget.DiffUtil
import id.novian.flowablecash.domain.models.AccountDomain
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.sameAndEqual

class BaseItemCallBack<T : Any> : DiffUtil.ItemCallback<T>() {
    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return when (oldItem) {
            is AccountDomain -> if (newItem is AccountDomain) oldItem.id.sameAndEqual(
                newItem
            ) else false

            is CashReceiptJournal -> if (newItem is CashReceiptJournal) oldItem.id.sameAndEqual(
                newItem
            ) else false

            is TransactionDomain -> if (newItem is TransactionDomain) oldItem.id.sameAndEqual(
                newItem
            ) else false

            else -> false
        }
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.sameAndEqual(newItem)
    }
}