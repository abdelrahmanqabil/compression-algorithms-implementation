package huffman;

import java.util.*;
import java.io.*;

public class huff {
	
    HashMap<String , String> mp = new HashMap<String, String>();
    HashMap<String , String> mpdec = new HashMap<String, String>();
    String ans="";
    public class node{

        node left , right ;
        int freq;
        String code , word ;
        public node(){
          left = right  = null;
          freq = 0;
          code = word = "";
        }
        int getprob(){return freq;}
    }
    void traverse(node x){
        if(x.word.length() == 1){
            mp.put(x.code, x.word);
            mpdec.put(x.word, x.code);
        }
        if(x.left != null){
            x.left.code=x.code+'0';
            traverse(x.left);
        }
        if(x.right != null){
            x.right.code=x.code+'1';
            traverse(x.right);
        }
        if(x.left == null && x.right == null)return;
    }
    int [] f = new int [128];
    public ArrayList<node> n = new ArrayList<node>();
    
    public String compress(String s) throws IOException{
        
        for(int i=0 ; i < (int)s.length(); i++) f[s.charAt(i)]++;
        
        for(int i=0 ; i<128 ; i++){
            if(f[i] != 0){
                node curr = new node();
                curr.freq = f[i];
                curr.word = ""+(char)i;
                n.add(curr);
            }
        }
        
        n.sort(Comparator.comparing(node::getprob));
        
        while((int)n.size() > 1){
            node curr = new node();
            curr.freq = n.get(0).freq + n.get(1).freq;
            curr.left = n.get(0);
            curr.right = n.get(1);
            curr.word = n.get(0).word + n.get(1).word;
            n.remove(n.get(0));
            n.remove(n.get(0));
            n.add(curr);
            n.sort(Comparator.comparing(node::getprob));
        }
        
        traverse(n.get(0));
 
        for(int i=0;i<s.length();i++){
           ans+=mpdec.get(""+s.charAt(i));
        }
        FileWriter f  = new FileWriter("D:/huff.txt");
        f.write(ans);
        f.close();
        
        return ans;
        
        }
        
    
    
    public String decompress() throws FileNotFoundException{
    	FileReader f=new FileReader("D:/huff.txt");
    	Scanner s=new Scanner(f);
    	String ans="";
    	while(s.hasNext()) ans=s.nextLine();
    	
        String temp = "" , dec = "";
        for(int i=0 ; i<(int)ans.length(); i++){
            temp += ans.charAt(i);
            if(mp.containsKey(temp)){
                dec += mp.get(temp);
                temp="";
            }
        }
return dec;
}
    
    public static void main(String[] args) throws IOException {
        huff x = new huff();
        x.compress("aabaacaad");
        System.out.println(x.decompress());
//        for(String key: x.mpdec.keySet())
//        {
//        	System.out.println(key+" "+x.mpdec.get(key));
//        }
        x.decompress();
   
    }
    
}
