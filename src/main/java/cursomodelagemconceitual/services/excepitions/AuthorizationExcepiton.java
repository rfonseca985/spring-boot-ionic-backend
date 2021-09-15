

package cursomodelagemconceitual.services.excepitions;

public class AuthorizationExcepiton extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AuthorizationExcepiton(String msg) {
		super(msg);
	}
	
	public AuthorizationExcepiton(String msg, Throwable cause) {
		super(msg, cause);
	}
}

