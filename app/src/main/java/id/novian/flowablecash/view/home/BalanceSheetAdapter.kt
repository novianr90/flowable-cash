package id.novian.flowablecash.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.novian.flowablecash.R
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

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.balance_sheet_table_header)
    }

    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.balance_sheet_table_content)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = holder.adapterPosition
        if (rowPos == 0) {
            holder.apply {
                setHeaderBg(tvAccountNo)
                setHeaderBg(tvAccountName)
                setHeaderBg(tvAccountDebit)
                setHeaderBg(tvAccountCredit)

                tvAccountNo.text = "Account No."
                tvAccountName.text = "Account Name"
                tvAccountDebit.text = "Debit"
                tvAccountCredit.text = "Credit"
            }
        } else {
            val data = differ.currentList[rowPos - 1]
            with(holder) {
                setContentBg(tvAccountNo)
                setContentBg(tvAccountName)
                setContentBg(tvAccountDebit)
                setContentBg(tvAccountCredit)

                tvAccountNo.text = data.accountNo
                tvAccountName.text = Helpers.accountNameToString(data.accountName)
                tvAccountDebit.text = Helpers.numberFormatter(data.balance.debit.toInt())
                tvAccountCredit.text = Helpers.numberFormatter(data.balance.credit.toInt())
            }
        }
    }

    inner class RowViewHolder(binding: BalanceSheetTableItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val tvAccountNo = binding.tvAccountNo
        val tvAccountName = binding.tvAccountName
        val tvAccountDebit = binding.tvAccountDebit
        val tvAccountCredit = binding.tvAccountCredit
    }

}