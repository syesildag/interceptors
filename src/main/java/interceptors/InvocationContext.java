package interceptors;

import java.lang.reflect.Method;

/**
 * @author SYESILDAG
 */
public class InvocationContext
{
   @SuppressWarnings("InstanceVariableMayNotBeInitialized")
   private Object proxy;
   @SuppressWarnings("InstanceVariableMayNotBeInitialized")
   private Object delegate;
   @SuppressWarnings("InstanceVariableMayNotBeInitialized")
   private Method method;
   @SuppressWarnings("InstanceVariableMayNotBeInitialized")
   private Object[] args;
   @SuppressWarnings("InstanceVariableMayNotBeInitialized")
   private int index;
   @SuppressWarnings("InstanceVariableMayNotBeInitialized")
   private Object result;

   @SuppressWarnings({"ImplicitCallToSuper", "PublicConstructor", "unused"})
   public InvocationContext() {
   }

   @SuppressWarnings({"WeakerAccess", "ImplicitCallToSuper", "PublicConstructor", "ConstructorWithTooManyParameters"})
   public InvocationContext(int index, Object proxy, Object delegate, Method method, Object[] args, Object result)
   {
      this.index = index;
      this.proxy = proxy;
      this.delegate = delegate;
      this.method = method;
      //noinspection AssignmentOrReturnOfFieldWithMutableType
      this.args = args;
      this.result = result;
   }
   
   /**
    * @return
    * @throws IllegalAccessException
    * @throws IllegalArgumentException
    * @throws java.lang.reflect.InvocationTargetException
    */
   @SuppressWarnings({"unused", "PublicMethodNotExposedInInterface", "JavaDoc"})
   public Object proceed() throws IllegalAccessException, java.lang.reflect.InvocationTargetException {
      return this.method.invoke(this.delegate, this.args);
   }
   
   @SuppressWarnings({"unused", "PublicMethodNotExposedInInterface"})
   public int getIndex()
   {
      return this.index;
   }
   
   @SuppressWarnings({"unused", "PublicMethodNotExposedInInterface"})
   public Object getProxy()
   {
      return this.proxy;
   }
   
   @SuppressWarnings({"unused", "PublicMethodNotExposedInInterface"})
   public Object getDelegate()
   {
      return this.delegate;
   }
   
   @SuppressWarnings({"unused", "PublicMethodNotExposedInInterface"})
   public Method getMethod()
   {
      return this.method;
   }
   
   @SuppressWarnings({"unused", "PublicMethodNotExposedInInterface"})
   public Object[] getArgs()
   {
      //noinspection AssignmentOrReturnOfFieldWithMutableType
      return this.args;
   }
   
   @SuppressWarnings({"unused", "PublicMethodNotExposedInInterface"})
   public Object getResult()
   {
      return this.result;
   }
}