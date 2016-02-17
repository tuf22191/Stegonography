# Stegonography - Written in Java
Basically, with the Steg class, you can hide information in an image. Hide a string in an image using LSB algorithm (and extract the string) or hide a file in an image using LSB algorithm (and extract the file). 

This was a computer assignment for my Cyber Security class assigned to me and my partner. I did most of the coding, and the professor provided the template for the class to be used. But I pretty much coded the contents (except for the return statements for some of the methods) of the methods extractFile(), extractString(), getExtension(), getNumberOfBytesForHiding(), getPayloadSizeFile(),hideString(), hideFile(), retrieveExtension(), retrieveSize, swapLsb(). 

NOTES: 
  - Firstly, the swapLsb might or might not be working, regardless, the only methods you need to use to hide a string in an image is the hideString() method (supply it with the right parameters) and this method returns the name of the file generated. The file gnerated. Just call extractString() on this returned file name string. For hiding a file, use hideFile() and extractFile().
.
  - YOU CAN ONLY HIDE FILES/STRINGS IN A BITMAP IMAGE FILE FORMAT!

