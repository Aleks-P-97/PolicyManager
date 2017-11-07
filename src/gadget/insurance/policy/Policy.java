package gadget.insurance.policy;


/**
 *
 * @author Aleksandra Petkova
 */
public class Policy 
{
    
    private String name;
    private String refNum;
    private double excess;
    private double discount;
    private double numItems;
    private double bPremium;
    private double limit;
    private String term;
    private double mExpensiveIt;
    

    Policy()
    {  
    }
    
    
    public double[] calcPremium()
    {
        
        double extraDiscount; //If the user pays annualy they get additional discount.
        double annualDiscount;
        double monthlyDiscount;
        double annualPremium = 0;
        double monthlyPremium = 0;  
        

        //Calculates the premium depending on the chosen terms.
        if (term.equalsIgnoreCase("Annual"))
        {  
            
            extraDiscount = 0.1;
              
            annualDiscount = (bPremium *12 ) * (extraDiscount + discount);
            annualPremium = bPremium *12;
            annualPremium -= annualDiscount;
    
        } else if(term.equalsIgnoreCase("Monthly")){ 
           
            monthlyDiscount = bPremium * discount;
            monthlyPremium = bPremium;
            monthlyPremium -= monthlyDiscount;    
        } 
        
        
        double[] premium = {
            monthlyPremium, annualPremium
        };
        
        return premium;
    }
    
    
    
    
    public String toString(String date)
    {

        double mPrem;
        double aPrem;
        double[] prem = calcPremium();
        mPrem = prem[0];
        aPrem = prem[1];
        String fTerm;
        char cTerm;
        
        fTerm = term.toUpperCase();
        cTerm = fTerm.charAt(0);
        
        String aPolicy = "";
        
        
        //Adds all the date of a policy to a String called aPolicy.
        if(term.equalsIgnoreCase("monthly"))
        {
            if((numItems < 1 || numItems > 5) || (mExpensiveIt > 1000) ){
                mPrem = -1;
                cTerm = 'R';
            }
            aPolicy = date +"\t"+ refNum +"\t"+ Math.round(numItems) +"\t"+ Math.round(mExpensiveIt*100.0)/100.0 +"\t"+ Math.round(excess) +"\t"
                    + Math.round(mPrem*100.0)/100.0  +"\t"+ cTerm +"\t"+ name +" \n";
        } else if(term.equalsIgnoreCase("annual")){
            
            if((numItems < 1 || numItems > 5) || (mExpensiveIt > 1000) ){
                aPrem = -1;
                cTerm = 'R';
            }
            aPolicy = date +"\t"+ refNum +"\t"+ Math.round(numItems) +"\t"+ Math.round(mExpensiveIt*100.0)/100.0 +"\t"+ Math.round(excess) +"\t"
                    + Math.round(aPrem*100.0)/100.0 +"\t"+ cTerm +"\t"+ name +" \n";
        }
        
        return aPolicy;
    }
    
    
    
    
    //Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String refNum) {
        this.refNum = refNum;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public double getExcess() {
        return excess;
    }

    public void setExcess(double excess) {
        this.excess = excess;
    }
    
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
    
    public double getNumItems() {
        return numItems;
    }

    public void setNumItems(double numItems) {
        this.numItems = numItems;
    }

    public double getbPremium() {
        return bPremium;
    }

    public void setbPremium(double bPremium) {
        this.bPremium = bPremium;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(double limit) {
        this.limit = limit;
    }
    
    public double getMExpensiveIt() {
        return mExpensiveIt;
    }
    
    public void setMExpensiveIt(double mExpensiveIt) {
        this.mExpensiveIt = mExpensiveIt;
    }
}