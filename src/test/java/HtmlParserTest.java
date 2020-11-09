import com.smart.xml.analyzer.HtmlParser;
import com.smart.xml.analyzer.Parser;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(value = Enclosed.class)
public class HtmlParserTest {

    private static final Path RESOURCES_PATH = Paths.get("src/test/resources");
    private static final String SAMPLE_0_ORIGIN_HTML = "sample-0-origin.html";
    private static final String MAKE_EVERYTHING_OK_BUTTON_ID = "make-everything-ok-button";
    private static final Parser htmlParser = new HtmlParser();

    public static class BaseHtmlParserPart {
        @Test(expected = NullPointerException.class)
        public void GIVEN_nullDocument_WHEN_findElementById_THEN_throwsNullPointerException() {
            htmlParser.findElementById(null, null, MAKE_EVERYTHING_OK_BUTTON_ID);
        }

        @Test(expected = NullPointerException.class)
        public void GIVEN_nullAttributes_WHEN_findElementById_THEN_throwsNullPointerException() {
            Document sourceDocument = htmlParser.parse(RESOURCES_PATH.resolve(SAMPLE_0_ORIGIN_HTML));
            htmlParser.findElementById(sourceDocument, null, MAKE_EVERYTHING_OK_BUTTON_ID);
        }

        @Test(expected = IllegalArgumentException.class)
        public void GIVEN_emptyAttributes_WHEN_findElementById_THEN_throwsIllegalArgumentException() {
            Document sourceDocument = htmlParser.parse(RESOURCES_PATH.resolve(SAMPLE_0_ORIGIN_HTML));
            Document targetDocument = htmlParser.parse(RESOURCES_PATH.resolve("sample-1-evil-gemini.html"));
            htmlParser.findElementById(sourceDocument, targetDocument, null);
        }
    }

    @RunWith(Parameterized.class)
    public static class HtmlParserParameterizedPart {
        private final String changedDocumentFileName;
        private final String expectedCssSelector;

        public HtmlParserParameterizedPart(String changedDocumentFileName, String expectedCssSelector) {
            this.changedDocumentFileName = changedDocumentFileName;
            this.expectedCssSelector = expectedCssSelector;
        }

        @Test
        public void GIVEN_validDocument_WHEN_findElementById_THEN_returnsExpectedCssSelector() {
            Document sourceDocument = htmlParser.parse(RESOURCES_PATH.resolve(SAMPLE_0_ORIGIN_HTML));
            Document targetDocument = htmlParser.parse(RESOURCES_PATH.resolve(changedDocumentFileName));
            Element foundElement = htmlParser.findElementById(sourceDocument, targetDocument, MAKE_EVERYTHING_OK_BUTTON_ID);
            assertThat(foundElement.cssSelector(), is(expectedCssSelector));
        }

        @Parameterized.Parameters(name = "{index}: GIVEN_validDocument_WHEN_findElementById_THEN_returnsExpectedCssSelector({0}) = {1}")
        public static Collection<Object[]> data() {
            return Arrays.asList(new Object[][]{
                    {"sample-1-evil-gemini.html", "#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > a.btn.btn-success"},
                    {"sample-2-container-and-clone.html", "#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-body > div.some-container > a.btn.test-link-ok"},
                    {"sample-3-the-escape.html", "#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success"},
                    {"sample-4-the-mash.html", "#page-wrapper > div.row:nth-child(3) > div.col-lg-8 > div.panel.panel-default > div.panel-footer > a.btn.btn-success"},
            });
        }
    }
}