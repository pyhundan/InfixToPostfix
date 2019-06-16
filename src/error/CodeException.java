package error;

public class CodeException extends Exception {
    public String message;
    public CodeException(String s)
    {
        super();
        message=s;
    }
}