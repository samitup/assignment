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

	public void printTrafficPerClientByTimeInterval(Client client, long startTime, long endTime) {
		long totalBytesPerClient = 0;
		long totalPacketCountPerClient = 0;
		long dataRate = 0;
		for (Packet packet : client.getPacket()) {

			if ((System.currentTimeMillis() - packet.getTimeStamp()) >= startTime
					&& (System.currentTimeMillis() - packet.getTimeStamp()) <= endTime) {
				totalBytesPerClient += packet.getBytes();
				totalPacketCountPerClient += client.getPacketCount();
			}
		}
		// long bytes = client.getPacket().stream().mapToLong(packet ->
		// packet.bytes).sum();
		if ((endTime - startTime) > 0) {
			dataRate = (totalBytesPerClient) / ((endTime - startTime) / 1000);
		}
		System.out.println("Client: " + client.getName() + " has sent " + totalPacketCountPerClient + " packets and "
				+ dataRate + " bytes/s at given time interval.");
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

	public Map<Integer, Long> getTotalTrafficByTimeInterval(long startTime, long endTime) {
		Map<Integer, Long> totalTraffic = new HashMap<>();
		int totalBytes = 0;
		int totalPacketCount = 0;
		long dataRate = 0;

		for (Client client : clientList) {
			for (Packet packet : client.getPacket()) {
				if ((System.currentTimeMillis() - packet.getTimeStamp()) >= startTime
						&& (System.currentTimeMillis() - packet.getTimeStamp()) <= endTime) {
					totalBytes += packet.getBytes();
					totalPacketCount += client.getPacketCount();
				}
			}
		}
		if ((endTime - startTime) > 0) {
			dataRate = totalBytes / (endTime - startTime);
			totalTraffic.put(totalPacketCount, dataRate);
		}
		return totalTraffic;

	}

}
