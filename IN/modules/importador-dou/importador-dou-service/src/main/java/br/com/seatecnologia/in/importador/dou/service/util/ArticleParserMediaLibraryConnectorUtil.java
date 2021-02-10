package br.com.seatecnologia.in.importador.dou.service.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.DatatypeConverter;

import br.com.seatecnologia.in.importador.dou.article.xml.parser.MediaLibraryConnector;

public class ArticleParserMediaLibraryConnectorUtil implements MediaLibraryConnector {
	private String imagesPath;
	
	public ArticleParserMediaLibraryConnectorUtil(String imagesPath) {
		this.imagesPath = imagesPath;
	}
	
	@Override
	public String saveMedia(String filename, String uri) {
		if (uri.startsWith("data:")) {
			String finalUri = convertData(filename, uri, imagesPath);
			if (finalUri.length() > 0) {
				return finalUri;
			}
		}
		return uri;
	}

	private String convertData(String filename, String base64, String imagesPath) {

		String[] strings = base64.split(",");
		String extension;
		switch (strings[0]) {//check media's extension
		case "data:image/jpeg;base64":
			extension = "jpg";
			break;
		case "data:image/jpg;base64":
			extension = "jpg";
			break;
		case "data:image/png;base64":
			extension = "png";
			break;
		case "data:image/gif;base64":
			extension = "gif";
			break;
			//				case "data:video/webm;base64":
			//					extension = "webm";
			//					break;
			//				case "data:video/mp4;base64":
			//					extension = "mp4";
			//					break;
		default://should write cases for more media types
			extension = null;
			break;
		}

		if (extension != null) {
			//convert base64 string to binary data
			byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
			String outputDirPath = imagesPath;
			String fullFilePath = outputDirPath + (outputDirPath.endsWith("/") ? "" : "/") + filename + "." + extension;
			
			//verifica se existe a pasta no caminho informado e se nao existir ela e criada
			File outputDir = new File(outputDirPath);
			if(!outputDir.exists()) {
				outputDir.mkdirs();
			}
			
			File file = new File(fullFilePath);
			
			try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
				outputStream.write(data);
			} catch (IOException e) {
				e.printStackTrace();
			}

			return file.getAbsolutePath();
		}
		return "";
	}
}
