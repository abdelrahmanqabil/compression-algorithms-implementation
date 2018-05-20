package feed;
import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


class ImageClass {
	
	public static Vector<Integer> readImage(String path){
		
		
		BufferedImage img;
		try {
			img = ImageIO.read(new File(path));
			
			
		int hieght=img.getHeight();
		int width=img.getWidth();
		
		int[][] imagePixels=new int[hieght][width];
		for(int x=0;x<width;x++){
			for(int y=0;y<hieght;y++){
				
				int pixel=img.getRGB(x, y);
				
				int red=(pixel  & 0x00ff0000) >> 16;
				int grean=(pixel  & 0x0000ff00) >> 8;
				int blue=pixel  & 0x000000ff;
				int alpha=(pixel & 0xff000000) >> 24;
				imagePixels[y][x]=red;
			}
		}
		Vector<Integer> v=new Vector<Integer>();
		for(int x=0;x<width;x++){
			for(int y=0;y<hieght;y++){
				v.addElement(imagePixels[x][y]);
			}}
		return v;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	
	public static void writeImage(int[][] imagePixels,String outPath){
		
		BufferedImage image = new BufferedImage(imagePixels.length, imagePixels[0].length, BufferedImage.TYPE_INT_RGB);
	    for (int y= 0; y < imagePixels.length; y++) {
	        for (int x = 0; x < imagePixels[y].length; x++) {
	             int value =-1 << 24;
	             value= 0xff000000 | (imagePixels[y][x]<<16) | (imagePixels[y][x]<<8) | (imagePixels[y][x]);
	             image.setRGB(x, y, value); 

	        }
	    }

	    File ImageFile = new File(outPath);
	    try {
	        ImageIO.write(image, "jpg", ImageFile);
	    } catch (IOException e) {
	        e.printStackTrace();
	    }

		
	}

}

public class feed {
	
	int bits=0,step=0,w,h;

	Vector<Integer> ranges=new Vector<Integer>();  //0-7
	Vector<Integer> deq=new Vector<Integer>();    //q inverse
	Vector<Integer> data=new Vector<Integer>();  
	Vector<Integer> dec=new Vector<Integer>();    //decoded
	Vector<Integer> q=new Vector<Integer>();		//quantization
	Vector<Integer> dq=new Vector<Integer>();		//dequantization
	Vector<Integer> diff=new Vector<Integer>();		//diff

	




	
	
	
public	void calc(int b,Vector<Integer> v){
	bits=b;
int mx=Collections.max(v);
double s=0;
for(int i=0;i<v.size();i++){
	s+=v.elementAt(i);
}
double avg= s/v.size();
step=(int) Math.ceil(((avg+mx)/Math.pow(2, b)));

diff.addElement(v.firstElement());
for(int i=1;i<v.size();i++){
	diff.addElement(Math.abs(v.elementAt(i)-v.elementAt(i-1)));
}

for(int i=0;i<Math.pow(2, b);i++){
	ranges.addElement(step*(i+1)-1);
}

deq.addElement((ranges.elementAt(0)+1)/2);
for(int i=0;i<Math.pow(2, b)-1;i++){
	deq.addElement((ranges.elementAt(i)+ranges.elementAt(i+1)+2)/2);
	
}

}

	
	public int[][] compress(String path,int b) throws IOException{
		ImageClass img= new ImageClass();
		
		
		data=img.readImage(path);
		BufferedImage im=ImageIO.read(new File(path));
		h=im.getHeight();
		w=im.getWidth();
		calc(b,data);

		q.addElement(data.firstElement());
		dq.addElement(data.firstElement());
		dec.addElement(data.firstElement());

		for(int i=1;i<diff.size();i++){
			
			
			for(int j=0;j<ranges.size();j++){
				if(diff.elementAt(i)<=ranges.elementAt(j)){
					q.addElement(j);
					dq.addElement(deq.elementAt(j));
					dec.addElement(dec.lastElement()+dq.lastElement());
				
					break;
					
				}
			}
		}			

		
			
		
		int index=0;
		int[][] a2d= new int[w][h];
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				a2d[i][j]=dec.elementAt(index);
index++;				
				
			}
			
		}

		return a2d;
		
		

	}
	
	
	
	
	
	
	public static void main(String args[]) throws IOException{
		
		
		Scanner s=new Scanner(System.in);
		int b=s.nextInt();
		
feed f=new feed();
int[][] im=new int[f.w][f.h];
im=f.compress("D:/cameraMan.jpg",2);
ImageClass img=new ImageClass();
img.writeImage(im, "D:/c.jpg");



		
		
		
		
		
		
	}
	
	

}
