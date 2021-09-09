import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class main {

	public static void main(String args[]) throws InterruptedException {

		Packet packet1 = new Packet(100, System.currentTimeMillis()); // Luodaan paketti, alustetaan tavuilla ja
																		// aikaleimalla
		Packet packet2 = new Packet(300, System.currentTimeMillis());

		Client client1 = new Client("Client1", packet1); // Luodaan asiakas, alustetaan nimellä ja paketilla
		Client client2 = new Client("Client2", packet2);

		List<Client> clientList = new ArrayList<>();
		clientList.add(client1); // Lisätään asiakkaat listaan
		clientList.add(client2);

		Analyzer trafficAnalyzer = new TrafficAnalyzer();
		trafficAnalyzer.addClient(client1); // Lisätään asiakkaat trafficanalyzerille
		trafficAnalyzer.addClient(client2);

		trafficAnalyzer.addListener(client1, new TestTrafficListener()); // Lisätään trafficanalyzerille asiakas ja
																			// trafficlistener
		trafficAnalyzer.addListener(client2, new TestTrafficListener());
		trafficAnalyzer.startUpdating(); // Aloitetaan tietoliikenteen päivitys kerran minuutissa

		TrafficGenerator trafficGenerator = new TrafficGenerator(clientList); // Luodaan tietoliikenne generaattori

		trafficGenerator.start(); // Generoidaan liikennettä 1sec välein
		Map<Integer, Long> getTotalTraffic = trafficAnalyzer.getTotalTrafficByTimeInterval(4000, 5000); // Hae
																										// pakettien
																										// ja
																										// tavujen
																										// kokonaismäärä
																										// tietyllä
																										// välillä

		Runnable printDataPerClient = new Runnable() {
			public void run() {
				trafficAnalyzer.printTrafficPerClientByTimeInterval(client1, 1000, 2000); // Haetaan pakettien ja
																							// tavujen määrä
																							// asiakkaalla tietyllä aika
																							// välillä
				trafficAnalyzer.printTrafficPerClientByTimeInterval(client2, 1000, 10000);
			}
		};

		ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
		scheduler.scheduleAtFixedRate(printDataPerClient, 5000, 10000, TimeUnit.MILLISECONDS);

	}

}
