open module pl.marcinchwedczuk.javafx.validation.test  {
    requires pl.marcinchwedczuk.javafx.validation;
    exports pl.marcinchwedczuk.javafx.validation.lib.test;

    requires transitive org.junit.jupiter.engine;
    requires transitive org.junit.jupiter.api;
}