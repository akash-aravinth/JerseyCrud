package com.akash.app.resourse;

import com.akash.app.dao.UserDao;
import com.akash.app.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
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
}
