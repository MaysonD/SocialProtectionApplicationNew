package com.module.xml;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;

public class XmlExporter {

    public static void exportDatabaseData(VeteransExchange veterans, File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(VeteransExchange.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(veterans, file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
