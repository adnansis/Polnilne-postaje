package si.fri.prpo.api.v1;

import javax.ws.rs.ApplicationPath;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "Polnilne postaje API", version = "v1",
        contact = @Contact(email = "prpo@fri.uni-lj.si"),
        license = @License(name = "dev"), description = "API za storitev Polnilne postaje"),
        servers = @Server(url = "http://localhost:8080"))
@ApplicationPath("v1")
public class PolnilnePostajeApplication extends javax.ws.rs.core.Application {
}
