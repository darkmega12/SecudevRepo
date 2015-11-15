package authentication;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ItemAuthentication {
	
	private Pattern itemLinkPattern;
	
	public ItemAuthentication()
	{
		itemLinkPattern = Pattern.compile("@[\\w\\s\\d]*@");
	}
	public String sanitizeItem(String input)
	{
		String sanitized = input;
		sanitized = sanitized.replace("\'", "\\\'");
		sanitized = sanitized.replace("=", "\\=");
		sanitized = sanitized.replace("*", "\\*");
		sanitized = sanitized.replace("+", "\\+");
		sanitized = sanitized.replace("&", "&amp;");
		sanitized = sanitized.replace("%", "\\%");
		sanitized = sanitized.replace("<", "&lt;");
		sanitized = sanitized.replace(">", "&gt;");
		
		sanitized = sanitized.replace("[b]", "<b>");
		sanitized = sanitized.replace("[/b]", "</b>");
		sanitized = sanitized.replace("[i]", "<i>");
		sanitized = sanitized.replace("[/i]", "</i>");
		
		System.out.println("result of sanitazing "+sanitized);
		return sanitized;
	}
	
	public String getLinkItem(String input)
	{
		String item = "";
		String edited = sanitizeItem(input);
		Matcher match = itemLinkPattern.matcher(edited);
		if(match.find())
		{
			item = match.group(0);
		}
		item.replace("@", "");
		return item;
	}
	
	public String provideItemLink(String input, String item)
	{
		String edited = sanitizeItem(input);
		String itemUrl;
		//If deployed
		itemUrl = "<a href=\"https://securedev.mybluemix.net/store/item?itemName="+item+"\">"+item+"</a>";
		//if local
		itemUrl = "<a href=\"localhost:8088/TestSecuProj/store/item?itemName="+item+"\">"+item+"</a>";
		edited = Pattern.compile("@[\\w\\s\\d]*@").matcher(edited).replaceAll(itemUrl);
		return edited;
	}
	
}
