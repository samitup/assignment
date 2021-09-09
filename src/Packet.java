
public class Packet {
	final long bytes;
	final long timeStamp;

	public Packet(long bytes, long timeStamp) {
		this.bytes = bytes;
		this.timeStamp = timeStamp;
	}

	public long getBytes() {
		return this.bytes;
	}

	public long getTimeStamp() {
		return this.timeStamp;
	}
}
