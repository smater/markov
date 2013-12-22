import java.util.*;

/**
 * @author Sam Mater and  Nora Hayes
 * Fall 2012
 *
 * Contains a random substring, keeps track of its frequency, and
 * outputs a random character according to the Markov model.
 * 
 */
public class Markov {
	String substring;
	int count;
	private TreeMap<Character, Integer> tree;
	ArrayList<Character> chance = new ArrayList<Character>();

	/**
	 * Constructor that takes in a substring, creates a count and treemap of characters that come after the substring.
	 * @param s
	 */
	public Markov(String s) {
		substring = s;
		count = 0;
		tree = new TreeMap<Character, Integer>();
	}

	/**
	 * Increments count by one every time it's called.
	 */
	public void add() {
		count++;
	}

	/**
	 * Adds a char c to the TreeMap, or if it's already in the TreeMap, increments the associated count
	 * it also adds the char to an ArrayList every time it's called, so proportionally there is the right amount of
	 * each following character in it. 
	 * @param c
	 */
	public void add(char c) {
		if (tree.containsKey(c)) {
			int value = tree.get(c);
			tree.put(c, value + 1);
		} else {
			tree.put(c, 1);
		}
		chance.add(c);

	}

	/**
	 * Enables the markov object to be printed in a nice way.
	 */
	public String toString() {
		String longer = "";
		for (Map.Entry<Character, Integer> entry : tree.entrySet()) {
			char c = entry.getKey();
			int value = entry.getValue();
			longer = longer + value + " " + c + "   ";
		}
		return count + "   " + substring + ": " + longer;
	}

	/**
	 * Will return the count of a markov object.
	 * @return count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Will return the substring of a markov object.
	 * @return substring
	 */
	public String getString() {
		return substring;
	}

	/**
	 * This returns the next char c in the text based on the probability that
	 * this character will appear after the Markov object on which random() is called.
	 * @return next character
	 */
	public char random() {
		Random rand = new Random();
		int r = rand.nextInt(chance.size());
		return chance.get(r);
	}

}
