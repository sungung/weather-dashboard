package com.dpworld.weather.messaging;

import com.dpworld.weather.web.model.Forecast;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.stereotype.Service;
import org.springframework.xml.xpath.XPathExpression;
import org.springframework.xml.xpath.XPathExpressionFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author PARK Sungung
 * @since 0.0.1
 */
@Service("bomForecastHttpConverter")
public class BOMForecastHttpConverter implements HttpMessageConverter<Forecast>{

    private List<MediaType> supportedMediaTypes = Collections.emptyList();

    @Override
    public boolean canRead(Class<?> aClass, MediaType mediaType) {
        return Forecast.class.equals(aClass);
    }

    @Override
    public boolean canWrite(Class<?> aClass, MediaType mediaType) {
        return false;
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return supportedMediaTypes;
    }

    @Override
    public Forecast read(Class<? extends Forecast> aClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        Forecast forecast = new Forecast();
        try {
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(inputMessage.getBody());

            forecast.setTimestamp(new Date());
            forecast.setCity(getFirstNodeText(document, "//*[@id=\"container\"]/h1"));
            forecast.setIssued(getFirstNodeText(document, "//*[@id=\"content\"]/p[2]"));

            for (int i = 1; i < 9; i++) {
                if (i == 2) continue;

                Forecast.DailyForecast dailyForecast = forecast.newDailyForecast();
                forecast.getDailyForecasts().add(dailyForecast);

                dailyForecast.setHeader(getFirstNodeText(document, "//*[@id=\"content\"]/div[" + i + "]/h2"));
                dailyForecast.setIcon(getFirstNodeText(document, "//*[@id=\"content\"]/div[" + i + "]/div/dl/dd[1]/img"));
                dailyForecast.setForecast(getFirstNodeText(document, "//*[@id=\"content\"]/div[" + i + "]/div/p"));

                if (i == 1) {
                    dailyForecast.setSummary(getFirstNodeText(document, "//*[@id=\"content\"]/div[" + i + "]/div/dl/dd[3]"));
                    dailyForecast.setMaxTemp(getFirstNodeText(document, "//*[@id=\"content\"]/div[" + i + "]/div/dl/dd[2]/em"));
                } else {
                    dailyForecast.setSummary(getFirstNodeText(document, "//*[@id=\"content\"]/div[" + i + "]/div/dl/dd[4]"));
                    dailyForecast.setMaxTemp(getFirstNodeText(document, "//*[@id=\"content\"]/div[" + i + "]/div/dl/dd[3]/em"));
                    dailyForecast.setMinTemp(getFirstNodeText(document, "//*[@id=\"content\"]/div[" + i + "]/div/dl/dd[2]/em"));
                }
            }

        } catch (Exception e) {
            throw new HttpMessageConversionException("Failed to convert response to: " + aClass, e);
        }
        return forecast;
    }

    private String getFirstNodeText(Document document, String xpath) {
        XPathExpression publishedXp = XPathExpressionFactory.createXPathExpression(xpath);
        List<Node> published = publishedXp.evaluateAsNodeList(document);
        if (!published.isEmpty()) {
            Element element = (Element)published.get(0);
            if (element.getNodeName().equalsIgnoreCase("img")) {
                return element.getAttribute("src");
            } else {
                return element.getTextContent();
            }
        }
        return null;
    }

    @Override
    public void write(Forecast forecast, MediaType mediaType, HttpOutputMessage httpOutputMessage) throws IOException, HttpMessageNotWritableException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
