package io.github.patonw.transposit.helper;

import io.github.patonw.transposit.model.DenseMatrix;
import io.github.patonw.transposit.model.MatrixView;
import io.github.patonw.transposit.model.NestedListMatrix;
import io.vavr.control.Either;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import javax.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class MatrixFactoryImpl implements MatrixFactory {
    private final String matrixImplementation;

    public MatrixFactoryImpl(
            @ConfigProperty(name = "transposit.matrix.impl") String matrixImplementation) {
        this.matrixImplementation = matrixImplementation == null ? "" : matrixImplementation;
    }

    @Override
    public <T> Either<String, MatrixView<T>> matrix(List<List<T>> input) {
        // TODO select sparse implementation when non-zero elements below threshold
        switch (matrixImplementation) {
            case "NestedListMatrix":
                return NestedListMatrix.fromList(input).map(it -> it);
            case "DenseMatrix":
            default:
                return DenseMatrix.fromList(input).map(it -> it);
        }
    }
}
