package br.com.seatecnologia.in.importador.dou.article;

import java.util.Map;

public interface Article {
	public static final String PUB_DATE_FORMAT = "dd/MM/yyyy";

	public String getArtSection();
	public String getArtCategory();
	public String getArtClass();
	public String getArtTitulo();
	public String getArtType();
	public String getData();
	public String getEditionNumber();
	public String getEmenta();
	public String getHierarchy();
	public String getHighlight();
	public String getHighlightPriority();
	public String getHighlightType();
	public String getHighlightImage();
	public String getID();
	public String getIdentifica();
	public String getIDMateria();
	public String getIDOficio();
	public String getName();
	public String getNumberPage();
	public String getPdfPage();
	public String getPubDate();
	public String getPubName();
	public String getSubTitulo();
	public String getTexto();
	public Map<String, String> getAutores();

	public String getUniqueName();
	String getHighlightImageName();
	public Boolean isRetification();
}
