# scala-cli as the new scala runner

* requirements for the new scala runner was discussed
* quality attributes: reliability, stability, ...
* documentation
* where on github: under the scala org?
* one executable is preferred; separate what is experimental under the --power flag


# more ergonomic union types

* more precise type inference is being worked on an some things have already improved
* generating a match for common members in a union is against nominal typing philosophy and there are workarounds, e.g. creating extensions methods on the union to make it more convenient (it could be done with macros but it is still dubious in terms of nominal typing)