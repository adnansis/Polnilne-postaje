package si.fri.prpo.api.v1.mappers;

import si.fri.prpo.storitve.izjeme.NeveljavenTerminDtoIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavenTerminDtoExceptionMapper implements ExceptionMapper<NeveljavenTerminDtoIzjema> {

    @Override
    public Response toResponse(NeveljavenTerminDtoIzjema exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\":\"" + exception.getMessage() + "\"}")
                .build();
    }
}
