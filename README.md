# Agile Engine Smart XML Analyzer

The program take an original page to collect all the required information about the target element.
Then the program print element in diff-case HTML documents that differs a bit from the original page.

### Technologies

    * Java 8
    * Jsoup
    * slf4j
    * JUnit 4
    * Maven
    
### Build:

    `mvn clean install`

### Examples:

    * Example 1:

        `java -jar target/agile-engine-smart-xml-analyzer-1.0-SNAPSHOT.jar target/test-classes/sample-0-origin.html target/test-classes/sample-1-evil-gemini.html`

        * expected output:

            `Found element: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > a.btn.btn-success`

    * Example 2:

        `java -jar target/agile-engine-smart-xml-analyzer-1.0-SNAPSHOT.jar target/test-classes/sample-0-origin.html target/test-classes/sample-2-container-and-clone.html`

        * expected output:

            ` #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > div.some-container > a.btn.test-link-ok`

    * Example 3:

        `java -jar target/agile-engine-smart-xml-analyzer-1.0-SNAPSHOT.jar target/test-classes/sample-0-origin.html target/test-classes/sample-3-the-escape.html`

        * expected output:

            `Found element: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success`

    * Example 4:

        `java -jar target/agile-engine-smart-xml-analyzer-1.0-SNAPSHOT.jar target/test-classes/sample-0-origin.html target/test-classes/sample-4-the-mash.html`

        * expected output:

            `Found element: #page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success`
            