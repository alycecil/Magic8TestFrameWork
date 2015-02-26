class MagicEightBallException extends Exception{
    MagicEightBallException(String s){
        super(s)
    }
}

class MagicTestHandler implements Interceptor{
    static Random r = new Random()
    Object beforeInvoke( Object object, String methodName, Object[] arguments ){
        if(r.nextBoolean()) {
            throw new MagicEightBallException("As I see it, nope.")
        }
    }
    boolean doInvoke(){ true }

    Object afterInvoke( Object object, String methodName,Object[] arguments,
                        Object result ){
        result
    }
}

def useInterceptor= { Class theClass, Class theInterceptor, Closure theCode->
    def proxy= ProxyMetaClass.getInstance( theClass )
    def interceptor= theInterceptor.newInstance()
    proxy.interceptor= interceptor
    proxy.use( theCode )
}


useInterceptor( ArrayList, MagicTestHandler ){
    assert ['a', 'b', 'c'].size() == 3
}
