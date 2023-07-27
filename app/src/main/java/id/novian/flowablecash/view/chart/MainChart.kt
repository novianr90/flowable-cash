package id.novian.flowablecash.view.chart

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import dagger.hilt.android.AndroidEntryPoint
import id.novian.flowablecash.R
import id.novian.flowablecash.base.layout.BaseFragment
import id.novian.flowablecash.databinding.FragmentChartBinding

@AndroidEntryPoint
class MainChart : BaseFragment<FragmentChartBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentChartBinding
        get() = FragmentChartBinding::inflate

    private val viewModel: ChartViewModel by viewModels()
    private val monthFormatter = MonthFormatter()
    private val balanceFormatter = BalanceFormatter()

    private lateinit var lineGraphKas: LineChart
    private lateinit var lineGraphPenjualan: LineChart
    private lateinit var lineGraphPembelian: LineChart

    override fun setup() {
        super.setup()
        viewModel.viewModelInitialized()
        initLineGraphView()

        setGraphWithData()

        buttonBack()
    }

    private fun initLineGraphView() {
        lineGraphKas = binding.graphKas
        lineGraphPenjualan = binding.graphPenjualan
        lineGraphPembelian = binding.graphPembelian

        lineGraphKas.apply {
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            setPinchZoom(false)
            setDrawGridBackground(false)
            extraLeftOffset = 15F
            extraRightOffset = 15F
            //to hide background lines
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawGridLines(false)

            this.axisRight.isEnabled = false

            val xAxis = this.xAxis
            xAxis.isEnabled = true
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.valueFormatter = monthFormatter

            val yAxis = this.axisLeft
            yAxis.valueFormatter = balanceFormatter
        }

        lineGraphPenjualan.apply {
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            setPinchZoom(false)
            setDrawGridBackground(false)
            extraLeftOffset = 15F
            extraRightOffset = 15F
            //to hide background lines
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawGridLines(false)

            this.axisRight.isEnabled = false

            val xAxis = this.xAxis
            xAxis.isEnabled = true
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.valueFormatter = monthFormatter

            val yAxis = this.axisLeft
            yAxis.valueFormatter = balanceFormatter
        }

        lineGraphPembelian.apply {
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(false)
            setPinchZoom(false)
            setDrawGridBackground(false)
            extraLeftOffset = 15F
            extraRightOffset = 15F
            //to hide background lines
            xAxis.setDrawGridLines(false)
            axisLeft.setDrawGridLines(false)
            axisRight.setDrawGridLines(false)

            this.axisRight.isEnabled = false

            val xAxis = this.xAxis
            xAxis.isEnabled = true
            xAxis.setDrawGridLines(false)
            xAxis.position = XAxis.XAxisPosition.BOTTOM
            xAxis.granularity = 1f
            xAxis.valueFormatter = monthFormatter

            val yAxis = this.axisLeft
            yAxis.valueFormatter = balanceFormatter
        }
    }

    private fun setGraphWithData() {
        with(viewModel) {

            // Kas
            kasAccounts.observe(viewLifecycleOwner) { list ->
                val entries = mutableListOf<Entry>()

                list.map {
                    entries.add(Entry(it.month.toFloat(), it.balance.debit.toFloat()))
                }

                entries.sortBy { it.x }

                val dataSet = LineDataSet(entries, "Data Kas")
                dataSet.color = R.color.blue_sea
                dataSet.valueTextColor = Color.BLACK

                val lineData = LineData(dataSet)

                val xAxis = lineGraphKas.xAxis
                xAxis.axisMinimum = (list.minBy { it.month }.month - 1).toFloat()
                xAxis.axisMaximum = (list.maxBy { it.month }.month + 1).toFloat()

                val minValue = list.minBy { it.balance.debit }.balance.debit
                val maxValue = list.maxBy { it.balance.debit }.balance.debit
                val scale = 0.1f
                val range = maxValue - minValue

                val yAxis = lineGraphKas.axisLeft
                yAxis.axisMinimum = (minValue - (range * scale))
                yAxis.axisMaximum = (maxValue + (range * scale))

                lineGraphKas.data = lineData
                lineGraphKas.invalidate()
                lineGraphKas.legend.isEnabled = false
                lineGraphKas.description.isEnabled = false
            }

            // Penjualan
            penjualanAccounts.observe(viewLifecycleOwner) { list ->
                val entries = mutableListOf<Entry>()

                list.map {
                    entries.add(Entry(it.month.toFloat(), it.balance.credit.toFloat()))
                }

                entries.sortBy { it.x }

                val dataSet = LineDataSet(entries, "Data Kas")
                dataSet.color = R.color.blue_sea
                dataSet.valueTextColor = Color.BLACK

                val lineData = LineData(dataSet)

                val xAxis = lineGraphPenjualan.xAxis
                xAxis.axisMinimum = (list.minBy { it.month }.month - 1).toFloat()
                xAxis.axisMaximum = (list.maxBy { it.month }.month + 1).toFloat()

                val minValue = list.minBy { it.balance.credit }.balance.credit
                val maxValue = list.maxBy { it.balance.credit }.balance.credit
                val scale = 0.1f
                val range = maxValue - minValue

                val yAxis = lineGraphPenjualan.axisLeft
                yAxis.axisMinimum = (minValue - (range * scale))
                yAxis.axisMaximum = (maxValue + (range * scale))

                lineGraphPenjualan.data = lineData
                lineGraphPenjualan.invalidate()
                lineGraphPenjualan.legend.isEnabled = false
                lineGraphPenjualan.description.isEnabled = false
            }

            // Pembelian
            pembelianAccounts.observe(viewLifecycleOwner) { list ->
                val entries = mutableListOf<Entry>()

                list.map {
                    entries.add(Entry(it.month.toFloat(), it.balance.debit.toFloat()))
                }

                entries.sortBy { it.x }

                val dataSet = LineDataSet(entries, "Data Kas")
                dataSet.color = R.color.blue_sea
                dataSet.valueTextColor = Color.BLACK

                val lineData = LineData(dataSet)

                val xAxis = lineGraphPembelian.xAxis
                xAxis.axisMinimum = (list.minBy { it.month }.month - 1).toFloat()
                xAxis.axisMaximum = (list.maxBy { it.month }.month + 1).toFloat()

                val minValue = list.minBy { it.balance.debit }.balance.debit
                val maxValue = list.maxBy { it.balance.debit }.balance.debit
                val scale = 0.1f
                val range = maxValue - minValue

                val yAxis = lineGraphPembelian.axisLeft
                yAxis.axisMinimum = (minValue - (range * scale))
                yAxis.axisMaximum = (maxValue + (range * scale))

                lineGraphPembelian.data = lineData
                lineGraphPembelian.invalidate()
                lineGraphPembelian.legend.isEnabled = false
                lineGraphPembelian.description.isEnabled = false
            }
        }
    }

    private fun buttonBack() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    inner class MonthFormatter : ValueFormatter() {
        private val months = arrayOf(
            "Jan",
            "Feb",
            "Mar",
            "Apr",
            "May",
            "Jun",
            "Jul",
            "Aug",
            "Sep",
            "Oct",
            "Nov",
            "Dec"
        )

        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            val index = value.toInt() - 1
            return if (index in months.indices) {
                months[index]
            } else {
                ""
            }
        }
    }

    inner class BalanceFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase): String {
            return formatValue(value)
        }

        private fun formatValue(value: Float): String {
            return when {
                value >= 1_000_000 -> {
                    val millions = value / 1_000_000
                    String.format("%.0fjt", millions)
                }

                value >= 1_000 -> {
                    val thousands = value / 1_000
                    String.format("%.0frb", thousands)
                }

                else -> {
                    String.format("%.0f", value)
                }
            }
        }
    }
}