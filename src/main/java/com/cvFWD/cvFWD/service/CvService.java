package com.cvFWD.cvFWD.service;

import com.cvFWD.cvFWD.model.CvModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.util.ArrayList;


@Service
public class CvService {

    @Autowired
    public CvService() {

    }

/*
    public void createCV(CvModel cvModel) {

        try {
            //Path path = Paths.get(ClassLoader.getSystemResource("index.png").toURI());
            Image image = Image.getInstance("./headfwd.png");

            float width = image.getScaledWidth();
            float height = image.getScaledHeight();
            Rectangle page = new Rectangle(width / 2, height / 2);

            Chunk newline = new Chunk(Chunk.NEWLINE);
            Chunk linebreak = new Chunk(new LineSeparator());

            Document document = new Document();
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(cvModel.getFirstName() + "_" + cvModel.getLastName() + ".pdf"));


            List<Font> fonts = new ArrayList<>();
            Font chapterFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);
            Font paragraphFont = FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL);
            Font cvFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLDITALIC);

            fonts.add(chapterFont);
            fonts.add(paragraphFont);

            document.open();


            //paragrahp
            Paragraph CV = new Paragraph("CURRICULUM VITAE", cvFont);
            CV.setSpacingAfter(10f);
            document.add(CV);
            document.add(newline);
            PdfContentByte canvas = writer.getDirectContentUnder();

            Rectangle rect = new Rectangle(50, 750, 250, 800);
            addColumn(writer, rect, false);
            rect = new Rectangle(300, 750, 500, 800);
            addColumn(writer, rect, true);


              e = zijwaards
              f = Hoogte
              b,c = rotatie
              a,d = width, height size

            canvas.addImage(image, 120, 0, 0, 30, 435, 790);

            //Table
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{3, 7});
            PdfPCell cell = userInfo(cvModel, document);
            table.addCell(cell);
            PdfPCell cell2 = summeryInfo(cvModel, document);
            table.addCell(cell2);
            document.add(table);

            //new page
            document.newPage();

            Paragraph workExperince = new Paragraph("Werkervaring", fonts.get(0));
            document.add(workExperince);
            document.add(linebreak);
            workExperince(cvModel, document, fonts);


            Paragraph education = new Paragraph("Opleiding", fonts.get(0));
            document.add(education);
            document.add(linebreak);
            eduation(cvModel, document, fonts);

            Paragraph language = new Paragraph("TalenKennis", fonts.get(0));
            document.add(language);
            document.add(linebreak);
            language(cvModel, document, fonts);

            Paragraph interest = new Paragraph("Interesses", fonts.get(0));
            document.add(interest);
            document.add(linebreak);
            interest(cvModel, document, fonts);


            //Image img = Image.getInstance(path.toAbsolutePath().toString());
            //document.add(img);

            //Footer
            PdfContentByte cb = writer.getDirectContent();
            Phrase footer = new Phrase("this is a footer", fonts.get(0));
            ColumnText.showTextAligned(cb, Element.ALIGN_CENTER,
                    footer,
                    (document.right() - document.left()) / 2 + document.leftMargin(),
                    document.bottom() - 10, 0);


            document.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void interest(CvModel cvModel, Document document, List<Font> fonts) {
        try {
            document.add(new Paragraph("This is the paragraph interest", fonts.get(1)));
        } catch (Exception e) {
            System.out.println("Expextiop in interest: " + e);
        }
    }

    private void language(CvModel cvModel, Document document, List<Font> fonts) {
        try {
            document.add(new Paragraph("This is the paragraph language", fonts.get(1)));
        } catch (Exception e) {
            System.out.println("Expextiop in language: " + e);
        }
    }

    private void eduation(CvModel cvModel, Document document, List<Font> fonts) {
        try {
            document.add(new Paragraph("This is the paragraph education", fonts.get(1)));
        } catch (Exception e) {
            System.out.println("Expextiop in education: " + e);
        }
    }

    private void workExperince(CvModel cvModel, Document document, List<Font> fonts) {
        try {
            //chapter.setNumberDepth(0);
            document.add(new Paragraph("This is the paragraph workexperince", fonts.get(1)));
        } catch (Exception e) {
            System.out.println("Expextiop in Workexperince: " + e);
        }

        return;
    }

    private PdfPCell summeryInfo(CvModel cvModel, Document document) {
        Font font = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, BaseColor.BLACK);

        Chunk newline = new Chunk(Chunk.NEWLINE);
        Chunk linebreak = new Chunk(new LineSeparator());

        PdfPCell cell = new PdfPCell();
        cell.setBorder(Rectangle.NO_BORDER);
        //cell2.setMinimumHeight(50);
        //cell2.setPadding(0f);
        //cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);


        Chunk profile = new Chunk("Profile", font);
        //Profile.setUnderline(1f, -3);


        Paragraph summery = new Paragraph(cvModel.getSummery(), font);
        Chunk skill = new Chunk("Vaardigheden", font);
        Paragraph skillsset = new Paragraph(" Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo" +
                " ligula eget dolor. Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur" +
                " ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa" +
                " quis enim. Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut," +
                " imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt. Cras dapibus." +
                " Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, porttitor eu, consequat vitae," +
                " eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, feugiat a, tellus. Phasellus viverra nulla ut" +
                " metus varius laoreet. Quisque rutrum. Aenean imperdiet. Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi. Nam eget dui."
                , font);


        cell.addElement(profile);
        cell.addElement(linebreak);
        cell.addElement(summery);
        cell.addElement(newline);
        cell.addElement(skill);
        cell.addElement(linebreak);
        cell.addElement(skillsset);
        return cell;
    }

    private PdfPCell userInfo(CvModel cvModel, Document document) {
        Font userInfoFont = new Font(Font.FontFamily.HELVETICA, 10);
        Font headerInfoFont = new Font(Font.FontFamily.TIMES_ROMAN, 10);

        Chunk newline = new Chunk(Chunk.NEWLINE);
        PdfPCell cell = new PdfPCell();
        try {
            Image image = Image.getInstance("./defaultHead.jpg");
            image.scaleToFit(100, 100);

            cell.setBorder(Rectangle.NO_BORDER);
            //cell.setMinimumHeight(50);
            //cell.setPadding(0f);
            //cell.setVerticalAlignment(Element.ALIGN_MIDDLE);


            Paragraph headerFunction = new Paragraph("Functie", headerInfoFont);
            headerFunction.setLeading(0, 1);
            Paragraph valueFunction = new Paragraph(cvModel.getFunction(), userInfoFont);
            valueFunction.setLeading(0, 1);

            Paragraph headerName = new Paragraph("Naam", headerInfoFont);
            headerName.setLeading(0, 1);
            Paragraph valueName = new Paragraph(cvModel.getFirstName() + " " + cvModel.getLastName(), userInfoFont);
            valueName.setLeading(0, 1);

            Paragraph headerCity = new Paragraph("Woonplaats", headerInfoFont);
            headerCity.setLeading(0, 1);
            Paragraph valueCity = new Paragraph(cvModel.getCity(), userInfoFont);
            valueCity.setLeading(0, 1);


            Paragraph headerDateOfBirt = new Paragraph("Geboortedatum", headerInfoFont);
            headerDateOfBirt.setLeading(0, 1);
            Paragraph valueDateOfBirt = new Paragraph(cvModel.getBirthday().toString(), userInfoFont);
            valueDateOfBirt.setLeading(0, 1);


            Paragraph headerNationality = new Paragraph("Nationaliteit", headerInfoFont);
            headerNationality.setLeading(0, 1);
            Paragraph valueNationality = new Paragraph(cvModel.getNationalty(), userInfoFont);
            valueNationality.setLeading(0, 1);

            Paragraph headerDrivingLicence = new Paragraph("Rijbewijs", headerInfoFont);
            headerDrivingLicence.setLeading(0, 1);
            Paragraph valueDrivingLicence = new Paragraph(cvModel.getDrivinglicence(), userInfoFont);
            valueDrivingLicence.setLeading(0, 1);

            Paragraph headerKeywords = new Paragraph("Keywords", headerInfoFont);
            headerKeywords.setLeading(0, 1);
            Paragraph valueKeywords = new Paragraph(cvModel.getKeywords(), userInfoFont);
            valueKeywords.setLeading(0, 1);


            cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            cell.addElement(image);
            cell.addElement(newline);

            cell.addElement(headerFunction);
            cell.addElement(valueFunction);
            cell.addElement(newline);
            cell.addElement(headerName);
            cell.addElement(valueName);
            cell.addElement(newline);
            cell.addElement(headerCity);
            cell.addElement(valueCity);
            cell.addElement(newline);
            //cell.addElement(headerDateOfBirt);
            //cell.addElement(valueDateOfBirt);
            cell.addElement(newline);
            cell.addElement(headerNationality);
            cell.addElement(valueNationality);
            cell.addElement(newline);
            cell.addElement(headerDrivingLicence);
            cell.addElement(valueDrivingLicence);
            cell.addElement(newline);
            cell.addElement(headerKeywords);
            cell.addElement(valueKeywords);

        } catch (Exception e) {
            System.out.println(e);
        }
        return cell;
    }
*/

}
