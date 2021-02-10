package br.com.seatecnologia.in.importador.dou.xml.parser.jsoup;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.XmlDeclaration;
import org.jsoup.parser.Parser;

import br.com.seatecnologia.in.importador.dou.article.xml.parser.ArticleParserBaseImpl;
import br.com.seatecnologia.in.importador.dou.article.xml.parser.MediaLibraryConnector;
import br.com.seatecnologia.in.importador.dou.exception.DOXMLParserException;

/**
 * 
 * @author Sea Tecnologia
 * 
 * Implementation based on the XML Format specification 1.0.8 of IMPRENSA NACIONAL
 *
 */
public class ArticleXMLParser extends ArticleParserBaseImpl {
	private static final String UNIQUE_PUB_DATE_FORMAT = "yyyyMMdd";
	private static final String NEW_XML_PUB_DATE_FORMAT = "yyyy-MM-dd";
	
	public ArticleXMLParser(String xml, MediaLibraryConnector mediaDecoder) throws DOXMLParserException {
		super(mediaDecoder);

		//<?xml version="1.0" encoding="ISO-8859-1"?>
		Document document = Jsoup.parse(xml, "", Parser.xmlParser());
		Node firstNode = document.childNode(0);
		if (firstNode instanceof XmlDeclaration) {
			XmlDeclaration xmlDeclaration = (XmlDeclaration) firstNode;
			String xmlVersion = xmlDeclaration.attr("version");
			log.debug("XML version: " + xmlVersion);
			String xmlEncoding = xmlDeclaration.attr("encoding");
			log.debug("XML encoding: " + xmlEncoding);
			String xmlStandalone = xmlDeclaration.attr("standalone");
			log.debug("XML standalone: " + xmlStandalone);

			if (xmlEncoding.length() > 0) {
				document.charset(Charset.forName(xmlEncoding));
			}
		}
		log.debug("Charset: " + document.charset().name());

		log.debug("Parser implemented for materia v1.0.8");
		/*<materia versao="1.0.8">
		<!-- 
		 ORIENTAÇÕES: Por padrão todos os campos são opcionais, exceto os que possuem o comentário OBRIGATÓRIO; 
		              A estrutura do XML pode alterar e o atributo "versão" será incrementado, garantindo o comportamento conforme a versão que cada Imprensa estiver utilizando;
					  Após chegarmos na versão 1.0.0 (release), será criado um XSD apropriado, que irá validar o XML.
		-->*/
		Element articles = getFirstElementByTag(document, "materia", true);
		if (articles != null) {
			this.articlesFormatVersion = articles.attr("versao");
			log.debug("Versão do formato das matérias: " + this.articlesFormatVersion);

			parseMateriaElement(articles);
		}
		//</materia>

		if (this.hierarchyList != null) {
			Collections.reverse(this.hierarchyList);
			this.hierarchy = String.join("/", hierarchyList);
		}
		if (this.pubName == null) {
			this.setPubNameFromNewspaperId(this.newspaperId);
		}
		if (this.name == null) {
			this.name = "";
		}
		if (this.highlight == null) {
			this.highlight = "";
		}
		if (this.highlightType == null) {
			this.highlightType = "";
		}
		if (this.highlightPriority == null) {
			this.highlightPriority = "";
		}
		
		if (this.highlightImage == null) {
			this.highlightImage = "";
		}
		
	}

	@Override
	public String getUniqueName() {
		StringBuilder unique = new StringBuilder();

		unique.append(this.newspaperId);

		try {
			String nameDate = new SimpleDateFormat(UNIQUE_PUB_DATE_FORMAT)
					.format(new SimpleDateFormat(PUB_DATE_FORMAT)
							.parse(this.pubDate));
			unique.append("_" + nameDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		unique.append("_" + this.idMateria);

		return unique.toString();
	}

	@Override
	public Boolean isRetification() {
		return this.isRetification;
	}

	private void parseAssinaturaElement(Element assinatura) throws DOXMLParserException {
		if (assinatura != null ) {
			//<assinante />
			String author = getFirstElementTextByTag(assinatura, "assinante", false);
			//<cargo />
			String role = getFirstElementTextByTag(assinatura, "cargo", false);

			this.autores.put(author, role);
		}
	}
	private void parseAssinaturasElement(Element assinaturas) throws DOXMLParserException {
		if (assinaturas != null) {
			this.autores = new HashMap<>();
			//<assinatura>
			for (Element signature : assinaturas.select(">" + "assinatura")) {
				parseAssinaturaElement(signature);
			}
			//</assinatura>
		}
	}
	private void parseDestaqueElement(Element destaque) throws DOXMLParserException {
		if (destaque != null) {
			//<!-- Metadado responsável por definir se trata-se de uma matérias que será destaque no portal -->
			//<tipo>
			//<!-- Tipos são:  DESTAQUE_DOU - Destaques do Diário Oficial da União
			//                 CONCURSO_SELECAO - Concursos e Seleções
			//                 DESTAQUE_ESPECIAL - Destaques Especiais*
			//
			//                 Obs.*: não existe mais a partir de 01/05/2019. -->
			this.highlightType = convertHighlightType(getFirstElementTextByTag(destaque, "tipo", false));
			//</tipo>
			//<prioridade>
			this.highlightPriority = getFirstElementTextByTag(destaque, "prioridade", false);
			//</prioridade>
			//<texto>
			this.highlight = getFirstElementTextByTag(destaque, "texto", false);
			//</texto>
			//<img src="" name=""/> <!-- Imagem do destaque. Atributo src contém o base64 da imagem (formato html); atributo name é o nome da imagem -->
			Element highlightImageElement = getFirstElementByTag(destaque, "img", false);
			if (highlightImageElement != null) {
				String source = highlightImageElement.attr("src");
				if (source.length() > 0) {
					String filename = highlightImageElement.attr("name");
					this.highlightImage = this._medialibraryConnector.saveMedia(filename, source);
				}
			}
		}
	}
	private void parseEstruturaElement(Element estrutura) throws DOXMLParserException {
		if (estrutura != null) {
			//<identificacao><!-- Texto que identifica aquela norma na estrutura organização 
			//                da origem ("Portaria 131 de 21 de março de 2017") -->
			this.identifica = getFirstElementTextByTag(estrutura, "identificacao", false);
			//</identificacao>
			//<titulo />
			this.artTitulo = getFirstElementTextByTag(estrutura, "titulo", false);
			//<subtitulo />
			this.subTitulo = getFirstElementTextByTag(estrutura, "subtitulo", false);
			//<dataTexto><!-- Texto contendo a data que identifica a matéria (Portaria 131 de "21 de março de 2017") -->
			this.data = getFirstElementTextByTag(estrutura, "dataTexto", false);
			//</dataTexto>
			//<ementa><!-- aqui aceita txt ou html5 simplificado ; -->
			this.ementa = getFirstElementTextByTag(estrutura, "ementa", false);
			//</ementa>
			//<resumo><!-- resumo do conteúdo da matéria - conceito de abstract; -->
			//</resumo>

			//<assinaturas>
			parseAssinaturasElement(getFirstElementByTag(estrutura, "assinaturas", false));
			//</assinaturas>
		}
	}
	private void parseJornalElement(Element jornal) throws DOXMLParserException {
		if (jornal != null) {
			//<ano><!--Texto ou Número --></ano>
			//<idJornal><!--OBRIGATÓRIO --><!-- Código do jornal (interno da Imprensa) -->
			this.newspaperId = getFirstElementTextByTag(jornal, "idJornal", true);
			//</idJornal>
			//<jornal><!--OBRIGATÓRIO -->
			log.debug("jornal : " + getFirstElementTextByTag(jornal, "jornal", true));
			//</jornal>
			//<numeroEdicao><!-- Número ou texto que identifica aquela edição. DOU 
			//                nº "151" -->
			this.editionNumber = getFirstElementTextByTag(jornal, "numeroEdicao", true);
			//</numeroEdicao>
			//<idSecao><!-- Código da seção (interno da Imprensa Oficial) --></idSecao>
			//<secao><!-- Nome ou número da sessão (DOU1, DOU2, DOU3) --></secao>
			//<isExtra><!-- TRUE ou FALSE - determina se a matéria faz parte de uma edição é extra ou não. Padrão é FALSE --></isExtra>
			//<isSuplemento><!-- TRUE ou FALSE - determina se a matéria faz parte de um suplemento é extra ou não. Padrão é FALSE--></isSuplemento>
		}
	}
	private void parseMateriaElement(Element materia) throws DOXMLParserException {
		if (materia != null) {
			//<estrutura>
			parseEstruturaElement(getFirstElementByTag(materia, "estrutura", false));
			//</estrutura>

			//<metadados>
			parseMetadadosElement(getFirstElementByTag(materia, "metadados", false));
			//</metadados>

			//<texto><!--OBRIGATÓRIO -->
			parseTextoElement(getFirstElementByTag(materia, "texto", true));
			//</texto>
		}
	}
	private void parseMateriasRelacionadasElement(Element materiasRelacionadas) throws DOXMLParserException {
		if (materiasRelacionadas != null) {
			//<!-- ID das matérias que estão relacionadas a esta matéria, por exemplo
			//        se uma materia revoga outras 3 matérias, aqui seria o ID das outras 3. aqui seria o ID das outras 3. -->
			//<idMateria />
		}
	}
	private void parseMetadadosElement(Element metadados) throws DOXMLParserException {
		if (metadados != null) {
			//<ordenacao></ordenacao>
			this.artClass = getFirstElementTextByTag(metadados, "ordenacao", false);
			//<idXML><!--OBRIGATÓRIO --><!--ID que identifica unicamente esta matérias em um JORNAL e em uma DATA (idJornal+DataPublicacaoEfetiva+IdMateria) -->
			this.id = getFirstElementTextByTag(metadados, "idXML", false);
			//</idXML>

			//<destaque>
			parseDestaqueElement(getFirstElementByTag(metadados, "destaque", false));
			//</destaque>

			//<imprensaOficial>
			//        <!--OBRIGATÓRIO -->
			//        <!-- Nome da imprensa oficial do estado ou município responsável pelo 
			//                envio da matéria -->
			log.debug("imprensaOficial : \"" + getFirstElementTextByTag(metadados, "imprensaOficial", true) + "\"");
			//</imprensaOficial>
			//<idImprensaOficial>
			//        <!--OBRIGATÓRIO -->
			//        <!-- ID da imprensa oficial do estado ou município responsável pelo envio 
			//                da matéria -->
			//        <!-- Será criado uma tabela com informações de todas as imprensas oficiais -->
			log.debug("idImprensaOficial : \"" + getFirstElementTextByTag(metadados, "idImprensaOficial", true) + "\"");
			//</idImprensaOficial>
			//<idOficio><!--O conceito de ofício é de grupo de matérias em um mesmo lote-->
			this.idOficio = getFirstElementTextByTag(metadados, "idOficio", false);
			//</idOficio>
			//<idMateria>
			this.idMateria = getFirstElementTextByTag(metadados, "idMateria", false);
			//</idMateria>
			//<tipoAto><!--OBRIGATÓRIO -->
			this.artType = getFirstElementTextByTag(metadados, "tipoAto", true);
			//</tipoAto>
			//<idTipoAto><!--OBRIGATÓRIO -->
			log.debug("idTipoAto : \"" + getFirstElementTextByTag(metadados, "idTipoAto", true) + "\"");
			//</idTipoAto>

			//<origem><!--OBRIGATÓRIO -->
			parseOrigemElement(getFirstElementByTag(metadados, "origem", true));
			//</origem>

			//<materiasRelacionadas>
			parseMateriasRelacionadasElement(getFirstElementByTag(metadados, "materiasRelacionadas", false));
			//</materiasRelacionadas>

			//<publicacao><!--OBRIGATÓRIO -->
			parsePublicacaoElement(getFirstElementByTag(metadados, "publicacao", true));
			//</publicacao>
		}
	}
	private void parseOrigemElement(Element origem) throws DOXMLParserException {
		if (origem != null) {
			if (this.hierarchyList == null) {
				this.hierarchyList = new LinkedList<>();
			}

			//<idOrigem><!--OBRIGATÓRIO -->
			log.debug("idOrigem : \"" + getFirstElementTextByTag(origem, "idOrigem", true) + "\"");
			//</idOrigem>
			//<nomeOrigem><!--OBRIGATÓRIO -->
			this.hierarchyList.add(getFirstElementTextByTag(origem, "nomeOrigem", true));
			//</nomeOrigem>
			//<idSiorg></idSiorg> <!--Código SIORG da origem -->
			//<uf><!-- Unidade da Federação origem da matéria --></uf>
			//<municipio><!-- Nome do município origem da matéria --></municipio>
			//<idMunicipioIbge><!-- Código IBGE do município originário da matéria. --></idMunicipioIbge>

			//<origemPai/> <!--Origem diretamente superior (mesma estrutura da tag origem)-->
			parseOrigemElement(getFirstElementByTag(origem, "origemPai", false));
		}
	}
	private void parsePublicacaoElement(Element publicacao) throws DOXMLParserException {
		if (publicacao != null) {
			//<jornal><!--OBRIGATÓRIO -->
			parseJornalElement(getFirstElementByTag(publicacao, "jornal", true));
			//</jornal>

			//<dataPublicacao><!--OBRIGATÓRIO --><!-- Formato yyyy-MM-dd -->
			String value = getFirstElementTextByTag(publicacao, "dataPublicacao", true);
			if (value != null) {
				try {
				this.pubDate = new SimpleDateFormat(PUB_DATE_FORMAT)
						.format(new SimpleDateFormat(NEW_XML_PUB_DATE_FORMAT)
								.parse(value));
				} catch (ParseException e) {
					String errorMessage = "Date format conversion error: " + e.getMessage();
					log.error(errorMessage);
					throw new DOXMLParserException(errorMessage);
				}
			}
			//</dataPublicacao>
			//<numeroPaginaPdf><!-- número da página onde está a matéria no PDF/caderno. -->
			this.numberPage = getFirstElementTextByTag(publicacao, "numeroPaginaPdf", false);
			//</numeroPaginaPdf>
			//<urlVersaoOficialPdf><!-- URL da imprensa oficial onde estará o PDF que contém a matéria, se houver -->
			this.pdfPage = getFirstElementTextByTag(publicacao, "urlVersaoOficialPdf", false);
			//</urlVersaoOficialPdf>
			//<urlVersaoOficialHtml><!-- URL da imprensa oficial onde estará disponível a HTML da matéria, se houver --></urlVersaoOficialHtml>
			//<retificacaoTecnica></retificacaoTecnica> <!--OBRIGATÓRIO --><!-- TRUE ou FALSE - determina se a matéria está sendo retificada.Pode ocorrer uma falha na disponibilização em XML, apesar do formato oficial está ok. Neste caso se retifica a matéria. Não é uma nova publicação. É retificação da mesma publicação. Padrão é FALSE -->
			this.isRetification = Boolean.parseBoolean(getFirstElementTextByTag(publicacao, "retificacaoTecnica", true));
		}
	}
	private void parseTextoElement(Element texto) throws DOXMLParserException {
		if (texto != null) {
			//<!-- Conterá o conteúdo completo da matéria, do titulo até a assinatura, 
			//        incluindo ementa. -->
			//<!-- aqui aceita txt ou html simplificado (b, strong, i, u, p, img com 
			//        base64, video, source com base64, audio com base64, li, ul, ol, br, table, 
			//        th, tr, td, border, width) -->
			//<!-- Aqui também aceita (E É RECOMENDADO) classes CSS de nome equivalentes aos elementos 
			//        da TAG estrututa abaixo (identificacao, titulo, subtitulo, dataTexto, ementa, 
			//        assinante_0, cargo_0, assinante_1...). Isto irá melhorar a apresentação da matéria em uma página web, mantendo a fidedignidade com o layout original. ; -->
			for (Element img : texto.select("img")) {
				String source = img.attr("src");
				if (source.length() > 0) {
					String filename = img.attr("name");
					String imgUri = this._medialibraryConnector.saveMedia(filename, source);
					img.attr("src", imgUri);
				}
			}

			for (Element media : texto.select("audio,video")) {
				String source = media.attr("src");
				if (source.length() > 0) {
					String mediaName = media.attr("name");
					String uri = this._medialibraryConnector.saveMedia(mediaName, source);
					media.attr("src", uri);
				}

				for (Element mediaSource : media.select("source")) {
					source = mediaSource.attr("src");
					String mediaName = mediaSource.attr("name");
					String uri = this._medialibraryConnector.saveMedia(mediaName, source);
					mediaSource.attr("src", uri);
				}
			}

			this.texto = texto.html();
		}
	}

	private String convertHighlightType(String type) {
		switch(type) {
		case "DESTAQUE_DOU":		return "Destaques do Diário Oficial da União";
		case "CONCURSO_SELECAO":	return "Concursos e Seleções";
		case "DESTAQUE_ESPECIAL":	return "Destaques Especiais";
		default:					return type;
		}
	}

	private String getFirstElementTextByTag(Element parent, String tagName, Boolean mandatory)
			throws DOXMLParserException {
		String cssQuery = ">" + tagName;
		Element element = parent.selectFirst(cssQuery);
		String text = element != null ? element.text() : "";

		if (mandatory && text.length() == 0) {
			String errorMessage = "Missing mandatory element: \"" + tagName + "\" from " + parent.cssSelector();
			log.error(errorMessage);
			throw new DOXMLParserException(errorMessage);
		}

		return text;
	}

	private Element getFirstElementByTag(Element parent, String tagName, Boolean mandatory)
		throws DOXMLParserException {
		String cssQuery = ">" + tagName;
		Element element = parent.selectFirst(cssQuery);

		if (mandatory && element == null) {
			String errorMessage = "Missing mandatory element: \"" + tagName + "\" from " + parent.cssSelector();
			log.error(errorMessage);
			throw new DOXMLParserException(errorMessage);
		}

		return element;
	}

	private String articlesFormatVersion;
	private String newspaperId;
	private List<String> hierarchyList;
	private boolean isRetification;

	private static Log log = LogFactoryUtil.getLog(ArticleXMLParser.class);
}
