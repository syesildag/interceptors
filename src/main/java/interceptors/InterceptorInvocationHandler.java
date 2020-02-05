package interceptors;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Optional;

/**
 * @author SYESILDAG
 */
@SuppressWarnings({"FinalClass", "unused"})
public final class InterceptorInvocationHandler<I, C extends I> extends AbstractInvocationHandler {
   private static final long serialVersionUID = 1L;

   private C delegate;

   @SuppressWarnings("ImplicitCallToSuper")
   private InterceptorInvocationHandler(C delegate) {
      this.delegate = delegate;
   }

   @SuppressWarnings({"PublicMethodNotExposedInInterface", "WeakerAccess"})
   public C getDelegate() {
      return this.delegate;
   }

   /* (non-Javadoc)
    * @see com.resalys.custom.interceptors.AbstractInvocationHandler#handleInvocation(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
    */

   /**
    * @param proxy
    * @param method
    * @param args
    * @return
    * @throws IllegalAccessException
    * @throws IllegalArgumentException
    * @throws java.lang.reflect.InvocationTargetException
    * @throws InstantiationException
    * @throws NoSuchMethodException
    * @throws SecurityException
    * @throws Exception
    */
   @SuppressWarnings({"FeatureEnvy", "MethodWithMultipleReturnPoints", "MethodWithTooExceptionsDeclared", "OverlyBroadThrowsClause", "ProhibitedExceptionDeclared", "JavaDoc"})
   @Override
   protected Object handleInvocation(Object proxy, Method method, Object[] args) throws Exception {
      Optional<Interceptors> interceptors = InterceptorInvocationHandler.findInterceptors(method);
      if (!interceptors.isPresent())
         return method.invoke(this.delegate, args);

      Object result = null;

      int index = 0;

      Class<? extends Interceptor>[] classInterceptors = interceptors.get().value();
      for (Class<? extends Interceptor> classInterceptor : classInterceptors) {
         Interceptor interceptor = null;

         //noinspection ConstantConditions
         if (interceptor == null)
            interceptor = classInterceptor.getDeclaredConstructor().newInstance();

         //noinspection ObjectAllocationInLoop,ValueOfIncrementOrDecrementUsed
         result = interceptor.intercept(new InvocationContext(index++, proxy, this.delegate, method, args, result));
      }

      return result;
   }

   @SuppressWarnings("MethodReturnOfConcreteClass")
   private static Optional<Interceptors> findInterceptors(Method method) {
      Interceptors interceptors = method.getAnnotation(Interceptors.class);
      if (interceptors == null)
         for (Annotation annotation : method.getDeclaredAnnotations()) {
            interceptors = annotation.annotationType().getAnnotation(Interceptors.class);
            //noinspection VariableNotUsedInsideIf
            if (interceptors != null)
               //noinspection BreakStatement
               break;
         }

      return Optional.ofNullable(interceptors);
   }

   @SuppressWarnings({"unchecked", "unused"})
   public static <I, C extends I> I newProxyInstance(Class<I> iface, C delegate) {
      //noinspection NestedMethodCall
      return (I) Proxy.newProxyInstance(InterceptorInvocationHandler.class.getClassLoader(),
         new Class[]{iface},
         new InterceptorInvocationHandler<I, C>(delegate));
   }

   @SuppressWarnings({"MethodReturnOfConcreteClass", "unused"})
   public static <I, C extends I> Optional<C> getDelegate(I entity) {
      //noinspection CallToSimpleGetterFromWithinClass,NestedMethodCall
      return InterceptorInvocationHandler
         .<I, C>of(entity)
         .map(interceptor -> interceptor.getDelegate());
   }

   @SuppressWarnings({"unchecked", "MethodReturnOfConcreteClass", "MethodWithMultipleReturnPoints", "WeakerAccess"})
   public static <I, C extends I> Optional<InterceptorInvocationHandler<I, C>> of(I entity) {

      //noinspection NestedMethodCall
      if (Proxy.isProxyClass(entity.getClass())) {
         InvocationHandler invocationHandler = Proxy.getInvocationHandler(entity);
         //noinspection InstanceofConcreteClass
         if (invocationHandler instanceof InterceptorInvocationHandler)
            //noinspection CastToConcreteClass
            return Optional.of((InterceptorInvocationHandler<I, C>) invocationHandler);
      }

      return Optional.empty();
   }
}
