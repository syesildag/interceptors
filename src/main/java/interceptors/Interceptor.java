package interceptors;

public interface Interceptor
{
   Object intercept(InvocationContext ctx) throws Exception;
}
