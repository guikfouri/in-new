package br.com.seatecnologia.in.importador.dou.json.parser;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;

import javax.xml.bind.DatatypeConverter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.seatecnologia.in.importador.dou.article.Article;
import br.com.seatecnologia.in.importador.dou.article.xml.parser.MediaLibraryConnector;
import br.com.seatecnologia.in.importador.dou.json.factory.ArticleJSONParserFactory;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JSONFactoryUtil.class)
public class ArticleJSONParserTest {

	private static final String outputDirPath = "/tmp";
	
	private MediaLibraryConnector mediaLibraryConnector;

	@Before
	public void init() {
		this.mediaLibraryConnector = new MediaLibraryConnector() {

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
				case "data:image/gif;base64":
					extension = "gif";
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

		mockStatic(JSONFactoryUtil.class);
		try {
			Mockito.when(JSONFactoryUtil.createJSONObject(Mockito.anyString())).thenThrow(new JSONException());
		} catch (JSONException e) {
			// do nothing
		}
	}

	@Test
	public void shouldParseJSONArticle() throws Exception {
		String jsonUnderTest = "" +
				"{\n" + 
				"    \"versao\": \"1.0.11\",\n" + 
				"    \"textoHTML\": \"<p class=\\\"identifica\\\">PORTARIA Nº 120, DE 21 DE FEVEREIRO DE 2020</p>\\r\\n  <p class=\\\"ementa\\\">Institui o Cadastro Nacional de Participação Social na Execução Penal do Departamento Penitenciário Nacional.</p>O DIRETOR-GERAL DO DEPARTAMENTO PENITENCIÁRIO NACIONAL, no uso das atribuições que lhe confere art. 62 inciso V da Portaria MJSP nº 199, de 9 de novembro de 2018, e tendo em vista o disposto no<strong>art. 3º B</strong>da Lei Complementar nº 79, de 7 de janeiro de 1994, resolve:Art. 1º A Portaria<em>Gab-Depen</em>nº 125 de 08 de março de 2019, publicada no D.O.U no dia 11 de março de 2019, passando a vigorar com as seguintes alterações:Art. 1º Fica instituído no âmbito do<strong><em>Departamento Penitenciário</em></strong>Nacional o Cadastro Nacional de Participação Social na Execução Penal.<p><p /></p><table><tr /><tr><td><p>UF</p></td><td><p>MUNICÍPIO</p></td><td><p>ENTIDADE</p></td><td><p>Nº DA PROPOSTA</p></td><td><p>VALOR TOTAL DA PROPOSTA (R$)</p></td><td><p>CÓD. EMENDA</p></td><td><p>VALOR POR PARLAMENTAR (R$)</p></td><td><p>FUNCIONAL PROGRAMÁTICA</p></td><td><p>CNES</p></td><td><p>VALOR</p></td></tr><tr><td><p>CE</p></td><td><p>QUIXERE</p></td><td><p>FUNDO MUNICIPAL DE</p><p>SAÚDE DE QUIXERE</p></td><td><p>36000282093201900</p></td><td><p>576.938,00</p></td><td><p>24410001</p></td><td><p>576.938,00</p></td><td><p>1030220152E900023</p></td><td><p>5340675</p></td><td><p>576.938,00</p></td></tr></table>Art. 2º A inscrição das entidades no cadastro deverá ser precedida do atendimento dos critérios estabelecidos pela metodologia utilizada pelo Mapa de Organização da Sociedade Civil, e pela Lei nº 13.019, de 31 de julho de 2014.<img name=\\\"1_MECON_8_001\\\" src=\\\"data:image/gif;base64,R0lGODlhEAAQAMQAAORHHOVSKudfOulrSOp3WOyDZu6QdvCchPGolfO0o/XBs/fNwfjZ0frl3/zy7////wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAkAABAALAAAAAAQABAAAAVVICSOZGlCQAosJ6mu7fiyZeKqNKToQGDsM8hBADgUXoGAiqhSvp5QAnQKGIgUhwFUYLCVDFCrKUE1lBavAViFIDlTImbKC5Gm2hB0SlBCBMQiB0UjIQA7\\\"></img><p class=\\\"data\\\">23 de Maio de 2020</p><p class=\\\"assina\\\">Primeira Assinatura</p><p class=\\\"cargo\\\">Primeiro Cargo</p><p class=\\\"assina\\\">Segunda Assinatura</p><p class=\\\"cargo\\\">Segundo Cargo</p><p class=\\\"nota\\\">Nota</p>\",\n" + 
				"    \"textoEstruturado\": {},\n" + 
				"    \"estrutura\": {\n" + 
				"      \"identificacao\": \"PORTARIA Nº 120, DE 21 DE FEVEREIRO DE 2020\",\n" + 
				"      \"titulo\": null,\n" + 
				"      \"subtitulo\": null,\n" + 
				"      \"dataTexto\": \"23 de Maio de 2020\",\n" + 
				"      \"ementa\": \" Institui o Cadastro Nacional de Participação Social na Execução Penal do Departamento Penitenciário Nacional.\",\n" + 
				"      \"resumo\": null,\n" + 
				"      \"assinaturas\": [\n" + 
				"        {\n" + 
				"          \"assinante\": \"Primeira Assinatura\",\n" + 
				"          \"cargo\": \"Primeiro Cargo\"\n" + 
				"        },\n" + 
				"        {\n" + 
				"          \"assinante\": \"Segunda Assinatura\",\n" + 
				"          \"cargo\": \"Segundo Cargo\"\n" + 
				"        }\n" + 
				"      ]\n" + 
				"\n" + 
				"    },\n" + 
				"    \"metadados\": {\n" + 
				"      \"idMateria\": 12650981,\n" + 
				"      \"idOficio\": 5857571,\n" + 
				"      \"idXML\": null,\n" + 
				"      \"ordem\": \"00018:00024:00000:00000:00000:00000:00000:00000:00000:00000:00054:00000\",\n" + 
				"      \"imprensaOficial\": \"Imprensa Nacional\",\n" + 
				"      \"idImprensaOficial\": 1,\n" + 
				"      \"materiaPai\":null,\n" + 
				"      \"tipoAto\": \"Portaria\",\n" + 
				"      \"idTipoAto\": 69,\n" + 
				"      \"origem\": {\n" + 
				"        \"idOrigem\": 198562,\n" + 
				"        \"nomeOrigem\": \"Departamento Penitenciário Nacional\",\n" + 
				"        \"idSiorg\": 1956,\n" + 
				"        \"origemPai\": {\n" + 
				"          \"idOrigem\": 498970,\n" + 
				"          \"nomeOrigem\": \"Ministério da Justiça e Segurança Pública\",\n" + 
				"          \"idSiorg\": 235881,\n" + 
				"          \"uf\": null,\n" + 
				"          \"nomeMunicipio\": null,\n" + 
				"          \"idMunicipioIbge\": null\n" + 
				"        },\n" + 
				"        \"uf\": null,\n" + 
				"        \"nomeMunicipio\": null,\n" + 
				"        \"idMunicipioIbge\": null\n" + 
				"      },\n" + 
				"      \"destaque\": {\n" + 
				"        \"tipo\": \"Destaques Do Diário Oficial da União\",\n" + 
				"        \"prioridade\": 3,\n" + 
				"        \"texto\": \"Depen cria cadastro de organizações que trabalham com reintegração social\",\n" + 
				"          \"nomeImagem\": \"1_MECON_8_001\",\n" + 
				"          \"fonteImagem\": \"data:image/gif;base64,R0lGODlhEAAQAMQAAORHHOVSKudfOulrSOp3WOyDZu6QdvCchPGolfO0o/XBs/fNwfjZ0frl3/zy7////wAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAACH5BAkAABAALAAAAAAQABAAAAVVICSOZGlCQAosJ6mu7fiyZeKqNKToQGDsM8hBADgUXoGAiqhSvp5QAnQKGIgUhwFUYLCVDFCrKUE1lBavAViFIDlTImbKC5Gm2hB0SlBCBMQiB0UjIQA7\"\n" + 
				"      },\n" + 
				"      \"materiasRelacionadas\": null,\n" + 
				"      \"publicacao\": {\n" + 
				"        \"retificacaoTecnica\": false,\n" + 
				"        \"jornal\": {\n" + 
				"          \"ano\": \"CLVIII\",\n" + 
				"          \"idJornal\": 600,\n" + 
				"          \"jornal\": \"Diário Oficial da União\",\n" + 
				"          \"numeroEdicao\": \"-A\",\n" + 
				"          \"idSecao\": 1,\n" + 
				"          \"secao\": \"Seção 1\",\n" + 
				"          \"isExtra\": true,\n" + 
				"          \"isSuplemento\": false\n" + 
				"        },\n" + 
				"        \"dataPublicacao\": \"2020-06-06\",\n" + 
				"        \"numeroPaginaPdf\": 1,\n" + 
				"        \"urlVersaoOficialPdf\": \"http://pesquisa.in.gov.br/imprensa/jsp/visualiza/index.jsp?data=06/06/2020&jornal=600&pagina=1\",\n" + 
				"        \"urlVersaoOficialHtml\": \"http://www.in.gov.br/en/web/dou/-/portaria-n-120-de-21-de-fevereiro-de-2020-257815335\"\n" + 
				"      }\n" + 
				"    }\n" + 
				"}";

		
		Article article1 = ArticleJSONParserFactory.getArticleParser(jsonUnderTest, this.mediaLibraryConnector);

		assertNotNull(article1);
	}

	@Test
	public void shouldParseFromFiles() throws Exception {
		String path = "src/test/resources";

		File fileDirectory = new File(path);
		String absolutePath = fileDirectory.getAbsolutePath();

		System.out.println(absolutePath);

		assertTrue(absolutePath.endsWith("src/test/resources"));

		final String fileExtension = ".json";
		File[] files = fileDirectory.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(fileExtension);
			}
		});

		if (files != null) {
			for (File json : files) {
				String currentFileName = json.getAbsolutePath();
				System.out.println(currentFileName);
				StringBuilder sb = new StringBuilder();
				try (BufferedReader br = new BufferedReader(new FileReader(currentFileName))) {
					String sCurrentLine;
					while ((sCurrentLine = br.readLine()) != null) {
						sb.append(sCurrentLine);
					}
				} catch (Exception e) {
					System.out.println("[ERROR] could not read file: " + currentFileName + e);
					fail();
				}

				byte[] b = sb.toString().getBytes(Charset.forName("UTF-8"));
				String finalText = new String(b, "UTF-8");

				Article article1 = ArticleJSONParserFactory.getArticleParser(finalText, this.mediaLibraryConnector);

				assertNotNull(article1);
			}
		}
	}

}
