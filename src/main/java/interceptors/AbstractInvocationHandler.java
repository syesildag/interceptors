package interceptors;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author SYESILDAG
 */
public abstract class AbstractInvocationHandler implements InvocationHandler, Serializable
{
   private static final long serialVersionUID = 1L;
   private static final Object[] NO_ARGS = {};
   
   /* (non-Javadoc)
    * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
    */
   @SuppressWarnings("boxing")
   @Override
   public final Object invoke(Object proxy, Method method, Object[] args)
         throws Throwable {
      if (args == null)
         args = NO_ARGS;
      
      if (args.length == 0 && method.getName().equals("hashCode"))
         return hashCode();
      
      if (args.length == 1
            && method.getName().equals("equals")
            && method.getParameterTypes()[0] == Object.class) {
         Object arg = args[0];
         if (arg == null)
            return false;
         
         if (proxy == arg)
            return true;
         
         return isProxyOfSameInterfaces(arg, proxy.getClass()) && equals(Proxy.getInvocationHandler(arg));
      }
      
      if (args.length == 0 && method.getName().equals("toString"))
         return toString();
      
      return handleInvocation(proxy, method, args);
   }
   
   protected abstract Object handleInvocation(Object proxy, Method method, Object[] args)
         throws Throwable;
   
   /* (non-Javadoc)
    * @see java.lang.Object#equals(java.lang.Object)
    */
   @Override
   public boolean equals(Object obj) {
      return super.equals(obj);
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   @Override
   public int hashCode() {
      return super.hashCode();
   }
   
   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   @Override
   public String toString() {
      return super.toString();
   }
   
   private static boolean isProxyOfSameInterfaces(Object arg, Class<?> proxyClass) {
      return proxyClass.isInstance(arg)
            || (Proxy.isProxyClass(arg.getClass())
                  && Arrays.equals(arg.getClass().getInterfaces(), proxyClass.getInterfaces()));
   }
}
