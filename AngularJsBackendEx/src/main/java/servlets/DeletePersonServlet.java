package servlets;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mongodb.MongoClient;
import dao.MongoDBPersonDAO;
import model.Person;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/deletePerson")
public class DeletePersonServlet extends HttpServlet {

    private static final long serialVersionUID = 6798036766148281767L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader reader = request.getReader();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        JsonParser parser = new JsonParser();
        JsonObject jsonObject = null;
        try {
            jsonObject = (JsonObject) parser.parse(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        String id = jsonObject.get("id").toString();
        id = id.substring(1, id.length() - 1);

        String name = jsonObject.get("name").toString();
        name = name.substring(1, name.length() - 1);

        String country = jsonObject.get("country").toString();
        country = country.substring(1, country.length()-1);

        if (id == null || "".equals(id)) {
            throw new ServletException("id missing for delete operation");
        }
        MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
        MongoDBPersonDAO personDAO = new MongoDBPersonDAO(mongo);

        Person p = new Person();
        p.setId(id);
        personDAO.deletePerson(p);
        System.out.println("Deleted successfully!");
    }

}