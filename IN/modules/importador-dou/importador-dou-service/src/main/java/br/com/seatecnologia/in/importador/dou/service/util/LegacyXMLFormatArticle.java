package br.com.seatecnologia.in.importador.dou.service.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.LinkedHashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import br.com.seatecnologia.in.importador.dou.article.xml.parser.ArticleParserBaseImpl;
import br.com.seatecnologia.in.importador.dou.article.xml.parser.MediaLibraryConnector;

public class LegacyXMLFormatArticle extends ArticleParserBaseImpl {

	public LegacyXMLFormatArticle(String xmlArticle, MediaLibraryConnector mediaDecoder) {
		super(mediaDecoder);

		log.debug("Parsing XML...");

		Document document = Jsoup.parse(xmlArticle, "", Parser.xmlParser());
		Elements article = document.select("article");

		this.hierarchy = article.attr("artCategory");
		this.artClass = article.attr("artClass");
		this.artType = article.attr("artType");
		this.editionNumber = article.attr("editionNumber");
		this.highlight = article.attr("highlight");
		this.highlightPriority = article.attr("highlightPriority");
		this.highlightType = article.attr("highlightType");
		this.highlightImage = this._medialibraryConnector.saveMedia(article.attr("highlightimagename"),
				article.attr("highlightimage"));
		this.id = article.attr("id");
		this.idMateria = article.attr("idMateria");
		this.idOficio = article.attr("idOficio");
		this.name = article.attr("name");
		this.numberPage = article.attr("numberPage");
		this.pdfPage = article.attr("pdfPage");
		this.pubDate = article.attr("pubDate");
		this.pubName = article.attr("pubName");

		this.data = article.select("Data").text();
		this.ementa = article.select("Ementa").text();
		this.identifica = article.select("Identifica").text();
		this.subTitulo = article.select("SubTitulo").text();
		this.artTitulo = article.select("Titulo").text();
		this.texto = article.select("Texto").text();

		this.autores = new LinkedHashMap<String, String>();

		Elements autoresNode = article.select("Autores");

		if (!autoresNode.select("assina").isEmpty()) {
			Elements assinaturas = autoresNode.select("assina");
			Elements cargos = autoresNode.select("cargo");

			if (!assinaturas.isEmpty()) {
				for (Element assina : assinaturas) {
					if (!(cargos.size() == 0)) {
						autores.put(assina.text(), cargos.get(0).text());
						cargos.remove(0);
					} else {
						autores.put(assina.text(), StringPool.BLANK);
					}
				}
			}
		}
	}

	@Override
	public String getUniqueName() {
		StringBuilder sb = new StringBuilder();

		if (this.getPubName() != null) {
			sb.append(this.getPubName());
		}
		if (this.getPubDate() != null) {
			if (sb.length() > 0) {
				sb.append("_");
			}
			sb.append(this.getPubDate());
		}
		if (this.getIDMateria() != null) {
			if (sb.length() > 0) {
				sb.append("_");
			}
			sb.append(this.getIDMateria());
		}

		return sb.toString();
	}

	private static Log log = LogFactoryUtil.getLog(LegacyXMLFormatArticle.class);
}
