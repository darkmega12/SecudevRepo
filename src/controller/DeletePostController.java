package controller;

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
		HttpSession session = request.getSession();
		boolean executed = false; //flag for if the changes were executed or not
		try
		{
			int page_num = (Integer)session.getAttribute("page_num");
			String username = (String) request.getParameter("");
			@SuppressWarnings("unchecked")
			ArrayList<Post> posts = (ArrayList<Post>)session.getAttribute("posts");
			int post_id = Integer.parseInt(request.getParameter("post_id"));
			Account currAccount = (Account)request.getSession().getAttribute("account");
			if(posts.size() > post_id%10)
			{
				if(posts.get(post_id%10).getUsername().equals(username))
				{
					if(currAccount.isAdmin() || currAccount.getUsername().equals(request.getAttribute("username")))
					{
						if(dbConnection.authenticatePost(post_id, (String)request.getAttribute("username"))) //checks if the right username is with the right post
						{
							dbConnection.modifyPost(post_id, "",null, false);
							executed = true;
						}
					}
				}
			}
		} catch(Exception e)
		{
			System.out.println(e);
		}
	}

}
