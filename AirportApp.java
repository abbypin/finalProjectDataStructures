import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Double;
import java.util.Stack;

/** Airport App gets airport data and creates a Directed Graph. Also, finds the Shortest Distance between two airports. */
public class AirportApp {
    /** Main() controls the menu of options. */
    public static void main(String[] args) {
        ArrayList<String[]> airportList = new ArrayList<>();
        ArrayList<String[]> distanceList = new ArrayList<>();
        Scanner user = new Scanner(System.in);
        
        // Get the Airport Data from storeData()
        ArrayList<String[]> dataList = storeData(airportList, distanceList);
        GraphInterface<String> graph = new DirectedGraph<>();
        int length = dataList.size(), size = distanceList.size();

        // Add Each Vertex
        for (int i = 0; i < length; i++) {
            String vertex = dataList.get(i)[0];
            graph.addVertex(vertex);
        }//end for-loop

        for (int i = 0; i < size; i++) {
            double distance = (double)Double.parseDouble(distanceList.get(i)[2]);
            graph.addEdge(distanceList.get(i)[0], distanceList.get(i)[1], distance);
        }//end for-loop

        char choice = ' ';
        do {
            // Variable
            String userLine = ""; // The String Prepared For If the User Inputs More for the Command
            int indexSpace = 0;

            // Prompt the User
            System.out.print("\nCommand? ");

            // Get the Choice from the Input Line
            String input = user.nextLine(); // Get the Input Line
            choice = input.substring(0, 1).toCharArray()[0]; // Get Choice from input

            //Get the Rest of the User Input if it exists
            if (input.length() > 1) {
                userLine = input.substring(2);
                userLine = userLine + "\n";
            }//end if

            // Manage the Menu
            switch (choice) {
                case 'H':
                    // Menu Choices
                    System.out.println("\nQ  Query the airport information by entering the airport code.");
                    System.out.println("D  Find the minimum distance between two airports.");
                    System.out.println("I  Insert an edge into the graph.");
                    System.out.println("R  Remove an edge from the graph. (Currently not supported).");
                    System.out.println("E  Exit");
                    break;
                case 'Q':
                    getAirportInfo(dataList, userLine);
                    break;
                case 'D':
                    indexSpace = userLine.indexOf(" ");
                    String firstCode = userLine.substring(0, indexSpace);
                    String secondCode = userLine.substring(indexSpace);
                    getShortestDistance(graph, firstCode, secondCode);
                    break;
                case 'I':
                    // indexSpace = userLine.indexOf(" ");
                    // String first = userLine.substring(0, indexSpace);
                    // int indexSpace2 = userLine.indexOf(" ", indexSpace);
                    // String second = userLine.substring(indexSpace, indexSpace2);
                    // String distStr = userLine.substring(indexSpace2);
                    // double distance = (double)Double.parseDouble(distStr);
                    // insertEdge(graph, first, second, distance);
                    System.out.println("The Input i was doing was not working and it is time to submit.");
                    break;
                case 'R':
                    deleteEdge();
                    break;
                case 'E':
                    break;
                default:
                    System.out.println("Invalid Command.");
                    // Menu Choices
                    System.out.println("\nQ  Query the airport information by entering the airport code.");
                    System.out.println("D  Find the minimum distance between two airports.");
                    System.out.println("I  Insert an edge into the graph.");
                    System.out.println("R  Remove an edge from the graph. (Currently not supported).");
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
    public static ArrayList<String[]> storeData(ArrayList<String[]> airportList, ArrayList<String[]> distanceList) {
        // Try to Open the Files
        try {
            // Airports File Open and Scanner
            File airports = new File("airports.csv"); // file with the airport info
            Scanner airportsScan = new Scanner(airports);
            airportsScan.useDelimiter("[,\\n]");
            // Distances File Open and Scanner
            File distances = new File("distances.csv"); // file with the edges to the graph
            Scanner distancesScan = new Scanner(distances);
            distancesScan.useDelimiter("[\\s\\n]");

            // Loop through Airports File O(n*4) = O(n)
            while (airportsScan.hasNextLine()) {
                // Each Line in the File contains 4 Pieces of Information
                String[] airportInfo = new String[4];
                for (int i = 0; i < 4; i++) {
                    airportInfo[i] = airportsScan.next();
                }//end for-loop

                airportList.add(airportInfo);
            }//end while-loop

            airportsScan.close(); // Close the Airport Scanner

            // Loop through the Distance File
            while (distancesScan.hasNextLine()) {
                String[] distanceInfo = new String[3];
                // Get the Airport Code of the "From" Airport
                distanceInfo[0] = distancesScan.next();
                // Get the Airport Code of the "To" Airport
                distanceInfo[1] = distancesScan.next();
                // Get the Distance between the Airports
                double distance = distancesScan.nextDouble();
                String distanceStr = "";
                distanceStr += distance;
                distanceInfo[2] = distanceStr;
                distanceList.add(distanceInfo);
            }//end while-loop

            distancesScan.close(); // Close the Distance Scanner

            System.out.println("Files Scan Successful.\n");
        } catch (FileNotFoundException err) {
            System.out.println(err);
        }//end try-catch

        return airportList;
    }//end storeData()

    /** Helper method that traverses the airport ArrayList to find the matching Airport Code.
     * @param airportList The ArrayList which contains a String array holding the airport file data
     * @param str The String airport code to search for.
     * @return The int type "index" of the airport code String[] or -1 if no match is found. */
    public static int getAirportIndex(ArrayList<String[]> airportList, String str) {
        // Variables
        boolean found = false;
        int i = 0, airportIndex = 0;

        // Loop through Airport Array List to find the String (str)
        while (!found && (i < airportList.size())) {
            String nextCode = airportList.get(i)[0]; // the next airport code
            if (nextCode.equals(str)) { // if found
                airportIndex = i;
                found = true;
                return airportIndex;
            } else { // if not found
                i++;
            }//end if-else
        } // get the index of the code in the ArrayList

        // If the String (str) is Not in the Airport ArrayList
        if (found == false) {
            airportIndex = -1;
        }//end if

        return airportIndex;
    }//end getAirportIndex()

    // 2. Let the user of this program enter an airport code and your program should print out the airport information.
    /** Takes in user inputed airport codes and prints out their airport information.
     * @param airportList The ArrayList that contains String array, each String[] contains the airport information.
     * @param airport A string that hold the user input. */
    public static void getAirportInfo(ArrayList<String[]> airportList, String airport) {
        // Variables
        int length = airport.length();
        String word = "";

        // The loop traverses the user's inputted line of data to get the separated Airport Codes
        for (int i = 0; i < length; i++) {
            if (airport.charAt(i) != ' ' && airport.charAt(i) != '\n') { // if the end of the word is not found yet
                word += airport.charAt(i);
            } else { // when the end of the word is found
                // Get the Airport Index
                int airportIndex = getAirportIndex(airportList, word);

                if (airportIndex == -1) { // If the User Input is not a known Airport Code
                    System.out.print(word);
                    System.out.println(" - unknown");
                } else { // If the User Input is a known Airport Code
                    // Print the Airport Information
                    System.out.println(airportList.get(airportIndex)[0] + " - " +
                    airportList.get(airportIndex)[2] + ". " + airportList.get(airportIndex)[1] + ", " + airportList.get(airportIndex)[3]);
                }//end if-else
                word = "";
            }//end if-else
        }//end for-loop
    }//end getAirportInfo()

    // 3. Find the connection between two airports. Get two airport codes and find the shortest distance between two airports.
    /** Gets the Shortest Path between two given airports. Prints a message if their is not path between the given airports.
     * @param graph The Directed Graph.
     * @param airportOne An object that labels the origin vertex of the path.
     * @param airportTwo An object that labels the end vertex of the path. */
    public static void getShortestDistance(GraphInterface<String> graph, String airportOne, String airportTwo) {
        // Variables
        Stack<String> path = new Stack<>(); // the path stack
        double pathCost = graph.getCheapestPath(airportOne, airportTwo, path); // the path cost from the getCheapestPath method

        // Check if the Path Exists and Print
        if (pathCost == 0) {
            System.out.println("Airports not connected.");
        } else {
            while (!path.isEmpty()) {
                System.out.print(path.pop() + " ");
            }//end while-loop
        }//end if
    }//end getShortestDistance()

    // 4. Insert a connection (edge) between two airports -- the user will be asked to enter the two airport codes and its distance. Note that if a pair of airport codes already exists or if the airport code doesn't exist, print out a warning message.
    /** Inserts a new edge into the graph, if possible.
     * @param graph The graph.
     * @param begin An object that labels the proposed origin vertex of the edge.
     * @param end An object that labels the proposed end vertex of the edge.
     * @param distance A double that is the proposed edge weight. */
    public static void insertEdge(GraphInterface<String> graph, String begin, String end, double distance) {
        // Add Edge and Save the Result
        boolean added = graph.addEdge(begin, end, distance);
        // Check if an Edge was Added
        if (added)
            System.out.println("An edge was added between " + begin + " and " + end + " with the distance of " + distance);
        else
            System.out.println("Could not add an edge between " + begin + " and " + end + ". Please check your airport codes.");
    }//end insertEdge()

    // 5. Delete a connection (edge) -- the user will be asked to enter two airport codes for this connection. Note that if the connection entered doesn't exist, print out a warning message.
    /** Currently not a supported method. */
    public static void deleteEdge() {
        System.out.println("The user may not delete edges at this time.");
    }//end deleteEdge()
}//end AirportApp

