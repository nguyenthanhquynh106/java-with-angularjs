package servlets;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.mongodb.MongoClient;
import dao.MongoDBPersonDAO;
import model.Person;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/getAllPersons")
public class GetAllPersonsServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Inside GetAllPersonsServlet");
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBPersonDAO personDAO = new MongoDBPersonDAO(mongo);
        List<Person> persons = personDAO.readAllPerson();

        JsonArray jsonArray = new Gson().toJsonTree(persons).getAsJsonArray();
        System.out.println("jsonArray size is: " + jsonArray.toString());

        response.setContentType("text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(jsonArray);
        System.out.println("Got successfully!");
    }
}
