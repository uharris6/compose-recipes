# Recipes App

The Recipes App consist of 3 screens:
1.- Recipes List: Show a list of different recipes with their image and name, also in allows you to 
search the recipes by name or the ingredients.
2.- Recipe Detail: In this screen the user can read the information about the recipe, name, image,
ingredients and steps to prepare the food.
3.- Recipe Map: Finally the user could check where is the exact location in which the recipe was uploaded.
For that reason the user could see a map with the marker of the location.

## Architecture
To implement the app it was decided to use Clean Architecture with MVVM to benefit of the use of coroutines 
on the data layer and ViewModel in the UI layer because of the association of the ViewModel with the 
lifecycle of the Activities and Fragments.

## Design Patterns
The most important design patterns use in the implementation of the app are:
1.- Singleton, with the creation of Retrofit as a Singleton we ensure that is has only one instance to be use
in the different services.
2.- Observer, this is the way we communicate between the ViewModel and the Composable Component, in that way
the Component is just waiting that the State so it could implement an specific view.
3.- State, Inside composable the way it works is using states, in that way every time the state change
it will start the process of recomposition of the components associated with that state.

## External Libs
Some external libraries that were use inside the app are:
1.- Hilt with Dagger, create the dependency graph of the classes to be use inside the app.
2.- Glide, image loader using URL.
3.- Coroutines, background management of different request that could be need it inside the app. 
Example: REST request, Database saving and retrieving, Big operational process.
4.- Retrofit and GSON, Http Client that helps in the connection to Web Services and the deserialization of JSONs.
5.- Google Maps, use to show the location of the recipe inside a Map.
6.- Compose, creation of components to create native UI for Android.
7.- Android Navigation, navigation between compose components that allows to sent information between components.
8.- Mockito Kotlin, that helps to mock the different classes and object and to implements Unit tests.