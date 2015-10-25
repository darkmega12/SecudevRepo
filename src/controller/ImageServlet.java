package controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class ImageServlet
 */
@WebServlet("/images")
public class ImageServlet extends HttpServlet {

    // content=blob, name=varchar(255) UNIQUE.
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
try{
			try {
				Class.forName("com.mysql.jdbc.Driver");
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		String url = "jdbc:mysql://localhost:3306/secudevs18";
		
		Connection dbConnection = DriverManager.getConnection(url, "root", "p@ssword");
		String postQuery = "SELECT attachment,imagename FROM posts WHERE post_id = ?";
		PreparedStatement pstmt = dbConnection.prepareStatement(postQuery);
		
        pstmt.setInt(1, Integer.parseInt(request.getParameter("id")));

            ResultSet resultSet = pstmt.executeQuery();
                if (resultSet.next()) {
                    byte[] content = resultSet.getBytes("attachment");
                    response.setContentType(resultSet.getString("imagename"));
                    response.setContentLength(content.length);
                    response.getOutputStream().write(content);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
                }
            
        }
    catch (SQLException e) {
            throw new ServletException("Something failed at SQL/DB level.", e);
        }
    }

}
