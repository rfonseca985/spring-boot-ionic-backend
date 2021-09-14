

package cursomodelagemconceitual.services.excepitions;

public class DataIntegrityExcepiton extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public DataIntegrityExcepiton(String msg) {
		super(msg);
	}
	
	public DataIntegrityExcepiton(String msg, Throwable cause) {
		super(msg, cause);
	}
}

