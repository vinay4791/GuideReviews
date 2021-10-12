# ReviewListApp

##  Android Studio :
android studio arctic fox 2020.3.1 patch 1 is used for development

##  Tools Used :
- MVVM architecture is used for the project.
- Dependency injection framework KOIN is used.
- Used RxJava  for asynchronous operations and threading in a very modular and abstract way. 
  Please refer com.example.guidereviews.rx(Appropriate comments are given in classes)
- Retrofit is used for Networking and Glide is used for Image caching and loading.
- Android Navigation Component Library is used for designing fragments and communicating between them.
- MOSHI Converter Factory is used for Json serialization and de-serialization.
- Junit and Mockito is used for unit testing.

## Architecture :

MVVM Architecture is used

Implementation is done based on https://developer.android.com/codelabs/kotlin-android-training-repository#5 

## Architecture Flow :

Activity Communicates to ViewModel
ViewModel Communicates to Repository
Repository makes api call with the help of Fetcher
Fetcher Makes api call with use of a retrofit interface
Abstraction is provided for Fetcher.

Data is passed based on a viewstate concept. Response from api is parsed and converted to a viewstate which the view(Acivity/Fragment) consumes.
The conversion is done using a converter which is basically a rxJava Function.(Please refer viewstate package)

Sealed class of kotlin are used for writing viewstates

For eg : The date received from the api is different from what should be shown on the ui.
This conversion is done in a converter so that ui should worry about the conversion logic
(Please refer Utils.kt and UtilsTest.kt files)

The idea of using converters is to provide the recycler viewholder a viewstate where no business 
logic is handled in the viewholder. Same applies to the views used in the project.

Also the converter makes sure that no element passed to vewistate will be null. This avoids potential 
threat of NullPointerException

## Dependency injection :

Koin is used for dependency injection because it works amazingly well with Kotlin.

## Navigation Component :
Fragments are managed using the Navigation Component Library 

Injections in a package are done in a module in respective packages.

## Base Module :
Activies, Fragments and Viewmodels inherit from BaseActivity, BaseFragment and BaseViewmodel respectively, 
which is written purposely for future scaling of the application. 

## Swipe Refresh : 
Swipe Refresh is added to the review list for recalling api in case of failed api calls in an error case.

## SharedViewModel : 
Shared ViewModel concept is used to communicate between the fragments. 

## Unit Testing :  
Business logic and conversions are completely unit tested using junit and mockito.
Please check reviews package in test module

Utils functions  used for Business logic and conversions are also unit tested with positive and negative test cases.
Please check UtilsTest.kt

## Additional Info/Optimizations done

All the dimens and string resources used in the project are added in their respective dimes.xml and strings.xml files to
make kotlin code files and layout xml files very clean.

Please note : In some of the review items received in the response empty string is received as a url. 
A default place holder image is used in this cases. Inorder to maintain parity the the text color used for
date textview is given to the placeholder image.

Appropriate comments are also given in areas inorder to understand why it is implemented like that.

All the libraries used in the project are updated to the latest versions.
Please check build.gradle file(Appropriate comments are  given for external libraries used.

Network Error cases are also handled and error text is shown when network is unavailable.

Test driven development approach is used for writing viewmodel, repository, converter and fetchers
All the unit tests are passing at the time of completion of the project.

## Pagination

A very basic logic is written for achieving pagination using the scroll listener of the recyclerview.

 



