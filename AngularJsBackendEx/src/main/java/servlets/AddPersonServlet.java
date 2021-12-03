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

@WebServlet("/addPerson")
public class AddPersonServlet extends HttpServlet {

    private static final long serialVersionUID = -7060758261496829905L;

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
        String name = jsonObject.get("name").toString();
        name = name.substring(1, name.length()-1);
        System.out.println("name is: " + name);

        String country = jsonObject.get("country").toString();
        country = country.substring(1, country.length()-1);
        System.out.println("country is: " + country);

        if ((name == null || name.equals("")) || (country == null || country.equals(""))) {
            System.out.println("name and country can't be empty");
        } else {
            Person p = new Person();
            p.setCountry(country);
            p.setName(name);

            MongoClient mongo = (MongoClient) request.getServletContext().getAttribute("MONGO_CLIENT");
            MongoDBPersonDAO personDAO = new MongoDBPersonDAO(mongo);
            personDAO.createPerson(p);
        }
    }

}
