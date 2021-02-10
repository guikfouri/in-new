package br.com.seatecnologia.in.importador.dou.json.factory;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import br.com.seatecnologia.in.importador.dou.article.Article;
import br.com.seatecnologia.in.importador.dou.article.xml.parser.MediaLibraryConnector;

public class ArticleJSONParserFactory {

	public static Article getArticleParser(String json, MediaLibraryConnector mediaLibraryConnector) {
		return getArticleParser(json, mediaLibraryConnector, getArticleVersion(json));
	}

	public static Article getArticleParser(String json, MediaLibraryConnector mediaLibraryConnector, String version) {
		switch(version) {
		case "1.0.14":
		case "1.0.13":
		case "1.0.12":
		case "1.0.11":
			return new br.com.seatecnologia.in.importador.dou.json.parser.v1_0_11.ArticleJSONParser(json, mediaLibraryConnector);
		}

		throw new IllegalArgumentException("There's no parser for JSON format version " + version);
	}

	private static String getArticleVersion(String json) {
		JSONObject jsonObject;
		try {
			log.info(json);
			jsonObject = JSONFactoryUtil.createJSONObject(json);
			return jsonObject.getString("versao", LATEST_VERSION);
		} catch (JSONException e) {
			log.warn("Could not parse JSON to get article version, trying to parse as version " + LATEST_VERSION, e);
		}
		return LATEST_VERSION;
	}

	private static final String LATEST_VERSION = "1.0.14";
	private static Log log = LogFactoryUtil.getLog(ArticleJSONParserFactory.class);
}
