package io.github.patonw.transposit.rest;

import io.github.patonw.transposit.model.MatrixView;
import io.github.patonw.transposit.model.NestedListMatrix;
import io.github.patonw.transposit.service.MatrixService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/matrix")
public class MatrixResource {
    private final MatrixService matrixService;

    @Inject
    public MatrixResource(MatrixService matrixService) {
        this.matrixService = matrixService;
    }

    /**
     * Transpose a matrix.
     *
     * If the input is a valid matrix, responds with the transposition.
     * Otherwise, responds with a list of errors.
     *
     * Input must be a JSON array of arrays with numeric values with at least one element.
     * Total number of elements must not exceed 105.
     * Elements must be in the range of [-109..109]
     */
    @POST
    @Path("/transpose")
    public Response transposeMatrix(List<List<Number>> input) {
        var validation = matrixService.validate(input);
        if (validation.isInvalid())
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(validation.getError().asJava())
                    .build();

        MatrixView<?> result = matrixService.transpose(validation.get());

        return Response.ok(result.asList()).build();
    }

}
