package es.codeurjc.utility_service.service;

import org.openpdf.text.*;
import org.openpdf.text.pdf.*;
import org.springframework.stereotype.Service;

import es.codeurjc.utility_service.DTOs.PDFCardDTO;
import es.codeurjc.utility_service.DTOs.PDFDeckDTO;

import java.io.OutputStream;
import java.util.List;

@Service
public class PDFService {

    public void exportDecksToPdf(List<PDFDeckDTO> decks, OutputStream outputStream) {
        Document document = new Document(PageSize.A4);
        try {
            PdfWriter.getInstance(document, outputStream);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22);
            Paragraph title = new Paragraph("Mis Mazos Guardados", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(30);
            document.add(title);

            Font deckTitleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 10);
            Font userFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

            for (PDFDeckDTO deck : decks) {
                PdfPTable mainTable = new PdfPTable(new float[]{1.5f, 5f});
                mainTable.setWidthPercentage(100);
                mainTable.setSpacingAfter(15);

                PdfPCell userCell = new PdfPCell();
                userCell.setBorder(PdfPCell.NO_BORDER);
                userCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                if (deck.userImage() != null && deck.userImage().length > 0) {
                    try {
                        Image userImg = Image.getInstance(deck.userImage());
                        userImg.scaleToFit(60, 60);
                        userImg.setAlignment(Element.ALIGN_CENTER);
                        userCell.addElement(userImg);
                    } catch (Exception e) {}
                }

                Paragraph username = new Paragraph(deck.username(), userFont);
                username.setAlignment(Element.ALIGN_CENTER);
                username.setSpacingBefore(5);
                userCell.addElement(username);
                mainTable.addCell(userCell);

                PdfPCell deckCell = new PdfPCell();
                deckCell.setBorder(PdfPCell.NO_BORDER);
                Paragraph deckName = new Paragraph(deck.deckName(), deckTitleFont);
                deckName.setSpacingAfter(5);
                deckCell.addElement(deckName);

                Paragraph desc = new Paragraph(deck.deckDescription(), normalFont);
                desc.setSpacingAfter(15);
                deckCell.addElement(desc);

                PdfPTable cardsTable = new PdfPTable(6);
                cardsTable.setWidthPercentage(100);

                for (PDFCardDTO card : deck.cards()) {
                    PdfPCell cardCell = new PdfPCell();
                    cardCell.setBorder(PdfPCell.NO_BORDER);
                    cardCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cardCell.setPadding(2);

                    if (card.image() != null && card.image().length > 0) {
                        try {
                            Image cardImg = Image.getInstance(card.image());
                            cardImg.scaleToFit(55, 80);
                            cardImg.setAlignment(Element.ALIGN_CENTER);
                            cardCell.addElement(cardImg);
                        } catch (Exception e) {
                            cardCell.addElement(new Paragraph(card.name(), normalFont));
                        }
                    } else {
                        cardCell.addElement(new Paragraph(card.name(), normalFont));
                    }
                    cardsTable.addCell(cardCell);
                }
                cardsTable.completeRow(); 
                deckCell.addElement(cardsTable);
                mainTable.addCell(deckCell);
                document.add(mainTable);
                
                Paragraph separator = new Paragraph("---------------------------------------------------------------------------------------------------------");
                separator.setAlignment(Element.ALIGN_CENTER);
                separator.setSpacingAfter(20);
                document.add(separator);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            if (document.isOpen()) {
                document.close();
            }
        }
    }
}