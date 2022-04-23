package io.github.patonw.transposit.helper;

import io.github.patonw.transposit.model.MatrixView;
import io.github.patonw.transposit.model.NestedListMatrix;
import io.vavr.collection.Seq;
import io.vavr.control.Validation;

import java.util.List;

public interface MatrixValidator {
    Validation<Seq<String>, MatrixView<Number>> validate(List<List<Number>> input);
}
