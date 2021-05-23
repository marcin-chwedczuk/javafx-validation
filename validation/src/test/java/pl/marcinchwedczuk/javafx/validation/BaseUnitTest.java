package pl.marcinchwedczuk.javafx.validation;

import org.assertj.core.api.ListAssert;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseUnitTest {

    // TODO: Remove this, add equals and toString to ObjectionsList
    protected ListAssert<Objection> assertObjections(ObjectionsList objections) {
        return assertThat(objections.asList());
    }
}
