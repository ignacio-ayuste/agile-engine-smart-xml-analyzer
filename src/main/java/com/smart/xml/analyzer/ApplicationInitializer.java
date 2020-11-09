package com.smart.xml.analyzer;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.nio.file.Paths;

import static java.util.Objects.requireNonNull;

@Slf4j
public class ApplicationInitializer {

    private static final String MAKE_EVERYTHING_OK_BUTTON_ID = "make-everything-ok-button";
    /**
     * Print Element CSS Selector.
     *
     * @param args
     *  "input_origin_file_path" - path to diff-case HTML file to search a similar element
     *  "input_other_sample_file_path" - path to diff-case HTML file to search a similar element
     *  [target_element_id] -
     * Print the Element CSS Selector.
     * @throws NullPointerException if Elements are not found.
     */
    public static void main(String[] args) {
        if (args.length < 2) {
            log.error("You should specify 2 or 3 args: input_origin_file_path input_other_sample_file_path [target_element_id]");
            return;
        }
        String originalFile = requireNonNull(args[0], "input_origin_file_path required at position 0");
        String changedFile = requireNonNull(args[1], "input_other_sample_file_path required at position 1");
        String elementId = args.length == 3 ? args[2] : MAKE_EVERYTHING_OK_BUTTON_ID;

        Parser htmlParser = new HtmlParser();
        Document sourceDocument = requireNonNull(htmlParser.parse(Paths.get(originalFile)), "can't find source Document");
        Document targetDocument = requireNonNull(htmlParser.parse(Paths.get(changedFile)), "can't find target sample file");
        Element foundElement = htmlParser.findElementById(sourceDocument, targetDocument, elementId);
        log.info("Found element: " + foundElement.cssSelector());
    }
}