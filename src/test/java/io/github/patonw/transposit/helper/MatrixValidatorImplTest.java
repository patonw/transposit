package io.github.patonw.transposit.helper;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.vavr.api.VavrAssertions.assertThat;

class MatrixValidatorImplTest {
    MatrixFactory matrixFactory = new MatrixFactoryImpl("default");

    @Test
    public void testInvalid() {
        var validator = new MatrixValidatorImpl(matrixFactory);
        assertThat(validator.validate(List.of()))
                .isInvalid();
        assertThat(validator.validate(List.of(List.of())))
                .isInvalid();
        assertThat(validator.validate(List.of(List.of(2000))))
                .isInvalid();

        assertThat(validator.validate(List.of(List.of(1,2), List.of(3))))
                .isInvalid();

        List<Number> longRow = IntStream.range(0, 106).boxed().collect(Collectors.toList());

        assertThat(validator.validate(List.of(longRow)))
                .isInvalid();

        List<List<Number>> tooMany = IntStream.range(0, 11)
                .mapToObj(i -> IntStream.range(0, 11)
                        .mapToObj(j -> (Number) j)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());

        assertThat(validator.validate(tooMany))
                .isInvalid();
    }

    @Test
    public void testValid() {
        var validator = new MatrixValidatorImpl(matrixFactory);
        assertThat(validator.validate(List.of(List.of(1,2), List.of(3, 4))))
                .isValid();
    }
}