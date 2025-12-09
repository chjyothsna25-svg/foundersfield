package com.project.foundersfeild.service;

import com.lowagie.text.Document;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.project.foundersfeild.model.ideas;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;

@Service
public class pdfgenerator {

    public String generateIdeaPdf(ideas idea) {
        try {
            String fileName = "idea_" + idea.getIdeaId() + ".pdf";
            File file = new File("generated_pdfs/" + fileName);
            file.getParentFile().mkdirs();

            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            document.open();

            document.add(new Paragraph("🔒 Secured Idea Details"));
            document.add(new Paragraph("----------------------------------"));
            document.add(new Paragraph("Title: " + idea.getTitle()));
            document.add(new Paragraph("Description: " + idea.getDescription()));
            document.add(new Paragraph("Owner: " + (idea.getOwner() != null ? idea.getOwner().getName() : "Unknown")));
            document.add(new Paragraph("Created At: " + idea.getCreatedAt()));
            document.add(new Paragraph("Secured: YES"));

            document.close();

            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating PDF";
        }
    }
}
