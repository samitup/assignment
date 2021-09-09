import java.util.Map;

public interface Analyzer {

	public void printTrafficPerClientByTimeInterval(Client client, long startTime, long endTime);

	public void addClient(Client client);

	public void addListener(Client client, TrafficListener trafficListener);

	public void startUpdating();

	public Map<Integer, Long> getTotalTrafficByTimeInterval(long startTime, long endTime);
}