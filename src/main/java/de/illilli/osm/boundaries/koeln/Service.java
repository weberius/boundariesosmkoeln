package de.illilli.osm.boundaries.koeln;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

@Path("/")
public class Service {

	private final static Logger logger = Logger.getLogger(Service.class);

	@Context
	private HttpServletRequest request;

	@Context
	private HttpServletResponse response;

	/**
	 * <p>
	 * Beispiel: <a
	 * href="http://localhost:8080/boundariesosmkoeln/service/stadtteile"></a>
	 * </p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{level}")
	public String getLevelGeoJson(@PathParam("level") String level)
			throws IOException {
		Integer threshold = 10;
		boolean fromResource = true;
		response.setCharacterEncoding("UTF-8");
		Facade facade = FacadeFactory.getFacade(fromResource, level, threshold);
		return facade.getGeoJson();
	}

	/**
	 * <p>
	 * Beispiel: <a
	 * href="http://localhost:8080/boundariesosmkoeln/service/stadtteile/100"
	 * ></a>
	 * </p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{level}/{threshold}")
	public String getLevelAndThresholdGeoJson(@PathParam("level") String level,
			@PathParam("threshold") Integer threshold) throws IOException {
		boolean fromResource = true;
		response.setCharacterEncoding("UTF-8");
		Facade facade = FacadeFactory.getFacade(fromResource, level, threshold);
		return facade.getGeoJson();
	}

	/**
	 * <p>
	 * This REST-Endpoint generates a set of json-Files to put into the
	 * resource-Folder. The path where to find the files will be announced in
	 * the answer of this service.
	 * </p>
	 * <p>
	 * Beispiel: <a
	 * href="http://localhost:8080/boundariesosmkoeln/service/generate"
	 * >/boundariesosmkoeln/service/generate</a>
	 * </p>
	 * 
	 * @return
	 * @throws IOException
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/generate")
	public String getStadtGeoJson() throws IOException {
		String level = request.getParameter("level");
		Integer threshold = 10;
		if (request.getParameter("threshold") != null) {
			threshold = Integer.parseInt(request.getParameter("threshold"));
		}

		Facade facade = new KoelnBoundaryGenerateFacade(level, threshold);
		return facade.getGeoJson();
	}

}
