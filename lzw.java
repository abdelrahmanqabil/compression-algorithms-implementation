import java.io.*;
import java.util.*;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class lzw {
    public static Vector<Integer> v = new Vector<Integer>();
    
    public void write(String filename){
        try(FileWriter f  = new FileWriter(filename)){
            for(int i=0 ; i<(int)v.size() ; i++){
                f.write((int)v.elementAt(i) + " ");
            }
            f.close();
        }
        catch (IOException e) 
        {System.out.println("ERROR");} 
    }
    
    public Vector<Integer> Read(String filename){
        Vector<Integer> vi = new Vector<Integer>();
        try(FileReader f = new FileReader (filename)){
        Scanner cin= new Scanner(f);
        while(cin.hasNext())
        {
            int temp=cin.nextInt();
            vi.addElement(temp);
        }
        f.close();
        } catch (IOException ex) {
            System.out.println("Error");
        }
        return vi;
    }
    
    
    
    
    
    
    int index = 128;
    public Vector<Integer> compress(String s){
        HashMap<String , Integer> mp = new HashMap<String, Integer>();
        for(int i=0;i<128;i++) mp.put(Character.toString((char)i),i);
        String temp = "";
        for(int i=0 ; i<(int)s.length() ; i++){
            temp+=s.charAt(i);
            
            if(!mp.containsKey(temp)){
                mp.put(temp, index++);
                temp = temp.substring(0, temp.length()-1);
                v.addElement(mp.get(temp));
                temp = "";
                i--;
                
            }
        }
        if(temp!="") v.addElement(mp.get(temp));
        write("LZW.txt");
        return v;
        
    }
    
    
    
    
    
    
    public String decompress(String filename){
      index = 128;
      HashMap<Integer , String> mp = new HashMap<Integer, String>();
      Vector<Integer> vi=new Vector<Integer>();
      vi = Read(filename);
      
      for(int i=0;i<128;i++) mp.put(i , Character.toString((char)i));
      String last_used = "" , ans = "";
      for(int i=0 ; i<(int)vi.size() ; i++){
          if(!mp.containsKey(vi.elementAt(i))) mp.put(index++, last_used+last_used.substring(0,1));
          
          ans += mp.get(vi.elementAt(i)); //get value
          if(!mp.containsValue(last_used+ mp.get(vi.elementAt(i)).substring(0,1)))
             mp.put(index++,last_used+ mp.get(vi.elementAt(i)).substring(0,1));
  
          last_used = mp.get(vi.elementAt(i));

      }
        return ans;
    }
    
    
    
    
    
    
    public static void main(String[] args) {
        
        gui g =new gui();
        g.setVisible(true);
//        String x;
//        Scanner q = new Scanner(System . in );
//        x = q.nextLine();
//        
//        lzw a = new lzw();
//        a.compress(x);
//        System.out.println(a.decompress("LZW.txt"));
}}
