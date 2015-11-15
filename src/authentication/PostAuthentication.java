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
		
		sanitized = sanitized.replace("<script>", "&lt;script&gt;");
		sanitized = sanitized.replace("</script>", "&lt;/script&gt;");
		sanitized = sanitized.replace("onclick=", "onclick&#61;");
		sanitized = sanitized.replace("onerror=", "onerror&#61;");
		
		System.out.println("result of sanitazing "+sanitized);
		return sanitized;
	}
}