
# JavaFX Validation Demo

This application shows how to implement simple validation in JavaFX from scratch.
By "simple", I mean that both debouncing and async-validators are not supported.
Support for them can be added with a bit of effort, 
but if you need them, then you will 
probably be better 
of using more advanced validation frameworks. In that case I can recommend checking out
[controlsfx](https://github.com/jinghai/controlsfx/blob/master/controlsfx-samples/src/main/java/org/controlsfx/samples/HelloValidation.java)
and [mvvmFX](https://github.com/sialcasa/mvvmFX/wiki/Validation) libraries.

This application comes in two parts. First we have of course the validation
library itself. Then we have three simple forms, with the most common
validation patterns, that demonstrate how to use
the library:
![demo app main window](docs/demo.png)

## How to?

Run application:
```
./mvnw javafx:run -pl demo
```

Regenerate scss styles:
```
./mvnw nl.geodienstencentrum.maven:sass-maven-plugin:update-stylesheets -pl demo
```

Watch for SCSS changes and regenerate them:
```
fswatch --exclude='.*' --include='.*[.]scss$' --print0 . | while read -d "" event; do
    ./mvnw nl.geodienstencentrum.maven:sass-maven-plugin:update-stylesheets -pl demo
done
```
You need to install `fswatch` for this to work.

