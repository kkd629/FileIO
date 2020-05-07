package javastory.club.stage3.step4.util;

public class NoSuchMemberException extends RuntimeException {
	//
	private static final long serialVersionUID = 5867172506387382920L;

	public NoSuchMemberException(String message) {
		super(message); 
	}
}