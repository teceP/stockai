package de.mtech.ai.crawler;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import de.mtech.ai.model.FetchedInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.mtech.ai.crawler.Crawler.MARKETWATCH;

@Service(value = MARKETWATCH)
public class MarketwatchCrawler extends BaseCrawler {

    private final static Logger logger = LoggerFactory.getLogger(MarketwatchCrawler.class);

    @Autowired
    private WebClient webClient;

    public List<FetchedInformation> fetchInformation() {
        String searchQuery = "";

        List<FetchedInformation> fetchedInformations = new ArrayList<>();

        String searchUrl = "https://www.marketwatch.com/latest-news";
        try {
            HtmlPage page = webClient.getPage(searchUrl);
            List<HtmlAnchor> items = page.getByXPath("//a[@class='link']");
            items.removeIf(htmlAnchor -> !htmlAnchor.getHrefAttribute().contains("https://www.marketwatch.com/story"));
            items.forEach(item -> logger.trace(item.getHrefAttribute()));

            List<String> articleLinks = items.stream().map(HtmlAnchor::getHrefAttribute).collect(Collectors.toList());
            articleLinks.forEach(articleLink -> {
                try {
                    logger.trace(articleLink);
                    HtmlPage articlePage = webClient.getPage(articleLink);

                    HtmlHeading1 articleHeadline = articlePage.getFirstByXPath("//h1[@class='article__headline']");
                    logger.trace("Headline: " + articleHeadline.getTextContent());

                    HtmlElement articleBody = articlePage.getHtmlElementById("js-article__body");
                    List<HtmlParagraph> paragraphs = articleBody.getByXPath("//p");
                    paragraphs.removeIf(p -> p.hasAttribute("class"));

                    StringBuilder textStringBuilder = new StringBuilder();
                    paragraphs.stream().map(HtmlParagraph::getTextContent).forEach(textStringBuilder::append);
                    logger.trace(textStringBuilder.toString());

                    final String headline = articleHeadline.getTextContent();
                    final String text = textStringBuilder.toString();
                    if (headline != null && !headline.isEmpty() && text != null && !text.isEmpty()) {
                        fetchedInformations.add(new FetchedInformation(headline, text, System.currentTimeMillis()));
                        logger.info(CollectionUtils.lastElement(fetchedInformations).toString());
                        logger.debug("Added new fetched information: " + CollectionUtils.lastElement(fetchedInformations));
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return fetchedInformations;
    }

}
