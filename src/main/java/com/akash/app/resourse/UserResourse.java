package com.akash.app.resourse;

import com.akash.app.dao.UserDao;
import com.akash.app.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/users")
public class UserResourse {
    private UserDao userDao;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers(){
        userDao = new UserDao();
        List<User> users = userDao.getUsers();
        return Response.ok(users).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user){
        userDao = new UserDao();
        boolean result = userDao.addUser(user);
        if (result){
            return Response.ok("User Added Successfully").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("Cannot Insert User").build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") int id){
        userDao = new UserDao();
        User user = userDao.getUserById(id);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User not found for ID: " + id)
                    .build();
        }
        return Response.ok(user).build();
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("id") int id,User user){
        userDao = new UserDao();
        boolean isAvailable = userDao.updateUserById(id,user);
        if (!isAvailable){
            return Response.status(Response.Status.NOT_FOUND).entity("User Not Found in User Id : "+id).build();
        }
        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deleteByUserId(@PathParam("id") int id){
        userDao = new UserDao();
        boolean result = userDao.deleteUser(id);
        if (result){
            return Response.ok("User Deleted").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("User Id is Not Available : "+id).build();
    }

    @PATCH
    @Path("/{id}/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response patchUser(@PathParam("id") int id,@PathParam("name") String name) {
        userDao = new UserDao();
        boolean result = userDao.patchUser(id, name);
        if (result) {
            return Response.ok("Update User Successfully").build();
        }
        return Response.status(Response.Status.NOT_FOUND).entity("User Id Not Found : "+id).build();
    }

    @HEAD
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHeadForUser(@PathParam("id") int id){
        userDao = new UserDao();
        User user = userDao.getUserById(id);
        if (user == null){
            return Response.status(Response.Status.NOT_FOUND).entity("User Not Found").build();
        }
        return Response.ok("User is Available").build();
    }
}
