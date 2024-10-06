package tn.esprit.spring.Service;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.TextAlignment;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import tn.esprit.spring.Entity.Compte;
import tn.esprit.spring.Entity.Transaction;
import tn.esprit.spring.Entity.User;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class ReleveService implements IReleveService{

    public byte[] generateRelevePDF(Compte compte) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            // Créer un PdfWriter avec ByteArrayOutputStream
            PdfWriter writer = new PdfWriter(baos);
            // Créer un PdfDocument avec le PdfWriter
            PdfDocument pdfDoc = new PdfDocument(writer);
            // Créer un document pour le PdfDocument
            com.itextpdf.layout.Document document = new Document(pdfDoc);

            // Ajouter les informations du propriétaire
            document.add(new Paragraph("Relevé de Compte")
                    .setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(18));
            document.add(new Paragraph("Nom: " + compte.getProprietaire().getNom()));
            document.add(new Paragraph("Prénom: " + compte.getProprietaire().getPrenom()));
            document.add(new Paragraph("Numéro de Compte: " + compte.getNumeroCompte()));
            document.add(new Paragraph("Solde: " + compte.getSolde()));

            // Ajouter un espace avant les transactions
            document.add(new Paragraph("\n Transactions:"));

            // Ajouter les transactions
            Set<Transaction> transactions = compte.getTransactions();
            if (transactions != null && !transactions.isEmpty()) {
                Table table = new Table(new float[]{4, 4, 4});
                table.addHeaderCell("Date de Transaction");
                table.addHeaderCell("Montant");
                table.addHeaderCell("Compte Destinataire");

                for (Transaction transaction : transactions) {
                    table.addCell(transaction.getDateDeTransaction().toString());
                    table.addCell(String.valueOf(transaction.getMontant()));
                    table.addCell(String.valueOf(transaction.getCompteDestinataire().getNumeroCompte()));
                }
                document.add(table);
            } else {
                document.add(new Paragraph("Aucune transaction trouvée."));
            }

            // Fermer le document
            document.close();

            // Retourner le contenu PDF en tant que tableau d'octets
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
