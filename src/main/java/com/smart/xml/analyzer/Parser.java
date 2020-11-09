package com.smart.xml.analyzer;

import lombok.NonNull;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.nio.file.Path;

public interface Parser {

    Document parse(Path filePath);

    Element findElementById(@NonNull Document sourceDocument, @NonNull Document targetDocument, @NonNull String sourceElementId);
}
