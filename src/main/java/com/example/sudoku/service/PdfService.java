package com.example.sudoku.service;

import static java.awt.Color.LIGHT_GRAY;
import static java.awt.Color.WHITE;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_BOLD;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_BOLD_OBLIQUE;
import static org.apache.pdfbox.pdmodel.font.PDType1Font.HELVETICA_OBLIQUE;
import static org.vandeseer.easytable.settings.HorizontalAlignment.CENTER;
import static org.vandeseer.easytable.settings.HorizontalAlignment.LEFT;
import static org.vandeseer.easytable.settings.HorizontalAlignment.RIGHT;
import static org.vandeseer.easytable.settings.VerticalAlignment.TOP;

import java.awt.Color;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;
import org.vandeseer.easytable.TableDrawer;
import org.vandeseer.easytable.settings.VerticalAlignment;
import org.vandeseer.easytable.structure.Row;
import org.vandeseer.easytable.structure.Table;
import org.vandeseer.easytable.structure.Table.TableBuilder;
import org.vandeseer.easytable.structure.cell.TextCell;

@Service
public class PdfService {


	public void createPdf(int[][] grid, float padding, Color backgroundColor1, Color backgroundColor2, 
			Color borderColor, Color thickBorderColor) throws IOException {
		PDDocument document = new PDDocument();

		PDPage page = new PDPage(PDRectangle.A4);

		document.addPage(page);

		PDPageContentStream contentStream = new PDPageContentStream(document, page);

		/*
		contentStream.setFont(PDType1Font.COURIER_BOLD, 12);
		contentStream.setNonStrokingColor(Color.DARK_GRAY);

		contentStream.setLineWidth(25);
		contentStream.beginText();
		contentStream.showText("Hello World");
		contentStream.endText();

		contentStream.moveTo(0, 0);
		contentStream.lineTo(1220, 1220);
		*/

		//contentStream.addRect(200, 650, 100, 100);

		Table table = createSimpleExampleTable(grid, backgroundColor1, backgroundColor2, borderColor, thickBorderColor);
		
        float startY = page.getMediaBox().getHeight() - padding;

		TableDrawer.builder().page(page).contentStream(contentStream).table(table).startX(padding).startY(startY)
				.endY(padding).build().draw(() -> document, () -> new PDPage(PDRectangle.A4), padding);

		contentStream.fill();

		contentStream.close();

		document.save("doc.pdf");

		// Closing the document
		document.close();
	}

	private Table createSimpleExampleTable(int[][] grid, Color backgroundColor1, Color backgroundColor2, 
			Color borderColor, Color thickBorderColor) {

		final TableBuilder tableBuilder = Table.builder().addColumnsOfWidth(20, 20, 20, 20, 20, 20, 20, 20, 20).fontSize(8).font(HELVETICA)
				.borderColor(borderColor);


		// ... and some data rows
		for (int i = 0; i < grid.length; i++) {
			final int[] dataRow = grid[i];
			
			Row row = Row.builder()
					.add(createTextCell(dataRow[0], 1, borderColor, thickBorderColor))
					.add(createTextCell(dataRow[1], 2, borderColor, thickBorderColor))
					.add(createTextCell(dataRow[2], 3, borderColor, thickBorderColor))
					.add(createTextCell(dataRow[3], 4, borderColor, thickBorderColor))
					.add(createTextCell(dataRow[4], 5, borderColor, thickBorderColor))
					.add(createTextCell(dataRow[5], 6, borderColor, thickBorderColor))
					.add(createTextCell(dataRow[6], 7, borderColor, thickBorderColor))
					.add(createTextCell(dataRow[7], 8, borderColor, thickBorderColor))
					.add(createTextCell(dataRow[8], 9, borderColor, thickBorderColor))
                    .backgroundColor(i % 2 == 0 ? backgroundColor1 : backgroundColor2)
                    .borderColor(i == 3 || i == 6 ? thickBorderColor :  borderColor)
					.height(Float.valueOf("20"))
                    .build();

			tableBuilder.addRow(row);
			
		}

		return tableBuilder.build();
	}
	
	private TextCell createTextCell(int value, int index, Color borderColor, Color thickBorderColor) {
		Color color = index == 4 || index == 7 ? thickBorderColor :  borderColor;
		return TextCell.builder()
				.text(value != 0 ? String.valueOf(value) : "")
				.horizontalAlignment(CENTER)
				.verticalAlignment(VerticalAlignment.MIDDLE)
				.borderWidth(1)
				.borderColorRight(borderColor)
				.borderColorLeft(color)
				.build();
	}

}
