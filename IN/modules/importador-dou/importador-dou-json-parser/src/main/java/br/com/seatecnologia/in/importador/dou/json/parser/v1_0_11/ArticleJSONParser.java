package br.com.seatecnologia.in.importador.dou.json.parser.v1_0_11;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import br.com.seatecnologia.in.importador.dou.article.xml.parser.ArticleParserBaseImpl;
import br.com.seatecnologia.in.importador.dou.article.xml.parser.MediaLibraryConnector;
import br.com.seatecnologia.in.importador.dou.exception.DOXMLParserException;
import br.com.seatecnologia.in.importador.dou.json.model.Destaque;
import br.com.seatecnologia.in.importador.dou.json.model.Estrutura;
import br.com.seatecnologia.in.importador.dou.json.model.Jornal;
import br.com.seatecnologia.in.importador.dou.json.model.Materia;
import br.com.seatecnologia.in.importador.dou.json.model.Metadados;
import br.com.seatecnologia.in.importador.dou.json.model.Origem;
import br.com.seatecnologia.in.importador.dou.json.model.Publicacao;

/**
 * @author mgsasaki
 */
public class ArticleJSONParser extends ArticleParserBaseImpl {

	public ArticleJSONParser(String json, MediaLibraryConnector mediaLibraryConnector) {
		super(mediaLibraryConnector);

		ObjectMapper mapper = new ObjectMapper();

		try {
			this.materia = mapper.readValue(json, Materia.class);

			log.trace("Versao: " + this.materia.getVersao());

			decodeEstrutura(this.materia.getEstrutura());
			decodeMetadados(this.materia.getMetadados());
			decodeTexto(this.materia.getTextoHTML());

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
		} catch (IOException | DOXMLParserException e) {
			log.error(e);
			throw new SystemException(e);
		}
	}
	
	private void parseTextoElement(String textoHtml) throws DOXMLParserException {
		Document texto = Jsoup.parse(textoHtml);
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
					if (this.id != null && !this.id.isEmpty()) {
						filename = this.id + "-" + filename;
					}
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
	
	private void decodeTexto(String textoHtml) throws DOXMLParserException {
		if(Validator.isNotNull(textoHtml)) {
			parseTextoElement(textoHtml);
		}
	}

	private void decodeEstrutura(Estrutura estrutura) {
		if (estrutura != null) {
			//  "identificacao": "string", //identificacao - OBRIGATÓRIO - Texto que identifica aquela norma na estrutura organização da origem ("Portaria 131 de 21 de março de 2017")
			this.identifica = estrutura.getIdentificacao();
			//  "titulo": "string", //titulo - OPCIONAL - O título definido na matéria, se houver.
			this.artTitulo = estrutura.getTitulo();
			//  "subtitulo": "string", //titulo - OPCIONAL - O subtítulo definido na matéria, se houver.
			this.subTitulo = estrutura.getSubtitulo();
			//  "dataTexto": "string", //dataTexto - OPCIONAL - Texto da data que identifica a matéria ("21 de março de 2017")
			this.data = estrutura.getDataTexto();
			//  "ementa": "string", //ementa - OPCIONAL - Ementa da matéria. Aceita HTML 5 ou texto.
			this.ementa = estrutura.getEmenta();
			//  "resumo": "string", //resumo - OPCIONAL - resumo do conteúdo da matéria - conceito de abstract
			log.trace("IGNORED - resumo: " + estrutura.getResumo());
			//  "assinaturas": [ //assinaturas - OPCIONAL - lista de assinaturas, em ordem de apresentação do texto públicado
			//    {
			//      "assinante": "string", //assinante - OPCIONAL 
			//      "cargo": "string" //cargo - OPCIONAL 
			//    }
			//  ]
			if (estrutura.getAssinaturas() != null) {
				this.autores = estrutura.getAssinaturas().stream()
						.collect(Collectors.toMap(
								a -> Optional.ofNullable(a.getAssinante()).orElse(""),
								a -> Optional.ofNullable(a.getCargo()).orElse("")));
			}
		}
	}

	private void decodeMetadados(Metadados metadados) {
		if (metadados != null) {
			//  "idMateria": "long", //idMateria - OBRIGATÓRIO - código da matéria dentro da imprensa oficial (interno)
			this.idMateria = String.valueOf(metadados.getIdMateria());
			//  "idOficio": "long", //idOficio - OPCIONAL - código do ofício (grupo de matérias) dentro da imprensa oficial (interno). O conceito de ofício é de grupo de matérias em um mesmo lote.
			this.idOficio = String.valueOf(metadados.getIdOficio());
			//  "idXML": "string", // idXML - OBRIGATÓRIO - ID que identifica unicamente esta matérias em um JORNAL e em uma DATA (idJornal+DataPublicacaoEfetiva+IdMateria)
			this.id = (String) metadados.getIdXML();
			//  "ordem": "string", //ordem - OBRIGATÓRIO - código ou hash de ordenação usado representar a ordem da matéria dentro do jornal.
			this.artClass = metadados.getOrdem();
			//  "imprensaOficial": "string", //imprensaOficial - OBRIGATÓRIO - Nome da imprensa oficial (federal, estado ou município) responsável pelo envio da matéria.
			log.trace("IGNORED - imprensaOficial: " + metadados.getImprensaOficial());
			//  "idImprensaOficial": "integer", //idImprensaOficial - OBRIGATÓRIO - código da imprensa oficial responsável pela públicação da matéria
			log.trace("IGNORED - idImprensaOficial: " + metadados.getIdImprensaOficial());

			//  "materiaPai": { //materiaPai - OPCIONAL - Matéria relacionada na qual seu conteúdo compõem o texto da matéria principal (pai). Por exemplo, anexos ou matérias que devem ser concatenadas em uma matéria pai.
			//  	"idXMLPai": "string",//idXMLPai - OBRIGATÓRIO - ID PAI que identifica unicamente a matéria que recebe a titulação e é a matéria principal
			//  	"ordemFilho": "string" //ordemFilho - OPCIONAL - código ou hash de ordenação usado representar a ordem desta matéria em relação as demais matérias filhas da matéria pai. Por exemplo, se este for um anexo de uma norma, em que posição este anexo deve ser apresentado em relação a outros anexos da mesma norma?
			//  },
			log.trace("IGNORED - materiaPai: " + metadados.getMateriaPai());

			//  "tipoAto": "string", //tipoAto - OBRIGATÓRIO - Nome do tipo da norma ou do ato. Por exemplo, Extrato, Edital, Portaria...
			this.artType = metadados.getTipoAto();
			//  "idTipoAto": "integer", // idTipoAto - OBRIGATÓRIO - código do tipo da norma
			log.trace("IGNORED - idTipoAto: " + metadados.getIdTipoAto());

			//  "origem": {
			decodeOrigem(metadados.getOrigem());
			//  },

			//  "destaque": { //destaque - OPCIONAL - Metadado responsável por definir se trata-se de uma matérias que será destaque no portal
			decodeDestaque(metadados.getDestaque());
			//  },

			//  "materiasRelacionadas": [ // materiasRelacionadas - OPCIONAL - Matérias que estão relacionadas a esta matéria, por exemplo se uma matéria revoga outras 3 matérias.
			//    {
			//      "idXML": "string", // idXML - OBRIGATÓRIO - ID que identifica unicamente a matéria relacionada (idJornal+DataPublicacaoEfetiva+IdMateria)
			//      "ordem": "integer" // ordem - OPCIONAL - ordem das matérias relacionadas. Uso futuro.
			//    }
			//  ],
			log.trace("IGNORED - materiasRelacionadas: " + metadados.getMateriasRelacionadas());

			//  "publicacao": {
			decodePublicacao(metadados.getPublicacao());
			//  }
		}
	}

	private void decodePublicacao(Publicacao publicacao) {
		if (publicacao != null) {
			//    "retificacaoTecnica": "boolean", //retificacaoTecnica - OBRIGATÓRIO - true ou false - determina se a matéria está sendo retificada.Pode ocorrer uma falha na disponibilização em XML, apesar do formato oficial está ok. Neste caso se retifica a matéria. Não é uma nova publicação. É retificação da mesma publicação. Padrão é false
			this.isRetification = publicacao.getRetificacaoTecnica();
			//    "jornal": {//jornal - OBRIGATÓRIO - informações do Jornal 
			decodeObject(publicacao.getJornal(), this::decodeJornal, true);
			//    },
			//    "dataPublicacao": "string",// dataPublicacao - OBRIGATÓRIO - Data da efetiva publicação da matéria, não é a data agendada para publicar a matéria. Formato yyyy-MM-dd
			String dataPublicacao = publicacao.getDataPublicacao();
			if (dataPublicacao != null) {
				try {
					this.pubDate = new SimpleDateFormat(PUB_DATE_FORMAT)
							.format(new SimpleDateFormat(JSON_PUB_DATE_FORMAT)
									.parse(dataPublicacao));
				} catch (ParseException e) {
					String errorMessage = "Date format conversion error: " + e.getMessage();
					log.error(errorMessage);
					throw new SystemException(errorMessage, e);
				}
			}
			//    "numeroPaginaPdf": "integer", //numeroPaginaPdf - OBRIGATÓRIO - número da página onde está a matéria dentro do PDF/caderno
			this.numberPage = String.valueOf(publicacao.getNumeroPaginaPdf());
			//    "urlVersaoOficialPdf": "string", //urlVersaoOficialPdf - OPCIONAL - URL da imprensa oficial onde estará o PDF que contém a matéria, se houver.
			this.pdfPage = publicacao.getUrlVersaoOficialPdf();
			//    "urlVersaoOficialHtml": "string" //urlVersaoOficialHtml - OPCIONAL - URL da imprensa oficial onde estará exibindo esta matéria em formato HTML.
			log.trace("IGNORED - urlVersaoOficialHtml: " + publicacao.getUrlVersaoOficialHtml());
		}
	}

	private void decodeJornal(Jornal jornal) {
		if (jornal != null) {
			//  "ano": "string", //ano - OPCIONAL - Ano em formato exibido no jornal, geralmente romano. Por exemplo, CLVIII.
			log.trace("IGNORED - ano: " + jornal.getAno());
			//  "idJornal": "integer", //idJornal - OBRIGATÓRIO - código do jornal na imprensa oficial.
			this.newspaperId = String.valueOf(jornal.getIdJornal());
			//  "jornal": "string", //jornal - OBRIGATÓRIO - nome do jornal (DOU, DODF...) na imprensa oficial.
			log.trace("IGNORED - jornal: " + jornal.getJornal());
			//  "numeroEdicao": "string", // numeroEdicao - OBRIGATÓRIO - texto que identifica aquela edição. DOU nº "151"
			this.editionNumber = jornal.getNumeroEdicao();
			//  "idSecao": "integer", // idSecao - OPCIONAL - Código da seção (interno da Imprensa Oficial)
			log.trace("IGNORED - idSecao: " + jornal.getIdSecao());
			//  "secao": "string",// secao - OPCIONAL - Nome ou número da sessão (DOU seção 1, DOU seção 2, DOU seção 3)
			log.trace("IGNORED - secao: " + jornal.getSecao());
			//  "isExtra": "boolean", // isExtra - OBRIGATÓRIO - true ou false - determina se a matéria faz parte de uma  edição é extra ou não. Padrão é false.
			log.trace("IGNORED - isExtra: " + jornal.getIsExtra());
			//  "isSuplemento": "boolean" //isSuplemento - OBRIGATÓRIO - true ou false - determina se a matéria faz parte de um suplemento é extra ou não.  Padrão é false
			log.trace("IGNORED - isSuplemento: " + jornal.getIsSuplemento());
		}
	}

	private void decodeDestaque(Destaque destaque) {
		if (destaque != null) {
			//  "tipo": "string", //tipo - OBRIGATÓRIO - Tipos são:  "DESTAQUE_DOU" - Destaques do Diário Oficial da União, "CONCURSO_SELECAO" - Concursos e Seleções, "DESTAQUE_ESPECIAL" - Destaques Especiais (Destaques especiais não existem na IN desde de 01/05/2019)
			this.highlightType = convertHighlightType(destaque.getTipo());
			//  "prioridade": "integer",//OBRIGATÓRIO - OPCIONAL - código que indica a prioridade de exibição no portal
			this.highlightPriority = String.valueOf(destaque.getPrioridade());
			//  "texto": "string",// texto - OBRIGATÓRIO - texto do destaque
			this.highlight = destaque.getTexto();
			//  "nomeImagem": "string", //name - OPCIONAL - Atributo name é o nome da imagem -->
			this.highlightImageName = destaque.getNomeImagem();
			//  "fonteImagem": "string" //src - OPCIONAL - Atributo src contém o base64 da imagem (formato html5);
			if (destaque.getFonteImagem() != null && !destaque.getFonteImagem().isEmpty()) {
				this.highlightImage = this._medialibraryConnector.saveMedia(
						destaque.getNomeImagem(), destaque.getFonteImagem());
			}
		}
	}

	private void decodeOrigem(Origem origem) {
		if (origem != null) {
			if (this.hierarchyList == null) {
				this.hierarchyList = new LinkedList<>();
			}

			//  "idOrigem": "long", // idOrigem - OBRIGATÓRIO - código do órgão de origem da matéria. Órgão responsável pela matéria.
			log.trace("IGNORED - idOrigem: " + origem.getIdOrigem());
			//  "nomeOrigem": "string",  // nomeOrigem - OBRIGATÓRIO - nome do órgão de origem da matéria. Órgão responsável pela matéria.
			this.hierarchyList.add(origem.getNomeOrigem());
			//  "idSiorg": "long", //idSiorg - OPCIONAL - código do órgão de origem no SIORG (Órgãos e entidades federais)
			log.trace("IGNORED - idSiorg: " + origem.getIdSiorg());
			//  "uf": "string", //uf - OPCIONAL - Unidade da Federação origem da matéria
			log.trace("IGNORED - uf: " + origem.getUf());
			//  "nomeMunicipio": "string", //nomeMunicipio - OPCIONAL - Nome do município origem da matéria
			log.trace("IGNORED - nomeMunicipio: " + origem.getNomeMunicipio());
			//  "idMunicipioIbge": "long", //idMunicipioIbge - OPCIONAL - Código IBGE do município originário da matéria
			log.trace("IGNORED - idMunicipioIbge: " + origem.getIdMunicipioIbge());

			//  "origemPai": {//origemPai - OPCIONAL - mesma descrição acima, mas diz respeito ao pai do órgão acima. Ou seja, se acima é Departamento Penitenciário Nacional, o pai será o MJ.
			decodeOrigem(origem.getOrigemPai());
			//  }
		}
	}

	private <T> void decodeObject(T object, Consumer<T> c, Boolean mandatory) {
		if (object != null) {
			c.accept(object);
		} else {
			if (mandatory) {
				String errorMessage = "Missing mandatory parameter (" + c.getClass().getName() + ")";
				throw new SystemException(errorMessage);
			}
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

	private final Materia materia;
	private String newspaperId;
	private List<String> hierarchyList;
	private boolean isRetification;

	private static final String UNIQUE_PUB_DATE_FORMAT = "yyyyMMdd";
	private static final String JSON_PUB_DATE_FORMAT = "yyyy-MM-dd";

	private static Log log = LogFactoryUtil.getLog(ArticleJSONParser.class);
}