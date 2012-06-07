package msgpack.rpc.sample.client;

import org.msgpack.rpc.Client;
import org.msgpack.rpc.loop.EventLoop;

public class ClientApp {
	private static class SpawnRequest {
		private SpawnRequest(final int clientCount, final int requestCount, final RPCInterface iface) {
			new Thread(new Runnable() {
				public void run() {
					for (int rc = 1; rc <= requestCount; rc++) {
			        	System.out.println("(" + clientCount + ":" + rc + ") 10 + 2 = " + iface.add(10, 2));
			        	System.out.println("(" + clientCount + ":" + rc + ") 10 - 2 = " + iface.sub(10, 2));
			        	System.out.println("(" + clientCount + ":" + rc + ") 10 * 2 = " + iface.mul(10, 2));
			        	System.out.println("(" + clientCount + ":" + rc + ") 10 / 2 = " + iface.div(10, 2));
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
 
        Client client = new Client("localhost", 1978, loop);
        RPCInterface iface = client.proxy(RPCInterface.class);

        final int CLIENT_COUNT = 1;
        final int REQUEST_COUNT = 1;
        
        System.out.println("Spawning " + CLIENT_COUNT + " clients to perform " + REQUEST_COUNT + " requests each");
        
    	for (int cc = 1; cc <= CLIENT_COUNT; cc++) {
        	new SpawnRequest(cc, REQUEST_COUNT, iface);
        }
    }
}
