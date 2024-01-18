import java.util.ArrayList;

/**
 * The StopAndFrisk class represents stop-and-frisk data, provided by
 * the New York Police Department (NYPD), that is used to compare
 * during when the policy was put in place and after the policy ended.
 * 
 * @author Tanvi Yamarthy
 * @author Vidushi Jindal
 */
public class StopAndFrisk {

    /*
     * The ArrayList keeps track of years that are loaded from CSV data file.
     * Each SFYear corresponds to 1 year of SFRecords. 
     * Each SFRecord corresponds to one stop and frisk occurrence.
     */ 
    private ArrayList<SFYear> database; 

    /*
     * Constructor creates and initializes the @database array
     * 
     * DO NOT update nor remove this constructor
     */
    public StopAndFrisk () {
        database = new ArrayList<>();
    }

    /*
     * Getter method for the database.
     * *** DO NOT REMOVE nor update this method ****
     */
    public ArrayList<SFYear> getDatabase() {
        return database;
    }

    /**
     * This method reads the records information from an input csv file and populates 
     * the database.
     * 
     * Each stop and frisk record is a line in the input csv file.
     * 
     * 1. Open file utilizing StdIn.setFile(csvFile)
     * 2. While the input still contains lines:
     *    - Read a record line (see assignment description on how to do this)
     *    - Create an object of type SFRecord containing the record information
     *    - If the record's year has already is present in the database:
     *        - Add the SFRecord to the year's records
     *    - If the record's year is not present in the database:
     *        - Create a new SFYear 
     *        - Add the SFRecord to the new SFYear
     *        - Add the new SFYear to the database ArrayList
     * 
     * @param csvFile
     */
    public void readFile ( String csvFile ) {

        // DO NOT remove these two lines
        StdIn.setFile(csvFile); // Opens the file
        StdIn.readLine();       // Reads and discards the header line

        // WRITE YOUR CODE HERE
        
        while(!StdIn.isEmpty()){
            String[] recordEntries = StdIn.readLine().split(",");
            int year = Integer.parseInt(recordEntries[0]);

            String description = recordEntries[2];

            String gender = recordEntries[52];

            String race = recordEntries[66];

            String location = recordEntries[71];

            Boolean arrested = recordEntries[13].equals("Y");

            Boolean frisked = recordEntries[16].equals("Y");

            SFRecord record = new SFRecord(description, arrested, frisked, gender, race, location);
           
            boolean yearExists = false;
            for (SFYear yearNum:database){
                if (yearNum.getcurrentYear() == year){
                    yearNum.addRecord(record);
                    yearExists = true;
                    break;
                }
            }
            if (!yearExists){
                SFYear newYear = new SFYear(year);
                newYear.addRecord(record);
                database.add(newYear);
            }
            
            /*if (database.isEmpty()){
                    SFYear yearNum = new SFYear(year);
                    database.add(yearNum);
            }
                for (int i = 0; i < database.size(); i++){
                    if (database.get(i).getcurrentYear() == year){
                        database.get(i).addRecord(record);
                        
                    }
                    else{
                        SFYear newYear = new SFYear(year);
                        database.add(newYear);
                    }
                }
                /*for ( SFYear yearCount: database){
                    if ( yearCount.getcurrentYear() == year ){
                        yearCount.addRecord(record);
                    }
                    else{
                        SFYear newYear = new SFYear(year);
                        newYear.addRecord(record);    
                        database.set(yearCount., newYear);
                    }        
                }*/
            
        }
           
        
    }

    /**
     * This method returns the stop and frisk records of a given year where 
     * the people that was stopped was of the specified race.
     * 
     * @param year we are only interested in the records of year.
     * @param race we are only interested in the records of stops of people of race. 
     * @return an ArrayList containing all stop and frisk records for people of the 
     * parameters race and year.
     */

    public ArrayList<SFRecord> populationStopped ( int year, String race ) {
        // WRITE YOUR CODE HERE
        
        ArrayList<SFRecord> RecordsByYear = new ArrayList<SFRecord>(); 
        for(SFYear yearCount: database){
            if(yearCount.getcurrentYear() == year){
                for (SFRecord record: yearCount.getRecordsForYear()){
                    if (record.getRace().equals(race)){
                        RecordsByYear.add(record);
                    }
                }
            }
        }

        return RecordsByYear;
    }

    /**
     * This method computes the percentage of records where the person was frisked and the
     * percentage of records where the person was arrested.
     * 
     * @param year we are only interested in the records of year.
     * @return the percent of the population that were frisked and the percent that
     *         were arrested.
     */
    public double[] friskedVSArrested ( int year ) {
        double friskCount = 0;
        double arrCount = 0;
        double[] frisk_and_arr = new double[2];
        ArrayList<SFRecord> targetRecords = new ArrayList<>();
        for(SFYear yearNum: database){
            if ( yearNum.getcurrentYear() == year){
                targetRecords = yearNum.getRecordsForYear();
        for (SFRecord spRecord: targetRecords){
                    if (spRecord.getFrisked()){
                        friskCount++;
                    }
                    if(spRecord.getArrested()){
                        arrCount++;
                    }
                }
            }
        }
        double friskPercent = (friskCount / targetRecords.size())*100;
        double arrPercent = (arrCount / targetRecords.size())*100;
        frisk_and_arr[0] = friskPercent;
        frisk_and_arr[1] = arrPercent;
        

        return frisk_and_arr; // update the return value
    }

    /**
     * This method keeps track of the fraction of Black females, Black males,
     * White females and White males that were stopped for any reason.
     * Drawing out the exact table helps visualize the gender bias.
     * 
     * @param year we are only interested in the records of year.
     * @return a 2D array of percent of number of White and Black females
     *         versus the number of White and Black males.
     */
    public double[][] genderBias ( int year ) {

        // WRITE YOUR CODE HERE
        double[][] getGenderBias = new double[2][3];
        ArrayList<SFRecord> targetRecord = new ArrayList<>();
        double blackPop = 0;
        double whitePop = 0;
        double bFemale = 0;
        double wFemale = 0;
        double bMale = 0;
        double wMale = 0;
        for(SFYear yearNum: database){
            if ( yearNum.getcurrentYear() == year){
                targetRecord = yearNum.getRecordsForYear();
            }
        }
        for(SFRecord record: targetRecord){
            if (record.getRace().equals("B")){
                blackPop++;
                if ( record.getGender().equals("F")){
                    bFemale++;
                }
                else if( record.getGender().equals("M")){
                    bMale++;
                }
                
            }
            if (record.getRace().equals("W")){
                whitePop++;
                if ( record.getGender().equals("F")){
                    wFemale++;
                }
                else if( record.getGender().equals("M")){
                    wMale++;
                }
            }
        }
        getGenderBias[0][0] = ((bFemale/blackPop) * 0.5) * 100;
        getGenderBias[0][1] = ((wFemale/whitePop) * 0.5) * 100;
        getGenderBias[0][2] = getGenderBias[0][0] + getGenderBias[0][1];
        getGenderBias[1][0] = ((bMale/blackPop) * 0.5) * 100;
        getGenderBias[1][1] = ((wMale/whitePop) * 0.5) * 100;
        getGenderBias[1][2] = getGenderBias[1][0] + getGenderBias[1][1];
        
            
        
        return getGenderBias; // update the return value
    }

    /**
     * This method checks to see if there has been increase or decrease 
     * in a certain crime from year 1 to year 2.
     * 
     * Expect year1 to preceed year2 or be equal.
     * 
     * @param crimeDescription
     * @param year1 first year to compare.
     * @param year2 second year to compare.
     * @return 
     */

    public double crimeIncrease ( String crimeDescription, int year1, int year2 ) {
        
        // WRITE YOUR CODE HERE
        double year1TCrime = 0;
        double year2TCrime = 0;
        double year1allCrime = 0;
        double year2allCrime = 0;
        for (SFYear yearNum: database){
            if ( yearNum.getcurrentYear() == year1){
                for ( SFRecord record: yearNum.getRecordsForYear()){
                    if (!record.getDescription().isEmpty()){
                        year1allCrime++;
                    }
                    if (record.getDescription().indexOf(crimeDescription) != -1){
                        year1TCrime++;
                    }
                }
            }
            else if ( yearNum.getcurrentYear() == year2){
                for (SFRecord record: yearNum.getRecordsForYear()){
                    if( !record.getDescription().isEmpty()){
                        year2allCrime++;
                    }
                    if (record.getDescription().indexOf(crimeDescription) != -1){
                        year2TCrime++;
                    }
                }
            }
        }
        year1TCrime = (year1TCrime / year1allCrime) * 100;
        year2TCrime = (year2TCrime/year2allCrime) * 100;
        double compareCrime = year2TCrime - year1TCrime;
        if ( year1>year2){
            compareCrime *= -1;
        }
        
	return compareCrime; // update the return value
    }

    /**
     * This method outputs the NYC borough where the most amount of stops 
     * occurred in a given year. This method will mainly analyze the five 
     * following boroughs in New York City: Brooklyn, Manhattan, Bronx, 
     * Queens, and Staten Island.
     * 
     * @param year we are only interested in the records of year.
     * @return the borough with the greatest number of stops
     */
    public String mostCommonBorough ( int year ) {

        // WRITE YOUR CODE HERE
        String targetBorough = "";
        int BKcount = 0;
        int MHTcount = 0;
        int BRXcount = 0;
        int QNScount = 0;
        int SIcount = 0;
        ArrayList<SFRecord> records = new ArrayList<>();
        for (SFYear yearsOfRecords: database){
            if ( yearsOfRecords.getcurrentYear() == year){
                records = yearsOfRecords.getRecordsForYear();
            }
            for(SFRecord individualRecord: records){
                if (individualRecord.getLocation().equalsIgnoreCase("BROOKLYN")){
                    BKcount++;
                }
                else if (individualRecord.getLocation().equalsIgnoreCase("MANHATTAN")){
                    MHTcount++;
                }
                else if (individualRecord.getLocation().equalsIgnoreCase("BRONX")){
                    BRXcount++;
                }
                else if (individualRecord.getLocation().equalsIgnoreCase("QUEENS")){
                    QNScount++;
                }
                else if (individualRecord.getLocation().equalsIgnoreCase("STATEN ISLAND")){
                    SIcount++;
                }
            }
        }
        int[] count = {BKcount,MHTcount, BRXcount, QNScount, SIcount};
        String [] boroughs = {"Brooklyn", "Manhattan", "Bronx", "Queens", "Staten Island"};
        
        int max = 0;
        for (int i = 0; i < boroughs.length; i++){
            if (count[i] > max){
                max = count[i];
                targetBorough = boroughs[i];
            }
        }
        return targetBorough; // update the return value
    }

}
