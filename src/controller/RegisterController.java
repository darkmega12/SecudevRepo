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
		response.sendRedirect("Register.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	private ArrayList<String> errorList = new ArrayList<String>();
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try
		{
			String date[] = request.getParameter("birthdate").split("/");
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String gender = request.getParameter("sex");
			String salutation;
			LocalDate birthdate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]));
			boolean isAdmin;
			
			HttpSession session = request.getSession();
			
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
			
			
			String aboutme = request.getParameter("aboutme");
			System.out.println("------------------------"+aboutme);
			if(gender.equals("Male"))
			{
				salutation = request.getParameter("salutationM");
			}
			else
			{
				salutation = request.getParameter("salutationF");
			}
			
			//regex used to validate the name and username
			Pattern namePattern = Pattern.compile("^[a-zA-Z ]{1,50}$");
			Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9_]{1,50}$");
			String[] genderArray = {"Male", "Female"};
			String[] salutationMArray = {"Mr", "Sir", "Senior", "Count"};
			String[] salutationFArray = {"Miss", "Ms", "Mrs", "Madame", "Majesty", "Seniora"};
			
			//birthday validation
			LocalDate ld = LocalDate.now();
			ld = ld.minusYears(18).minusDays(-1);
			System.out.println("18 years before date: " + ld);
			
			boolean isError = false;
			
			errorList = new ArrayList<String>();
			
			//validates the user input; makes isError true when one falls into the error criteria
			if (!usernamePattern.matcher(username).matches())
			{
				System.out.println("Username error: "+ username);
				errorList.add("Invalid Username");
				isError = true;
			} else if (!namePattern.matcher(firstname).matches()){
				errorList.add("Invalid Firstname");
				System.out.println("First name error");
				isError = true;
			} else if (!namePattern.matcher(lastname).matches()){
				errorList.add("Invalid Lastname");
				System.out.println("Last name error");
				isError = true;
			} else if(!Arrays.asList(genderArray).contains(gender)){
				errorList.add("Gender Error");
				System.out.println("gender error");
				isError = true;
			} else if (gender.equals("Male") && !Arrays.asList(salutationMArray).contains(salutation)){
				errorList.add("Invalid Male Salutation");
				System.out.println("male salutation error");
				isError = true;
			} else if (gender.equals("Female") && !Arrays.asList(salutationFArray).contains(salutation)){
				errorList.add("Invalid Female Salutation");
				System.out.println("female salutation error");
				isError = true;
			} else if (!birthdate.isBefore(ld)){
				errorList.add("You're too young for this.");
				System.out.println("birthday");
				isError = true;
			} else if(password.length()==0 || password.length()>24){
				errorList.add("Invalid Password");
				System.out.println("passwordlength");
				isError = true;
			}
			
			if(!isError){
				DatabaseCon db = new DatabaseCon();
			
				Calendar cal = Calendar.getInstance();
				cal.set(birthdate.getYear(), birthdate.getMonthValue() - 1, birthdate.getDayOfMonth());
				Date bday = cal.getTime();
				
				System.out.println(bday);
				Account currAccount = db.register(username, password, firstname, lastname, gender, salutation, bday, isAdmin, aboutme);
			
				if(currAccount != null)
				{
					response.sendRedirect("index.jsp");
				}  else
				{
					response.sendRedirect("Register.jsp");
					if(errorList.size() > 0)
					{
						response.setContentType("text/html");
					    PrintWriter out = response.getWriter();
					    
					    out.println("<script> alert(\"ERROR ENCOUNTERED \\n\"");
					    for(String error:errorList)
					    	out.println(error+"\\n");
					    out.println("</script>");
					}
				}
			
			
			} else {
				System.out.println("There's error");
				response.sendRedirect("Register.jsp");
				if(errorList.size() > 0)
				{
					response.setContentType("text/html");
				    PrintWriter out = response.getWriter();
				    
				    out.println("<script> alert(\"ERROR ENCOUNTERED \\n\"");
				    for(String error:errorList)
				    	out.println(error+"\\n");
				    out.println("</script>");
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("There's an error: "+e);
			response.sendRedirect("Register.jsp");
			//supposedly, this would print out the errors as an alert but it doesn't work
			if(errorList.size() > 0)
			{
				response.setContentType("text/html");
			    PrintWriter out = response.getWriter();
			    
			    out.println("<script> alert(\"ERROR ENCOUNTERED \\n\"");
			    for(String error:errorList)
			    	out.println(error+"\\n");
			    out.println("</script>");
			}
		}
	}
}
