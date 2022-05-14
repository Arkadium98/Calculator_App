public class Suma {

    
    public static Double run(String args){
	String[] arrSplit = args.split(" ");   
        double num1 =Double.parseDouble(arrSplit[1]);
        double num2 =Double.parseDouble(arrSplit[2]);
      return num1 + num2;
    }
    
}