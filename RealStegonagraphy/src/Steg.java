import java.io.*;
import java.util.*;

class Steg
{



/**
 * A constant to hold the number of bits per byte
 */
private final int byteLength=8;

/**
 * A constant to hold the number of bits used to store the size of the file extracted
 */
protected final int sizeBitsLength=32;
/**
 * A constant to hold the number of bits used to store the extension of the file extracted
 */
protected final int extBitsLength=64;

 
 /**
Default constructor to create a steg object, doesn't do anything - so we actually don't need to declare it explicitly. Oh well. 
*/

public Steg()
{

}
private char[] payload_char;
private byte[] actual_image; 
private int[] bytes54 = new int[54];
/**
A method for hiding a string in an uncompressed image file such as a .bmp or .png
You can assume a .bmp will be used
@param cover_filename - the filename of the cover image as a string 
@param payload - the string which should be hidden in the cover image.
@return a string which either contains 'Fail' or the name of the stego image which has been 
written out as a result of the successful hiding operation. 
You can assume that the images are all in the same directory as the java files
*/
//TODO you must write this method
public String hideString(String payload, String cover_filename)
{
	//added
	String ext= "none";
	ArrayList<Integer> extensionBits = this.getExtension(ext, true); //returns 64 bits 
	ArrayList<Integer> sizeBits= this.getPayloadSizeFile(payload.length()*16); //returns 32 bits to indicate number of bytes used 
	//added end	
	File insFile=new File(cover_filename);

	FileInputStream ins = null;
	try {
		ins = new FileInputStream(insFile);
	} catch (FileNotFoundException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	//FileOutputStream out = new FileOutputStream("stego" +cover_filename);
	
    //STEP 1:
    //check if there is enough space for header in the file, if there is not, this is not a bmp file!
	int sizeInBits=(int)insFile.length()*8; //taken from FileReader
	sizeInBits=sizeInBits-8*54;
	if(sizeInBits<0){
		System.out.println("The file is too small to be a bmp");
		return "Fail";
	}
	
	
	//STEP 2:
	//BMP HEADER storage into the bytes54[] array 
	int readin;
	int byt54 []= new int[54];
	
	for(int j = 0;j<54;j++){
		try {
			byt54[j]=ins.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//STEP 3:
	//Check if there is enough space to go through with the string hiding
	int sizeInBytes=sizeInBits/8; //sizeInBits already has the Header bits subtracted out
	int numberOfBytesForHiding = getNumberOfBytesForHiding(payload);//using Unicode for the Strings so, each character requires 16 bits!
	if (sizeInBytes<numberOfBytesForHiding){
		System.out.println("Sorry, cannot implement least significant bit algorithm. Need to go to second least most significant bits as well.\n Therefore, please pick a smaller string.");
		return "Fail";
		//System.exit(1); //exit application
	}
	
	//STEP 4: 
	//Get rest of file
	//Stack<Integer> stack=new Stack<Integer>(); 
	ArrayList<Integer> list =new ArrayList<Integer>();
	
	try {
		while((readin=ins.read())!=-1){
		//	stack.push(new Integer(readin));
			list.add(new Integer(readin));
		}
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	int[] restOfBytes=new int[list.size()];// number of elements in the list
	
	for(int j=0;j<restOfBytes.length;j++){
		restOfBytes[j]=(int)list.get(j);//(int)stack.pop();
	}
	
	
	
	//Get String Bits
	int count=0;
	int[] stringBits =new int[payload_char.length*16];
	for(int j=0;j<payload_char.length;j++){
		for(int k=0;k<16;k++){
			stringBits[count]=(payload_char[j]>>k)&0x1;
			count++;
		}
	}
	System.out.println("The lenght of stringBits is : " + stringBits.length);
	
	//Step 4.5 // add the extension and the size bits first 
	//added
	int placeHolder=0;
	for(int j=0; j<32;j++){
		if(	sizeBits.get(j)==1)
		{
			restOfBytes[j]|=0x00000001;
		}else if(sizeBits.get(j)==0){
			restOfBytes[j]&=0xFFFFFFFE;
		}else{
			System.out.println("THIS SHOULD NOT BE HAPPENING!");
		}
	}
	placeHolder=32;
	for(int j=0; j<64;j++){
		if(	extensionBits.get(j)==1)
		{
			restOfBytes[j+placeHolder]|=0x00000001;
		}else if(extensionBits.get(j)==0){
			restOfBytes[j+placeHolder]&=0xFFFFFFFE;
		}else{
			System.out.println("THIS SHOULD NOT BE HAPPENING!");
		}
	}
	
	placeHolder=96; //I think
	//0-31 31+64=95 +1
	//added end
	//set placeHolder to 0 if above does not work out
	//STEP 5: Go through the restOfFile array and do the LSB swapping if needed
	for(int j=0;j<stringBits.length;j++){
//		System.out.print( " "+ stringBits[j]);
//		if((j+1)%16==0){
//			System.out.print("|");
//		}
		if(stringBits[j]==1)
			{
				restOfBytes[j+placeHolder]|=0x00000001;
			}else if(stringBits[j]==0){
				restOfBytes[j+placeHolder]&=0xFFFFFFFE;
			}else{
				System.out.println("THIS SHOULD NOT BE HAPPENING!");
			}
	}
	
	
	//Step 6: Combine the Header byt54 array with the restOfBytes array
	byte[] forWriting=new byte[byt54.length+restOfBytes.length];
	
	for(int j=0; j<byt54.length+restOfBytes.length;j++){
		if(j>=byt54.length){
			forWriting[j]=(byte)restOfBytes[j-byt54.length];
		}else{
			forWriting[j]=(byte)byt54[j];
		}
	}
	
	
	//Step 7: Write to the File
	try {
		FileOutputStream out=new FileOutputStream("stego"+cover_filename);
		out.write(forWriting);
		out.flush();
		out.close();
	} catch ( IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
			//STEP 8:
			return "stego"+cover_filename;
		}

public int getNumberOfBytesForHiding(String payload) {
	payload_char = payload.toCharArray(); 
	return 8*2*payload_char.length; // a char datatype is 16 bits wide, not 8, so multiply by 2
	                                              // we multiply by 8 because 8 bits in a byte
}









//TODO you must write this method
/**
The extractString method should extract a string which has been hidden in the stegoimage
@param the name of the stego image 
@return a string which contains either the message which has been extracted or 'Fail' which indicates the extraction
was unsuccessful
*/
public String extractString(String stego_image)
{
	File insFile=new File(stego_image);

	FileInputStream ins = null;
	try {
		ins = new FileInputStream(insFile);
	} catch (FileNotFoundException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	//FileOutputStream out = new FileOutputStream("stego" +cover_filename);
	
    //STEP 1:
    //check if there is enough space for header in the file, if there is not, this is not a bmp file!
	int sizeInBits=(int)insFile.length()*8; //taken from FileReader
	sizeInBits=sizeInBits-8*54;
	if(sizeInBits<0){
		System.out.println("The File format is invalid");
		return "Fail";
	}
	
	
	//STEP 2:
	//BMP HEADER storage into the bytes54[] array 
	int readin;
	int byt54 []= new int[54];
	
	for(int j = 0;j<54;j++){
		try {
			byt54[j]=ins.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//STEP 3: 
	//Get rest of file
	//Stack<Integer> stack=new Stack<Integer>(); 
	ArrayList<Integer> list =new ArrayList<Integer>();
	
	try {
		while((readin=ins.read())!=-1){
		//	stack.push(new Integer(readin));
			list.add(new Integer(readin));
		}
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	int[] restOfBytes=new int[list.size()];// number of elements in the list
	
	for(int j=0;j<restOfBytes.length;j++){
		restOfBytes[j]=(int)list.get(j);//(int)stack.pop();
	}
	
	
	
	//Step 3.5 get the extension and the size bits
	ArrayList<Integer> extensionBits=new ArrayList<Integer>();
	ArrayList<Integer> sizeBits=new ArrayList<Integer>();
	
	int placeHolder=0;
	for(int j=0;j<32;j++){
		sizeBits.add(new Integer(restOfBytes[j]&0x1));
	}
	placeHolder=32;
	for(int j=0;j<64;j++){
		extensionBits.add(new Integer(restOfBytes[j+placeHolder]&0x1));
	}
	placeHolder=96;
	
	int size=retrieveSize(sizeBits);
	System.out.println("Size is the " + size);
	String extension=retrieveExtension(extensionBits);
	System.out.println("\n The extension is: \"" + extension + "\" . As in, there is no extension, it is only a string and not a file that was hidden. \n\n\n\n\n");
	
	//Extract String
	String actualString="";
	String binary="";
	StringBuilder stringBuilder =new StringBuilder();
	
	for(int j=0;j<size;j++){
		restOfBytes[j+placeHolder]&=0x1;
		binary= binary + restOfBytes[j+placeHolder]; //concatenation
		if(binary.length()==16){
			stringBuilder=new StringBuilder(binary);
			binary=stringBuilder.reverse().toString();
			actualString+=(char)Integer.parseInt(binary, 2);
			binary="";
		}
		
	}
	
	return actualString;
	
	
}

public String retrieveExtension(ArrayList<Integer> extensionBits) {
	// TODO Auto-generated method stub
//	System.out.println("HERE WE GO! Retrieve Extension");
//	for(int q=0;q<extensionBits.size();q++){
//		System.out.print(extensionBits.get(q));
//	}
//
//	System.out.println("\n HERE WE GO!");
	
	String ret="";
	String binary="";
	StringBuilder stringBuilder=new StringBuilder();
	
	for(int j=0;j<extensionBits.size();j++){
		binary=binary + extensionBits.get(j);
		if(binary.length()==16){
			stringBuilder=new StringBuilder(binary);
			binary=stringBuilder.reverse().toString();
			ret = ret+ (char) Integer.parseInt(binary, 2);
			binary="";
		}
	}
	return ret;
}

public int retrieveSize(ArrayList<Integer> sizeBits) {
	// TODO Auto-generated method stub
	int bytesToReadAmount=0;
	String binary="";
	
	for(int j=0;j<sizeBits.size();j++){
		binary=binary + sizeBits.get(j);
	}
	System.out.println(binary.length() + " and binary is "+ binary);
	bytesToReadAmount=(int)Integer.parseInt(binary, 2);
	return bytesToReadAmount;
}

//TODO you must write this method
/**
The hideFile method hides any file (so long as there's enough capacity in the image file) in a cover image

@param file_payload - the name of the file to be hidden, you can assume it is in the same directory as the program
@param cover_image - the name of the cover image file, you can assume it is in the same directory as the program
@return String - either 'Fail' to indicate an error in the hiding process, or the name of the stego image written out as a
result of the successful hiding process
*/
public String hideFile(String file_payload, String cover_image)
{
	try{
	File fileToHide = new File(file_payload);
	int sizeOfPayload= (int)fileToHide.length();
	ArrayList<Integer> sizeBits = this.getPayloadSizeFile((sizeOfPayload*8));
	ArrayList<Integer> extensionBits= this.getExtension(file_payload, false);
	
	File coverImageFile = new File(cover_image);

	int numberOfBytesNeeded= (int)(fileToHide.length()*8) + 32 +64; //Each bit of every byte of the file has to be hidden so, we multiply by 8 for each bit
	 																// ad 32 and 64, for the file size and the file extension respectively 
	int numberOfBytesWeHave= (int)(coverImageFile.length())-54; // how many bytes are available for hiding? Subtract 54 for the header. 
	
	if (numberOfBytesWeHave<numberOfBytesNeeded){
		System.out.println("The file is too big to hide via LSB in the cover image. After using least significant bit, you would have to use second least significant bit");
		return "Fail";
	}
	
	FileInputStream infoToHide=new FileInputStream(fileToHide);
	FileInputStream coverImage=new FileInputStream(coverImageFile);
	
	//STEP 2:
	//BMP HEADER storage into the bytes54[] array 
	int readin;
	int byt54 []= new int[54];
	
	for(int j = 0;j<54;j++){
		try {
			byt54[j]=coverImage.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//STEP 3: Get rest of cover image
ArrayList<Integer> list =new ArrayList<Integer>();
	
	try {
		while((readin=coverImage.read())!=-1){
			list.add(new Integer(readin));
		}
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	int[] restOfBytes=new int[list.size()];// number of elements in the list
	
	for(int j=0;j<restOfBytes.length;j++){
		restOfBytes[j]=(int)list.get(j);
	}
	
	
	//Step 4
	//Get the file that is supposed to be hided
		
		list =new ArrayList<Integer>();
		readin=0;
		try {
			while((readin=infoToHide.read())!=-1){   //readin all the bytes
				list.add(new Integer(readin));
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

                //turn file that is supposed to be hided into a list of bits
	ArrayList<Integer> listn=new ArrayList<Integer>();
            int random=0;
	String stringer;
	for(int k=0;k<list.size();k++){
		System.out.print(list.get(k) + " " );
		random = list.get(k);
		stringer= Integer.toBinaryString(random);
        System.out.print(stringer + " ");
		for(int q=stringer.length()-1;q!=-1;q--){
			listn.add(new Integer(stringer.charAt(q)=='0'?0:1));
		}
		for(int q=8-stringer.length();q!=0;q--){
			 listn.add(0);
		}			
		for(int q=0;q<8;q++){
			System.out.print(listn.get(k*8+q) + ",");
		}			
		
        
		System.out.print("\n");
	
	}



       int[] BitsFromFileToHide=new int[listn.size()];
       for(int j=0;j<BitsFromFileToHide.length;j++){
		BitsFromFileToHide[j]=(int)listn.get(j);
		}

        //add in the size bits
       int placeHolder=0;
       for(int j=0; j<32;j++){
   		if(	sizeBits.get(j)==1)
   		{
   			restOfBytes[j]|=0x00000001;
   		}else if(sizeBits.get(j)==0){
   			restOfBytes[j]&=0xFFFFFFFE;
   		}else{
   			System.out.println("THIS SHOULD NOT BE HAPPENING!");
   		}
   	}
       placeHolder=32;

       //add in the extension bits
   	for(int j=0; j<64;j++){
   		if(	extensionBits.get(j)==1)
   		{
   			restOfBytes[j+placeHolder]|=0x00000001;
   		}else if(extensionBits.get(j)==0){
   			restOfBytes[j+placeHolder]&=0xFFFFFFFE;
   		}else{
   			System.out.println("THIS SHOULD NOT BE HAPPENING!");
   		}
   	}
   	placeHolder=96;
   	
   	
    
       
	//Step 5: Do the LSB swapping if needed
		for(int j=0;j<BitsFromFileToHide.length;j++){
			//System.out.println(BitsFromFileToHide[j]);
			if(BitsFromFileToHide[j]==1)
				{
					restOfBytes[j+placeHolder]|=0x00000001;
				}else if(BitsFromFileToHide[j]==0){
					restOfBytes[j+placeHolder]&=0xFFFFFFFE;
				}else{
					System.out.println("THIS SHOULD NOT BE HAPPENING!");
				}
		}
	
		
		
		
	//STEP 6: Merge the Header and the Cover Image restOfFiles back!
		byte[] forWriting=new byte[byt54.length+restOfBytes.length];
		
		for(int j=0; j<byt54.length+restOfBytes.length;j++){
			if(j>=byt54.length){
				forWriting[j]=(byte)restOfBytes[j-byt54.length];
			}else{
				forWriting[j]=(byte)byt54[j];
			}
		}
		
//		for(int j=0;j<forWriting.length;j++){
//			System.out.println("oh " + forWriting[j]);
//		}
		System.out.println("Length of: " + forWriting.length);
		//Step 7: Write to the File
		try {
			FileOutputStream out=new FileOutputStream("stego"+cover_image);
			out.write(forWriting);
			out.flush();
			out.close();
			return "stego"+cover_image;
		} catch ( IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}	
				//STEP 8:
		return "fail";
	
	}catch(Exception d){
		d.printStackTrace();
	}

	return "fail";
	}



//TODO you must write this method
/**
The extractFile method hides any file (so long as there's enough capacity in the image file) in a cover image

@param stego_image - the name of the file to be hidden, you can assume it is in the same directory as the program
@return String - either 'Fail' to indicate an error in the extraction process, or the name of the file written out as a
result of the successful extraction process
*/
public String extractFile(String stego_image)
{
	File insFile=new File(stego_image);
        
        //IF GET EXTENSION NOT BMP! CHECK
	//STEP 0: Check:



        
    //STEP 1:
    //check if there is enough space for header in the file, if there is not, this is not a bmp file!
	int sizeInBits=(int)insFile.length()*8; //taken from FileReader
	sizeInBits=sizeInBits-8*54;
	if(sizeInBits<0){
		System.out.println("The File format is invalid");
		return "Fail";
	}


        FileInputStream ins = null;
	try {
		ins = new FileInputStream(insFile);
	} catch (FileNotFoundException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	}
	//FileOutputStream out = new FileOutputStream("stego" +cover_filename);
	
	
	
	//STEP 2:
	//BMP HEADER storage into the bytes54[] array 
	int readin;
	int byt54 []= new int[54];
	
	for(int j = 0;j<54;j++){
		try {
			byt54[j]=ins.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//STEP 3: 
	//Get rest of file
	ArrayList<Integer> list =new ArrayList<Integer>();
	
	try {
		while((readin=ins.read())!=-1){
			list.add(new Integer(readin));
		}
	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
           
      for(int j=0;j<list.size();j++){
//
//            System.out.println("oh " + list.get(j));
              list.set(j, new Integer(((int)(list.get(j)))&0x1));
		}
        
        
            //Getting the size and the extension
        ArrayList<Integer> extensionBits=new ArrayList<Integer>();
    	ArrayList<Integer> sizeBits=new ArrayList<Integer>();
       
    	int placeHolder=0;
    	for(int j=0;j<32;j++){
    		sizeBits.add(list.get(j));
    	}
    	placeHolder=32;
    	for(int j=0;j<64;j++){
    		extensionBits.add(list.get(j+placeHolder));
    		//System.out.println(extensionBits.get(j));
    	}
        placeHolder=96;
        int sizeOfHiddenFile=this.retrieveSize(sizeBits); //the sizeOfHiddenFile is in bytes!
        String extension= this.retrieveExtension(extensionBits);
        System.out.println(sizeOfHiddenFile + " needed for the hidden file. So, file is " + sizeOfHiddenFile/8.0 + " bytes");
        System.out.println("extension is " +extension);
        
//  Solid
        
//for (int u=0;u<96;u++){
//	    System.out.println(list.get(u));
//}
//System.out.println("listereere" + list.size());
        
        
        
        
for (int u=0;u<placeHolder;u++){
	list.remove(0);
}
        //comment the Bits loop in the HideFile method an
        //and print the bits from the list, also, check that size is 8000 for hide file

System.out.println( "Break"+ sizeOfHiddenFile);
for (int u=0;u<sizeOfHiddenFile/8;u++){
	System.out.println(list.get(u));
}
System.out.println();


        ArrayList<Byte> realList=new ArrayList<Byte>();
		byte bite=0;
		
		for(int u=0;u<sizeOfHiddenFile/8;u++){
			for(int y=0;y<8;y++){
				bite+=(byte)((list.get(u*8+y))<<y); 
			}
			realList.add(bite);
			bite=0;
		}
		
		
		
		//
		
         byte[] last = new byte[realList.size()];

         System.out.println("last.size " + last.length);
         for (int v=0;v<realList.size();v++){
			last[v]=(byte)realList.get(v);
		}




	String filename="recoveredFile"+ extension;

	
	
	try {
		FileOutputStream out=new FileOutputStream(filename);
		out.write(last);
		out.flush();
		out.close();
	} catch ( IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	
	
return filename;
}
//TODO you must write this method
/**
 * This method swaps the least significant bit with a bit from the filereader
 * @param bitToHide - the bit which is to replace the lsb of the byte of the image
 * @param byt - the current byte
 * @return the altered byte
 */
public int swapLsb(int bitToHide,int byt)
{		
	//bitToHide should be a 1 or 0
	int integerToOperateWith ;
	if(bitToHide==0){
		integerToOperateWith =0xFFFFFFFE;
		byt= byt&integerToOperateWith;
	}
	else{
		integerToOperateWith =0x00000001;
		byt= byt|integerToOperateWith;
	}		
	return byt;
}


//returns how many bytes are used for the payload, it converts int to a list of bits
public ArrayList<Integer> getPayloadSizeFile(int size){
	String temp =Integer.toBinaryString(size);
	System.out.println("Temp is " + temp);
	ArrayList<Integer> list=new ArrayList<>();
	for(int q=32-temp.length();q!=0;q--){
		 list.add(0);
	} //binary string reads from right, so pad with 0's
	
	for(int q=0;q<temp.length();q++){
		list.add(new Integer(temp.charAt(q)=='0'?0:1));
	}
	
	return list;
	
}
//WE ADDED ONTO YOUR CODE
public ArrayList<Integer> getExtension(String name, boolean isString)
{ 
	ArrayList<Integer> list=new ArrayList<Integer>();
	//set up the string

	String ext="";
	if(isString){ext=name;}
	
	else{//check if there is a dot in the file, if not, throw an error. 
	//find the position of the .
	int pos=name.lastIndexOf('.');
	//now create a substring from the . to the end
	ext=name.substring(pos);
	//return this string
	if(ext.length()!=4){
		System.out.println("The file extension is not suitable. The extension is not ");
			System.exit(1);
	}
	}
	
	int character='d';
	for(int j=0;j<4;j++){
		character = (int)ext.charAt(j);
		
		for(int k=0; k<16;k++){
			list.add(new Integer(character & 0x1));  //remember, lsb bit goes in first
			character = character>>1;                  //also, first bit in is the first bit retrieved from the iterator
		}
	}
	System.out.println("HERE WE GO!");
	for(int q=0;q<list.size();q++){
		System.out.print(list.get(q));
	}

	System.out.println("\n HERE WE GO!");
	return list;
}


}