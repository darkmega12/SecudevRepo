package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;

/**
 * Servlet implementation class DeletePostController
 */
@WebServlet("/DeletePostController")
public class DeletePostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeletePostController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DatabaseCon dbConnection = new DatabaseCon();
		boolean executed = false; //flag for if the changes were executed or not
		try
		{
			boolean check = true;
			int post_id = Integer.parseInt(request.getParameter("post_id"));
			String username = request.getParameter("username");
			Account currAccount = (Account)request.getSession().getAttribute("account");
			if(currAccount.isAdmin() || currAccount.getUsername().equals(request.getAttribute("username")))
			{
				if(dbConnection.authenticatePost(post_id, username)) //checks if the right username is with the right post
				{
						dbConnection.modifyPost(post_id, "", "", false);
						executed = true;
				}
			}
		} catch(Exception e)
		{
			System.out.println(e);
		}
			response.sendRedirect("GlobalPost.jsp");
	}

}
