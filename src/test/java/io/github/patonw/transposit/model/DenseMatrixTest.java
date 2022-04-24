package io.github.patonw.transposit.model;

import org.assertj.core.api.Assertions;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.patonw.transposit.model.MatrixView.INVALID_COLUMN;
import static io.github.patonw.transposit.model.MatrixView.INVALID_ROW;

class DenseMatrixTest {

    @Test
    void testInvalidMatrix() {
        VavrAssertions.assertThat(DenseMatrix.fromList(null))
                .isLeft();
        VavrAssertions.assertThat(DenseMatrix.fromList(List.of(List.of(1), List.of(2, 4), List.of(3))))
                .isLeft();
    }

    @Test
    void testEmptyMatrix() {
        VavrAssertions.assertThat(DenseMatrix.fromList(List.of()))
                .isRight()
                .hasRightValueSatisfying(m -> {
                    Assertions.assertThat(m.numRows())
                            .isEqualTo(0);
                    Assertions.assertThat(m.numColumns())
                            .isEqualTo(0);

                    VavrAssertions.assertThat(m.get(0, 0))
                            .containsOnLeft(INVALID_ROW);
                });

        VavrAssertions.assertThat(DenseMatrix.fromList(List.of(List.of())))
                .isRight()
                .hasRightValueSatisfying(m -> {
                    Assertions.assertThat(m.numRows())
                            .isEqualTo(1);
                    Assertions.assertThat(m.numColumns())
                            .isEqualTo(0);

                    VavrAssertions.assertThat(m.get(0, 0))
                            .containsOnLeft(INVALID_COLUMN);
                });
    }

    @Test
    void testRowVector() {
        VavrAssertions.assertThat(DenseMatrix.fromList(List.of(List.of(1,2,3))))
                .isRight()
                .hasRightValueSatisfying(m -> {
                    Assertions.assertThat(m.numRows())
                            .isEqualTo(1);
                    Assertions.assertThat(m.numColumns())
                            .isEqualTo(3);
                    VavrAssertions.assertThat(m.get(0, 2))
                            .containsOnRight(3);
                    VavrAssertions.assertThat(m.get(1, 2))
                            .isLeft();
                });
    }

    @Test
    void testColumnVector() {
        VavrAssertions.assertThat(DenseMatrix.fromList(List.of(List.of(1), List.of(2), List.of(3))))
                .isRight()
                .hasRightValueSatisfying(m -> {
                    Assertions.assertThat(m.numRows())
                            .isEqualTo(3);
                    Assertions.assertThat(m.numColumns())
                            .isEqualTo(1);
                    VavrAssertions.assertThat(m.get(1, 0))
                            .containsOnRight(2);
                    VavrAssertions.assertThat(m.get(1, 1))
                            .isLeft();
                });
    }

    @Test
    void testSquareMatrix() {
        VavrAssertions.assertThat(DenseMatrix.fromList(List.of(List.of(1, 2, 3), List.of(4, 5, 6), List.of(7, 8, 9))))
                .isRight()
                .hasRightValueSatisfying(m -> {
                    Assertions.assertThat(m.numRows())
                            .isEqualTo(3);
                    Assertions.assertThat(m.numColumns())
                            .isEqualTo(3);
                    VavrAssertions.assertThat(m.get(1, 1))
                            .containsOnRight(5);
                    VavrAssertions.assertThat(m.get(-1, -1))
                            .isLeft();
                });
    }
}