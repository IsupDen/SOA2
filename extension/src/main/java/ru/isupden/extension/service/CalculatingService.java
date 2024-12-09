package ru.isupden.extension.service;

import java.io.StringReader;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.ServiceUnavailableException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import ru.isupden.extension.model.City;
import ru.isupden.extension.resource.dto.Page;

public class CalculatingService {
    private final String dataServiceUrl = "https://127.0.0.1:25443/api/v1/city/cities";
    private final Client client = ClientBuilder.newClient();

    public double calculateDistance(long id1, long id2) throws JAXBException {
        City city1 = getCityById(id1);
        City city2 = getCityById(id2);
        return calculateDistance(city1, city2);
    }

    public double calculateDistance() throws JAXBException {
        City city1 = getOldestCity();
        City city2 = getNewestCity();
        return calculateDistance(city1, city2);
    }

    private City getCityById(long id) throws JAXBException {
        var response = client
                .target(dataServiceUrl + "/" + id)
                .request()
                .get();

        String springSourceHeader = response.getHeaderString("X-Spring-Source");

        if (response.getStatus() == 404 && springSourceHeader == null) {
            throw new ServiceUnavailableException();
        }
        if (response.getStatus() != 200) {
            throw new WebApplicationException(response);
        }

        JAXBContext jaxbContext = JAXBContext.newInstance(City.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (City) unmarshaller.unmarshal(new StringReader(response.readEntity(String.class)));
    }

    private City getOldestCity() throws JAXBException {
        try {
            String response = client
                    .target(dataServiceUrl + "?limit=1&sort=creationDate")
                    .request()
                    .get(String.class);

            JAXBContext jaxbContext = JAXBContext.newInstance(Page.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return ((Page) unmarshaller.unmarshal(new StringReader(response))).getCities().get(0);
        } catch (WebApplicationException e) {
//            if (e.getResponse().getHeaders().containsKey("X-Spring-Source")) {
//                throw e;
//            }
//            throw new ServiceUnavailableException();
            throw new ServiceUnavailableException(e.getResponse().getHeaderString("X-Spring-Source"));
        }

    }

    private City getNewestCity() throws JAXBException {
        try {
            String response = client
                    .target(dataServiceUrl + "?limit=1&sort=-creationDate")
                    .request()
                    .get(String.class);

            JAXBContext jaxbContext = JAXBContext.newInstance(Page.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return ((Page) unmarshaller.unmarshal(new StringReader(response))).getCities().get(0);
        } catch (WebApplicationException e) {
//            if (e.getResponse().getHeaders().containsKey("X-Spring-Source")) {
//                throw e;
//            }
            throw new ServiceUnavailableException(e.getResponse().getHeaderString("X-Spring-Source"));
        }

    }

    private double calculateDistance(City city1, City city2) {
        return Math.sqrt(Math.pow(Math.abs(city1.getCoordinates().getX() - city2.getCoordinates().getX()), 2) + Math.pow(Math.abs(city1.getCoordinates().getY() - city2.getCoordinates().getY()), 2));
    }
}
