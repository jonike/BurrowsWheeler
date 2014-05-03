/**
 *
 * @author Webber Huang
 */

public class MoveToFront {
    private static final int R = 256;
    
    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        // read the input   
        char[] alphabet = alphabetList();

        char c, cnt, tmpIn, tmpOut;
        while (!BinaryStdIn.isEmpty()) {
            c = BinaryStdIn.readChar();
            for (cnt = 0, tmpOut = alphabet[0]; c != alphabet[cnt]; cnt++) {
                tmpIn = alphabet[cnt];
                alphabet[cnt] = tmpOut;
                tmpOut = tmpIn;
            }  
            alphabet[cnt] = tmpOut;
            BinaryStdOut.write(cnt);
            alphabet[0] = c;
        }        
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        char[] alphabet = alphabetList();
        char cnt, c;
        while (!BinaryStdIn.isEmpty()) {            
            cnt = BinaryStdIn.readChar();
            for (c = alphabet[cnt]; cnt > 0; cnt--) 
                alphabet[cnt] = alphabet[cnt-1];
            alphabet[cnt] = c;
            BinaryStdOut.write(c);
        }         
        BinaryStdOut.close();
    }
    
    private static char[] alphabetList() {
        char[] al = new char[R];
        for (char i = 0; i < R; i++)
            al[i] = i;
        return al;
    }

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument"); 
    }
}
