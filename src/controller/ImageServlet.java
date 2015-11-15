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
		DatabaseCon dbConnection = new DatabaseCon();
    	try{
		dbConnection.open();
		String postQuery = "SELECT attachment,imagename FROM posts WHERE post_id = ?";
		PreparedStatement pstmt = dbConnection.getConnection().prepareStatement(postQuery);
		
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
    	finally
    	{
    		dbConnection.close();
    	}
    }

}
