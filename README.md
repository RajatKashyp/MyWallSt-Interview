## MyWallSt-Interview
#### To demonstrate kotlin programming for android ####

An app to display fallen meteors on Earth using datapoints available from Nasa.

### Criteria
1. List to be sorted by Size
2. List filtered from the year 1900
3. open map with a pin of location of selected meteor
4. Data to be fetched on app launch
5. way to refresh data while using the app

### Guidlines
1. Latest version of Kotlin
2. good test coverage

### Did I meet the criteria? 
1. List is sorted on the bases of size (Mass) of the meteor - please refer applyFilters() method in MainActivity.kt
2. Response items with year < 1900 are filtered out - please refer checkForYear() method in MainActivity.kt
3. On Tap, Google Map is integrated and location marker is displayed - Please refer MapsActivity.kt
4. Data is fetched on app launch - please refer onCreate() and displayResults() method in MainActivity.kt 
5. Swipe Down / Pull to refresh is applied - please refer handleSwipeRefresh() method in MainActivity.kt

### Prerequisites / Guidelines
1. Using Android Studio 4.1.1
2. Using build.gradle 4.0.0
3. Using Kotlin version 1.3.72
4. Using retrofit 2.9.0 
5. Minimum API level 19 - Android 4.4 KitKat
6. Using JsonToKotlinClass plugin
7. Using Extensions.kt file to reference views
8. Internet / wifi connection (Have handled this check as well)
9. Unit Tests are written - please refer ResponseUnitTest.kt

### Dependencies
1. Swipe Refresh - androidx.swiperefreshlayout:swiperefreshlayout:1.0.0
2. KotlinX - org.jetbrains.kotlin:kotlin-stdlib:$kotlin_version
3. Retrofit2 - com.squareup.retrofit2:retrofit:$version_retrofit, com.squareup.retrofit2:converter-gson:$version_retrofit
4. Coroutines - org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2, org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2 
5. Google Maps - com.google.android.gms:play-services-maps:17.0.0
6. Unit Tests - junit:junit:4.12, com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0, com.nhaarman:mockito-kotlin-kt1.1:1.5.0

*I have also made a Trello Board for task management. Please feel free to check this out!*
 - https://trello.com/b/4eXepHS6/mywallstinterview
