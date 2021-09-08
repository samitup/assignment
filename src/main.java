import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class main {

	public static void main(String args[]) {

		Packet packet1 = new Packet(100, System.currentTimeMillis()); // Luodaan paketti, alustetaan tavuilla ja
																		// aikaleimalla
		Packet packet2 = new Packet(300, System.currentTimeMillis());

		Client client1 = new Client("Client1", packet1); // Luodaan asiakas, alustetaan nimellä ja paketilla
		Client client2 = new Client("Client2", packet2);

		List<Client> clientList = new ArrayList<>();
		clientList.add(client1); // Lisätään asiakkaat listaan
		clientList.add(client2);

		TrafficAnalyzer trafficAnalyzer = new TrafficAnalyzer();
		trafficAnalyzer.addClient(client1); // Lisätään asiakkaat trafficanalyzerille
		trafficAnalyzer.addClient(client2);

		trafficAnalyzer.addListener(client1, new TestTrafficListener()); // Lisätään trafficanalyzerille asiakas ja
																			// trafficlistener
		trafficAnalyzer.addListener(client2, new TestTrafficListener());
		trafficAnalyzer.startUpdating(); // Aloitetaan tietoliikenteen päivitys kerran minuutissa

		Map<Integer, Integer> getTotalTraffic = trafficAnalyzer.getTotalTrafficByTimeInterval(4000, 5000); // Hae
																											// pakettien
																											// ja
																											// tavujen
																											// kokonaismäärä
																											// tietyllä
																											// välillä

		TrafficGenerator trafficGenerator = new TrafficGenerator(clientList); // Luodaan tietoliikenne
																				// generaattori
		trafficGenerator.start(); // Generoidaan liikennettä 1sec välein

		trafficAnalyzer.printTrafficPerClient(client1); // Haetaan pakettien ja tavujen määrä asiakkaalla
		trafficAnalyzer.printTrafficPerClient(client2);

	}

}
