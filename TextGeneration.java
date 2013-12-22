import java.io.*;
import java.util.*;

/**
 * Takes in an integer k (the length of each Markov substring), an integer m
 * (the number of characters the program will generate) and a file name and uses 
 * it to generate m characters that sound similar to the original file.
 *
 * @author Sam Mater and Nora Hayes
 * Fall 2012
 */
public class TextGeneration {
	/**
	 * Takes in a file, an integer k which is the size of each Markov, and reads
	 * the file line by line making Markovs out of each unique substring of
	 * length k in the file.
	 * 
	 * @throws FileNotFoundException
	 */
	public static void FrequencyCounter(int k, String s,
			HashMap<String, Markov> markov) throws FileNotFoundException {
		File F = new File(s);
		Scanner console = new Scanner(F);

		String carry = "";
		while (console.hasNextLine()) {
			String line = carry + console.nextLine();
			for (int i = 0; i < line.length() - k; i++) {
				String x = line.substring(i, i + k);
				char c = '^';
				try {
					c = line.charAt(i + k);
				} catch (IndexOutOfBoundsException e) {
					c = '\n';
				}
				if (!markov.containsKey(x)) {
					Markov mark = new Markov(x);
					mark.add();
					mark.add(c);

					markov.put(x, mark);
				} else {
					Markov marks = markov.get(x);
					marks.add();
					marks.add(c);
					markov.put(x, marks);
				}

				if (i + 1 == line.length() - k) {
					carry = line.substring(i + 1, i + k + 1) + "\n";
				}
			}
		}
	}

	/**
	 * Takes in command line arguments k, M, and a file, where k is the number of 
	 * characters in the original text that the program will read in, M is the 
	 * number of characters in the output, and the file is the original text that
	 * you want to use the Markov model to mimic. Text Generation seems to work best
	 * when k is fairly small (20 characters or less).
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		int k = Integer.parseInt(args[0]);
		int M = Integer.parseInt(args[1]);
		String file = args[2];
		String gen = "";

		File F = new File(file);
		Scanner console = new Scanner(F);

		// first make Markov objects out of everything in the file
		HashMap<String, Markov> markov = new HashMap<String, Markov>();
		FrequencyCounter(k, file, markov);

		// to print out the first k characters.
		console = new Scanner(F);
		if (console.hasNext()) {
			String line = console.nextLine();
			int lcount = line.length();

			for (int i = 0; i < k; i++) {
				if (i >= line.length() && lcount < k && console.hasNext()) {
					line = console.next();
				}
				System.out.print(line.charAt(i));
				gen = gen + "" + line.charAt(i) + "";

			}
		}
		String sub = gen;
		// now generate some text!
		for (int i = 0; i < M - k; i++) {
			if (!markov.containsKey(sub)) {
				sub = gen;
				System.out.println();
				System.out.print(sub);
			}
			Markov mar = markov.get(sub);
			char next = mar.random();
			System.out.print(next);
			sub = sub + next;
			sub = sub.substring(1, sub.length());

		}
		System.out.println();
	}

}
