package pl.marcinchwedczuk.javafx.validation;

import org.assertj.core.api.ListAssert;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseUnitTest {

    protected ListAssert<Objection> assertObjections(ObjectionsList objections) {
        return assertThat(objections.asList());
    }
}
