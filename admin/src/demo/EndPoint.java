package demo;

public class EndPoint<E> {
    private RestProxy.HTTP_METHOD method;
    private String endPoint;
    private Class<E> returnedClass;

    public EndPoint(RestProxy.HTTP_METHOD method, String endPoint, Class<E> returnedClass) {
        this.endPoint = endPoint;
        this.method = method;
        this.returnedClass = returnedClass;
    }

    public EndPoint(RestProxy.HTTP_METHOD method, Class<E> returnedClass) {
        this.method = method;
        this.returnedClass = returnedClass;
    }

    public RestProxy.HTTP_METHOD getMethod() {
        return method;
    }

    public void setMethod(RestProxy.HTTP_METHOD method) {
        this.method = method;
    }

    public String getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(String endPoint) {
        this.endPoint = endPoint;
    }

    public Class<E> getReturnedClass() {
        return returnedClass;
    }

    public void setReturnedClass(Class<E> returnedClass) {
        this.returnedClass = returnedClass;
    }

    public String getKey() {
        return method.getMethod() + returnedClass.getName();
    }
}
