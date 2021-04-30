package pl.marcinchwedczuk.javafx.validation.lib;

public class StringValidators {
    private StringValidators() { }

    public static Validator<String> required() {
      return (value) -> {
          boolean isValid = (value != null) && !value.isBlank();
          return new ValidationResult<>(value,
                  Objections.combine(
                      Objections.errorIf(!isValid, "Value is required."),
                      Objections.warningIf(true, "Sample warning")));
      };
    }

    public static Validator<String> hasLength(int min, int maxExcluding) {
        // TODO: Validate parameters
        String errorMessage = String.format(
                "Value must be at least %d and at most %d characters long.", min, maxExcluding-1);

        return (value) -> {
            int length = (value == null) ? 0 : value.length();
            boolean isValid = (min <= length && length < maxExcluding);
            return new ValidationResult<>(value,
                    Objections.errorIf(!isValid, errorMessage));
        };
    }
}
