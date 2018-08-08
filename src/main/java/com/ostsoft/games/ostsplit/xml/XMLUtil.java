package com.ostsoft.games.ostsplit.xml;

import com.ostsoft.games.ostsplit.xml.config.ConfigXML;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XMLUtil {

    public static ConfigXML decodeConfig(String fileName) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConfigXML.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            return (ConfigXML) jaxbUnmarshaller.unmarshal(new FileInputStream(fileName));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void encodeConfig(String fileName, ConfigXML configXML) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(ConfigXML.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty("jaxb.formatted.output", true);
            marshaller.marshal(configXML, new FileOutputStream(fileName));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
