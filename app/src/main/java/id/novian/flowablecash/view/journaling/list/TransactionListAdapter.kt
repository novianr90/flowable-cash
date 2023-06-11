package id.novian.flowablecash.view.journaling.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.novian.flowablecash.databinding.TransactionItemBinding
import id.novian.flowablecash.domain.models.TransactionDomain
import id.novian.flowablecash.helpers.Helpers
import id.novian.flowablecash.helpers.sameAndEqual

class TransactionListAdapter(
    private val onClick: (item: TransactionDomain) -> Unit
) : RecyclerView.Adapter<TransactionListAdapter.TransactionListViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<TransactionDomain>() {
        override fun areItemsTheSame(
            oldItem: TransactionDomain,
            newItem: TransactionDomain
        ): Boolean {
            return oldItem.sameAndEqual(newItem)
        }

        override fun areContentsTheSame(
            oldItem: TransactionDomain,
            newItem: TransactionDomain
        ): Boolean {
            return oldItem.sameAndEqual(newItem)
        }

    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(item: List<TransactionDomain>) = differ.submitList(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionListViewHolder {
        val binding =
            TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionListViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: TransactionListViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class TransactionListViewHolder(private val binding: TransactionItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TransactionDomain) {
            with(binding) {

                tvItemNameDetails.text = item.transactionName
                tvItemDateDetails.text = item.transactionDate
                tvItemTypeDetails.text = Helpers.transactionTypeChanger(item.transactionType)
                tvItemTotalDetails.text = Helpers.numberFormatter(item.total)
                tvItemDescDetails.text = item.transactionDescription
                tvItemFeeTypeDetails.text = Helpers.feeTypeChanger(item.feeType)
                tvItemFeeDetails.text = Helpers.numberFormatter(item.fee)

                cvItemData.setOnClickListener {
                    onClick(item)
                }
            }

        }
    }
}