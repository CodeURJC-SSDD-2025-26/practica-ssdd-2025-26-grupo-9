package es.codeujrc.distribuidos.service;

import org.openpdf.text.Document;
import org.openpdf.text.DocumentException;
import org.openpdf.text.Element;
import org.openpdf.text.Font;
import org.openpdf.text.FontFactory;
import org.openpdf.text.Image;
import org.openpdf.text.PageSize;
import org.openpdf.text.Paragraph;
import org.openpdf.text.pdf.PdfPCell;
import org.openpdf.text.pdf.PdfPTable;
import org.openpdf.text.pdf.PdfWriter;

import es.codeujrc.distribuidos.entity.Card;
import es.codeujrc.distribuidos.entity.Deck;

import org.springframework.stereotype.Service;

import java.io.OutputStream;
import java.util.List;

@Service
public class PDFService {

    public void exportDecksToPdf(List<Deck> decks, OutputStream outputStream) {
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

            for (Deck deck : decks) {
                PdfPTable mainTable = new PdfPTable(new float[]{1.5f, 5f});
                mainTable.setWidthPercentage(100);
                mainTable.setSpacingAfter(15);

                PdfPCell userCell = new PdfPCell();
                userCell.setBorder(PdfPCell.NO_BORDER);
                userCell.setHorizontalAlignment(Element.ALIGN_CENTER);

                try {
                    byte[] userImgBytes = deck.getUser().getImage();
                    if (userImgBytes != null && userImgBytes.length > 0) {
                        Image userImg = Image.getInstance(userImgBytes);
                        userImg.scaleToFit(60, 60);
                        userImg.setAlignment(Element.ALIGN_CENTER);
                        userCell.addElement(userImg);
                    }
                } catch (Exception e) {
                }

                Paragraph username = new Paragraph(deck.getUser().getUsername(), userFont);
                username.setAlignment(Element.ALIGN_CENTER);
                username.setSpacingBefore(5);
                userCell.addElement(username);
                
                mainTable.addCell(userCell);

                PdfPCell deckCell = new PdfPCell();
                deckCell.setBorder(PdfPCell.NO_BORDER);

                Paragraph deckName = new Paragraph(deck.getName(), deckTitleFont);
                deckName.setSpacingAfter(5);
                deckCell.addElement(deckName);

                Paragraph desc = new Paragraph(deck.getDescription(), normalFont);
                desc.setSpacingAfter(15);
                deckCell.addElement(desc);

                PdfPTable cardsTable = new PdfPTable(6);
                cardsTable.setWidthPercentage(100);

                for (Card card : deck.getCards()) {
                    PdfPCell cardCell = new PdfPCell();
                    cardCell.setBorder(PdfPCell.NO_BORDER);
                    cardCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cardCell.setPadding(2);

                    try {
                        byte[] cardImgBytes = card.getImage();
                        if (cardImgBytes != null && cardImgBytes.length > 0) {
                            Image cardImg = Image.getInstance(cardImgBytes);
                            cardImg.scaleToFit(55, 80);
                            cardImg.setAlignment(Element.ALIGN_CENTER);
                            cardCell.addElement(cardImg);
                        } else {
                            cardCell.addElement(new Paragraph(card.getName(), normalFont));
                        }
                    } catch (Exception e) {
                        cardCell.addElement(new Paragraph(card.getName(), normalFont));
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
//Tecnologia implementada gracias a la libreria que hemos sacado del repo
//https://github.com/LibrePDF/OpenPDF