/**
 * An example class to illustrate readability tests
 * 
 * Michael O'Mahony
 * 20/02/2013
 */

package examples;

import util.com.representqueens.lingua.en.Fathom;
import util.com.representqueens.lingua.en.Readability;


public class ReadabilityExample {
	public static void main(String[] args)
	{
		String str = "It provides amazing picture quality and is perfect for amateur photographers. " +
				"The battery life is truly outstanding, I am not even going to buy a spare battery. " +
				"It's weight is heavier than the D60, but that's not a problem. " +
				"The stereo sound and autofocus are limited, however. " +
				"The viewfinder is also really poor.";
		System.out.println(str + "\n");
		
		Fathom.Stats stats = Fathom.analyze(str); // create an instance of the Fathom.Stats class

		System.out.println("Number of words: " + stats.getNumWords()); // print the number of words
		System.out.println("Number of complex words: " + stats.getNumComplexWords()); // print the number of complex words
		System.out.println("Number of sentences: " + stats.getNumSentences()); // print the number of sentences

		System.out.println("Mean number of syllables per word: " + Readability.syllablesPerWords(stats)); // print the mean number of syllables per word
		System.out.println("Mean mumber of words per sentence: " + Readability.wordsPerSentence(stats)); // print the mean numner of words per sentence

		System.out.println("Fog index: " + Readability.calcFog(stats)); // print Fog index
		System.out.println("Flesch reading ease: " + Readability.calcFlesch(stats)); // print Flesch reading ease
		System.out.println("Flesch-Kincaid grade level: " + Readability.calcKincaid(stats)); // print Flesch-Kincaid grade level
		System.out.println("SMOG grade level: " + Readability.calcSMOG(stats)); // print SMOG grade level
	}
}
