
public class TestTrafficListener implements TrafficListener {

	@Override
	public void updateTraffic(Client client) {
		int bytes = client.getPacket().stream().mapToInt(packet -> packet.bytes).sum();
		System.out.println("Traffic, client name: " + client.getName() + ", sent packets: " + client.getPacketCount()
				+ ", bytes sent :" + bytes);

	}

}
