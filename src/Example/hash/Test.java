package Example.hash;

public class Test {

	public static void main(String[] args) {
		String salt = BCrypt.gensalt(10);
		String text_pass = "1234567890";
		String hash_pass = BCrypt.hashpw(text_pass, salt);
		boolean b = BCrypt.checkpw(text_pass, hash_pass);
		
		System.out.println(text_pass);
		System.out.println(hash_pass);
		System.out.println(b);
	}
}
