package interceptors;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author SYESILDAG
 *
 */
public class InterceptorInvocationHandler implements InvocationHandler
{
   Object proxied;
   
   public InterceptorInvocationHandler(Object proxied)
   {
      this.proxied = proxied;
   }
   
   /* (non-Javadoc)
    * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
    */
   @Override
   public Object invoke(Object proxy, Method method, Object[] args)
      throws Throwable
   {
      Interceptors declaredAnnotation = method.getAnnotation(Interceptors.class);
      if(declaredAnnotation == null)
         return method.invoke(this.proxied, args);
      
      Object result = null;
      
      int index = 0;
      
      Class<? extends Interceptor>[] classInterceptors = declaredAnnotation.value();
      for(Class<? extends Interceptor> classInterceptor : classInterceptors)
         result = classInterceptor.newInstance().intercept(new InvocationContext(index++, proxy, this.proxied, method, args));
      
      return result;
   }
   
   @SuppressWarnings("unchecked")
   public static <T> T newProxyInstance(Class<T> clazz, T proxied)
   {
      return (T)java.lang.reflect.Proxy.newProxyInstance( InterceptorInvocationHandler.class.getClassLoader(),
                                                          new Class[] {clazz},
                                                          new InterceptorInvocationHandler(proxied));
   }
}
