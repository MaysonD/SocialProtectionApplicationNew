package com.module.helpers;

import com.module.model.entity.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class WordDocumentGenerator {

    public static void generateWordReport(VeteranEntity veteranEntity, File file) {
        if (veteranEntity != null && file != null)
            try {

                XWPFDocument document = new XWPFDocument(WordDocumentGenerator.class.getResourceAsStream("/shablon.docx"));

                /** ---Title Paragraph--- */
                XWPFParagraph titleParagraph = document.createParagraph();
                titleParagraph.setAlignment(ParagraphAlignment.CENTER);
                XWPFRun titleText = titleParagraph.createRun();
                titleText.setFontFamily("Times New Roman");
                titleText.setFontSize(16);
                titleText.setBold(true);
                titleText.setText("УЧЕТНАЯ КАРТОЧКА");

                /** ---Name Paragraph--- */
                String name = "";
                name += veteranEntity.getSecondName() + " " + veteranEntity.getFirstName() + " " + veteranEntity.getMiddleName();
                if (!name.equals("")) {
                    XWPFParagraph nameParagraph = document.createParagraph();
                    nameParagraph.setAlignment(ParagraphAlignment.LEFT);

                    XWPFRun nameLabel = nameParagraph.createRun();
                    nameLabel.setFontFamily("Times New Roman");
                    nameLabel.setFontSize(14);
                    nameLabel.setText("Фамилия, имя, отчество: ");

                    XWPFRun nameValue = nameParagraph.createRun();
                    nameValue.setFontFamily("Times New Roman");
                    nameValue.setFontSize(14);
                    nameValue.setBold(true);
                    nameValue.setCapitalized(true);
                    nameValue.setText(name);
                }

                /** ---Birth Paragraph---*/
                if (veteranEntity.getDateOfBirth() != null) {
                    XWPFParagraph birthParagraph = document.createParagraph();
                    birthParagraph.setAlignment(ParagraphAlignment.LEFT);

                    XWPFRun birthLaber = birthParagraph.createRun();
                    birthLaber.setFontFamily("Times New Roman");
                    birthLaber.setFontSize(14);
                    birthLaber.setText("Дата рождения: ");

                    XWPFRun birthValue = birthParagraph.createRun();
                    birthValue.setFontFamily("Times New Roman");
                    birthValue.setFontSize(14);
                    birthValue.setBold(true);
                    birthValue.setCapitalized(true);
                    birthValue.setText(veteranEntity.getDateOfBirth().toString());
                }

                /** ---Rank Paragraph---*/
                if (veteranEntity.getMilitaryRank() != null) {
                    XWPFParagraph rankParagraph = document.createParagraph();
                    rankParagraph.setAlignment(ParagraphAlignment.LEFT);

                    XWPFRun rankLabel = rankParagraph.createRun();
                    rankLabel.setFontFamily("Times New Roman");
                    rankLabel.setFontSize(14);
                    rankLabel.setText("Воинское звание: ");

                    XWPFRun rankValue = rankParagraph.createRun();
                    rankValue.setFontFamily("Times New Roman");
                    rankValue.setFontSize(14);
                    rankValue.setBold(true);
                    rankValue.setText(veteranEntity.getMilitaryRank().getName());
                }

                /** ---MilitaryTerm Paragraph---*/
                if (veteranEntity.getMilitaryTerms().size() > 0) {
                    String term = "";
                    MilitaryTermEntity militaryTermEntity = veteranEntity.getMilitaryTerms().get(veteranEntity.getMilitaryTerms().size() - 1);
                    term += !militaryTermEntity.getStartOfMilitaryService().toString().equals("") ? "c " + militaryTermEntity.getStartOfMilitaryService().toString() : "";
                    term += !militaryTermEntity.getEndOfMilitaryService().toString().equals("") ? " по " + militaryTermEntity.getEndOfMilitaryService().toString() : "";
                    term += !militaryTermEntity.getCountry().equals("") ? ", " + militaryTermEntity.getCountry() : "";
                    term += !militaryTermEntity.getLocality().equals("") ? ", " + militaryTermEntity.getLocality() : "";
                    term += !militaryTermEntity.getUnit().equals("") ? ", " + militaryTermEntity.getUnit() : "";
                    term += !veteranEntity.getPosition().equals("") ? ", " + veteranEntity.getPosition() : "";

                    if (!term.equals("")) {
                        XWPFParagraph termParagraph = document.createParagraph();
                        termParagraph.setAlignment(ParagraphAlignment.LEFT);

                        XWPFRun termLabel = termParagraph.createRun();
                        termLabel.setFontFamily("Times New Roman");
                        termLabel.setFontSize(14);
                        termLabel.setText("Период и место прохождения службы: ");


                        XWPFRun termValue = termParagraph.createRun();
                        termValue.setFontFamily("Times New Roman");
                        termValue.setFontSize(14);
                        termValue.setBold(true);
                        termValue.setText(term);
                    }
                }

                /** ---Honors Paragraph---*/
                if (veteranEntity.getVeteranHonors().size() > 0) {

                    XWPFParagraph honorParagraph = document.createParagraph();
                    honorParagraph.setAlignment(ParagraphAlignment.LEFT);

                    XWPFRun honorLabel = honorParagraph.createRun();
                    honorLabel.setFontFamily("Times New Roman");
                    honorLabel.setFontSize(14);
                    honorLabel.setText("Награды: ");

                    for (VeteranHonorEntity veteranHonor : veteranEntity.getVeteranHonors()) {
                        if (veteranHonor.getHonor().getName() != null) {
                            XWPFRun honorValue = honorParagraph.createRun();
                            honorValue.setFontFamily("Times New Roman");
                            honorValue.setFontSize(14);
                            honorValue.setBold(true);
                            honorValue.setText(veteranHonor.getHonor().getName() + "; ");
                        }
                    }
                }

                /** ---Wounds Type Paragraph---*/
                if (veteranEntity.getWounds().size() > 0) {
                    XWPFParagraph woundParagraph = document.createParagraph();
                    woundParagraph.setAlignment(ParagraphAlignment.LEFT);

                    XWPFRun woundLabel = woundParagraph.createRun();
                    woundLabel.setFontFamily("Times New Roman");
                    woundLabel.setFontSize(14);
                    woundLabel.setText("Ранения: ");

                    for (VeteranWoundEntity wound : veteranEntity.getWounds()) {
                        if (wound.getWoundType().getType() != null) {
                            XWPFRun woundTypeValue = woundParagraph.createRun();
                            woundTypeValue.setFontFamily("Times New Roman");
                            woundTypeValue.setFontSize(14);
                            woundTypeValue.setBold(true);
                            woundTypeValue.setText(wound.getWoundType().getType() + "; ");
                        }
                    }
                }

                /** ---Wounds Disability Paragraph---*/
                if (veteranEntity.getWounds().size() > 0) {
                    XWPFParagraph woundParagraph = document.createParagraph();
                    woundParagraph.setAlignment(ParagraphAlignment.LEFT);

                    XWPFRun woundLabel = woundParagraph.createRun();
                    woundLabel.setFontFamily("Times New Roman");
                    woundLabel.setFontSize(14);
                    woundLabel.setText("Инвалидности: ");

                    for (VeteranWoundEntity wound : veteranEntity.getWounds()) {
                        if (wound.getWoundDisability().getDisability() != null) {
                            XWPFRun woundDisabilityValue = woundParagraph.createRun();
                            woundDisabilityValue.setFontFamily("Times New Roman");
                            woundDisabilityValue.setFontSize(14);
                            woundDisabilityValue.setBold(true);
                            woundDisabilityValue.setText(wound.getWoundType().getType() + "; ");
                        }
                    }
                }

                /** ---Address Paragraph--- */
                if (veteranEntity.getAddress() != null) {
                    XWPFParagraph addressParagraph = document.createParagraph();
                    addressParagraph.setAlignment(ParagraphAlignment.LEFT);

                    XWPFRun addressLabel = addressParagraph.createRun();
                    addressLabel.setFontFamily("Times New Roman");
                    addressLabel.setFontSize(14);
                    addressLabel.setText("Домашний адрес: ");

                    XWPFRun addressValue = addressParagraph.createRun();
                    addressValue.setFontFamily("Times New Roman");
                    addressValue.setFontSize(14);
                    addressValue.setBold(true);
                    addressValue.setCapitalized(true);
                    addressValue.setText(veteranEntity.getAddress());
                }

                /** ---Phone Paragraph--- */
                if (veteranEntity.getPhoneNumber() != null) {
                    XWPFParagraph phoneParagraph = document.createParagraph();
                    phoneParagraph.setAlignment(ParagraphAlignment.LEFT);

                    XWPFRun phoneLabel = phoneParagraph.createRun();
                    phoneLabel.setFontFamily("Times New Roman");
                    phoneLabel.setFontSize(14);
                    phoneLabel.setText("Номер телефона: ");

                    XWPFRun phoneValue = phoneParagraph.createRun();
                    phoneValue.setFontFamily("Times New Roman");
                    phoneValue.setFontSize(14);
                    phoneValue.setBold(true);
                    phoneValue.setCapitalized(true);
                    phoneValue.setText(veteranEntity.getPhoneNumber());
                }

                /** ---WorkPlace Paragraph---*/
                if (veteranEntity.getWorkPlaces().size() > 0) {
                    String place = "";
                    WorkPlaceEntity workPlaceEntity = veteranEntity.getWorkPlaces().get(veteranEntity.getWorkPlaces().size() - 1);
                    place += !workPlaceEntity.getLocality().equals("") ? workPlaceEntity.getLocality() : "";
                    place += !workPlaceEntity.getOrganization().equals("") ? ", " + workPlaceEntity.getOrganization() : "";
                    place += !workPlaceEntity.getPosition().equals("") ? ", " + workPlaceEntity.getPosition() : "";

                    if (!place.equals("")) {
                        XWPFParagraph placeParagraph = document.createParagraph();
                        placeParagraph.setAlignment(ParagraphAlignment.LEFT);

                        XWPFRun placeLabel = placeParagraph.createRun();
                        placeLabel.setFontFamily("Times New Roman");
                        placeLabel.setFontSize(14);
                        placeLabel.setText("Место работы: ");


                        XWPFRun placeValue = placeParagraph.createRun();
                        placeValue.setFontFamily("Times New Roman");
                        placeValue.setFontSize(14);
                        placeValue.setBold(true);
                        placeValue.setText(place);
                    }
                }

                /** ---Rank Rgvk---*/
                if (veteranEntity.getRgvk() != null) {
                    XWPFParagraph rgvkParagraph = document.createParagraph();
                    rgvkParagraph.setAlignment(ParagraphAlignment.LEFT);

                    XWPFRun rgvkLabel = rgvkParagraph.createRun();
                    rgvkLabel.setFontFamily("Times New Roman");
                    rgvkLabel.setFontSize(14);
                    rgvkLabel.setText("Р(Г)ВК: ");

                    XWPFRun rgvkValue = rgvkParagraph.createRun();
                    rgvkValue.setFontFamily("Times New Roman");
                    rgvkValue.setFontSize(14);
                    rgvkValue.setBold(true);
                    rgvkValue.setText(veteranEntity.getRgvk().getName());
                }

                if (veteranEntity.getPhoto() != null) {
                    XWPFParagraph photo = document.createParagraph();
                    XWPFRun run = photo.createRun();
                    photo.setAlignment(ParagraphAlignment.LEFT);

                    Image image = ImageConverter.convertBytesToImage(veteranEntity.getPhoto());
                    BufferedImage originalImage = SwingFXUtils.fromFXImage(image, null);
                    File outputFile = new File(System.getProperty("user.home") + "/Social Protection Module/saved.jpeg");
                    ImageIO.write(originalImage, "jpeg", outputFile);

                    String imgFile = System.getProperty("user.home") + "/Social Protection Module/saved.jpeg";
                    InputStream inputStream = new FileInputStream(imgFile);
                    run.addPicture(inputStream, XWPFDocument.PICTURE_TYPE_JPEG, imgFile, Units.toEMU(125), Units.toEMU(150));
                    inputStream.close();

                    outputFile.delete();
                }

                document.write(new FileOutputStream(file.getAbsolutePath()));
                document.close();
            } catch (IOException | InvalidFormatException e) {
                e.printStackTrace();
            }
    }
}
