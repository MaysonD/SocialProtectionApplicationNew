package com.module.xml;

import com.module.database.DatabaseWorker;
import com.module.model.entity.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.LinkedList;
import java.util.List;


public class XmlImporter {

    public static void importDatabaseData(DatabaseWorker databaseWorker, DatabaseChecker databaseChecker, File file) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(VeteransExchange.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            VeteransExchange veteransExchange = (VeteransExchange) jaxbUnmarshaller.unmarshal(file);

            for (VeteranEntity veteran : veteransExchange.getVeterans()) {
                veteran = prepare(veteran, databaseChecker);
                if (veteran != null) {
                    databaseWorker.saveVeteran(veteran);
                }
            }

            System.out.println("Done");
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    private static VeteranEntity prepare(VeteranEntity veteran, DatabaseChecker databaseChecker) {
        List<VeteranHonorEntity> veteranHonorEntityList = new LinkedList<>();
        List<VeteranWoundEntity> veteranWoundEntityList = new LinkedList<>();

        for (VeteranHonorEntity veteranHonorEntity : veteran.getVeteranHonors()) {
            veteranHonorEntity.setHonor(databaseChecker.checkHonor(veteranHonorEntity.getHonor()));
            veteranHonorEntityList.add(veteranHonorEntity);
        }

        for (VeteranWoundEntity veteranWoundEntity : veteran.getWounds()) {
            veteranWoundEntity.setWoundDisability(databaseChecker.checkWoundDisability(veteranWoundEntity.getWoundDisability()));
            veteranWoundEntity.setWoundType(databaseChecker.checkWoundType(veteranWoundEntity.getWoundType()));
            veteranWoundEntityList.add(veteranWoundEntity);
        }

        for (DisplacementEntity displacementEntity : veteran.getDisplacements()) {
            displacementEntity.setVeteran(veteran);
        }

        for (DocumentEntity documentEntity : veteran.getDocuments()) {
            documentEntity.setVeteran(veteran);
        }

        for (FamilyMemberEntity familyMemberEntity : veteran.getFamilyMembers()) {
            familyMemberEntity.setVeteran(veteran);
        }

        for (HelpEntity helpEntity : veteran.getHelpList()) {
            helpEntity.setVeteran(veteran);
        }

        for (MilitaryTermEntity militaryTermEntity : veteran.getMilitaryTerms()) {
            militaryTermEntity.setVeteran(veteran);
        }

        for (VeteranHonorEntity veteranHonorEntity : veteran.getVeteranHonors()) {
            veteranHonorEntity.setVeteran(veteran);
        }

        for (VeteranWoundEntity veteranWoundEntity : veteran.getWounds()) {
            veteranWoundEntity.setVeteran(veteran);
        }

        for (WorkPlaceEntity workPlaceEntity : veteran.getWorkPlaces()) {
            workPlaceEntity.setVeteran(veteran);
        }

        veteran.setMilitaryRank(databaseChecker.checkRank(veteran.getMilitaryRank()));
        veteran.setRgvk(databaseChecker.checkRgvk(veteran.getRgvk()));
        veteran.setVeteranHonors(veteranHonorEntityList);
        veteran.setWounds(veteranWoundEntityList);


        return veteran;
    }
}
