package id.novian.flowablecash.view.report.main

import android.view.LayoutInflater
import android.view.ViewGroup
import id.novian.flowablecash.base.BaseAdapter
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
                ivReport.setImageResource(data.image)

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
}