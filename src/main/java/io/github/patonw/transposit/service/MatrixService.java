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

    public Validation<Seq<String>, MatrixView<Number>> validate(List<List<Number>> input) {
        return matrixValidator.validate(input);
    }

    public MatrixView<Number> transpose(MatrixView<Number> input) {
        return input.transpose();
    }
}
