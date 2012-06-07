package msgpack.rpc.sample.client;

import org.msgpack.rpc.Client;
import org.msgpack.rpc.loop.EventLoop;

public class ClientApp {
	private static class SpawnRequest {
		private SpawnRequest(final int i, final int k, final RPCInterface iface) {
        	new Thread(new Runnable() {
				public void run() {
					for (int j = 1; j <= k; j++) {
			        	System.out.println("(" + i + ":" + j + ") 10 + 2 = " + iface.add(10, 2));
			        	System.out.println("(" + i + ":" + j + ") 10 - 2 = " + iface.sub(10, 2));
			        	System.out.println("(" + i + ":" + j + ") 10 * 2 = " + iface.mul(10, 2));
			        	System.out.println("(" + i + ":" + j + ") 10 / 2 = " + iface.div(10, 2));
					}
				}
			}).start();
		}
	}
	
    public static interface RPCInterface {
        int add(int a, int b);
        int sub(int a, int b);
        int mul(int a, int b);
        double div(int a, int b);
    }
 
    public static void main(String[] args) throws Exception {
        EventLoop loop = EventLoop.defaultEventLoop();
 
        Client cli = new Client("localhost", 1978, loop);
        RPCInterface iface = cli.proxy(RPCInterface.class);

        for (int i = 10; i <= 50; i++) {
        	new SpawnRequest(i, 1, iface);
        }
        
        System.out.println("What else?");
    }
}
