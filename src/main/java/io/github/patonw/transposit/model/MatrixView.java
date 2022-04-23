package io.github.patonw.transposit.model;

import io.vavr.control.Either;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public interface MatrixView<T> {
    String INVALID_ROW = "row out of bounds";
    String INVALID_COLUMN = "column out of bounds";

    int numRows();

    int numColumns();

    Either<String, T> get(int row, int column);

    default List<List<T>> asList() {
        return IntStream.range(0, this.numRows())
                .mapToObj(i -> IntStream.range(0, this.numColumns() )
                        .mapToObj(j -> this.get(i, j).getOrElseThrow(e -> new RuntimeException(e)))
                        .collect(Collectors.toList()) )
                .collect(Collectors.toList());
    }

    default MatrixView<T> transpose() {
        return new TransposeView<>(this);
    }

    static <T> Either<String, MatrixView<T>> fromList(List<List<T>> input) {
        return FlatArrayMatrix.fromList(input).map(it -> it);
    }
}
