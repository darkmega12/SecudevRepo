package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;
import model.Account;
import model.Post;
/**
 * Servlet implementation class EditProfileController
 */
@WebServlet("/EditProfileController")
public class EditProfileController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfileController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		String username =  request.getParameter("username");
		if(username == null)
		{
			response.sendRedirect("index.jsp");
		}
		else
		{
			DatabaseCon db = new DatabaseCon("root","p@ssword");
			Account viewAccount = db.getUser(username);
			ArrayList<Post> userPosts = db.getUserPosts(username);
			request.getSession().setAttribute("viewAccount", viewAccount);
			request.getSession().setAttribute("userPosts", userPosts);
			response.sendRedirect("ViewProfile.jsp");
		}
	}
	
	private ArrayList<String> errorList = new ArrayList<String>();
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		errorList = new ArrayList<String>();
		
		Account currAccount = (Account)session.getAttribute("account");
		String date[] = request.getParameter("birthdate").split("/");
		String newPassword = request.getParameter("password");
		String newFirstname = request.getParameter("firstname");
		String newLastname = request.getParameter("lastname");
		String newGender = request.getParameter("sex");
		String newSalutation;
		String newAboutme = request.getParameter("aboutme");
		LocalDate newBirthdate = LocalDate.of(2000, 2, 2);
		if(newGender.equals("Male"))
		{
			newSalutation = request.getParameter("salutationM");
		}
		else
		{
			newSalutation = request.getParameter("salutationF");
		}
		boolean newIsAdmin = false;
		
		System.out.println(newPassword+"\n"+newFirstname+"\n"+newLastname+"\n"+newGender+"\n"+newSalutation+"\n"+newAboutme);
		
		if(request.getParameter("accesscontrollolplsdonthack") != null)
			if(request.getParameter("accesscontrollolplsdonthack").equals("Adminplsdonthack") && (boolean)session.getAttribute("isAdmin"))
				newIsAdmin = true;
		try
		{
			newBirthdate = LocalDate.of(Integer.parseInt(date[2]), Integer.parseInt(date[0]), Integer.parseInt(date[1]));
			System.out.println(newBirthdate);
		}
		catch(Exception e)
		{ 
			System.out.println("Invalid date. Error log: "+e);
			errorList.add("Invalid Date");
			printError(response);
			response.sendRedirect("EditProfile.jsp");
		}
		try
		{
			if(!validationError(newFirstname, newLastname, newPassword, newGender, newSalutation, newBirthdate))
			{
				DatabaseCon db = new DatabaseCon("root", "p@ssword");
				Calendar cal = Calendar.getInstance();
				cal.set(newBirthdate.getYear(), newBirthdate.getMonthValue() - 1, newBirthdate.getDayOfMonth());
				Date bday = cal.getTime();
				Account curr = db.updateAccount(currAccount.getUsername(), newPassword, newFirstname, newLastname, newGender, newSalutation, bday, newIsAdmin, newAboutme);
				session.setAttribute("account", curr);
				session.setAttribute("isAdmin", curr.isAdmin());
			}
			else
			{
				errorList.add("You tampered with data!");
				printError(response);
				response.sendRedirect("EditProfile.jsp");
			}
		}
		catch(Exception e)
		{
			System.out.println("Error Log: "+e);
			printError(response);
			response.sendRedirect("EditProfile.jsp");
		}
		response.sendRedirect("HomePage.jsp");
	}
	
	private void printError(HttpServletResponse response) throws IOException
	{
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
	
	private boolean validationError(String firstname, String lastname, String password, String gender, String salutation, LocalDate birthdate)
	{
		boolean isError = false;
		Pattern namePattern = Pattern.compile("^[a-zA-Z ]{1,50}$");
		Pattern usernamePattern = Pattern.compile("^[a-zA-Z0-9_]{1,50}$");
		String[] genderArray = {"Male", "Female"};
		String[] salutationMArray = {"Mr", "Sir", "Senior", "Count"};
		String[] salutationFArray = {"Miss", "Ms", "Mrs", "Madame", "Majesty", "Seniora"};
		
		LocalDate datetoday = LocalDate.now();
		datetoday = datetoday.minusYears(18).minusDays(-1);
		System.out.println("18 years before date: " + datetoday);
		
		try
		{
			//validates the user input; makes isError true when one falls into the error criteria
			if (!namePattern.matcher(firstname).matches()){
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
			} else if (!birthdate.isBefore(datetoday)){
				errorList.add("You're too young for this.");
				System.out.println("birthday");
				isError = true;
			} else if(password.length()==0 || password.length()>24){
				errorList.add("Invalid Password");
				System.out.println("passwordlength");
				isError = true;
			}
			return isError;
		}catch (Exception e)
		{
			System.out.println(e);
			return true;
		}
	}

}
