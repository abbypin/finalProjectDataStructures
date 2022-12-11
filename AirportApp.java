import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;

public class AirportApp {
    public static void main(String[] args) {
        LinkedList<AirportInfoStructClass> airportList = new LinkedList<>();
        storeData(airportList);
        
    }//end main()

    // 1. Read the original data files and store the data to appropriate data structures.
    public static GraphInterface<String> storeData(LinkedList<AirportInfoStructClass> airportList) {
        // Create the Graph to store the Graph Data
        GraphInterface<String> graph = new DirectedGraph<>();
        // Try to Open the Files
        try {
            // Airports File Open and Scanner
            File airports = new File("airports.csv"); // file with the airport info
            Scanner airportsScan = new Scanner(airports);
            // Distances File Open and Scanner
            File distances = new File("distances.csv"); // file with the edges to the graph
            Scanner distancesScan = new Scanner(distances);

            // Loop through Airports File
            while (airportsScan.hasNextLine()) {
                // Each Line in the File contains 4 Pieces of Information
                String airportCode = airportsScan.next(); // Get the Airport Code
                String city = airportsScan.next(); // Get the Airport City
                String airportName = airportsScan.next(); // Get the Airport Name
                String state = airportsScan.next(); // Get the Airport's State
                // Store the Airport Information in a Class designed to Keep the Airport information accessable in one Object
                AirportInfoStructClass airport = new AirportInfoStructClass(airportCode, city, airportName, state);
                // Add the New Airport Info Object the a Java Created Linked List
                airportList.add(airport); // the list will store the airport Info Object for when we want to access the names
                graph.addVertex(airport.getAirportCode()); // Add the Airport Code as a Vertex in the Graph
            }//end while-loop

            airportsScan.close(); // Close the Airport Scanner

            // Loop through the Distance File
            while (distancesScan.hasNextLine()) {
                // Get the Airport Code of the "From" Airport
                String fromAirport = distancesScan.next();
                // Get the Airport Code of the "To" Airport
                String toAirport = distancesScan.next();
                // Get the Distance between the Airports
                int distance = distancesScan.nextInt();
                // Create the Edge with the New Information
                graph.addEdge(fromAirport, toAirport, distance);
            }//end while-loop

            distancesScan.close(); // Close the Distance Scanner
        } catch (FileNotFoundException err) {
            System.out.println(err);
        }//end try-catch

        return graph;
    }//end storeData()

    // 2. Let the user of this program enter an airport code and your program should print out the airport information.
    public static void getAirportInfo() {

    }//end getAirportInfo()

    // 3. Find the connection between two airports. Get two airport codes and find the shortest distance between two airports.
    public static void getShortestDistance() {

    }//end getShortestDistance()

    // 4. Insert a connection (edge) between two airports -- the user will be asked to enter the two airport codes and its distance. Note that if a pair of airport codes already exists or if the airport code doesn't exist, print out a warning message.
    public static void insertEdge() {

    }//end insertEdge()

    // 5. Delete a connection (edge) -- the user will be asked to enter two airport codes for this connection. Note that if the connection entered doesn't exist, print out a warning message.
    public static void deleteEdge() {

    }//end deleteEdge()

    // TODO: Ready information
    // Sout << "Command?"
    // TODO: Recieve path
    // TODO: filter input
    // If elegable input print out the cheapest path
    // Respond: Invaid Command, Airport code unknown, Airports not connected
}//end AirportApp
