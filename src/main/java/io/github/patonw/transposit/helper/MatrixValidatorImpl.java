package io.github.patonw.transposit.helper;

import io.github.patonw.transposit.model.FlatArrayMatrix;
import io.github.patonw.transposit.model.MatrixView;
import io.github.patonw.transposit.model.NestedListMatrix;
import io.vavr.collection.Seq;
import io.vavr.collection.Vector;
import io.vavr.control.Either;
import io.vavr.control.Validation;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MatrixValidatorImpl implements MatrixValidator {
    static final int MAX_ELEMENTS = 105;
    static final float VALUE_LIMIT = 109.0f;

    /**
     * Validates a nested list of numbers and returns a matrix.
     *
     * @return A Valid with the matrix or an Invalid of the errors
     */
    @Override
    public Validation<Seq<String>, MatrixView<Number>> validate(List<List<Number>> input) {
        // TODO report all applicable errors (e.g. misshapen row with element outside bound)

        int numRows = input.size();
        if (numRows < 1)
            return Validation.invalid(Vector.of("Matrix must have at least one row"));

        int numCols = input.get(0).size();
        if (numCols < 1)
            return Validation.invalid(Vector.of("Matrix must have at least one column"));

        // TODO report all rows with incorrect length
        if (!input.stream().allMatch(row -> row.size() == numCols))
            return Validation.invalid(Vector.of("All rows must have the same number of columns"));

        if (numRows * numCols > MAX_ELEMENTS)
           return Validation.invalid(Vector.of("Matrix cannot have more than 105 elements"));

        // TODO report all invalid elements with index & value
        if (input.stream().anyMatch(row -> row.stream().anyMatch(it -> (it.floatValue() < -VALUE_LIMIT) || (it.floatValue() > VALUE_LIMIT))))
            return Validation.invalid(Vector.of(String.format("Matrix entries must be elements of [-%.1f, %.1f]", VALUE_LIMIT, VALUE_LIMIT)));

        var matrix = MatrixView.fromList(input);
        if (matrix.isLeft())
            return Validation.invalid(Vector.of(matrix.getLeft()));
        else
            return Validation.valid(matrix.get());
    }
}
