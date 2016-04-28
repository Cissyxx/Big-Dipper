package com.dipper.big;

/**
 * customed exception to handle errors caused by invalid inputs
 */
public class LocationException extends Exception
{

    /**
     * default serial version
     */
    private static final long serialVersionUID = 1L;

    /**
     * default constructor
     */
    public LocationException()
    {

    }

    /**
     * constructor with error message
     * @param message
     */
    public LocationException(String message)
    {
        super(message);
    }

    /**
     * constructor with throwable
     * @param cause
     */
    public LocationException(Throwable cause)
    {
        super(cause);
    }

    /**
     * constructor with error message and throwable
     * @param message
     * @param cause
     */
    public LocationException(String message, Throwable cause)
    {
        super(message, cause);
    }

    @Override
    public String getMessage()
    {
        return super.getMessage();
    }

    @Override
    public Throwable getCause()
    {
        return super.getCause();
    }

    @Override
    public StackTraceElement[] getStackTrace()
    {
        return super.getStackTrace();
    }

}
