package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import authentication.AccountAuthentication;
import model.Account;

/**
 * Servlet implementation class RegisterController
 */
@WebServlet("/RegisterController")
public class RegisterController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.sendRedirect("https://securedev.mybluemix.net/register");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private ArrayList<String> errorList = new ArrayList<String>();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ArrayList<String> errors = new ArrayList<String>();			
		AccountAuthentication accValidate = new AccountAuthentication();
		HttpSession session = request.getSession();
		
		session.setAttribute("registerErrors", null);
		try
		{
			String date[] = request.getParameter("birthdate").split("/");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String gender = request.getParameter("sex");
			String salutation = "";
			LocalDate birthdate = null;
			try
			{
				birthdate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]));
			}
			catch(Exception e)
			{
				errors.add("Invalid Date");
			}
			boolean isAdmin;
			
			//following block of code is the validation if it was an admin registration and if the registered user is an admin
			if(request.getParameter("accesscontrollolplsdonthack") == null) 
				isAdmin = false;
			else
			{
				if((boolean)session.getAttribute("isAdmin"))
				{
					System.out.println(request.getParameter("accesscontrollolplsdonthack"));
					if(request.getParameter("accesscontrollolplsdonthack").equals("Adminplsdonthack"))			
						isAdmin = true;
					else
						isAdmin = false;
				} else
					isAdmin = false;
			}
			String aboutme = accValidate.sanitize(request.getParameter("aboutme"));
			if(gender.equals("Male"))
			{
				salutation = request.getParameter("salutationM");
			}
			else if(gender.equals("Female"))
			{
				salutation = request.getParameter("salutationF");
			}

			if(errors.size() == 0)
			{
				if(!accValidate.validateName(firstname) || !accValidate.validateName(lastname))
				{
					errors.add("Invalid name");
				}
				if(!accValidate.validateUsername(username))
				{
					errors.add("Invalid username");
				}
				if(!accValidate.validatePassword(password))
				{
					errors.add("Invalid password");
				}
				if(!accValidate.validateGender(gender, salutation))
				{
					errors.add("Invalid gender and/or salutation");
				}
				if(!accValidate.validateDate(birthdate))
				{
					errors.add("Invalid birthday");
				}
			}
			if(errors.size() == 0)
			{
				DatabaseCon db = new DatabaseCon();
			
				Calendar cal = Calendar.getInstance();
				cal.set(birthdate.getYear(), birthdate.getMonthValue() - 1, birthdate.getDayOfMonth());
				Date bday = cal.getTime();
				Account currAccount = db.register(username, password, firstname, lastname, gender, salutation, bday, isAdmin, aboutme, 0, 0, 0);
			
				if(currAccount != null)
				{
					response.sendRedirect("index.jsp");
				}  else
				{
					errors.add("Account not created. Possible duplicate user");
					session.setAttribute("registerErrors", errors);
					response.sendRedirect("https://securedev.mybluemix.net/register");
				}
			
			
			} else {
				System.out.println("There's error");
				for(String error:errors)
					System.out.println(error);
				session.setAttribute("registerErrors", errors);
				
			}
		}
		catch(Exception e)
		{
			System.out.println("There's an error: "+e);
			session.setAttribute("registerErrors", errors);
			response.sendRedirect("https://securedev.mybluemix.net/register");

		}
	}
}
