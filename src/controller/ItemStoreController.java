package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import authentication.ItemAuthentication;
import model.Item;

/**
 * Servlet implementation class ItemStoreController
 */
@WebServlet("/ItemStoreController")
public class ItemStoreController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemStoreController() {
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
			ItemAuthentication authenticate = new ItemAuthentication();
			HttpSession session = request.getSession();
			DatabaseCon db = new DatabaseCon();
			String itemName = request.getParameter("itemName");
			itemName = authenticate.sanitizeItem(itemName);
			Item currItem = db.getItem(itemName);
			session.setAttribute("currItem", currItem);
			response.sendRedirect("https://securedev.mybluemix.net/store");
//			response.sendRedirect("localhost:8088/TestSecuProj/store");
		}
		catch(Exception e)
		{
			System.out.println(e);
			response.sendRedirect("https://securedev.mybluemix.net/store");		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
