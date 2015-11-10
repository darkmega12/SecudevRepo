package controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

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
import model.Item;
import model.Post;



/**
 * Servlet implementation class ItemController
 */
@WebServlet("/ItemController")
@MultipartConfig(maxFileSize = 16177215)
public class ItemController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ItemController() {
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

			ArrayList<Item> items = db.getItems();
			HashMap<String, Account> accounts = db.getUsers();
			request.getSession().setAttribute("items", items);
			System.out.println("HELOOOOOOOOOOOOO!");
			request.getSession().setAttribute("accounts", accounts);
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		response.sendRedirect("Store.jsp");
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
			String action = request.getParameter("itemsel");
			System.out.println("action:"+action);
			DatabaseCon db = new DatabaseCon();
			if(action != null)
			{
			Account currAccount = (Account)request.getSession().getAttribute("account");
			if(currAccount.isAdmin())
			{
					
						if(action.equals("Add"))
						{
							
							System.out.println("Adding");
							  InputStream inputStream = null; // input stream of the upload file
							  String name = request.getParameter("itemname");
							  String desc = request.getParameter("itemdesc");
							  float price = Float.parseFloat(request.getParameter("itemprice"));
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
							Account account = (Account)session.getAttribute("account");
							System.out.println("Attachment = "+filePart.getContentType());
							
							Item k = new Item(name,desc,filePart.getContentType(),price);
							k.setImage(inputStream);
							db.createItem(k);
							
							ArrayList<Item> it = db.getItems();
							request.getSession().setAttribute("items", it);
							
							executed = true;
						}
						else if(action.equals("Edit"))
						{
							System.out.println("Edits");
						}
						else if(action.equals("Remove"))
						{

						}
			}
			}
			
		} catch(Exception e)
		{
			System.out.println(e);
		}
		if(!executed)
		{
			response.sendRedirect("Store.jsp");
		}
		else
			response.sendRedirect("Store.jsp");
	}
}
