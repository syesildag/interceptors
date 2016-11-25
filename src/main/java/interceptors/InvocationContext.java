package interceptors;

import java.lang.reflect.Method;

public class InvocationContext
{
   private Object proxy;
   private Object proxied;
   private Method method;
   private Object[] args;
   private int index;
   
   public InvocationContext(int index, Object proxy, Object proxied, Method method, Object[] args)
   {
      this.index = index;
      this.proxy = proxy;
      this.proxied = proxied;
      this.method = method;
      this.args = args;
   }
   
   public Object proceed() throws Exception
   {
      return this.method.invoke(this.proxied, this.args);
   }
   
   public int getIndex()
   {
      return this.index;
   }
   
   public Object getProxy()
   {
      return this.proxy;
   }
   
   public Object getProxied()
   {
      return this.proxied;
   }
   
   public Method getMethod()
   {
      return this.method;
   }
   
   public Object[] getArgs()
   {
      return this.args;
   }
}