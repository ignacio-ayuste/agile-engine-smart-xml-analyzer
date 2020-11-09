package com.smart.xml.analyzer;

import com.smart.xml.analyzer.exception.ElementNotFoundException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.AbstractMap.SimpleEntry;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Comparator.comparingInt;
import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.joining;

@Slf4j
public class HtmlParser implements Parser {

    @Override
    @SneakyThrows
    public Document parse(Path filePath) {
        return Jsoup.parse(new File(filePath.toUri()), StandardCharsets.UTF_8.name());
    }

    @Override
    public Element findElementById(Document sourceDocument, Document targetDocument, String sourceElementId) {
        Element sourceElement = requireNonNull(sourceDocument.getElementById(sourceElementId), "can't find target element by ID in source document");
        List<Attribute> sourceDocumentElementAttributes = sourceElement.attributes().asList();
        if(sourceDocumentElementAttributes.isEmpty()) throw new IllegalArgumentException("sourceDocumentElementAttributes can't be empty");
        SimpleEntry<Element, List<Attribute>> foundEntry = targetDocument.getAllElements()
                .stream()
                .map(e -> new SimpleEntry<>(e, intersect(e.attributes().asList(), sourceDocumentElementAttributes)))
                .max(comparingInt(entry -> entry.getValue().size()))
                .orElseThrow(() -> new ElementNotFoundException(stringifyAttributes(sourceDocumentElementAttributes)));
        printDiff(sourceDocumentElementAttributes, foundEntry);
        return foundEntry.getKey();
    }

    private void printDiff(List<Attribute> elementAttributes, SimpleEntry<Element, List<Attribute>> foundEntry) {
        log.info("Element attributes: " + stringifyAttributes(elementAttributes));
        log.info("Found Element attributes: " + stringifyAttributes(foundEntry.getValue()));
        log.info("Common Element attributes: " + stringifyAttributes(intersect(elementAttributes, foundEntry.getValue())));
    }

    private String stringifyAttributes(List<Attribute> elementAttributes) {
        return elementAttributes.stream()
                .map(Attribute::toString)
                .collect(joining(", "));
    }

    private <E> List<E> intersect(Collection<E> source, Collection<E> target) {
        return source.stream()
                .filter(target :: contains)
                .collect(Collectors.toList());
    }
}