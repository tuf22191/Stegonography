import java.io.*;
import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) throws IOException {


		Steg de =new Steg ();
		//System.out.println(Integer.parseInt("1000000000000000000000000000000", 2));
		
		
//		String binary = "0010101000000000";
//		StringBuilder reverse=new StringBuilder(binary);
//		binary = reverse.reverse().toString();
//		char s =(char)Integer.parseInt(binary, 2);
//		System.out.println(s);
//		
//		
//		
//		
//		//System.exit(0);
//		System.out.println("Hel");
//		Steg df= new Steg();
//		df.hideString("This is how we do ijjkhkjt", "Test5.bmp");
//		
//		
//		File ins = new File("fileToHide.txt");
//		FileInputStream in = new FileInputStream(ins);
//		int readin=0;
//		File out=new File("driverTests.txt");
//		out.delete(); 
//		FileOutputStream outs = new FileOutputStream("driverTests.txt");
//		ArrayList<Integer> listf= new ArrayList<Integer>();
//		ArrayList<Integer> listn=new ArrayList<Integer>();
//		while((readin=in.read())!=-1){
//				listf.add(readin);
//		}
//		
//		int random=0;
//		String stringer;
//		for(int k=0;k<listf.size();k++){
//			System.out.print(listf.get(k) + " " );
//			random = listf.get(k);
//			stringer= Integer.toBinaryString(random);
//            System.out.print(stringer + " ");
//			for(int q=stringer.length()-1;q!=-1;q--){
//				listn.add(new Integer(stringer.charAt(q)=='0'?0:1));
//			}
//			for(int q=8-stringer.length();q!=0;q--){
//				 listn.add(0);
//			}			
//			for(int q=0;q<8;q++){
//				System.out.print(listn.get(k*8+q) + ",");
//			}			
//			
//            
//			System.out.print("\n");
//			//if(k%60==0){
//			//	System.out.print("\n");
//			//}
//		
//		}
//		
//		ArrayList<Byte> realList=new ArrayList<Byte>();
//		byte bite=0;
//		for(int u=0;u<listn.size()/8;u++){
//			for(int y=0;y<8;y++){
//				bite+=(byte)((listn.get(u*8+y))<<y);
//			}
//			realList.add(bite);
//			bite=0;
//		}
//		
//		System.out.println("BYTES" + listn.size());
//		for(int gk=0;gk<realList.size();gk++){
//			System.out.print(" "+ realList.get(gk));
//		}
//		
////		
////		byte byt=0;
////		int g=0; 
////		int counteer=0;
////		while(g<listn.size()){
////			byt=(byte)(listn.get(g)<<counteer);
////			if(counteer==7){counteer=0; realList.add(byt); byt=0;}
////			counteer++;
////			g++;
////		}
////		
////		System.out.println("BYTES" + listn.size());
////		for(int gk=0;gk<realList.size();gk++){
////			System.out.print(" "+ realList.get(gk));
////		}
////		
////		
//		byte[] last = new byte[realList.size()];
//		for (int v=0;v<realList.size();v++){
//			last[v]=(byte)realList.get(v);
//		}
//		
//		
//		outs.write(last);
//		
		
//		System.exit(0);
//		ArrayList<Integer> list =new ArrayList<Integer>();
//		list.add(new Integer(9));
//		list.add(new Integer(10));
//		System.out.println(list.get(0));
//
//		System.out.println(list.get(0));
	
//	System.out.println(de.hideString("OKY DOKY", "Test4.bmp"));
//	System.out.println(de.extractString("stegoTest4.bmp"));
//	
//	String StegImage= de.hideFile("fileToHide.txt", "Test4.bmp");
//		
//	ArrayList<Integer> list =new ArrayList<Integer>();
//	list= de.getExtension("Test4.bmp");
//	for(int k =0; k<list.size();k++){
//		System.out.print(" "+ list.get(k));
//		if(k==15 || k==31 || k== 47 || k==63)
//			System.out.print("    ");
//	}
//	System.out.println("Okay");
//	
//	String s=".bmp";
//	String t="";
//	for(int j=0;j<s.length();j++){
//		t=Integer.toBinaryString((int)s.charAt(j));
//	 System.out.print(t + " ");
//	}
//	
//	for(int j=0;j<s.length();j++){
//	//	t=Integer.toBinaryString((int)s.charAt(j));
//	 System.out.print((int)s.charAt(j) + " ");
//	}
//
//	System.out.println("\n\n");
////	
//    ArrayList<Integer> list= de.getExtension(s, true);
//	
//    for(int k=0;k<list.size();k++){
//    	System.out.print(list.get(k));
//    }
//
//	System.out.println("\n\n");
////	
//	System.out.println("\n\n");
////	
//    
//	String s="none";
//	String t="";
//	for(int j=0;j<s.length();j++){
//		t=Integer.toBinaryString((int)s.charAt(j));
//	 System.out.print(t + " ");
//	}
//	
//	for(int j=0;j<s.length();j++){
//	//	t=Integer.toBinaryString((int)s.charAt(j));
//	 System.out.print((int)s.charAt(j) + " ");
//	}
//	System.out.println("\n\n");
// ArrayList<Integer> listd= de.getExtension("none", true);
//	
//    for(int k=0;k<listd.size();k++){
//    	System.out.print(listd.get(k));
//    }
//    
//    
//    
//	System.out.println("\nGET PAYLOAD SIZE\n");
//
//	 ArrayList<Integer> liste= de.getPayloadSizeFile(101);
//	 
//	    for(int k=0;k<liste.size();k++){
//	    	System.out.print(liste.get(k));
//	    }
//	System.out.println("\n\n\n\n\n");
//	System.out.println(Integer.parseInt("00000000000000000000000001100101",2));
//	
//	
//	System.out.print(de.hideFile("AllBits.txt", "Test7.bmp") );
		 ArrayList<Integer> liste= new ArrayList<Integer>();
		 liste.add(new Integer(3));
		 liste.add(new Integer(4));
		 System.out.println(liste.get(0));

		 System.out.println(liste.get(1));

		 System.out.println(liste.remove(0));

		 System.out.println(liste.get(0));
		
		
		
//		
//		byte[] filer=new byte[1000];
//		for(int k=0;k<filer.length;k++)
//			filer[k]=(byte) 0xFF;
//		File sd=new File("AllBits.txt");
//		FileOutputStream sdf=new FileOutputStream(sd);
//		sdf.write(filer);
//		sdf.flush();
//		sdf.close();
//		
		
		
		
    System.out.print(de.hideFile("fileToHide.txt", "Test7.bmp"));
  System.out.print(de.extractFile("stegoTest7.bmp"));
 	
	
	
	
//		System.out.println("File Hidden in " + de.extractFile("stegoTest4.bmp"));
//		
		
//		System.out.print(de.extractString("stegoTest4.bmp")+ "was the hidden message");
//		
//		FileInputStream ins = new FileInputStream(old);
//		FileInputStream outs = new FileInputStream(newf);
//		
//		System.out.println(outs.hashCode());
	//	705927765

	}

}
