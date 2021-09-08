import java.util.ArrayList;
import java.util.List;

public class Client {
	private String name;
	private List<Packet> packets;
	private int packetCount;

	public Client(String name, Packet packet) {
		this.packetCount = 0;
		this.name = name;
		this.packets = new ArrayList<>();
		this.packets.add(packet);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public List<Packet> getPacket() {
		return this.packets;
	}

	public void addPacket(int bytes, long timeStamp) {
		this.packets.add(new Packet(bytes, timeStamp));
		this.packetCount = (int) (packetCount + Math.floor(Math.random()*100)+1);
	}

	public int getPacketCount() {
		return this.packetCount;
	}
}
