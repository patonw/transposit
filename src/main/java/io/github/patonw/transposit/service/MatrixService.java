package io.github.patonw.transposit.service;

import io.github.patonw.transposit.helper.MatrixValidator;
import io.github.patonw.transposit.model.MatrixView;
import io.github.patonw.transposit.model.NestedListMatrix;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.util.List;

@ApplicationScoped
public class MatrixService {
    private final MatrixValidator matrixValidator;

    @Inject
    public MatrixService(MatrixValidator matrixValidator) {
        this.matrixValidator = matrixValidator;
    }

    /**
     * Converts a list of lists into a valid matrix or a sequence of errors.
     */
    public Validation<Seq<String>, MatrixView<Number>> validate(List<List<Number>> input) {
        return matrixValidator.validate(input);
    }

    /**
     * Returns a transposed view of the input matrix.
     *
     * The returned view may share the same underlying data or may use newly allocated memory.
     */
    public MatrixView<Number> transpose(MatrixView<Number> input) {
        return input.transpose();
    }
}
