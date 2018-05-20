

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class arithmetic {

   
    
    public static Double start = 0.0 , end = 1.0;
    public static Double [] f = new Double [128];
    public static Double [] LS = new Double [128];
    public static Double [] US = new Double [128];   
    int size;
    public double compress(String s) throws IOException{
        size = s.length();
        Arrays.fill(f,0.0);
        Arrays.fill(LS,0.0);
        Arrays.fill(US,0.0);
        for(int i=0 ; i<size ; i++)f[s.charAt(i)]++;
        
      for(int i=0 ; i<128 ; i++)if(f[i] != 0) f[i] = (f[i]*1.0) / s.length();
      double current_upper = 0.0;
      for(int i=0 ; i<128 ; i++){
          if(f[i]>0){
              LS[i]=current_upper;
              US[i]=current_upper+f[i];
              current_upper=US[i];
              System.out.println((char)i+ " "+LS[i]+" "+US[i]);
          }
      }
      double range = 1 ;
      for(int i=0 ; i<size ; i++){
         end = start + (range) * US[s.charAt(i)];
         start = start + (range) * LS[s.charAt(i)];
         range = end - start;
      }
        //System.out.println(start+ " "+ end);
      FileWriter f  = new FileWriter("D:/arithmetic.txt");
      f.write(Double.toString((start+end)/2.0));
      f.close();
       return (start+end)/2.0;
    }
    
   public String decompress() throws FileNotFoundException{
       String finall="";
       FileReader f=new FileReader("D:/arithmetic.txt");
   	Scanner s=new Scanner(f);
   	double code = 0;
   	while(s.hasNext()) code=s.nextDouble();
   		
       //double code = (start+end)/2.0;
       double lower = 0.0 , upper = 1.0 , range  = 1.0;
       for(int j=0 ; j<size ; j++){
                code = (code - lower) /range ;

         for(int i=0 ; i<128 ; i++){
            if(Double.compare(LS[i], code) <0 && Double.compare(US[i], code)>0 ){
                finall+=(char)i;
                lower=LS[i];
                      upper=US[i];
                     range=upper-lower;
                     break;
             }
           }
         
    }

       
   return finall;}
    public static void main(String[] args) throws IOException {
    	arithmetic x=new arithmetic();
    	x.compress("aaabccaaa");
    	x.decompress();
    	

    }
    
}
