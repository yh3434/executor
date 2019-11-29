package rpc;

public class RpcProvider {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServieImpl();
        RpcFramework.export(service, 1234);
    }
}
