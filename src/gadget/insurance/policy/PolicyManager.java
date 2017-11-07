
/**
 * This is a gadget policy calculator prototype.
 * The program creates the policies using client input.
 * After a policy is created it is stored in a text file and allows the user to search trough it. 
 */

package gadget.insurance.policy;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner; 

/**
 * @author Aleksandra Petkova
 */


public class PolicyManager 
{
    public static void main(String[] args)
    {
        Scanner input = new Scanner(System.in);
        
        boolean dontExit = true;
        
        //Creates an instance of the Policy class
        Policy policy = new Policy();
        
        //1. Show menu & loop until user chooses to exit
        while (dontExit == true)
        {    
            System.out.println("1. Enter new policy");
            System.out.println("2. Display Summary of Policies");
            System.out.println("3. Display Summary of Policies for Selected Month");
            System.out.println("4. Find and display Policy (not started)");
            System.out.println("0. Exit");
            
            int menu = input.nextInt();
            
            if(menu == 0) //Closes program
            {
                dontExit = false; 
                System.out.println( "Program terminated.");
                
            } else if(menu == 1){   //Creates new policy and displays it.
                addPolicy(policy);
                
            } else if(menu == 2){   
                readFile(menu);
                
            } else if(menu == 3){
                readFile(menu);
            }
        }
    }
    
    
    
    //Gets the name from the user, validates and returns it.
    public static String getClientName()    
    {
        //Gets name from user
        Scanner clientDetails = new Scanner(System.in);  
        System.out.println("Please enter your name here e.g, A Petkova: ");
        String clientName = clientDetails.nextLine();
        
        //Checks if the input is correct length.
        while(clientName.length() >  20 )
        {
            System.out.println("Invalid input, name should be less than 20 char long.");
            clientName = clientDetails.nextLine(); 
        }
        
        return clientName;
    }   
    
    
    //Gets the reference number from the user, validates and returns it.
    public static String getRefNumber()
    {
        Scanner inputRef = new Scanner(System.in);
        
        System.out.println("Please enter your reference number: ");
        String refNum = inputRef.nextLine();
        String refNumUpper = refNum.toUpperCase();
        
        //Goes through every character in refNum
        for (int count = 0; count < refNumUpper.length(); count++) {
            
            //Converts the characters of the string to ints from the ascii table.
            int ascii = (int)refNumUpper.charAt(count); 
            
            //Checks if the char is a letter for the 1st, 2nd and last character in the string.
            if((count == 0 || count == 1 || count == 5) && ascii >= 65 && ascii <=90 ) 
            { 
                //Checks if character is a number for the 3rd, 4th and 5th character in the string.
            } else if((count == 2 ||count == 3 || count == 4) && ascii >= 48 && ascii <=57){ 
                
                //validates the input
            } else{
                System.out.println("Invalid reference number, please re-enter, eg: SA863M");
                count = 0;
                refNum = inputRef.nextLine();
                refNumUpper = refNum.toUpperCase();

                int asciii= (int)refNumUpper.charAt(count);
                ascii = asciii;
            }
        }
        
        return refNumUpper;
    }
    
    
    
    //Gets the current date and formats it.
    public static String getCurrentDate()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
        return sdf.format(cal.getTime());
    }
    
    
    
    //Gets the number of items from the user, validates and returns it. 
    public static int getNumItems()
    {
        int numIt;
        
        Scanner inputNum = new Scanner(System.in);
        System.out.println("Please enter the numbers of items (no more than five): ");
        numIt = inputNum.nextInt();
        
        //validates for negative numbers
        while(numIt <= 0)
        {
            System.out.println("Invalid! Please enter a positive number.");
            numIt = inputNum.nextInt();
        }
        
        return numIt;
    }
    
    
    //Validates the user inputs, calculates the excess and the discount and returns them .
    public static double[] getDiscount()
    {
       int excess;
       String excessInc;   // yes/no
       int increaseEx = 0;  //Increased with how much
       
       double discount = 0;
       
       Scanner inputCalc = new Scanner(System.in);
       
       System.out.println("The excess per claim is £30 it can go up to £70. Do you wish to increase it? Select Y/N: ");
       excessInc = inputCalc.next();
       
        
        while (!excessInc.equalsIgnoreCase("y") && !excessInc.equalsIgnoreCase("n"))
        {
            System.out.println("Invalid input, please enter Y/N: ");
            excessInc = inputCalc.next();
        }  
        
        
        if (excessInc.equalsIgnoreCase("y") )
        {
            System.out.println("Do you want to increase it by £10, £20, £30 or £40?");
            increaseEx = inputCalc.nextInt();
            
            //Gets discount according to the increase in excess.
            if(increaseEx == 10){
                discount = 0.05;
                
            } else if(increaseEx == 20){
                discount = 0.1;
                
            } else if(increaseEx == 30){
                discount = 0.15;
                
            } else if(increaseEx == 40){
                discount = 0.2; 
            }else{
                
                //Checks if the user input is in the right range and in multiples of 10.
                for (increaseEx = 0; increaseEx < 10 || increaseEx > 40; increaseEx += 10){
                    
                    System.out.println("Invalid! The excess can only be increased in increments of 10, between £10 and £40. \n"
                            + "Your excess is currently £30. Please re-enter the increase you wish to apply!");
                    
                    increaseEx = inputCalc.nextInt();
                }
            }
        }
        
        excess = 30 + increaseEx;
        
        
        double discountContainer [] = {
            excess, discount    
        };
           
        return discountContainer;
    }
    
    
    //Gets the most expensive item from user, calculates the basic premium, validates and returns the basic premium and the limit.
    public static double[] calcBasicPremium()
    {
        Scanner inputNum = new Scanner(System.in);
        
        int numItems = getNumItems();
        
        double basicPremium = 0;
        double limit = 0;

        System.out.println("How much is the most expensive item? ");
        double mExpItem = inputNum.nextInt();
        
        
        //Array that contains the limits and the basic premium
        double[] bPremium = {
        
            550,       800,       1000,
            4.99,      6.15,	  7.30,
            9.99,      12.35,	  14.55,
            14.99,     18.60,	  21.82
        };
        
        
        /**
        * The for loop goes to the array bPremium and checks the 0, 1, and 2 value(limit) using the first if statement.
        * The second if statement checks the basic premium using the number of items.
        * It breaks out of the for when we get the correct value.
        */
        
        for (int count = 0; count < 3; count++)
        {
            if (mExpItem <= bPremium[count])
            {
                limit = bPremium[count];
                
                if(numItems == 1)
                {
                    basicPremium = bPremium[count +3];
                    break;
                    
                } else if(numItems == 2 || numItems == 3)
                {
                    basicPremium = bPremium[count +6];
                    break;
                    
                } else if(numItems == 4 || numItems == 5)
                {
                    basicPremium = bPremium[count +9];
                    break;
                }
            }
        }
        
        
        double[] calc = {
            basicPremium, limit, numItems, mExpItem
        };
        
        return calc;
    }

    
    
    //Gets the term from the user, validates and returns it.
    public static String getTerm()
    {
        //Gets the terms, the excess & the number of items from the customer
        Scanner inputTerm = new Scanner(System.in);
        System.out.println("How do you wish to pay, annual or monthly? ");
        String term = inputTerm.nextLine();
                
        //validates the input of the term
        while(!term.equalsIgnoreCase("Monthly") && !term.equalsIgnoreCase("Annual"))
        {
            System.out.println("Incorrect input, please select either monthly or annual.");
            term = inputTerm.nextLine();
        }
        
        return term;
    }
    
    
    
    //Gets all the data for a policy and encapsulates it into the Policy Class using setters.
    public static void addPolicy(Policy policy)
    {   
        
        //Getting the data that's needed for a single policy.
        String name = getClientName();
        String refNum = getRefNumber();
        String term = getTerm();
        
        double[] tableBsPrem = getDiscount(); 
        double[] arrayDiscount = calcBasicPremium();
        
        double excess = tableBsPrem[0]; //Grabs the excess from the array in getDiscount.
        double discount = tableBsPrem[1]; //Grabs the discount from the array in getDiscount.
        
        double bPremium = arrayDiscount[0]; //Grabs the basic premium from the array in calcBasicPremium.
        double limit = arrayDiscount[1]; //Grabs the basic limit from the array in calcBasicPremium.
        double numItems = arrayDiscount[2];
        double mExpensive = arrayDiscount[3];
        
        
        //Encapsulating all the data
        policy.setName(name);
        policy.setRefNum(refNum);
        policy.setTerm(term);
        policy.setExcess(excess);
        policy.setDiscount(discount);
        policy.setNumItems(numItems);
        policy.setbPremium(bPremium);
        policy.setLimit(limit);
        policy.setMExpensiveIt(mExpensive);
        
        displaySummary(policy);
        appendFile(policy);
    }    
       
    
    
    //Gets all the data needed for a summary  using getters and displays it. 
    public static void displaySummary(Policy policy)
    {
        
        String formatedDate = getCurrentDate();
        
        String name;
        String refNum; 
        String term;
        double excess;
        double bPremium;
        double limit;
        double items;
        double mPremium;
        double aPremium;
        

        //All the data needed for a summary.
        name = policy.getName();
        refNum = policy.getRefNum();
        term = policy.getTerm();
        excess = policy.getExcess();
        bPremium = policy.getbPremium();
        limit = policy.getLimit();
        items = policy.getNumItems();
        
        double[] cPremium = policy.calcPremium();
        mPremium = cPremium[0]; //Grab the monthly premium form the method in the policy class
        aPremium = cPremium[1]; //Grab the annual premium form the method in the policy class
        
        
        //Variables used to format the display.
        String spc = "";
        String reject = "Rejected";
        String strNum;
        String numberOfItems = "";
        
        //variables used to get rid of the .0 in limit
        String lToString;
        char charLimit;
        String temporary = "";
        String limitString;
        
        char intOut;
        String holder = "";

        //if the number of items are valid it uses this array to display them as stings
        String[] sItems = {
        
        "One","Two","Three","Four","Five"
        };
        
        
        double counting = items; 
        
        if(counting >=1 && counting <=5)
        { 
            strNum = "" + counting; //Conversts counting to a string by concatinating it to a blank string and assigns it to a new string

            int ascii = (int)strNum.charAt(0); //Turns the number to an int

            //referencing index of array - input(ascii) - 1(49)             
            numberOfItems = sItems[ascii-49];
            
        //Gets rid of the .0 from the number of items.  
        }else{
            
            //if the input for number of items is invalid.
            
            numberOfItems = "" + counting;
            
            int count = 0;
            
            //grabs every character from the string before the .
            while(numberOfItems.charAt(count) != '.') 
            {
                intOut = numberOfItems.charAt(count);
                
                holder += intOut;
                count++;
            }
            
            numberOfItems = holder;
        }

        
        
       //Gets rid of the .0 from the limit.    
       lToString = "" + limit;
                
       for (int c = 0; lToString.charAt(c) != '.'; c++) 
       {
            charLimit = lToString.charAt(c);
            
            temporary += charLimit;
        }
        limitString = temporary;
        
        
        
        //Foratting and displaying the summary of the policy.
        System.out.println("+=========================================+");
        System.out.println("|                                         |");
        System.out.printf("|  Client: %-30s |", name);
        System.out.println("");
        System.out.println("|                                         |");
        System.out.printf("|    Date: %-16s   Ref: %6s |", formatedDate, refNum);
        System.out.println("");
        System.out.printf("|   Terms: %-17s Items: %5s |", term, numberOfItems);
        System.out.println("");
        System.out.printf("|  Excess: £%-29.2f |", excess);
        System.out.println("");
        System.out.println("|                                         |");
        System.out.printf("| %-22s Limit per %6s |", term, spc );
        System.out.println("");
        if (term.equalsIgnoreCase("Annual")){
            if(bPremium == 0){
                System.out.printf("| Premium: %-13s Gadget: %5s |", reject, reject);
            }else{
                System.out.printf("| Premium: £%-15.2f Gadget: %5s |", aPremium, limitString);
            }
        } else if(term.equals("Monthly") || term.equals("monthly")){
            if(bPremium == 0){
                System.out.printf("| Premium: %-13s Gadget: %5s |", reject, reject);
            }else{
                System.out.printf("| Premium: £%-15.2f Gadget: %5s |", mPremium, limitString);
            }
        }
        System.out.println("");
        System.out.println("|                                         |");
        System.out.println("+=========================================+");   
        
    }
    
    
    //Adds a new policy using the doString method in the Policy Class.
    public static void appendFile(Policy policy)
    {
        
        PrintWriter output = null;
        
        String date = getCurrentDate();
        
        
        //Creates a policy file
        File policyFile = new File("policy.txt");
        try 
        {	
            FileWriter fw = new FileWriter(policyFile, true); 
            output = new PrintWriter(fw);

        } catch (FileNotFoundException e) //File can't be found.
        {
            System.out.println("Problem creating the file! Program closing");
            System.exit(0);  //Terminates the program
        } catch (IOException ex) { //Input or output error.
            System.out.println("Problem creating the file! Program closing");
            System.exit(0);  //Terminates the program
        }

        
        String out = policy.toString(date);
        
        output.print(out); //Appends
        
        output.close(); //Closes the file
    }
    
    
    
    //OPTION 2:
    /**
     * Reads every policy from the selected file.
     * Counts: total policies, total accepted policies, the policies for every month;
     * Calculates: average number of items for accepted policies, average premium for accepted policies;
     * Displays summary of policies.
     */
    public static void readFile(int menu)
    {
    
        int[] pCountByM = {
        0,0,0,0,0,0,0,0,0,0,0,0
        };
        
        Scanner input = null;
        
        String policies = "";
        int totalPolicies = 0;
        double[] averages = {0,0,0,0,0,0,0,0};
        int monthChoice = 0;
        
        
        //Variables used to create substrings of policies.
        int day;
        String month;
        int year;
        String refNum;
        double numItems;
        double mExpensiveIt;
        double excess; 
        double premium;
        String term = "";
        String name;
        
        String fileName = selectFile();
        
        try 
        {
            input = new Scanner(new File(fileName));
            
        } catch (FileNotFoundException e) {
            System.out.println("File doesn't exist");
            System.exit(1);
        }
        
        //Grabs all the data form the file and puts it into the policies variable. 
        while (input.hasNext())
        {
            policies = input.nextLine();
            
            Scanner inputP = new Scanner(policies);
            
            //Breaks down a policy into substrings
            day = inputP.nextInt();
            month = inputP.next();
            year = inputP.nextInt();
            refNum = inputP.next();
            numItems = inputP.nextDouble();
            mExpensiveIt = inputP.nextDouble();
            excess = inputP.nextDouble();
            premium = inputP.nextDouble();
            term = inputP.next();
            name = inputP.nextLine(); 
            
            totalPolicies++;
            
            
            //Calculates the total number of policies per month
            policiesByMonth(month,pCountByM);
            
            //Calculates the number of accepted policies, average number of items and average premium and displays a summary
            calcAverages(numItems, premium, term, menu, pCountByM, totalPolicies, month, averages);

        }
        
        summaryDisplay(menu, pCountByM, totalPolicies, (int) averages[5], averages[4], (int)averages[6]);
        
        input.close();
    }
    
    
    
    //Asks the user to choose between the archive and the policy file. 
    //It's then used in readFile and summaryForAMonth methods.
    public static String selectFile()
    {
        Scanner kb = new Scanner(System.in);
        
        String fileChoice;
        String fileName = "";
        
        System.out.println("Which file do you want to open?");
        System.out.println("Please enter p for policy.txt or a for archive.txt!");
                
        fileChoice = kb.nextLine();
        
        while(!fileChoice.equalsIgnoreCase("a") && !fileChoice.equalsIgnoreCase("p")){
            System.out.println("Invalid input, please select a or p! ");
            fileChoice = kb.nextLine();
        }
        
        if(fileChoice.equalsIgnoreCase("a")){
            fileName = "archive.txt";
        }else if(fileChoice.equalsIgnoreCase("p")){
            fileName = "policy.txt";
        }
        
        return fileName;
    }
    
    
    
    //Counts the number of policies per month and populates an array
    public static void policiesByMonth(String month, int[] pCountByM)
    {
        if(month.equals("Jan"))
        {
            pCountByM[0]++;    
        }else if(month.equals("Feb")){
            pCountByM[1]++;
        }else if(month.equals("Mar")){
            pCountByM[2]++;
        }else if(month.equals("Apr")){
            pCountByM[3]++;
        }else if(month.equals("May")){
            pCountByM[4]++;
        }else if(month.equals("Jun")){
            pCountByM[5]++;
        }else if(month.equals("Jul")){
            pCountByM[6]++;
        }else if(month.equals("Aug")){
            pCountByM[7]++;
        }else if(month.equals("Sep")){
            pCountByM[8]++;
        }else if(month.equals("Oct")){
            pCountByM[9]++;
        }else if(month.equals("Nov")){
            pCountByM[10]++;
        }else if(month.equals("Dec")){
            pCountByM[11]++;
        }
    }
    
    
    
    //Calculates the average premium and average number of items for OPtion 2 and 3
    public static void calcAverages(double numItems, double premium, String term, int menu, int[] pCountByM, int totalPolicies, String month, double[] averages) 
    {                                    
        Scanner keyboard = new Scanner(System.in);
        
        
        double avgNumItems = 0;
        int avgNumIt = 0;
        double avgMonthlyPrem = 0;
        
        double monthChoice = 0;
        
        String[] monthNames = {"Jan" , "Feb", "Mar", "Apr", "May", "Jun", "Aug", "Sep", "Oct", "Nov", "Dec"};

        if(menu == 2)
        {
            if(!term.equals("R"))
            {
                averages[0]++; //Gets number of accepted policies 
                averages[1] += numItems; //Adds all number of items together and stores them in an array.
                 
                
                //Gets the monthly premium for accepted policies.
                if(term.equals("A")){
                    averages[2] += (premium/12);   
                    
                }else{
                    averages[3] += premium;
                    
                }
                avgMonthlyPrem = (averages[2] + averages[3]) / averages[0]; 
                averages[4] = avgMonthlyPrem ;
                
                avgNumItems = averages[1] /averages[0];
                averages[5] = avgNumItems;
            }
            
        }else if(menu == 3){
            
            //Gets the month
            if (averages[6] == 0){
                System.out.println("Please input the number of the month for the summaries you wish to view. ");
                monthChoice = keyboard.nextDouble();
                averages[6] = monthChoice;

            }
            
            /**
             * Takes only accepted policies and the month read from the file compares it to month names array 
             * which uses the client's input to calculate the index.
             */
            if(!term.equals("R") && month.equalsIgnoreCase(monthNames[(int)averages[6]-1]))
            {
                
                averages[0]++; //Gets number of accepted policies for the month
                averages[1] += numItems;
                
                //Gets the monthly premium for accepted policies.
                if(term.equals("A")){
                    averages[2] += (premium/12);   
                    
                }else{
                    averages[3] += premium;
                    
                }
                avgMonthlyPrem = (averages[2] + averages[3]) / averages[0]; 
                averages[4] = avgMonthlyPrem ;
                
                avgNumItems = averages[1] /averages[0];
                averages[5] = avgNumItems;
            }
        }
        
        
    }
    
    
    
    //Displays summaries for option 2 and 3 depending on what menu option the user chooses
    public static void summaryDisplay(int menu, int[] pCountByM, int totalPolicies, int avgNumIt, double avgMonPrem, int monthChoice)
    {   
        String countMonths = " ";
        int monthCount = 0;
        
        
        if(menu == 2){
            //Goes through the array for total policies by month and adds all values onto a single string.
            for (int count = 0; count < 12; count++) {
            countMonths +=pCountByM[count] + "    ";
            }
            
            System.out.println("Total Number of policies: " + totalPolicies);
            System.out.println("Average number of items (Accepted policies): " + avgNumIt);
            System.out.println("Average Monthly Premium: " + Math.round(avgMonPrem*100.0)/100.0);
            System.out.println("Number of Policies per Month (inc. non-accepted): ");
            System.out.println("");
            System.out.println("Jan  Feb  Mar  Apr  May  Jun  Jul  Aug  Sep  Oct  Nov  Dec");
            System.out.println(countMonths);
            System.out.println(" ");
            
            
        }else if (menu == 3){
            
            //Checks against the selected month and takes out the total number of policies for that specific month.
            for (int count = 0; count < 12; count++) {
                if(monthChoice == count+1){
                    monthCount = pCountByM[count];
                }
            }
            
            System.out.println("Total Number of policies: " + monthCount);
            System.out.println("Average number of items (Accepted policies): " + avgNumIt);
            System.out.println("Average Monthly Premium: " + Math.round(avgMonPrem*100.0)/100.0);
            System.out.println(" ");
        }
    }
}
