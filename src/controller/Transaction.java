package controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Item;

/**
 * Servlet implementation class Transaction
 */
@WebServlet("/Transaction")
public class Transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Transaction() {
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
		String item = request.getParameter("item_name");
		Item currItem = dbConnection.getItem(item);
		int item_number = Integer.parseInt(request.getParameter("item_number"));
		int amount = Integer.parseInt(request.getParameter("amount"));
		String currency_code = request.getParameter("currency_code");
		
		boolean correctItem = true;
		if(item_number != currItem.getId() || amount != currItem.getPrice() || !currency_code.equals("USD"))
		{
			correctItem = false;
		}
		
		if(correctItem)
		{
			request.getRequestDispatcher("https://sandbox.paypal.com/cgi-bin/webscr").forward(request, response);
			
		}
		else
		{
			response.sendRedirect("https://securedev.mybluemix.net/store");
		}
	}

}
