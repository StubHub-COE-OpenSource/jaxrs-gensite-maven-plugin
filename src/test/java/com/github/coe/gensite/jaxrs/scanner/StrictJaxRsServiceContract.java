package com.github.coe.gensite.jaxrs.scanner;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.cxf.jaxrs.model.wadl.Description;

@Path("/services")
@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
public interface StrictJaxRsServiceContract {
    @POST
    @Path("/")
    @Description("Create a item")
    public void createItem();

    @GET
    @Path("/{uniqueID:.*}")
    public void readItem();

    @PUT
    @Path("/{uniqueID:.*}")
    public void updateItem();
}