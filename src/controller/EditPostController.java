package controller;

/*
* This servlet handles the session where the user edits or deletes a post
*/



import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Account;
import model.Post;
/**
 * Servlet to handle edit/delete post requests
 */
@WebServlet("/EditPostController")
public class EditPostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditPostController() {
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
			boolean check = false;
			int post_id = Integer.parseInt(request.getParameter("post_id"));
			String username = request.getParameter("username");
			Account currAccount = (Account)request.getSession().getAttribute("account");
			if(currAccount.isAdmin() || currAccount.getUsername().equals(request.getParameter("username")))
			{
				username = currAccount.getUsername();
				if(dbConnection.authenticatePost(post_id, username)) //checks if the right username is with the right post
				{
					response.sendRedirect("EditPost.jsp");
					check = true;
				}
			}
		} catch(Exception e)
		{
			System.out.println(e);
		}
		response.sendRedirect("GlobalPost.jsp");
	}
}
