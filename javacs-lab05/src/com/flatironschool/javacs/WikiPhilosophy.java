package com.flatironschool.javacs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;

import org.jsoup.select.Elements;

public class WikiPhilosophy {

	final static List<String> visited = new ArrayList<String>();
	final static WikiFetcher wf = new WikiFetcher();

	/**
	 * Tests a conjecture about Wikipedia and Philosophy.
	 *
	 * https://en.wikipedia.org/wiki/Wikipedia:Getting_to_Philosophy
	 *
	 * 1. Clicking on the first non-parenthesized, non-italicized link
     * 2. Ignoring external links, links to the current page, or red links
     * 3. Stopping when reaching "Philosophy", a page with no links or a page
     *    that does not exist, or when a loop occurs
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String destination = "https://en.wikipedia.org/wiki/Philosophy";
		String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";

		testConjecture(destination, url, 10);

//        String baseUrl = "https://en.wikipedia.org";
//        String url = "https://en.wikipedia.org/wiki/Java_(programming_language)";
//        int count = 0;
//        while (!url.equals("https://en.wikipedia.org/wiki/Philosophy") && count < 15) {
//            System.out.println("Visiting: " + url);
//            Elements paragraphs = wf.fetchWikipedia(url);
//            Element firstPara = paragraphs.get(0);
//            Iterable<Node> iter = new WikiNodeIterable(firstPara);
//            boolean hasLink = false;
//            for (Node node : iter) {
//                if (node.hasAttr("href")) {
//                    url = baseUrl + node.attr("href");
//                    hasLink = true;
//                    break;
//                }
//            }
//            if (!hasLink) {
//                break;
//            }
//            count++;
//         }

     }

	public static void testConjecture(String destination, String source, int limit) throws IOException {
		String url = source;
		for (int i=0; i<limit; i++) {
			if (visited.contains(url)) {
				System.err.println("We're in a loop, exiting.");
				return;
			} else {
				visited.add(url);
			}
			Element elt = getFirstValidLink(url);
			if (elt == null) {
				System.err.println("Got to a page with no valid links.");
				return;
			}

			System.out.println("**" + elt.text() + "**");
			url = elt.attr("abs:href");

			if (url.equals(destination)) {
				System.out.println("Eureka!");
				break;
			}
		}
	}

	public static Element getFirstValidLink(String url) throws IOException {
		print("Fetching %s...", url);
		Elements paragraphs = wf.fetchWikipedia(url);
		WikiParser wp = new WikiParser(paragraphs);
		Element elt = wp.findFirstLink();
		return elt;
	}

	private static void print(String msg, Object... args) {
		System.out.println(String.format(msg, args));
	}

    // the following throws an exception so the test fails
    // until you update the code
    //String msg = "Complete this lab by adding your code and removing this statement.";
   // throw new UnsupportedOperationException(msg);
}
