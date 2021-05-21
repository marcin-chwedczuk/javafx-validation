package pl.marcinchwedczuk.javafx.validation.converters;

import pl.marcinchwedczuk.javafx.validation.ConversionResult;
import pl.marcinchwedczuk.javafx.validation.Objection;
import pl.marcinchwedczuk.javafx.validation.ValidatingValueConverter;

import static org.assertj.core.api.Assertions.assertThat;

public abstract class BaseConverterTest {
    protected <UIV,MV> void assertConvertsBetween(ValidatingValueConverter<UIV, MV> converter, UIV uiValue, MV modelValue) {
        // UI -> Model
        ConversionResult<UIV, MV> modelResult = converter.toModelValue(uiValue);

        assertThat(modelResult.isSuccessful())
                .isTrue();

        assertThat(modelResult.objections)
                .isEmpty();

        assertThat(modelResult.modelValue)
                .isEqualTo(modelValue);

        // Model -> UI
        UIV uiResult = converter.toUiValue(modelValue);

        assertThat(uiResult)
                .isEqualTo(uiValue);
    }

    protected <UIV,MV> void assertUiToModelConvertionFails(
            ValidatingValueConverter<UIV, MV> converter,
            UIV uiValue,
            Objection... expectedObjections)
    {
        ConversionResult<UIV, MV> modelResult = converter.toModelValue(uiValue);

        assertThat(modelResult.isSuccessful())
                .isFalse();

        assertThat(modelResult.objections)
                .hasSize(expectedObjections.length);

        for (Objection expectedObjection: expectedObjections) {
            assertThat(modelResult.objections)
                    .contains(expectedObjection);
        }

        assertThat(modelResult.modelValue)
                .isNull();
    }
}
