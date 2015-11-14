package controller;

/*
* This servlet handles the session where the user edits or deletes a post
*/



import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import authentication.PostAuthentication;
import model.Account;
import model.Post;
/**
 * Servlet to handle edit/delete post requests
 */
@WebServlet("/EditPostController")
@MultipartConfig(maxFileSize = 16177215)
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
		PostAuthentication validate = new PostAuthentication();
		boolean executed = false; //flag for if the changes were executed or not
		try
		{
			String action = request.getParameter("edit");
			System.out.println("action:"+action);
			DatabaseCon db = new DatabaseCon();
			if(action != null)
			{
			int post_id = Integer.parseInt(request.getParameter("post_id"));
			String username = request.getParameter("username");
			
			Account currAccount = (Account)request.getSession().getAttribute("account");
			if(currAccount.getUsername().equals(request.getAttribute("username"))|| currAccount.isAdmin())
			{
				if(dbConnection.authenticatePost(post_id, username)) //checks if the right username is with the right post
				{
				
					
						if(action.equals("delete"))
						{
							
							int total = db.numPosts();
							int page_num = 1;
							System.out.println("I deleted!");
							dbConnection.modifyPost(post_id, "", null, false);
							ArrayList<Post> posts = db.getGlobalPosts(page_num);
							
							request.getSession().setAttribute("posts", posts);
							request.getSession().setAttribute("total", total);
							
							executed = true;
						}
						else if(action.equals("edit"))
						{
							System.out.println("Edits");
							Post p = db.getPost(post_id);
							request.getSession().setAttribute("editid", post_id);
							request.getSession().setAttribute("editpost",p);
						}
						else if(action.equals("editpost"))
						{
							String message = "";
							//String imagepath = "";
							if(request.getParameter("message") != null)
							{
								message = request.getParameter("message");
							}
							System.out.println("Message:" +message);
							int total = db.numPosts();
							int page_num = 1;
							message = validate.sanitizePost(message);
							System.out.println("I Edited!");
							Part filePart = request.getPart("attachment");
							System.out.println("I Edited!");
							InputStream inputStream = null;
							if(filePart != null)
							{
								inputStream = filePart.getInputStream();
							}
							int yo = (Integer)request.getSession().getAttribute("editid");
							System.out.println(yo);
							System.out.println("I Edited!");
							
						
							dbConnection.modifyPost(yo , message, inputStream, true);
							System.out.println("I Edited!");
							ArrayList<Post> posts = db.getGlobalPosts(page_num);
							
							request.getSession().setAttribute("posts", posts);
							request.getSession().setAttribute("total", total);
							
							executed = true;
						}
					
					
					
				}
			}
			}
			
		} catch(Exception e)
		{
			System.out.println(e);
		}
		if(!executed)
		{
			response.sendRedirect("EditPost.jsp");
		}
		else
			response.sendRedirect("GlobalPost.jsp");
	}

}
