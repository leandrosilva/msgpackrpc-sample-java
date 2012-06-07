package msgpack.rpc.sample.server;

import org.msgpack.rpc.Server;
import org.msgpack.rpc.loop.EventLoop;

public class ServerApp {

	public int add(int a, int b) {
		return a + b;
	}
    
	public int sub(int a, int b) {
		return a - b;
    }
    
	public int mul(int a, int b) {
		return a * b;
    }
    
	public double div(int a, int b) {
		return a / b;
    }
 
    public static void main(String[] args) throws Exception {
        EventLoop loop = EventLoop.defaultEventLoop();
 
        Server svr = new Server();
        svr.serve(new ServerApp());
        svr.listen(1978);
        
        System.out.println("Server [1] listening on 1978");
 
        loop.join();
    }
}
