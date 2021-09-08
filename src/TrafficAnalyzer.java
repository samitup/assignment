import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TrafficAnalyzer implements Analyzer {
	List<Client> clientList;
	Map<Client, List<TrafficListener>> trafficListeners;

	public TrafficAnalyzer() {
		this.clientList = new ArrayList<>();
		this.trafficListeners = new HashMap<>();
	}

	public void printTrafficPerClient(Client client) {
		int bytes = client.getPacket().stream().mapToInt(packet -> packet.bytes).sum();
		System.out.println("Client: " + client.getName() + " has sent " + client.getPacketCount() + " packets and "
				+ bytes + " bytes.");
	}

	public void addClient(Client client) {
		this.clientList.add(client);

	}

	public void addListener(Client client, TrafficListener trafficListener) {
		List<TrafficListener> clientListeners = trafficListeners.get(client);
		if (clientListeners == null) {
			clientListeners = new ArrayList<>();
			trafficListeners.put(client, clientListeners);
		}

		clientListeners.add(trafficListener);
	}

	public void startUpdating() {
		Runnable update = new Runnable() {
			public void run() {
				for (Client client : trafficListeners.keySet()) {
					for (TrafficListener trafficListener : trafficListeners.get(client)) {
						trafficListener.updateTraffic(client);
					}
				}
			}
		};

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
		scheduler.scheduleAtFixedRate(update, 0, 60000, TimeUnit.MILLISECONDS);

	}

	public Map<Integer, Integer> getTotalTrafficByTimeInterval(long startTime, long endTime) {
		Map<Integer, Integer> totalTraffic = new HashMap<>();
		int totalBytes = 0;
		int totalPacketCount = 0;

		for (Client client : trafficListeners.keySet()) {
			for (Packet packet : client.getPacket()) {
				if (packet.getTimeStamp() >= startTime && packet.getTimeStamp() <= endTime)
					totalBytes += packet.getBytes();
				totalPacketCount = client.getPacketCount();
				totalTraffic.put(totalPacketCount, totalBytes);
			}
		}
		return totalTraffic;

	}

}
