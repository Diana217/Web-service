// WebServiceClientServlet.java
package org.example;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WebServiceClientServlet")
public class WebServiceClientServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Your SOAP client logic here
        try {
            // Create an instance of your generated WebService client
            com.baeldung.soap.ws.client.generated.WebService stub = new com.baeldung.soap.ws.client.generated.WebService();

            // Call the SOAP method (e.g., CreateDB)
            String result = stub.getWebServiceSoap().createDB("DatabaseName");

            // Process the result (you can store it in request attributes for JSP to display)
            request.setAttribute("result", result);

            // Forward to a JSP page to display the result
            request.getRequestDispatcher("/result.jsp").forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        }
    }
}
