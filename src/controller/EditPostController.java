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

import authentication.PostAuthentication;
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
		DatabaseCon dbConnection = new DatabaseCon();
		HttpSession session = request.getSession();
		boolean executed = false; //flag for if the changes were executed or not
		try
		{
			if(request.getParameter("username") != null && request.getParameter("post_id") != null)
			{
				String username = (String) request.getParameter("username");
				Post post = null;
				System.out.println("aw");
				@SuppressWarnings("unchecked")
				ArrayList<Post> posts = (ArrayList<Post>)session.getAttribute("posts");
				int post_id = Integer.parseInt(request.getParameter("post_id"));
				Account currAccount = (Account)request.getSession().getAttribute("account");
				System.out.println("aw");
				for(Post i:posts)
				{
					if(i.getPostid() == post_id)
					{
						post = i;
						break;
					}
				}
				System.out.println(post.getUsername());
				if(post != null)
				{
					if(post.getUsername().equals(username))
					{
						if(currAccount.isAdmin() || currAccount.getUsername().equals(username))
						{
							if(dbConnection.authenticatePost(post_id, username)) //checks if the right username is with the right post
							{
								session.setAttribute("currEdit", post);
								executed = true;
							}
						}
					}
				}
			} 
		}catch(Exception e)
		{
			System.out.println(e);
		}
		if(executed)
		{
			response.sendRedirect("EditPost.jsp");
		}
		else
		{
			response.sendRedirect("GlobalPost.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		DatabaseCon dbConnection = new DatabaseCon();
		HttpSession session = request.getSession();
		PostAuthentication authenticate = new PostAuthentication();
		boolean executed = false; //flag for if the changes were executed or not
		try
		{
			Post currEdit;
			String message = "";
			if(session.getAttribute("currEdit") != null && session.getAttribute("message") != null)
			{
				message = (String)session.getAttribute("message");
				if(!message.equals(""))
				{
					currEdit = (Post)session.getAttribute("currEdit");
					message = authenticate.sanitizePost(message);
					Account currAccount = (Account)request.getSession().getAttribute("account");
					if(currAccount.isAdmin() || currAccount.getUsername().equals(currEdit.getUsername()))
					{
						dbConnection.modifyPost(currEdit.getPostid(), message, null, true);
						executed = true;
						session.setAttribute("success", "Post updated successfully");
					}
				}
				else
				{
					session.setAttribute("errors", "NO message input");
				}
			}
		} catch(Exception e)
		{
			System.out.println(e);
		}
		if(executed)
		{
			response.sendRedirect("GlobalPost.jsp");
		}
		else
		{
			response.sendRedirect("EditPost.jsp");
		}
	}
}
