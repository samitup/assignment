
public class TestTrafficListener implements TrafficListener {

	@Override
	public void updateTraffic(Client client) {
		long bytes = client.getPacket().stream().mapToLong(packet -> packet.bytes).sum();
		System.out.println("Traffic, client name: " + client.getName() + ", sent packets: " + client.getPacketCount()
				+ ", data rate :" + (bytes / 60) + " bytes/s");

	}

}
