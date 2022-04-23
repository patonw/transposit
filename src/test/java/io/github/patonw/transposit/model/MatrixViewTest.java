package io.github.patonw.transposit.model;

import org.assertj.core.api.Assertions;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.vavr.api.VavrAssumptions.assumeThat;
import static org.junit.jupiter.api.Assertions.*;

class MatrixViewTest {
    @Test
    public void testAsList() {
        List<List<Integer>> input = List.of(List.of(1, 2), List.of(3, 4));
        var matrix = MatrixView.fromList(input);
        assumeThat(matrix)
                .isRight();

        Assertions.assertThat(matrix.get().asList())
                .usingRecursiveComparison()
                .isEqualTo(input);
    }

    @Test
    public void testTranspose() {
        List<List<Integer>> input = List.of(List.of(1, 2), List.of(3, 4));
        List<List<Integer>> expected = List.of(List.of(1, 3), List.of(2, 4));
        var matrix = MatrixView.fromList(input);
        assumeThat(matrix)
                .isRight();

        Assertions.assertThat(matrix.get().transpose().asList())
                .usingRecursiveComparison()
                .isEqualTo(expected);
    }

}