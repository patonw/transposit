package io.github.patonw.transposit.rest;

import com.fasterxml.jackson.databind.node.ArrayNode;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class MatrixResourceIT {
    @Test
    public void testEmptyMatrix() {
        given()
                .when().body(List.of(List.of()))
                .contentType(ContentType.JSON)
                .post("/matrix/transpose")
                .then()
                .statusCode(400)
                .body("$", hasItem("Matrix must have at least one column"));
    }

    @Test
    public void testLoneElement() {
        var result = given()
                .when().body(List.of(List.of(1.1)))
                .contentType(ContentType.JSON)
                .post("/matrix/transpose")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<List<List<Number>>>() {});

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(List.of(List.of(1.1)));

    }
    @Test
    public void testVector() {
        var result = given()
                .when().body(List.of(List.of(1.1, 2.2)))
                .contentType(ContentType.JSON)
                .post("/matrix/transpose")
                .then()
                .statusCode(200)
                .extract().as(new TypeRef<List<List<Number>>>() {});

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(List.of(List.of(1.1), List.of(2.2)));

    }
}