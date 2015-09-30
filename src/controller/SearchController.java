package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import authentication.SearchAuthentication;
import model.Account;
import model.Post;

/**
 * Servlet implementation class SearchController
 */
@WebServlet("/SearchController")
public class SearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SearchAuthentication authenticate = new SearchAuthentication();
		boolean success = true;
		ArrayList<Post> searchedPosts = new ArrayList<Post>();
		HashMap<String, Account> accounts = new HashMap<String, Account>();
		int page_num = 0;
		try
		{
			if(request.getSession().getAttribute("page_num") != null)
			{
				request.getSession().setAttribute("page_num", 1);
			}
			DatabaseCon db = new DatabaseCon();
			if(request.getParameter("searchType") != null && request.getParameter("searchType").equals("basic"))
			{
				String value;
				if(request.getParameter("search") != null)
				{
					value = request.getParameter("search");
					value = authenticate.sanitize(value);
					
					accounts = db.getUsers();
					
					searchedPosts = db.basicSearch(value);
					if(searchedPosts.size() > 10)
					{
						searchedPosts = db.getSearchedPosts(1);
					}
				}
			}
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		request.getSession().setAttribute("page_num", page_num);
		request.getSession().setAttribute("posts", searchedPosts);
		request.getSession().setAttribute("accounts", accounts);
		if(success)
		{
			response.sendRedirect("SearchPost.jsp");
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
