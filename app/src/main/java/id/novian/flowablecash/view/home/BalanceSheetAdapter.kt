package id.novian.flowablecash.view.home

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.novian.flowablecash.databinding.BalanceSheetTableItemBinding
import id.novian.flowablecash.domain.models.BalanceSheetDomain
import id.novian.flowablecash.helpers.Helpers
import id.novian.flowablecash.helpers.sameAndEqual

class BalanceSheetAdapter : RecyclerView.Adapter<BalanceSheetAdapter.RowViewHolder>() {

    private val diffCallBack = object : DiffUtil.ItemCallback<BalanceSheetDomain>() {
        override fun areItemsTheSame(
            oldItem: BalanceSheetDomain,
            newItem: BalanceSheetDomain
        ): Boolean {
            return oldItem.sameAndEqual(newItem)
        }

        override fun areContentsTheSame(
            oldItem: BalanceSheetDomain,
            newItem: BalanceSheetDomain
        ): Boolean {
            return oldItem.sameAndEqual(newItem)
        }
    }

    private val differ = AsyncListDiffer(this, diffCallBack)

    fun submitList(item: List<BalanceSheetDomain>) = differ.submitList(item)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val binding =
            BalanceSheetTableItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RowViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size + 1
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.apply {

                    tvAccountNo.text = "Acc No."
                    tvAccountName.text = "Account Name"
                    tvAccountDebit.text = "Debit"
                    tvAccountCredit.text = "Credit"

                    tvAccountNo.setTypeface(null, Typeface.BOLD)
                    tvAccountName.setTypeface(null, Typeface.BOLD)
                    tvAccountDebit.setTypeface(null, Typeface.BOLD)
                    tvAccountCredit.setTypeface(null, Typeface.BOLD)
                }
            }

            itemCount - 1 -> {
                with(holder) {
                    tvAccountNo.visibility = View.GONE

                    val layoutParams = tvAccountName.layoutParams as LinearLayout.LayoutParams

                    layoutParams.width = 0
                    layoutParams.weight = 3F
                    tvAccountName.layoutParams = layoutParams

                    tvAccountName.text = "Total"

                    var debit = 0
                    var credit = 0

                    for (i in 0 until differ.currentList.size) {
                        debit += differ.currentList[i].balance.debit
                        credit += differ.currentList[i].balance.credit
                    }

                    tvAccountDebit.text = if (debit != 0) Helpers.numberFormatter(debit) else ""
                    tvAccountCredit.text = if (credit != 0) Helpers.numberFormatter(credit) else ""
                }
            }

            else -> {
                val data = differ.currentList[position - 1]
                with(holder) {

                    tvAccountNo.visibility = View.VISIBLE

                    val layoutParams = tvAccountName.layoutParams as LinearLayout.LayoutParams

                    layoutParams.width = 0
                    layoutParams.weight = 2F
                    tvAccountName.layoutParams = layoutParams

                    tvAccountNo.setTypeface(null, Typeface.NORMAL)
                    tvAccountName.setTypeface(null, Typeface.NORMAL)
                    tvAccountDebit.setTypeface(null, Typeface.NORMAL)
                    tvAccountCredit.setTypeface(null, Typeface.NORMAL)

                    tvAccountNo.text = data.accountNo
                    tvAccountName.text = Helpers.accountNameToString(data.accountName)
                    tvAccountDebit.text = Helpers.numberFormatter(data.balance.debit)
                    tvAccountCredit.text = Helpers.numberFormatter(data.balance.credit)
                }
            }
        }
    }

    inner class RowViewHolder(binding: BalanceSheetTableItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvAccountNo = binding.tvAccountNo
        val tvAccountName = binding.tvAccountName
        val tvAccountDebit = binding.tvAccountBalanceDebit
        val tvAccountCredit = binding.tvAccountBalanceCredit
    }

}