import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class AirportApp {
    public static void main(String[] args) {
        BagInterface<Object> airportList = new LinkedBag<>();
        // Create the Graph to store the Graph Data
        GraphInterface<String> graph = new DirectedGraph<>();
        Scanner user = new Scanner(System.in);
        
        // Get the Airport Data from storeData()
        BagInterface<Object> airportData = storeData(airportList);

        // Fill the Graph
        for (int i = 0; i < airportData.getSize(); i++) {
            String vertex = airportData.get(i).toString();
            graph.addVertex(vertex); // Add the Airport Code as a Vertex in the Graph

            Object[] edgeData = (Object[]) airportData.get(i)[5];
            String fromAirport = (String) edgeData[0];
            String toAirport = (String) edgeData[1];
            int distance = (int) edgeData[2];

            // Create the Edge with the New Information
            graph.addEdge(fromAirport, toAirport, distance);
        }//end for-loop

        char choice = ' ';
        do {
            // Variable
            String airport = ""; // The String Prepared For If the User Inputs More for the Command

            // Prompt the User
            System.out.print("Command? ");

            // Get the Choice from the Input Line
            String input = user.nextLine(); // Get the Input Line
            String ch = input.substring(0, 1); // Get the Choice out of the Sting
            char[] chArray = ch.toCharArray(); // Set the Choice String in a Char Array
            choice = chArray[0]; // Take out Choice from the Char Array

            // Get the Rest of the User Input if it exists
            if (input.length() > 1) {
                airport = input.substring(2); // This code is correct
            }//end if

            // Manage the Menu
            switch (choice) {
                case 'H':
                    // Method 2
                    System.out.println("\nQ  Query the airport information by entering the airport code.");
                    // Method 3
                    System.out.println("D  Find the minimum distance between two airports.");
                    System.out.println("E  Exit");
                    break;
                case 'Q':
                    getAirportInfo(airportList, airport);
                    break;
                case 'E':
                    break;
                default:
                    System.out.println("Not a correct Menu Choice.");
                    // Method 2
                    System.out.println("\nQ  Query the airport information by entering the airport code.");
                    // Method 3
                    System.out.println("D  Find the minimum distance between two airports.");
                    System.out.println("E  Exit");
            };//end switch
        } while (choice != 'E');

        user.close(); // Close the User Scanner
    }//end main()

    // 1. Read the original data files and store the data to appropriate data structures.
    /** A method that reads the data files "airport.csv" and "distance.csv". 
     * With airport.csv, the airport data is collected in an object of the "AirportInfoStructClass" class. 
     * Then the object is stored in a Java created LinkedList. From airports the airportCode is collected into the DirectedGraph and the Vertex. 
     * With distances.csv, the data is read and put into the Edges of the DirectedGraph.
     * @param airportList The LinkedList variable created in main(). Used to hold the airport data objects.
     * @return Returns the created DirectedGraph. */
    // TODO: Fix the code to put the airport and distance data into the LinkedBag
   public static BagInterface<Object> storeData(BagInterface<Object> airportList) {
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
                String airportLine = airportsScan.nextLine(); // Get Next Line

                // Get Airport Code's End Index and the String Itself
                int airportCodeIndex = airportLine.indexOf(",");
                String airportCode = airportLine.substring(0, airportCodeIndex);// Get the Airport Code
                // Get Airport City's End Index and the String Itself
                int cityIndex = airportLine.indexOf(",", airportCodeIndex);
                String city = airportLine.substring(airportCodeIndex, cityIndex); // Get the Airport City
                // Get Airport Name's End Index and the String Itself
                int airportNameIndex = airportLine.indexOf(",", cityIndex);
                String airportName = airportLine.substring(cityIndex, airportNameIndex); // Get the Airport Name
                // Get Airport State's End Index and the String Itself
                int stateIndex = airportLine.indexOf(",", airportNameIndex);
                String state = airportLine.substring(airportNameIndex, stateIndex); // Get the Airport's State
                
                // The Array will Store the Airport Info Object. for when we want to access the data
                airportList.add(airportCode, city, airportName, state, null);                
            }//end while-loop

            airportsScan.close(); // Close the Airport Scanner

            int j = 0;
            // Loop through the Distance File
            while (distancesScan.hasNextLine()) {
                // Get the Airport Code of the "From" Airport
                String fromAirport = distancesScan.next();
                // Get the Airport Code of the "To" Airport
                String toAirport = distancesScan.next();
                // Get the Distance between the Airports
                int distance = distancesScan.nextInt();

                Object[] edge = new Object[3];
                edge[0] = fromAirport;
                edge[1] = toAirport;
                edge[2] = distance;

                airportList.replace(j, edge);
                j++;
            }//end while-loop

            distancesScan.close(); // Close the Distance Scanner

            System.out.println("Files Scan Successful.\n");
        } catch (FileNotFoundException err) {
            System.out.println(err);
        }//end try-catch

        return airportList;
    }//end storeData()

    // 2. Let the user of this program enter an airport code and your program should print out the airport information.
    public static void getAirportInfo(BagInterface<Object> airportList, String airport) {
        boolean done = false;
        int index = 0; // The Starting Index for Separationg the User Inputs
        
        while (!done) {
            // Variables
            int endIndex;
            String str = "";

            // Get the Airport Code
            if (index == airport.length() - 3) {
                // Get the Airport Code String
                str = airport.substring(index);
                done = true; // set done
            } else {
                // Get the End Index for the Airport Code
                endIndex = airport.indexOf(" ", index);
                // Get the Airport Code
                str = airport.substring(index, endIndex);

                // Check if this is the Last User Input
                if (endIndex == airport.length()) {
                    done = true;
                }//end if
                index = endIndex;
            }//end if-else

            // Get the Index of the Airport in the LinkedList
            int airportIndex = airportList.getIndexOf(str);
            if (airportIndex == -1) { // If the User Input is not a known Airport Code
                System.out.print(str);
                System.out.println(" - unknown");
            } else { // If the User Input is a known Airport Code
                // Get the Airport Information
                String[] airportInfo = (String[])new Object[5];
                airportInfo = (String[]) airportList.get(airportIndex);
                // Print the Airport Information
                System.out.print(str + " - ");
                System.out.println(airportInfo[2] + " " + airportInfo[1] + ", " + airportInfo[3]);
            }//end if-else
        }//end while-loop
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
