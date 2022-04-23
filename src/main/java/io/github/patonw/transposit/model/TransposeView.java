package io.github.patonw.transposit.model;

import io.vavr.control.Either;

public class TransposeView<T> implements MatrixView<T> {
    private final MatrixView<T> inner;

    public TransposeView(MatrixView<T> inner) {
        this.inner = inner;
    }

    @Override
    public int numRows() {
        return inner.numColumns();
    }

    @Override
    public int numColumns() {
        return inner.numRows();
    }

    @Override
    public Either<String, T> get(int row, int column) {
        if (row < 0 || row >= this.numRows())
            return Either.left("row out of bounds");

        if (column < 0 || column >= this.numColumns())
            return Either.left("column out of bounds");

        return inner.get(column, row);
    }
}
