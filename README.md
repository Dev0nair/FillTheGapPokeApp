# FillTheGapPokeApp

There are 4 modules:

1. App Module: This module is practically the launcher of the application, which only includes the Application class for Hilt and the MainActivity.
2. Features Module: This module is based on the features developed in the application, which includes the Home & Details screens which refers to the main pokemon list and the pokemon detail screen.
3. Repository Module: This module is based on the repository both remote and local/mocked data.
4. Common Module: This last module, includes all the common elements in the applications, like String formats, extensions, base classes, error types...


- This application is developed with Kotlin, using Clean Architecture + MVVM, Hilt, Coroutines, UseCase Pattern and Repository Pattern. 

- Only ViewModels and UseCases are tested in this project just as an example of what do we need for coroutines testing.

- This application, the way it has been developed, is referencing the way a restaurant works:
  1. The Application is the client which only cares about the menu (features).
  2. The features are managed by the waiter, who just cares about receiving an order and then asking the useCase (the person at the bar) to receive what he needs for the plate (Feature). No matter how they are going to get all the ingredients needed.
  3. The repository is the kitchen itself. Only cares about receiving an order and preparing it with the ingredients.
  4. The common module grabs all the general rules of the local and some common things like the bathroom. Everyone like the client and also the cheff will need to use it.

As this proceed explains, the app split its responsabilities to individualize every individual responsability (like an old friend always say called SOLID).
