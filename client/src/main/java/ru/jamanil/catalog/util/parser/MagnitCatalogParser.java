package ru.jamanil.catalog.util.parser;

import lombok.RequiredArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import ru.jamanil.catalog.model.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author Victor Datsenko
 * 19.12.2022
 */
@Component
@RequiredArgsConstructor
public class MagnitCatalogParser extends CatalogParser {
    private static final Logger logger = LoggerFactory.getLogger(MagnitCatalogParser.class);
    private static final String URL_CATALOG = "https://dostavka.magnit.ru/catalog/";
    private static final String URL_ITEM = "https://dostavka.magnit.ru";
    public List<Item> parseCatalog(String catalogName) {
        String catalogUrl = URL_CATALOG + catalogName;
        logger.info("Parsing page {}", catalogUrl);
        List<Item> itemList = new ArrayList<>();
        try {
            Document catalogPage = Jsoup.connect(catalogUrl)
                    .userAgent(USER_AGENT)
                    .referrer(REFERRER)
                    .timeout(TIMEOUT)
                    .get();
            Elements catalogElements = getCatalogElements(catalogPage);
            for (Element element : catalogElements) {
                itemList.add(parseItem(element));
            }
            return itemList;
        } catch (IOException e) {
            logger.error("Parsing error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private Item parseItem(Element element) throws IOException {
        String itemUrl = URL_ITEM + element.attr("href");
        Document itemPage = Jsoup.connect(itemUrl)
                .userAgent(USER_AGENT)
                .referrer(REFERRER)
                .timeout(TIMEOUT)
                .get();

        Item item =  Item.builder()
                .id(parseId(itemPage))
                .category(parseCategory(itemPage))
                .name(parseName(itemPage))
                .price(parsePrice(itemPage))
                .discount(parseDiscount(itemPage))
                .description(parseDescription(itemPage))
                .characteristics(parseCharacteristics(itemPage))
                .build();
        logger.info("Parsed Item {}", item);
        return item;
    }

    private Elements getCatalogElements(Document catalogPage) {
        return catalogPage.getElementsByClass("product-card product-list__item");
    }
    private String parseId(Document itemPage) {
        return itemPage.select("div.product-characteristics__elem:nth-child(1) > span:nth-child(2)").text();
    }
    private String parseCategory(Document itemPage) {
        return getStringOrNull(itemPage.select("a.m-breadcrumbs__link:nth-child(2)").text());
    }
    private String parseName(Document itemPage) {
        return getStringOrNull(itemPage.select(".m-page-header__title").text());
    }
    private float parsePrice(Document itemPage) {
        return Float.parseFloat(itemPage.select(".m-price__current > span:nth-child(1)").text());
    }
    private Float parseDiscount(Document itemPage) {
        Float discount = null;
        if (!itemPage.select(".m-price__old > span:nth-child(1)").text().isEmpty()) {
            discount = Float.parseFloat(itemPage.select(".m-price__old > span:nth-child(1)").text().replaceAll(" ₽", "")) - parsePrice(itemPage);
        }
        return discount;
    }
    private String parseDescription(Document itemPage) {
        return getStringOrNull(itemPage.getElementsByClass("product-detail-text").text());
    }
    private Map<String, String> parseCharacteristics(Document itemPage) {
        Elements characteristicElements = itemPage.getElementsByClass("product-characteristics__elem");
        Map<String, String> characteristicMap = new TreeMap<>();
        for (Element characteristicPair : characteristicElements) {
                    characteristicMap.put(characteristicPair.getElementsByClass("product-characteristics__name").text(),
                    characteristicPair.getElementsByClass("product-characteristics__value").text());
        }
        return characteristicMap.isEmpty() ? null : characteristicMap;
    }

    private String getStringOrNull(String string) {
        return string.isEmpty() ? null : string;
    }
}
