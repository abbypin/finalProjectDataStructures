/** A class created to hold and access the airport's information. (PS: I called it "Struct" because it acts like a c++ struct in the fact that it holds the variables together in one place.) */
public class AirportInfoStructClass {
    private String airportCode;
    private String city;
    private String airportName;
    private String state;

    public AirportInfoStructClass() {
        airportCode = null;
        city = null;
        airportName = null;
        state = null;
    }//end default constructor

    public AirportInfoStructClass(String code, String newCity, String name, String newState) {
        airportCode = code;
        city = newCity;
        airportName = name;
        state = newState;
    }//end constructor

    public String getAirportCode() {
        return airportCode;
    }//end getAirportCode

    public String getCity() {
        return city;
    }//end getCity()

    public String getAirportName() {
        return airportName;
    }//end getAirportName()

    public String getState() {
        return state;
    }//end getState()

    public void setAirportCode(String code) {
        airportCode = code;
    }//end setAirportCode()

    public void setCity(String newCity) {
        city = newCity;
    }//end setCity()

    public void setAirportName(String name) {
        airportName = name;
    }//end setAirportName()

    public void setState(String newState) {
        state = newState;
    }//end setState()
}//end AirportInfoStruckClass
