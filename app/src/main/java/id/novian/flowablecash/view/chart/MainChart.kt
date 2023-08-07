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

        val oneMonthBefore = viewModel.calendarHelper.getMonth() - 1
        val currentMonth = viewModel.calendarHelper.getMonth()
        val oneMonthAfter = viewModel.calendarHelper.getMonth() + 1

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

//                val minValue = list.minBy { it.balance.debit }.balance.debit
//                val maxValue = list.maxBy { it.balance.debit }.balance.debit
//                val scale = 0.1f
//                val range = maxValue - minValue

//                val yAxis = lineGraphKas.axisLeft
//                yAxis.axisMinimum = (minValue - (range * scale))
//                yAxis.axisMaximum = (maxValue + (range * scale))

                lineGraphKas.data = lineData
                lineGraphKas.invalidate()
                lineGraphKas.legend.isEnabled = false
                lineGraphKas.description.isEnabled = false
            }

            // Pemasukkan
            pemasukkanData.observe(viewLifecycleOwner) { list ->
                val entries = mutableListOf<Entry>()

                val filteredBefore: Int? = list
                    .filter {
                        val parts = it.transactionDate.split("-")
                        val months = if (parts.size == 3) {
                            parts[1].toInt()
                        } else -1

                        months == oneMonthBefore
                    }
                    .sumOf { it.total }
                    .takeIf { it != 0 }

                filteredBefore?.let {
                    if (it != 0) {
                        entries.add(Entry(oneMonthBefore.toFloat(), filteredBefore.toFloat()))
                    }
                }

                val filteredCurrent = list
                    .filter {
                        val parts = it.transactionDate.split("-")
                        val months = if (parts.size == 3) {
                            parts[1].toInt()
                        } else -1

                        months == currentMonth
                    }.sumOf { it.total }

                entries.add(Entry(currentMonth.toFloat(), filteredCurrent.toFloat()))

                val filteredAfter = list
                    .filter {
                        val parts = it.transactionDate.split("-")
                        val months = if (parts.size == 3) {
                            parts[1].toInt()
                        } else -1

                        months == viewModel.calendarHelper.getMonth() + 1
                    }
                    .sumOf { it.total }
                    .takeIf { it != 0 }

                filteredAfter?.let {
                    if (it != 0) {
                        entries.add(Entry(oneMonthBefore.toFloat(), filteredAfter.toFloat()))
                    }
                }

                entries.sortBy { it.x }

                val dataSet = LineDataSet(entries, "Data Kas")
                dataSet.color = R.color.blue_sea
                dataSet.valueTextColor = Color.BLACK

                val lineData = LineData(dataSet)

                val xAxis = lineGraphPenjualan.xAxis
                xAxis.axisMinimum = oneMonthBefore.toFloat()
                xAxis.axisMaximum = oneMonthAfter.toFloat()

//                val minValue = list.minBy { it.total }.total
//                val maxValue = list.maxBy { it.total }.total
//                val scale = 0.1f
//                val range = maxValue - minValue

//                val yAxis = lineGraphPenjualan.axisLeft
//                yAxis.axisMinimum = (minValue - (range * scale))
//                yAxis.axisMaximum = (maxValue + (range * scale))

                lineGraphPenjualan.data = lineData
                lineGraphPenjualan.invalidate()
                lineGraphPenjualan.legend.isEnabled = false
                lineGraphPenjualan.description.isEnabled = false
            }

            // Pengeluaran
            pengeluaranData.observe(viewLifecycleOwner) { list ->
                val entries = mutableListOf<Entry>()

                val filteredBefore: Int? = list
                    .filter {
                        val parts = it.transactionDate.split("-")
                        val months = if (parts.size == 3) {
                            parts[1].toInt()
                        } else -1

                        months == oneMonthBefore
                    }
                    .sumOf { it.total }
                    .takeIf { it != 0 }

                filteredBefore?.let {
                    if (it != 0) {
                        entries.add(Entry(oneMonthBefore.toFloat(), filteredBefore.toFloat()))
                    }
                }

                val filteredCurrent = list
                    .filter {
                        val parts = it.transactionDate.split("-")
                        val months = if (parts.size == 3) {
                            parts[1].toInt()
                        } else -1

                        months == currentMonth
                    }.sumOf { it.total }

                entries.add(Entry(currentMonth.toFloat(), filteredCurrent.toFloat()))

                val filteredAfter: Int? = list
                    .filter {
                        val parts = it.transactionDate.split("-")
                        val months = if (parts.size == 3) {
                            parts[1].toInt()
                        } else -1

                        months == oneMonthAfter
                    }
                    .sumOf { it.total }
                    .takeIf { it != 0 }

                filteredAfter?.let {
                    if (it != 0) {
                        entries.add(Entry(oneMonthBefore.toFloat(), filteredAfter.toFloat()))
                    }
                }

                val dataSet = LineDataSet(entries, "Data Kas")
                dataSet.color = R.color.blue_sea
                dataSet.valueTextColor = Color.BLACK

                val lineData = LineData(dataSet)

                val xAxis = lineGraphPembelian.xAxis
                xAxis.axisMinimum = oneMonthBefore.toFloat()
                xAxis.axisMaximum = oneMonthAfter.toFloat()

//                val minValue = list.minBy { it.total }.total
//                val maxValue = list.maxBy { it.total }.total
//                val scale = 0.1f
//                val range = maxValue - minValue
//
//                val yAxis = lineGraphPembelian.axisLeft
//                yAxis.axisMinimum = (minValue - (range * scale))
//                yAxis.axisMaximum = (maxValue + (range * scale))

                lineGraphPembelian.data = lineData
                lineGraphPembelian.invalidate()
                lineGraphPembelian.legend.isEnabled = false
                lineGraphPembelian.description.isEnabled = false
            }
        }
    }

    private fun buttonBack() {
        binding.topAppBar.setNavigationOnClickListener {
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