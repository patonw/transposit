package io.github.patonw.transposit.service;

import io.github.patonw.transposit.helper.MatrixValidator;
import io.github.patonw.transposit.model.FlatArrayMatrix;
import io.github.patonw.transposit.model.MatrixView;
import io.vavr.collection.Vector;
import io.vavr.control.Validation;
import org.assertj.vavr.api.VavrAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MatrixServiceTest {
    @Mock
    MatrixValidator validator;

    @Mock
    MatrixView<Number> mockMatrix;

    MatrixService matrixService;

    @BeforeEach
    public void setup() {
        matrixService = new MatrixService(validator);
    }

    @Test
    public void testValidation_failure() {
        when(validator.validate(any()))
                .thenReturn(Validation.invalid(Vector.of("Bad matrix")));

        VavrAssertions.assertThat(matrixService.validate(List.of(List.of())))
                .isInvalid();
    }

    @Test
    public void testValidation_success() {
        when(validator.validate(any()))
                .thenReturn(Validation.valid(mockMatrix));

        VavrAssertions.assertThat(matrixService.validate(List.of(List.of(1.2))))
                .isValid();
    }

    @Test
    public void testTranspose() {
        when(mockMatrix.transpose())
                .thenReturn(mockMatrix);

        assertThat(matrixService.transpose(mockMatrix))
                .isEqualTo(mockMatrix);

        verify(mockMatrix).transpose();
    }
}