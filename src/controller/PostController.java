package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
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
 * Servlet implementation class PostController
 */
@WebServlet("/PostController")
public class PostController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PostController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			DatabaseCon db = new DatabaseCon();
			int total = db.numPosts();
			int page_num = 1;
			if(request.getParameter("page_num") != null)
			{
				page_num = Integer.parseInt(request.getParameter("page_num"));
			}
			ArrayList<Post> posts = db.getGlobalPosts(page_num);
			HashMap<String, Account> accounts = db.getUsers();
			request.getSession().setAttribute("posts", posts);
			request.getSession().setAttribute("accounts", accounts);
			request.getSession().setAttribute("total", total);
			
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		response.sendRedirect("GlobalPost.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		boolean noErrors = true;
		PostAuthentication validate = new PostAuthentication();
		try
		{
			String message = "";
			System.out.println(request.getParameter("message"));
			if(request.getParameter("message") != null)
			{
				message = request.getParameter("message");
				message = validate.sanitizePost(request.getParameter("message"));
			}
			else if(message.equals(""))
			{
				session.setAttribute("errors", "No message input.");
				noErrors = false;
			}
			/*
			else
			{				
				message = validate.sanitizePost(request.getParameter("message"));
				InputStream inputStream = null; // input stream of the upload file
			         
			        // obtains the upload file part in this multipart request
			    Part filePart = request.getPart("attachment");
			    if (filePart != null) {
			            // prints out some information for debugging
			            System.out.println(filePart.getName());
			            System.out.println(filePart.getSize());
			            System.out.println(filePart.getContentType());
			             
			            // obtains input stream of the upload file
			            inputStream = filePart.getInputStream();
			        }
	
				//imagepath = (String)request.getParameter("attachment");
			}*/
				Account account = (Account)session.getAttribute("account");
				DatabaseCon db = new DatabaseCon();
				//System.out.println("Attachment = "+filePart.getContentType());
				Post currPost = db.createPost(account.getUsername(), message);
			
		}
		catch(Exception e)
		{
			noErrors = false;
			System.out.println(e);
			session.setAttribute("errors", e.toString());
			
		}	
		if(session.getAttribute("postnum") == null)
		{
			session.setAttribute("postnum", 1);
		}
		if(noErrors)
		{
			session.setAttribute("success", "Post created successfully.");
		}
		response.sendRedirect("CreatePost.jsp");
	}
}