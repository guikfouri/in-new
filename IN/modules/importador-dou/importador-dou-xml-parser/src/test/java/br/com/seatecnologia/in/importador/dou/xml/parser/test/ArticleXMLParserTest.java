package br.com.seatecnologia.in.importador.dou.xml.parser.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.xml.bind.DatatypeConverter;

import org.junit.Test;

import br.com.seatecnologia.in.importador.dou.article.xml.parser.MediaLibraryConnector;
import br.com.seatecnologia.in.importador.dou.xml.parser.jsoup.ArticleXMLParser;

public class ArticleXMLParserTest {
	public static final String douExample01 =
		"<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n" + 
		"<materia versao=\"1.0.8\">\n" + 
		"	<texto>\n" + 
		"		<p class=\"identifica\">EXTRATO DE NOTA DE EMPENHO</p>\n" + 
		"		<p>Nota de Empenho Ordinário. TRE-PE nº 2019NE0358, emitida em 15/03/2019. SEI nº 0026321-24.2018.6.17.8000. CONTRATADA: CLARIT COMERCIAL EIRELI. Valor: R$ 3.300,00. OBJETO: Material de limpeza e higienização. FUNDAMENTO LEGAL: Ata de Registro de Preço nº 54/18, vinculada ao pregão eletrônico n° 27/18 deste TRE-PE. PTRES: 84609. Elemento de despesa: 3390.30.</p>\n" + 
		"		<img name=\"reddot\" src=\"data:image/png;base64, iVBORw0KGgoAAAANSUhEUgAAAAUAAAAFCAYAAACNbyblAAAAHElEQVQI12P4//8/w38GIAXDIBKE0DHxgljNBAAO9TXL0Y4OHwAAAABJRU5ErkJggg==\" alt=\"Red dot\" />\n" +
		"		<img name=\"reddot\" src=\"data:image/jpeg;base64, /9j/4AAQSkZJRgABAQAAAQABAAD//gAfQ29tcHJlc3NlZCBieSBqcGVnLXJlY29tcHJlc3P/2wCEAAQEBAQEBAQEBAQGBgUGBggHBwcHCAwJCQkJCQwTDA4MDA4MExEUEA8QFBEeFxUVFx4iHRsdIiolJSo0MjRERFwBBAQEBAQEBAQEBAYGBQYGCAcHBwcIDAkJCQkJDBMMDgwMDgwTERQQDxAUER4XFRUXHiIdGx0iKiUlKjQyNEREXP/CABEIAAUABQMBIgACEQEDEQH/xAAUAAEAAAAAAAAAAAAAAAAAAAAF/9oACAEBAAAAAEf/xAAUAQEAAAAAAAAAAAAAAAAAAAAG/9oACAECEAAAADv/xAAUAQEAAAAAAAAAAAAAAAAAAAAH/9oACAEDEAAAAFr/xAAdEAABAwUBAAAAAAAAAAAAAAADBAUGAAECEhNS/9oACAEBAAE/AIPB357fp0hQzpe2mbV9gqFAbE3W59C49Calw8V//8QAHBEAAgICAwAAAAAAAAAAAAAAAQIDBAAxMkNh/9oACAECAQE/AKlSBoASH5ydjjTn3P/EAB0RAAIBBAMAAAAAAAAAAAAAAAIDBAAFBiESFDH/2gAIAQMBAT8AyLIrqi6sWtkfj14pbioLZIAvSCv/2Q==\" alt=\"Red dot\" />\n" + 
		"	</texto>\n" + 
		"	<estrutura>\n" + 
		"		<identificacao>EXTRATO DE NOTA DE EMPENHO</identificacao>\n" + 
		"		<titulo>Texto Titulo</titulo>\n" + 
		"		<subtitulo>Texto Subtitulo</subtitulo>\n" +
		"		<dataTexto>11 DE JUNHO DE 2018</dataTexto>\n" + 
		"		<ementa>Texto Ementa</ementa>\n" + 
		"		<resumo>Aqui fica o resumo deste desta matéria, caso exista.</resumo>\n" +
		"		<assinaturas>\n" + 
		"			<assinatura>\n" + 
		"				<assinante>MIRIAM JEAN MILLER</assinante>\n" + 
		"				<cargo>Diretora Substituta</cargo>\n" + 
		"			</assinatura>\n" + 
		"			<assinatura>\n" + 
		"				<assinante>JOÃO DA SILVA</assinante>\n" + 
		"				<cargo>COORDENADOR DE TI</cargo>\n" + 
		"			</assinatura>\n" + 
		"		</assinaturas>\n" +
		"	</estrutura>\n" + 
		"	<metadados>\n" + 
		"		<idXML>5527802</idXML>\n" + 
		"		<ordenacao>00043:00028:00008:00000:00000:00000:00000:00000:00000:00000:00137:00007</ordenacao>\n" + 
		"		<destaque>\n" + 
		"			<tipo>DESTAQUE_DOU</tipo>" + 
		"			<prioridade>7</prioridade>" + 
		"			<texto>Defesa Civil reconhece situação de emergência de sete cidades atingidas por desastres naturais</texto>" +
		"			<img\n" + 
		"				src=\"data:image/jpeg;base64,/9j/4QAYRXhpZgAASUkqAAgAAAAAAAAAAAAAAP/sABFEdWNreQABAAQAAAAyAAD/4QMdaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wLwA8P3hwYWNrZXQgYmVnaW49Iu+7vyIgaWQ9Ilc1TTBNcENlaGlIenJlU3pOVGN6a2M5ZCI/PiA8eDp4bXBtZXRhIHhtbG5zOng9ImFkb2JlOm5zOm1ldGEvIiB4OnhtcHRrPSJBZG9iZSBYTVAgQ29yZSA1LjYtYzAxNCA3OS4xNTY3OTcsIDIwMTQvMDgvMjAtMDk6NTM6MDIgICAgICAgICI+IDxyZGY6UkRGIHhtbG5zOnJkZj0iaHR0cDovL3d3dy53My5vcmcvMTk5OS8wMi8yMi1yZGYtc3ludGF4LW5zIyI+IDxyZGY6RGVzY3JpcHRpb24gcmRmOmFib3V0PSIiIHhtbG5zOnhtcE1NPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvbW0vIiB4bWxuczpzdFJlZj0iaHR0cDovL25zLmFkb2JlLmNvbS94YXAvMS4wL3NUeXBlL1Jlc291cmNlUmVmIyIgeG1sbnM6eG1wPSJodHRwOi8vbnMuYWRvYmUuY29tL3hhcC8xLjAvIiB4bXBNTTpEb2N1bWVudElEPSJ4bXAuZGlkOjFCRjUxNkRENTgzMzExRTg5RThGOEE4MTMxMzlGMEREIiB4bXBNTTpJbnN0YW5jZUlEPSJ4bXAuaWlkOjFCRjUxNkRDNTgzMzExRTg5RThGOEE4MTMxMzlGMEREIiB4bXA6Q3JlYXRvclRvb2w9IkFkb2JlIFBob3Rvc2hvcCBDQyAyMDE0IFdpbmRvd3MiPiA8eG1wTU06RGVyaXZlZEZyb20gc3RSZWY6aW5zdGFuY2VJRD0iMzE5NDc4RDI5OEYyOEQ0NEYyMjcwNjM0Mjk3QTBBRTIiIHN0UmVmOmRvY3VtZW50SUQ9IjMxOTQ3OEQyOThGMjhENDRGMjI3MDYzNDI5N0EwQUUyIi8+IDwvcmRmOkRlc2NyaXB0aW9uPiA8L3JkZjpSREY+IDwveDp4bXBtZXRhPiA8P3hwYWNrZXQgZW5kPSJyIj8+/+4ADkFkb2JlAGTAAAAAAf/bAIQACAYGBgYGCAYGCAwIBwgMDgoICAoOEA0NDg0NEBEMDg0N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\"\n" + 
		"				name=\"1_MME_13_002\"/>" +
		"		</destaque>\n" + 
		"		<imprensaOficial>IMPRENSA NACIONAL</imprensaOficial>\n" + 
		"		<idImprensaOficial>1</idImprensaOficial>\n" + 
		"		<idOficio>5221775</idOficio>\n" + 
		"		<idMateria>11539483</idMateria>\n" + 
		"		<tipoAto>Extrato de Nota de Empenho</tipoAto>\n" + 
		"		<idTipoAto>118</idTipoAto>\n" + 
		"		<origem>\n" + 
		"			<idOrigem>180833</idOrigem>\n" + 
		"			<nomeOrigem>Secretaria de Orçamento e Finanças</nomeOrigem>\n" + 
		"		</origem>\n" + 
		"		<publicacao>\n" + 
		"			<jornal>\n" + 
		"				<anoJornal>156</anoJornal>\n" + 
		"				<idJornal>530</idJornal>\n" + 
		"				<jornal>Diário Oficial da União - Seção 3</jornal>\n" + 
		"				<numeroEdicao>55</numeroEdicao>\n" + 
		"				<idSecao>3</idSecao>\n" + 
		"				<secao>Seção 3</secao>\n" + 
		"				<isExtra>false</isExtra>\n" + 
		"				<isSuplemento>false</isSuplemento>\n" + 
		"			</jornal>\n" + 
		"			<dataPublicacao>2019-03-21</dataPublicacao>\n" + 
		"			<numeroPaginaPdf>125</numeroPaginaPdf>\n" + 
		"			<urlVersaoOficialPdf>http://pesquisa.in.gov.br/imprensa/jsp/visualiza/index.jsp?data=21/03/2019&amp;jornal=530&amp;pagina=125</urlVersaoOficialPdf>\n" +
		"			<retificacaoTecnica>false</retificacaoTecnica>\n" + 
		"		</publicacao>\n" + 
		"	</metadados>\n" + 
		"</materia>";

	private static final String outputDirPath = "/tmp";

	@Test
	public void testArticleXMLParser() throws Exception {
		String xmlUnderTest = douExample01;

		MediaLibraryConnector mediaLibraryConnector = new MediaLibraryConnector() {

			@Override
			public String saveMedia(String filename, String uri) {
				if (uri.startsWith("data:")) {
					String finalUri = convertData(filename, uri);
					if (finalUri.length() > 0) {
						return finalUri;
					}
				}
				return uri;
			}

			private String convertData(String filename, String base64) {

				String[] strings = base64.split(",");
				String extension;
				switch (strings[0]) {//check media's extension
				case "data:image/jpeg;base64":
					extension = "jpg";
					break;
				default://should write cases for more media types
					extension = null;
					break;
				}

				if (extension != null) {
					//convert base64 string to binary data
					byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
					String fullFilePath = outputDirPath + (outputDirPath.endsWith("/") ? "" : "/") + filename + "." + extension;
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
		};

		ArticleXMLParser article = new ArticleXMLParser(xmlUnderTest, mediaLibraryConnector);

		assertNotNull(article);
		assertEquals("530_20190321_11539483", article.getUniqueName());
		assertFalse(article.isRetification());
	}

}