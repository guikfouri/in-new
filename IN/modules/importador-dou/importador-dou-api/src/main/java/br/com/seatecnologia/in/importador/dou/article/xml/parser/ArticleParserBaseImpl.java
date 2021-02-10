package br.com.seatecnologia.in.importador.dou.article.xml.parser;

import java.util.Map;

import br.com.seatecnologia.in.importador.dou.article.Article;

public abstract class ArticleParserBaseImpl implements Article {
	private String artSection;
	private String artCategory;
	protected String artClass;
	protected String artTitulo;
	protected String artType;
	protected String data;
	protected String editionNumber;
	protected String ementa;
	protected String hierarchy;
	protected String highlight;
	protected String highlightPriority;
	protected String highlightType;
	protected String highlightImage;
	protected String highlightImageName;
	protected String id;
	protected String identifica;
	protected String idMateria;
	protected String idOficio;
	protected String name;
	protected String numberPage;
	protected String pdfPage;
	protected String pubDate;
	protected String pubName;
	protected String subTitulo;
	protected String texto;

	protected Map<String, String> autores;

	protected MediaLibraryConnector _medialibraryConnector;

	public ArticleParserBaseImpl(MediaLibraryConnector mediaLibraryConnector) {
		this._medialibraryConnector = mediaLibraryConnector;
	}

	@Override
	public String getArtSection() {
		if (this.artSection == null && this.hierarchy != null) {
			this.artSection = getSection(1, this.hierarchy);
		}
		return this.artSection;
	}

	@Override
	public String getArtCategory() {
		if (this.artCategory == null && this.hierarchy != null) {
			this.artCategory = getSection(2, this.hierarchy);
		}
		return this.artCategory;
	}

	@Override
	public String getArtClass() {
		return this.artClass;
	}

	@Override
	public String getArtTitulo() {
		return this.artTitulo;
	}

	@Override
	public String getArtType() {
		return this.artType;
	}

	@Override
	public String getData() {
		return this.data;
	}

	@Override
	public String getEditionNumber() {
		return this.editionNumber;
	}

	@Override
	public String getEmenta() {
		return this.ementa;
	}

	@Override
	public String getHierarchy() {
		return this.hierarchy;
	}

	@Override
	public String getHighlight() {
		return this.highlight;
	}

	@Override
	public String getHighlightPriority() {
		return this.highlightPriority;
	}

	@Override
	public String getHighlightType() {
		return this.highlightType;
	}
	
	@Override
	public String getHighlightImage() {
		return this.highlightImage;
	}
	
	@Override
	public String getHighlightImageName() {
		return this.highlightImageName;
	}

	@Override
	public String getID() {
		return this.id;
	}

	@Override
	public String getIdentifica() {
		return this.identifica;
	}

	@Override
	public String getIDMateria() {
		return this.idMateria;
	}

	@Override
	public String getIDOficio() {
		return this.idOficio;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public String getNumberPage() {
		return this.numberPage;
	}

	@Override
	public String getPdfPage() {
		return this.pdfPage;
	}

	@Override
	public String getPubDate() {
		return this.pubDate;
	}

	@Override
	public String getPubName() {
		return this.pubName;
	}

	@Override
	public String getSubTitulo() {
		return this.subTitulo;
	}

	@Override
	public String getTexto() {
		return this.texto;
	}

	@Override
	public Map<String, String> getAutores() {
		return this.autores;
	}

	@Override
	abstract public String getUniqueName();

	@Override
	public Boolean isRetification() {
		return false;
	}

	@Override
	public String toString() {
		String result = 
		"{" + "\n" +
		"	uniqueName=" + this.getUniqueName() + ",\n" +
		"	artSection=" + this.getArtSection() + ",\n" +
		"	artCategory=" + this.getArtCategory() + ",\n" +
		"	artClass=" + this.getArtClass() + ",\n" +
		"	artTitulo=" + this.getArtTitulo() + ",\n" +
		"	artType=" + this.getArtType() + ",\n" +
		"	data=" + this.getData() + ",\n" +
		"	editionNumber=" + this.getEditionNumber() + ",\n" +
		"	ementa=" + this.getEmenta() + ",\n" +
		"	hierarchy=" + this.getHierarchy() + ",\n" +
		"	highlight=" + this.getHighlight() + ",\n" +
		"	highlightPriority=" + this.getHighlightPriority() + ",\n" +
		"	highlightType=" + this.getHighlightType() + ",\n" +
		"	id=" + this.getID() + ",\n" +
		"	identifica=" + this.getIdentifica() + ",\n" +
		"	idMateria=" + this.getIDMateria() + ",\n" +
		"	idOficio=" + this.getIDOficio() + ",\n" +
		"	name=" + this.getName() + ",\n" +
		"	numberPage=" + this.getNumberPage() + ",\n" +
		"	pdfPage=" + this.getPdfPage() + ",\n" +
		"	pubDate=" + this.getPubDate() + ",\n" +
		"	pubName=" + this.getPubName() + ",\n" +
		"	subTitulo=" + this.getSubTitulo() + ",\n" +
		"	texto=" + this.getTexto() + ",\n" +
		"	autores=" + this.getAutores() + "\n" +
		"}" + "\n";

		return result;
	}

	protected void setPubNameFromNewspaperId(String newspaperId) {
		this.pubName = getPubNameFromNewspaperId(newspaperId);
	}

	private static String getSection(int i, String attr) {
		String[] split = attr.split("/");

		if (split.length > i -1) {
			return split[i -1];
		} else {
			return attr;
		}
	}

	private static String getPubNameFromNewspaperId(String newspaperId) {
		String pubName = "";

		// TODO: Pegar tabela de SIGLAS dos Jornais: DO1 DO1A, DO1E, DO2, DO2E, DO3, DO3E...
		switch (newspaperId) {
		case "515": pubName = "do1"; break; 
		case "529": pubName = "do2"; break; 
		case "530": pubName = "do3"; break; 
		case "531": pubName = "anvisa"; break; 
		case "550": pubName = "dodf"; break; 
		case "551": pubName = "dodf_suplementar"; break; 
		case "552": pubName = "dodf_extra"; break; 
		case "540": pubName = "do1_sup"; break; 
		case "612": pubName = "do1_extra_E"; break;
		case "613": pubName = "do1_extra_F"; break; 
		case "614": pubName = "do1_extra_G"; break;                                                  
		case "615": pubName = "do1_extra_H"; break;                                                  
		case "1020": pubName ="orcamento"; break;                                                   
		case "600": pubName = "do1_extra_A"; break;                                                  
		case "601": pubName = "do1_extra_B"; break;                                                  
		case "602": pubName = "do1_extra_C"; break;                                                  
		case "603": pubName = "do1_extra_D"; break;                                                  
		case "604": pubName = "do2_extra_A"; break;                                                  
		case "605": pubName = "do2_extra_B"; break;                                                  
		case "606": pubName = "do2_extra_C"; break;                                                  
		case "607": pubName = "do2_extra_D"; break;                                                  
		case "608": pubName = "do3_extra_A"; break;                                                  
		case "609": pubName = "do3_extra_B"; break;                                                  
		case "610": pubName = "do3_extra_C"; break;                                                  
		case "611": pubName = "do3_extra_D"; break;                                                  
		case "701": pubName = "do1esp"; break;                                                  
		case "702": pubName = "do2esp"; break;    
		
		}

		return pubName.toUpperCase();
	}
}
