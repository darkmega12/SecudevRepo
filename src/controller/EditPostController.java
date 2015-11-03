package controller;

/*
* This servlet handles the session where the user edits or deletes a post
*/



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
		HttpSession session = request.getSession();
		boolean executed = false; //flag for if the changes were executed or not
		try
		{
			int page_num = (Integer)session.getAttribute("page_num");
			@SuppressWarnings("unchecked")
			ArrayList<Post> posts = (ArrayList<Post>)session.getAttribute("posts");
			HashMap<String, Account> accounts = dbConnection.getUsers();
			int post_id = Integer.parseInt(request.getParameter("post_id"));
			Account currAccount = (Account)request.getSession().getAttribute("account");
			if(currAccount.isAdmin() || currAccount.getUsername().equals(request.getAttribute("username")))
			{
				if(dbConnection.authenticatePost(post_id, (String)request.getAttribute("username"))) //checks if the right username is with the right post
				{
				}
			}
		} catch(Exception e)
		{
			System.out.println(e);
		}
		response.sendRedirect("GlobalPost.jsp");
	}
}
