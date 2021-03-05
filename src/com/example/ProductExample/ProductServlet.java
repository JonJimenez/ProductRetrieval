package com.example.ProductExample;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.ProductExample.model.DBConnection;

/**
 * Servlet implementation class ProductServlet
 */

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        
        try {
                 PrintWriter out = response.getWriter();
                 
                 out.println("<html>"
                		 +"<head>"
                		 +"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/index.css\">"
                		 +"</head>"
                 		+ "<body>");
                 
                InputStream in = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
                Properties props = new Properties();
                props.load(in);

                out.println("<form action = \"list\" method = \"post\">"
                   		+ "<label for= \"productID\">Enter Product ID:</label><br>"
                   		+ "<input type= \"text\" id=\"productID\"name =\"productID\"><br>"
                   		+ "<input type = \"submit\" value=\"Search\">"
                   		+ "</form>");

                String productID = request.getParameter("productID");
                DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("userid"), props.getProperty("password"));
                
                PreparedStatement stmt = conn.getConnection().prepareStatement("SELECT * from productdetails.eproduct WHERE ID=?");
                stmt.setString(1, productID);
                ResultSet rst = stmt.executeQuery();

               if(productID!=null) {
            	   if(rst.next()==false) {
            		   out.println("<p>Error: Enter Valid Product ID</p>");
            	   }else {
            	   		out.println("<table><tr>"
                					+ "<th>Name</th>"
                					+ "<th>Price</th>"
                					+ "</tr>");
            	   		do {
                        	out.println("<tr><td>"+rst.getString("name") + "</td><td>" + rst.getString("price") + "</td></tr>");
            	   		}while (rst.next());

            	   		out.println("</table>");
            	   }
     
               }
                stmt.close();
                out.println("</body></html>");
                conn.closeConnection();
                
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
        } catch (SQLException e) {
                e.printStackTrace();
        }
}

/**
 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 */
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
}

}

