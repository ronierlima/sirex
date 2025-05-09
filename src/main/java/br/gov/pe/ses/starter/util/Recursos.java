package br.gov.pe.ses.starter.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

public class Recursos {
	
public StreamedContent getImagemIniciaisUsuario(String nomeCompleto) {
        
		try {	
			           
            if (StringUtils.isEmpty(nomeCompleto)) {
				
            	return null;
			}		
			
            String iniciais = getIniciais(nomeCompleto);

            int width = 100, height = 100;
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
           
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.BLUE);  
            g2d.fillOval(0, 0, width, height);
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 40));
            FontMetrics fm = g2d.getFontMetrics();
            int x = (width - fm.stringWidth(iniciais)) / 2;
            int y = (height - fm.getHeight()) / 2 + fm.getAscent();

            g2d.drawString(iniciais, x, y);
            g2d.dispose();
            
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, "png", baos);            
            return DefaultStreamedContent.builder()
                    .stream(() -> new ByteArrayInputStream(baos.toByteArray()))
                    .contentType("image/png")
                    .build();
            
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

	protected String getIniciais(String nomeCompleto) {

		if (!StringUtils.isAllEmpty(nomeCompleto)) {

			String[] parts = nomeCompleto.trim().split("\\s+");
			if (parts.length >= 2) {
				return (parts[0].substring(0, 1) + parts[parts.length - 1].substring(0, 1)).toUpperCase();
			} else if (parts.length == 1) {
				return parts[0].substring(0, 1).toUpperCase();
			}
			return "";
		}

		return "NI";

	}

}
