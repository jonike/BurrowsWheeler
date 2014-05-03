/**
 *
 * @author Webber Huang
 */

public class BurrowsWheeler {  
    private static final int R = 256;
    
    // apply Burrows-Wheeler encoding, reading from standard input and writing to standard output
    public static void encode() {
        // read the input
        String s = BinaryStdIn.readString();
        int N = s.length();
        
        CircularSuffixArray CSA = new CircularSuffixArray(s);
        
        for (int first = 0; first < N; first++) 
            if (CSA.index(first) == 0) {
                BinaryStdOut.write(first);
                break;
            }
        
        for (int i = 0; i < N; i++) 
            BinaryStdOut.write(s.charAt((CSA.index(i) + N - 1) % N));
        
        // print Burrows-Wheeler transform            
        BinaryStdOut.close();
    }

    // apply Burrows-Wheeler decoding, reading from standard input and writing to standard output
    public static void decode() {        
        int first = BinaryStdIn.readInt();
        String t = BinaryStdIn.readString(); 
        int N = t.length();     
        
        // construct the next array with Key-indexed counting
        int[] count = new int[R + 1], next = new int[N];
        for (int i = 0; i < N; i++)
            count[t.charAt(i) + 1]++;
        
        for (int r = 0; r < R; r++)
            count[r + 1] += count[r];
        
        for (int i = 0; i < N; i++) 
            next[count[t.charAt(i)]++] = i;
        
        // decode message
        for (int i = next[first], c = 0; c < N; i = next[i], c++) 
            BinaryStdOut.write(t.charAt(i));              
        BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler encoding
    // if args[0] is '+', apply Burrows-Wheeler decoding
    public static void main(String[] args) {
        if      (args[0].equals("-")) encode();
        else if (args[0].equals("+")) decode();
        else throw new IllegalArgumentException("Illegal command line argument");      
    }
}
