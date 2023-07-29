package id.novian.flowablecash.view.report.rekap_pemasukkan

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import id.novian.flowablecash.base.layout.BaseTableAdapter
import id.novian.flowablecash.base.layout.VIEW_TYPE_FOOTER
import id.novian.flowablecash.base.layout.VIEW_TYPE_HEADER
import id.novian.flowablecash.databinding.JournalTableItemBinding
import id.novian.flowablecash.domain.models.CashReceiptJournal
import id.novian.flowablecash.helpers.Helpers


class CashReceiptAdapter : BaseTableAdapter<CashReceiptJournal>() {

    inner class HeaderViewHolder(
        private val binding: JournalTableItemBinding
    ): BaseTableViewHolder<CashReceiptJournal>(binding.root) {
        override fun onBindHeader(position: Int) {
            with(binding) {
                if (position == 0) {
                    val dateLayoutParams = tvDate.layoutParams as LinearLayout.LayoutParams
                    val nameLayoutParams =
                        tvName.layoutParams as LinearLayout.LayoutParams
                    val descLayoutParams =
                        tvDesc.layoutParams as LinearLayout.LayoutParams
                    val totalLayoutParams = tvTotal.layoutParams as LinearLayout.LayoutParams


                    200.apply {
                        dateLayoutParams.height = this
                        nameLayoutParams.height = this
                        descLayoutParams.height = this
                        totalLayoutParams.height = this
                    }

                    tvDate.text = "Tanggal"
                    tvName.text = "Nama Transaksi"
                    tvDesc.text = "Deskripsi"
                    tvTotal.text = "Total"

                    tvDate.layoutParams = dateLayoutParams
                    tvName.layoutParams = nameLayoutParams
                    tvDesc.layoutParams = descLayoutParams
                    tvTotal.layoutParams = totalLayoutParams

                    tvDate.setTypeface(null, Typeface.BOLD)
                    tvName.setTypeface(null, Typeface.BOLD)
                    tvDesc.setTypeface(null, Typeface.BOLD)
                    tvTotal.setTypeface(null, Typeface.BOLD)
                }
            }
        }
    }

    inner class FooterViewHolder(
        private val binding: JournalTableItemBinding
    ): BaseTableViewHolder<CashReceiptJournal>(binding.root) {
        override fun onBindFooter(data: List<CashReceiptJournal>, position: Int) {
            with(binding) {
                if (position == itemCount - 1) {
                    tvDate.visibility = View.GONE
                    val nameLayout =
                        tvName.layoutParams as LinearLayout.LayoutParams

                    nameLayout.width = 0
                    nameLayout.weight = 3F

                    tvName.layoutParams = nameLayout

                    tvDesc.visibility = View.GONE
                    val totalLayout = tvTotal.layoutParams as LinearLayout.LayoutParams
                    totalLayout.width = 0
                    totalLayout.weight = 3f

                    tvTotal.layoutParams = totalLayout

                    var debit = 0
                    var credit = 0
                    for (i in 0 until currentList.size) {
                        debit += data[i].debit
                        credit += data[i].credit
                    }

                    val total = if (debit != 0) debit else credit

                    tvName.text = "Total"
                    tvTotal.text = Helpers.numberFormatter(total)

                    tvName.setTypeface(null, Typeface.BOLD)
                    tvTotal.setTypeface(null, Typeface.NORMAL)
                }
            }
        }
    }

    inner class ItemViewHolder(
        private val binding: JournalTableItemBinding
    ): BaseTableViewHolder<CashReceiptJournal>(binding.root) {
        override fun onBindItem(data: CashReceiptJournal, position: Int) {
            with(binding) {
                tvDate.visibility = View.VISIBLE
                tvDesc.visibility = View.VISIBLE

                val dateLayoutParams = tvDate.layoutParams as LinearLayout.LayoutParams
                val nameLayoutParams =
                    tvName.layoutParams as LinearLayout.LayoutParams
                val descLayoutParams = tvDesc.layoutParams as LinearLayout.LayoutParams
                val totalLayoutParams = tvTotal.layoutParams as LinearLayout.LayoutParams

                0.apply {
                    dateLayoutParams.width = this
                    nameLayoutParams.width = this
                    descLayoutParams.width = this
                    totalLayoutParams.width = this
                }

                dateLayoutParams.weight = 1f
                nameLayoutParams.weight = 2F
                descLayoutParams.weight = 2f
                totalLayoutParams.weight = 1f

                tvDate.layoutParams = dateLayoutParams
                tvName.layoutParams = nameLayoutParams
                tvDesc.layoutParams = descLayoutParams
                tvTotal.layoutParams = totalLayoutParams

                val total = if (data.debit != 0) data.debit else data.credit

                tvDate.text = data.date
                tvName.text = data.name
                tvDesc.text = data.description
                tvTotal.text = Helpers.numberFormatter(total)

                tvDate.setTypeface(null, Typeface.NORMAL)
                tvName.setTypeface(null, Typeface.NORMAL)
                tvDesc.setTypeface(null, Typeface.NORMAL)
                tvTotal.setTypeface(null, Typeface.NORMAL)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseTableViewHolder<CashReceiptJournal> {
        val binding = JournalTableItemBinding.inflate(
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