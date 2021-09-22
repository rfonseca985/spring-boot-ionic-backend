

package cursomodelagemconceitual.services.excepitions;

public class FileExcepiton extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public FileExcepiton(String msg) {
		super(msg);
	}
	
	public FileExcepiton(String msg, Throwable cause) {
		super(msg, cause);
	}
}

