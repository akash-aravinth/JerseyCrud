package com.akash.app.resourse;

import com.akash.app.dao.StudentDao;
import com.akash.app.model.Student;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentResourse {
    private StudentDao studentDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getStudents(){
        studentDao = new StudentDao();
        List<Student> studentList = studentDao.getAllUsers();
        if (studentList == null){
           return Response.status(Response.Status.NOT_FOUND).entity("Students Not Found").build();
        }
        return Response.ok(studentList).build();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getStudents(@QueryParam("name") String name, @QueryParam("email") String email){
        studentDao = new StudentDao();
        Student student = studentDao.getUserByEmailName(name,email);
        if (student == null){
            return Response.status(Response.Status.NOT_FOUND).entity("User Not Found").build();
        }
        return Response.ok(student).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addStudent(@FormParam("studentName")String studentName,@FormParam("studentEmail") String studentEmail){
      if (studentName == null || studentEmail == null){
          return Response.status(Response.Status.BAD_REQUEST).entity("Name And Email Not be Empty").build();
      }
      studentDao = new StudentDao();
      boolean result = studentDao.addUser(studentName,studentEmail);
      if (!result){
          return Response.status(Response.Status.NOT_FOUND).entity("Cannot Add User").build();
      }
      return Response.ok("User Added Success").build();
    }

    @GET
    @Path("/header")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response headerParam(@HeaderParam("Authorization") String token,@HeaderParam("User-Agent")String agent,@HeaderParam("Accept")String accept){
        return Response.ok().entity(agent+"  "+accept+" Token : "+token).build();
    }

    @GET
    @Path("/addCookie")
    @Produces(MediaType.APPLICATION_JSON)
    public Response addCookie(){
        NewCookie newCookie = new NewCookie("userToken", "abcdef123456", "/", null, "HttpOnly", 3600, false);
        return Response.ok().entity("Cookie Added Successfully").cookie(newCookie).build();
    }

    @GET
    @Path("/getCookie")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCookie(@CookieParam("userToken")String token){
        if (token == null){
            return Response.status(Response.Status.NOT_FOUND).entity("Token Not Found").build();
        }
        return Response.ok(token).build();
    }
}
