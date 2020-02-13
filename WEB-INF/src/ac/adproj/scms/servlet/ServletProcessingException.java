package ac.adproj.scms.servlet;

public class ServletProcessingException extends RuntimeException
{
	public ServletProcessingException()
	{
		super();
	}

	public ServletProcessingException(String message)
	{
		super(message);
	}

	public ServletProcessingException(Throwable cause)
	{
		super(cause);
	}
}