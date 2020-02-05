package interceptors;

/**
 * @author SYESILDAG
 */
@SuppressWarnings("InterfaceNeverImplemented")
@FunctionalInterface
public interface Interceptor {
   @SuppressWarnings("ProhibitedExceptionDeclared")
   Object intercept(@SuppressWarnings("MethodParameterOfConcreteClass") InvocationContext ctx) throws Exception;
}
