
public class Packet {
	final int bytes;
	final long timeStamp;

	public Packet(int bytes, long timeStamp) {
		this.bytes = bytes;
		this.timeStamp = timeStamp;
	}

	public int getBytes() {
		return this.bytes;
	}

	public long getTimeStamp() {
		return this.timeStamp;
	}
}
