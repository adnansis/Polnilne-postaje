package si.fri.prpo.storitve.interceptorji;

import si.fri.prpo.storitve.anotacije.BeleziKlice;
import si.fri.prpo.storitve.zrna.BelezenjeKlicevZrno;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {

    @Inject
    private BelezenjeKlicevZrno belezenjeKlicevZrno;

    @AroundInvoke
    public Object log(InvocationContext ctx) throws Exception{

        belezenjeKlicevZrno.beleziSteviloKlicev(ctx.getMethod().getDeclaringClass().getName(), ctx.getMethod().getName());

        return ctx.proceed();
    }

}