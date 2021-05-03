
## How to?

Run application:
```
./mvnw javafx:run
```

Regenerate scss styles:
```
./mvnw nl.geodienstencentrum.maven:sass-maven-plugin:update-stylesheets
```

Watch for SCSS changes and regenerate them:
```
fswatch --exclude='.*' --include='.*[.]scss$' --print0 . | while read -d "" event; do
    ./mvnw nl.geodienstencentrum.maven:sass-maven-plugin:update-stylesheets
done
```
You need to install `fswatch` for this to work.

