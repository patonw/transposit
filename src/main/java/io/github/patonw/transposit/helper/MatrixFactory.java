package io.github.patonw.transposit.helper;

import io.github.patonw.transposit.model.MatrixView;
import io.vavr.control.Either;

import java.util.List;

public interface MatrixFactory {
    /**
     * Create a matrix from a list of lists.
     *
     * The underlying implementation may vary based on configuration and input data.
     */
    <T> Either<String, MatrixView<T>> matrix(List<List<T>> input);
}
