package id.novian.flowablecash.view.report.main

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import id.novian.flowablecash.base.layout.BaseAdapter
import id.novian.flowablecash.databinding.MenuReportItemBinding

class MenuReportAdapter(
    private val onClick: (String) -> Unit
) : BaseAdapter<ModelMenu>() {

    inner class MenuReportViewHolder(
        private val binding: MenuReportItemBinding
    ) : BaseViewHolder<ModelMenu>(binding.root) {
        override fun onBind(data: ModelMenu) {
            with(binding) {
                tvReportTitle.text = data.title

                Glide.with(itemView.context)
                    .load(data.image)
                    .override(dpToPx(100), dpToPx(100))
                    .into(ivReport)

                itemDataReport.setOnClickListener {
                    onClick(data.title)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ModelMenu> {
        val binding = MenuReportItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MenuReportViewHolder(binding)
    }

    private fun dpToPx(dp: Int): Int {
        val res = Resources.getSystem().displayMetrics
        return (dp * res.density).toInt()
    }
}