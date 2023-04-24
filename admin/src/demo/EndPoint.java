package demo;

public class EndPoint {
    private RestProxy.HTTP_METHOD method;
    private String endPoint;
    private Class returnedClass;

    public EndPoint(RestProxy.HTTP_METHOD method, String endPoint, Class returnedClass) {
        this.endPoint = endPoint;
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

    public Class getReturnedClass() {
        return returnedClass;
    }

    public void setReturnedClass(Class returnedClass) {
        this.returnedClass = returnedClass;
    }

    public String getKey() {
        return method.getMethod() + returnedClass.getName();
    }
}
