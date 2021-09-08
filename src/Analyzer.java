import java.util.Map;

public interface Analyzer {

	public void printTrafficPerClient(Client client);

	public void addClient(Client client);

	public void addListener(Client client, TrafficListener trafficListener);

	public void startUpdating();

	public Map<Integer, Integer> getTotalTrafficByTimeInterval(long startTime, long endTime);
}