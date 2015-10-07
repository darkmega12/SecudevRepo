package authentication;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.regex.Pattern;

//Used to validate inputs made through registration and edit profile
public class AccountAuthentication {
	
	Pattern namePattern;
	Pattern usernamePattern;
	Pattern passwordPattern;
	Pattern datePattern;
	String[] genderArray = {"Male", "Female"};
	HashMap<String, String[]> genderSalutation;
	String[] salutationMArray = {"Mr", "Sir", "Senior", "Count"};
	String[] salutationFArray = {"Miss", "Ms", "Mrs", "Madame", "Majesty", "Seniora"};
	
	
	public AccountAuthentication()
	{
		namePattern = Pattern.compile("^[a-zA-Z ]{1,50}$");
		usernamePattern = Pattern.compile("^[a-zA-Z0-9_]{1,50}$");
		datePattern = Pattern.compile("^((0?[13578]|10|12)(-|\\/)(([1-9])|(0[1-9])|([12])([0-9]?)|(3[01]?))(-|\\/)((19)([2-9])(\\d{1})|(20)([01])(\\d{1})|([8901])(\\d{1}))|(0?[2469]|11)(-|\\/)(([1-9])|(0[1-9])|([12])([0-9]?)|(3[0]?))(-|\\/)((19)([2-9])(\\d{1})|(20)([01])(\\d{1})|([8901])(\\d{1})))$");
		passwordPattern = Pattern.compile("^[a-zA-Z]\\w{1,24}$");
		
		genderSalutation = new HashMap<String, String[]>();
		genderSalutation.put("Male", salutationMArray);
		genderSalutation.put("Female", salutationFArray);
	}
	
	public boolean validateName(String input)
	{
		if(input == null)
			return false;
		else
			return namePattern.matcher(input).matches();
	}
	
	public boolean validateUsername(String input)
	{
		if(input == null)
			return false;
		else
			return usernamePattern.matcher(input).matches();
	}
	
	public boolean validatePassword(String input)
	{
		if(input == null)
			return false;
		else
		return passwordPattern.matcher(input).matches();
	}
	
	public boolean validateGender(String gender, String salutation)
	{
		if(gender == null || salutation == null || salutation.length() ==0)
			return false;
		else if(Arrays.asList(genderArray).contains(gender))
		{
			return Arrays.asList(genderSalutation.get(gender)).contains(salutation);
		}
		else
		{
			return false;
		}
	}
	
	public boolean validateDate(LocalDate input)
	{
		LocalDate ld = LocalDate.now();
		ld = ld.minusYears(18).minusDays(-1);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		String inputString = formatter.format(input);
		return datePattern.matcher(inputString).matches() && input.isBefore(ld);
	}
	
	public String sanitize(String input)
	{
		String temp = input;
		if(temp == null)
		{
			temp = "";
		}
		else
		{
			temp = temp.replace("\'", "\\\'");
			temp = temp.replace("=", "\\=");
			temp = temp.replace("*", "\\*");
			temp = temp.replace("+", "\\+");
			temp = temp.replace("&", "&amp;");
			temp = temp.replace("%", "\\%");
			temp = temp.replace("<", "&lt;");
			temp = temp.replace(">", "&gt;");
			System.out.println("result of sanitazing "+temp);
		}
		return temp;
	}
}