package io.github.patonw.transposit.model;

import io.vavr.control.Either;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Matrix backed by nested lists.
 *
 * All rows must have the same number of columns.
 *
 * @param <T> Type parameter for matrix elements
 */
public class NestedListMatrix<T> implements MatrixView<T> {
    private final ArrayList<ArrayList<T>> data;

    protected NestedListMatrix(ArrayList<ArrayList<T>> data) {
        this.data = data;
    }

    /**
     * Convert a nested list of elements into a Matrix.
     *
     * @param input
     * @return
     * @param <T>
     */
    public static <T> Either<String,NestedListMatrix<T>> fromList(List<List<T>> input) {
        var data = input.stream()
                .map(ArrayList::new)
                .collect(Collectors.toCollection(ArrayList::new));

        // TODO check shape
        return Either.right(new NestedListMatrix<>(data));
    }

    @Override
    public int numRows() {
        return data.size();
    }

    @Override
    public int numColumns() {
        if (this.numRows() > 0)
            return data.get(0).size();
        else
            return 0;
    }

    /**
     * Attempt to return the element at the given index.
     *
     * @return Right of the element or Left with an error.
     */
    @Override
    public Either<String, T> get(int row, int column) {
        if (row < 0 || row >= this.numRows())
            return Either.left(INVALID_ROW);

        if (column < 0 || column >= this.numColumns())
            return Either.left(INVALID_COLUMN);

        return Either.right(data.get(row).get(column));
    }

    /**
     * Transpose this matrix.
     *
     * @return A new matrix with the same elements as this, but with rows and columns swapped
     */
    @Override
    public NestedListMatrix<T> transpose() {
        var result = IntStream.range(0, this.numColumns())
                .mapToObj(j -> IntStream.range(0, this.numRows() )
                        .mapToObj(i -> this.get(i, j).getOrElseThrow(e -> new RuntimeException(e)))
                        .collect(Collectors.toList()) )
                .collect(Collectors.toList()) ;
        return NestedListMatrix.fromList(result).get();
    }
}
