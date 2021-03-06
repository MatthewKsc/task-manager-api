# Task Manager
This project is created with Spring Boot backend and 
Angular frontend.

* `task-manager/src/main/java/com/matthewksc/taskmanager/controllers` -> folder with
basic controllers to provide Task and TaskList endpoints
* `task-manager/src/main/java/com/matthewksc/taskmanager/dao` ->
folder with entity's and repositories
* `task-manager/src/main/java/com/matthewksc/taskmanager/services` ->
folder separate dao from controllers.
* `task-manager/src/main/java/com/matthewksc/taskmanager/security` -> security 
config for application
* `task-manager/src/main/java/com/matthewksc/taskmanager/jwt` -> Implementation
of JSON Web Token
* `task-ng-api` - folder with angular that consume endpoints
* `task-manager/src/test/java/com/matthewksc/taskmanager` -> provided test's for services,
controllers etc.

# TaskNgApi

This project was generated with [Angular CLI](https://github.com/angular/angular-cli) version 9.1.6.

## Development server

Run `ng serve` for a dev server. Navigate to `http://localhost:4200/`. The app will automatically reload if you change any of the source files.

## Code scaffolding

Run `ng generate component component-name` to generate a new component. You can also use `ng generate directive|pipe|service|class|guard|interface|enum|module`.

## Build

Run `ng build` to build the project. The build artifacts will be stored in the `dist/` directory. Use the `--prod` flag for a production build.

## Running unit tests

Run `ng test` to execute the unit tests via [Karma](https://karma-runner.github.io).

## Running end-to-end tests

Run `ng e2e` to execute the end-to-end tests via [Protractor](http://www.protractortest.org/).

## Further help

To get more help on the Angular CLI use `ng help` or go check out the [Angular CLI README](https://github.com/angular/angular-cli/blob/master/README.md).
