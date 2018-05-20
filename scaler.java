import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
class node{
	
	Vector<Integer> v= new Vector<Integer>();
	double avg=0.0;
	node left=null,right=null;
	int split_left=0,split_right=0;
	void calc_avg(){
		int sum=0;
		for(int i=0;i<v.size();i++){
			sum+=v.elementAt(i);
		}
		avg=sum/v.size();
		split_left=(int) (Math.floor(avg)-1);
		split_right=(int) (Math.floor(avg)+1);
	}
	
	
}
class tree{
	 Vector<Integer>r=new Vector<Integer>();
	node root=null;
	void build(node n,int bits){
		if(bits==0){
			return ;
		}
		node l=new node();
		
		for(int i=0;i<n.v.size();i++){
			if(n.v.elementAt(i)<=n.split_left)
			l.v.addElement(n.v.elementAt(i));
		}
		l.calc_avg();
		n.left=l;
		
		
	node r=new node();
		
		for(int i=0;i<n.v.size();i++){
			if(n.v.elementAt(i)>=n.split_right)
			r.v.addElement(n.v.elementAt(i));
		}
		r.calc_avg();
		n.right=r;
		
		build(n.left, bits-1);
		build(n.right, bits-1);

	}
	
	void get_range(node n){
		if(n.left==null) {
			r.addElement((int) n.avg);
			return;
		}
		get_range(n.left);
		get_range(n.right);
	}
	
}



public class scaler {
	public static Vector<Integer>ranges=new Vector<Integer>();
	public static Vector<Double> table=new Vector<Double>();
	public static Vector<Integer> q = new Vector<Integer>();
	public static Vector<Integer> inverse = new Vector<Integer>();
	private static Scanner s;
	public static String compress(Vector<Integer> v,int bits) throws IOException{
		String comp="";
		
		node n=new node();
		n.v=v;
		n.calc_avg();
		tree t=new tree();
		t.root=n;
		t.build(n, bits);
		t.get_range(t.root);
		ranges=t.r;
		for(int i=0;i<ranges.size()-1;i++){
			table.addElement( (ranges.elementAt(i) + ranges.elementAt(i+1)) / 2.0);
		}
		table.addElement(127.0);
		
		for(int i=0 ; i< (int)v.size() ; i++){
			for(int j = 0 ; j<(int)table.size() ; j++){
				if( v.elementAt(i) < table.elementAt(j) ){
					q.addElement(j);
					break;
				}
			
		}
			for( int j = 0 ; j<(int)table.size() ; j++){
				if( v.elementAt(i) < table.elementAt(j) ){
					inverse.addElement(ranges.elementAt(j));
					break;
				}

		}
		}
//		for(int i=0 ; i<(int)q.size() ; i++)System.out.print(q.elementAt(i) + " ");
//		System.out.println();
//		for(int i=0 ; i<(int)inverse.size() ; i++)System.out.print(inverse.elementAt(i) + " ");
		
		for(int i=0;i<q.size();i++){
			comp+=q.elementAt(i).toString()+" ";
		}
		FileWriter f=new FileWriter("D:/quantizers.txt");
		f.write(comp);
		f.close();
		
		
		return comp;
	}
	static String decomp(){
		String decomp="";
		for(int i=0;i<inverse.size();i++){
			decomp+=inverse.elementAt(i).toString()+" ";
		}
		
		
		return decomp;
	}

	public static void main(String[] args) throws IOException{
		Vector<Integer> in=new Vector<Integer>();

		s = new Scanner(System.in);
		System.out.println("n?");
		

		int n=s.nextInt();
		System.out.println("bits?");
		int b=s.nextInt();

	for(int i=0;i<n;i++) {
		int t=s.nextInt();
		in.addElement(t);
	}
	System.out.println(compress(in,b));
	System.out.println(decomp());

    }
   
        
    }
