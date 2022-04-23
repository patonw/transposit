package io.github.patonw.transposit.model;

import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class FlatArrayMatrix<T> implements MatrixView<T> {
    private final ArrayList<T> data;
    private final int numRows;
    private final int numColumns;

    public static <T> Either<String, FlatArrayMatrix<T>> fromList(List<List<T>> input) {
        if (input == null)
            return Either.left("Matrix cannot be null");

        var data = input.stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toCollection(ArrayList::new));

        int numRows = input.size();
        int numColumns = numRows > 0 ? input.get(0).size() : 0;
        if (data.size() == numRows * numColumns)
            return Either.right(new FlatArrayMatrix<>(data, numRows, numColumns));
        else
            return Either.left("Rows must have a uniform number of columns");
    }

    protected FlatArrayMatrix(ArrayList<T> data, int numRows, int numColumns) {
        this.data = data;
        this.numRows = numRows;
        this.numColumns = numColumns;
    }

    @Override
    public int numRows() {
        return this.numRows;
    }

    @Override
    public int numColumns() {
        return this.numColumns;
    }

    @Override
    public Either<String, T> get(int row, int column) {
        if (row < 0 || row >= this.numRows()) {
            return Either.left(INVALID_ROW);
        }

        if (column < 0 || column >= this.numColumns()) {
            return Either.left(INVALID_COLUMN);
        }

        var offset = row * numColumns + column;
        return Either.right(data.get(offset));
    }

    // TODO toString, etc
}
