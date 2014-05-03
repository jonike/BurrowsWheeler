/**
 *
 * @author Webber Huang
 */

public class CircularSuffixArray {
    private static final short CUTOFF = 15;   // cutoff to insertion sort (any value between 0 and 12)

    private final int[] index;   // index[i] = j means text.substring(j) is ith largest suffix
    private final int N;         // number of characters in text
    
    // circular suffix array of s
    public CircularSuffixArray(String s) {
        N = s.length();
        index = new int[N];
        for (int i = 0; i < N; i++)
            index[i] = i;

        // shuffle
        sort(s, 0, N-1, 0);       
    }
    
    private char charAt(String s, int i, int d) {
        return s.charAt((i + d) % N);
    }
    
    // 3-way string quicksort lo..hi starting at dth character
    private void sort(String s, int lo, int hi, int d) { 
        // cutoff to insertion sort for small subarrays
        if (hi <= lo + CUTOFF) {
            insertion(s, lo, hi, d);
            return;
        }

        int lt = lo, gt = hi, v = charAt(s, index[lo], d), i = lo + 1;
        while (i <= gt) {
            int t = charAt(s, index[i], d);
            if      (t < v) exch(lt++, i++);
            else if (t > v) exch(i, gt--);
            else            i++;
        }

        // a[lo..lt-1] < v = a[lt..gt] < a[gt+1..hi]. 
        sort(s, lo, lt-1, d);
        sort(s, lt, gt, d+1);
        sort(s, gt+1, hi, d);
    }

    // sort from a[lo] to a[hi], starting at the dth character
    private void insertion(String s, int lo, int hi, int d) {
        for (int i = lo; i <= hi; i++) 
            for (int j = i; j > lo && less(s, j, j-1, d); j--) 
                exch(j, j-1);
    }

    // is text[i+d..N) < text[j+d..N) ?
    private boolean less(String s, int i, int j, int d) {
        int q = index[i], p = index[j];
        for (int cnt = d; cnt < N; cnt++) {
            int pv = charAt(s, p, cnt), qv = charAt(s, q, cnt);
            if (qv < pv) return true;
            if (qv > pv) return false;
        }
        return false;
    }

    // exchange index[i] and index[j]
    private void exch(int i, int j) {
        int swap = index[i];
        index[i] = index[j];
        index[j] = swap;
    }

    /**
     * Returns the length of the input string.
     * @return the length of the input string
     */
    public int length() {
        return N;
    }

    /**
     * Returns the index into the original string of the <em>i</em>th smallest suffix.
     * That is, <tt>text.substring(sa.index(i))</tt> is the <em>i</em> smallest suffix.
     * @param i an integer between 0 and <em>N</em>-1
     * @return the index into the original string of the <em>i</em>th smallest suffix
     * @throws java.lang.IndexOutOfBoundsException unless 0 &le; <em>i</em> &lt; <Em>N</em>
     */
    public int index(int i) {
        if (i < 0 || i >= N) throw new IndexOutOfBoundsException();
        return index[i];
    }
    
    public static void main(String[] args) {
    }    
}
