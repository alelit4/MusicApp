# 🎧MusicApp

This is a sample app to search music based on [iTunes Api](https://affiliate.itunes.apple.com/resources/documentation/itunes-store-web-service-search-api/)

## 📐Architecture
The design of this app is based on [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
Specific I used MVVM(Model-View-ViewModel) of [Android developer guide](https://developer.android.com/jetpack/guide)

I have used the layers I briefly describe below:

* data: This is the infrastructure layer where external implementations
to my domain goes, they are the 'adapters'; like repository implementations.
* di: The dependency injection layer (based on Hilt)
* domain: Here is where the logic of the app goes, such us: models,
repository interfaces, custom network management
* ui: The layer which control UI interactions
* utils: Shared utilities to the project (there are some technical debt here)

## 💉Dependencies

### Testing

* JUnit ->  'junit:junit:4.13.1'
* Espresso -> 'androidx.test.espresso:espresso-core:3.3.0'
* Mockito -> 'org.mockito:mockito-core:3.6.0'
* OkHttp3 -> 'com.squareup.okhttp3:mockwebserver:4.9.0'

### Jetpack

* Navigation -> 'androidx.navigation:navigation-fragment-ktx:2.3.2', 'androidx.navigation:navigation-ui-ktx:2.3.2'
* Data binding -> 'com.android.databinding:compiler:3.2.0-alpha10', 'androidx.databinding:databinding-common:4.1.1'
* Hilt ->  'com.google.dagger:hilt-android:2.28-alpha'

### UI

* Material Design -> 'com.google.android.material:material:1.3.0-beta01'
* Shimmer effect -> 'com.facebook.shimmer:shimmer:0.5.0', 'com.todkars:shimmer-recyclerview:0.4.0'

### API conmmunication
* Retrofit -> 'com.squareup.retrofit2:retrofit:2.9.0'
* Gson -> 'com.google.code.gson:gson:2.8.6'

## 🎵iTunes Api
Some examples of the use:

⚡️\[GET\] Artist by ***name***
https://itunes.apple.com/search?term=***name***&entity=allArtist&attribute=allArtistTerm&limit=25

⚡️\[GET\] Artist work by ***artistId***
https://itunes.apple.com/lookup?id=***artistId***&entity=album
