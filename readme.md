# FinTrack
## An Accounting Information System for Android

FinTrack is an accounting information system that MSME can use to provide financial
information for their needs.

### Features

- **Record Transactions**: Easily input, update, and delete financial transactions. Each transaction can include details such as date, description, amount, and accounts involved.
- **View Transactions**: Display a list of recorded transactions with filters and sorting options. This feature helps users quickly find and review specific transactions.
- **Income Summary**: Generate an income summary report that compiles all the income-related transactions over a specific period. This report aids in understanding revenue trends.
- **Expense Summary**: Obtain an overview of expenses by generating an expense summary report. It provides insights into where the company's money is being spent.
- **Cost of Goods Sold (COGS) Analysis**: Access a Cost of Goods Sold (COGS) report to analyze the costs directly associated with producing goods or services. This information is vital for evaluating profitability.
- **Profit and Loss Statement**: Generate a profit and loss statement that outlines the company's revenues, costs, and expenses over a specified period. This report helps assess the financial health of the business.
- **Balance Sheet**: View a balance sheet report that presents a snapshot of the company's financial position, including assets, liabilities, and equity.
- **Cash, Income, and Expenses Graph**: View a graph that presents the flow of `cash`, `income`, or `expense` each month.
 
### Tech
- [Retrofit](https://square.github.io/retrofit/) - Libraries to call API
- [RxJava](https://reactivex.io/) - Libraries to handle all the asynchronous job
- [Hilt Dagger](https://dagger.dev/hilt/) - Libraries to handle Dependency Injection
- [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) - Libraries to handle all the graph
- [Navigation Component](https://developer.android.com/guide/navigation?hl=id) - Libraries to handle navigating to other screens
- MVVM - Design Pattern for Architecture on Android

### Installation
- **Download APK File**: Download APK file from [this](https://github.com/novianr90/flowable-cash/blob/master/app/release/FinTrack.apk)
- **Enable Installation**: On your Android device, go to `Settings > Security` and enable the "Unknown sources" option to allow installation of apps from sources other than the Play Store.
- **Install App**: Locate the downloaded APK file and tap on it to start the installation process.
- **Open the App**: Once installed, open `FinTrack` and use it!

### Usage
- **Record Transaction**: Tap `+` on floating button, then tap `Rekam transaksi pemasukkan` or `Rekam transaksi pengeluaran anda` and fill the transaction details
- **Add Balance Sheet**: Tap `+` on floating button, then tap `Masukkan Saldo Awal` and fill in your asset details (such as cash, tools, etc)
- **Summary**: Tap `Report` on Bottom Navigation then tap `Rekap Pemasukkan`, `Rekap Pengeluaran` or `Rekap HPP` to check summary of `incomes`, `expenses`, and `cogs`.
- **Profit and Loss Statements**: Tap `Report` on Bottom Navigation then tap `Laporan Laba Rugi` to check `profit and loss` on 1 month period
- **Financial Position**: Tap `Report` on Bottom Navigation then tap `Laporan Posisi Keuangan` to check `assets, liabilities, and equities` on 1 month period.
- **Chart**: Tap `Chart` on Bottom Navigation to check the `graph` for `cash`, `income`, or `expenses` flow.

## License
Distributed under the MIT License. See `LICENSE.txt` for more information.
