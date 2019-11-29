package rpc;

public class HelloServieImpl implements HelloService {
    @Override
    public String hello(String name) {
        return Thread.currentThread().getName() + ":" + name;
    }
}
