package authentication;

public class PostAuthentication {
	
	
	public String sanitizePost(String input)
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
}