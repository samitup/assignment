import java.util.concurrent.ScheduledExecutorService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TrafficGenerator {
	private List<Client> clientList;

	public TrafficGenerator(List<Client> clientList) {
		this.clientList = clientList;
	}

	public void start() {

		Runnable send = new Runnable() {
			public void run() {
				for (Client client : clientList) {
					client.addPacket(500, System.currentTimeMillis());

				}
			}
		};

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
		scheduler.scheduleAtFixedRate(send, 0, 1000, TimeUnit.MILLISECONDS);
	}

}
