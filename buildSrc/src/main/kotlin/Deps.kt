
object Deps {

    object Core {
        const val coreKtx = "androidx.core:core-ktx:${Versions.Core.coreKtxVersion}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.Core.appCompatVersion}"

        const val coreNavComp = "androidx.navigation:navigation-fragment-ktx:${Versions.Core.navigationComponentVersion}"
        const val navCompKtx = "androidx.navigation:navigation-ui-ktx:${Versions.Core.navigationComponentVersion}"
        const val navCompPlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.Core.navigationComponentVersion}"

        const val androidMaterial = "com.google.android.material:material:${Versions.Core.materialVersion}"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.Core.constraintLayoutVersion}"
        const val coordinatorLayout = "androidx.coordinatorlayout:coordinatorlayout:${Versions.Core.coordinatorLayout}"
    }

    object Room {
        const val roomRuntime = "androidx.room:room-runtime:${Versions.Core.roomVersion}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.Core.roomVersion}"
        const val roomRx = "androidx.room:room-rxjava3:${Versions.Core.roomVersion}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.Core.roomVersion}"
    }

    object Retrofit {
        const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.Core.retrofitVersion}"
        const val gsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.Core.retrofitVersion}"
        const val gson = "com.google.code.gson:gson:${Versions.Core.retrofitVersion}"
    }

    object OkHttp {
        const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.Core.okhttpVersion}"
        const val interceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.Core.okhttpVersion}"
    }

    object ViewModel {
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.Core.viewModelVersion}"
        const val liveData = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.Core.viewModelVersion}"
    }

    object RxJava {
        const val rxJava = "io.reactivex.rxjava3:rxjava:${Versions.Core.rxJava3Version}"
        const val rxAndroid = "io.reactivex.rxjava3:rxandroid:${Versions.Core.rxJava3Version}"
        const val rxRetrofitAdapter = "com.squareup.retrofit2:adapter-rxjava3:${Versions.Core.rxJavaAdapter}"
    }

    object Hilt {
        const val hilt = "com.google.dagger:hilt-android:${Versions.Core.hiltVersion}"
        const val compiler = "com.google.dagger:hilt-android-compiler:${Versions.Core.hiltVersion}"

        const val plugin = "com.google.dagger:hilt-android-gradle-plugin:${Versions.Core.hiltVersion}"
    }

    object Glide {
        const val glide = "com.github.bumptech.glide:glide:${Versions.Core.glideVersion}"
        const val compiler = "com.github.bumptech.glide:compiler:${Versions.Core.glideVersion}"
    }

    object GraphDependency {
        const val graph = "com.github.PhilJay:MPAndroidChart:${Versions.Core.graphVersion}"
    }

    object UnitTest {
        const val junit = "junit:junit:${Versions.UnitTest.jUnitVersion}"
    }

    object AndroidTest {
        const val junit = "androidx.test.ext:junit:${Versions.AndroidTest.jUnitVersion}"
        const val espresso = "androidx.test.espresso:espresso-core:${Versions.AndroidTest.espressoVersion}"
    }

}