package authentication;

import java.util.ArrayList;

public class SearchAuthentication {
	private ArrayList<String> searchCriteria = new ArrayList<String>();
	private ArrayList<String> setOperators = new ArrayList<String>();
	
	public SearchAuthentication()
	{
		init();
	}
	
	private void init()
	{
		searchCriteria.add("username");
		searchCriteria.add("message");
		searchCriteria.add("before date");
		searchCriteria.add("after date");
		searchCriteria.add("during date");
		
		
		setOperators.add("N/A");
		setOperators.add("AND");
		setOperators.add("OR");
	}
	
	public boolean validateCriteria(String criteria)
	{
		return searchCriteria.contains(criteria);
	}
	
	public boolean validateOperator(String operator)
	{
		return setOperators.contains(operator);
	}
	
	//quickfix to an escape-on-input approach of sanitizing
	public String sanitize(String input)
	{
		String temp = input;
		temp = temp.replace("\'", "\\\'");
		temp = temp.replace("=", "\\=");
		temp = temp.replace("*", "\\*");
		temp = temp.replace("+", "\\+");
		temp = temp.replace("&", "\\&");
		temp = temp.replace("%", "\\%");
		System.out.println("searched: "+temp);
		return temp;
	}
	
	public String testSqlScript(String[] criteria, String[] value, String[] setOperator)
	{
		String query = "Select * from posts where ";
		for(int i=0; i<criteria.length; i++)
		{
			if(i > 0)
			{
				if(setOperator[i].equals("AND"))
				{
					query += "exists (Select * from posts where ";
				}
				else if(setOperator[i].equals("OR"))
				{
					query += "";
				}
				else
				{
					continue;
				}
			}
			String queryCriteria = criteria[i];
			if(query.equals("before date"))
			{
				queryCriteria = "datemodified <= ? ";
			}
			else if (query.equals("after date"))
			{
				queryCriteria = "datemodified >= ? ";
			}
			else if (query.equals("during date"))
			{
				queryCriteria = "datemodified = ? ";
			}
			else if(query.equals("message"))
			{
				queryCriteria = "message like \"%?%\"";
			}
			else
			{
				queryCriteria = criteria[i] + " = ? ";
			}
			query += queryCriteria;
			
			if(i>0 && setOperator[i].equals("AND"))
			{
				query += ")";
			}
		}
		return query;
	}
}
