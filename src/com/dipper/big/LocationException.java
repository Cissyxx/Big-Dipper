package com.dipper.big;

public class LocationException extends Exception
{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public LocationException()
    {

    }

    public LocationException(String message)
    {
        super(message);
    }

    public LocationException(Throwable cause)
    {
        super(cause);
    }

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
