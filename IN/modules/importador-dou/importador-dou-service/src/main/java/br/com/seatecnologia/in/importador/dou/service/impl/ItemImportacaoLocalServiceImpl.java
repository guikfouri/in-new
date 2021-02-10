/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package br.com.seatecnologia.in.importador.dou.service.impl;

import com.liferay.asset.kernel.exception.DuplicateCategoryException;
import com.liferay.asset.kernel.exception.DuplicateVocabularyException;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.document.library.sync.constants.DLSyncConstants;
import com.liferay.journal.exception.DuplicateFolderNameException;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import br.com.seatecnologia.in.importador.dou.article.Article;
import br.com.seatecnologia.in.importador.dou.context.ItemImportacaoServiceContext;
import br.com.seatecnologia.in.importador.dou.model.ItemImportacao;
import br.com.seatecnologia.in.importador.dou.service.base.ItemImportacaoLocalServiceBaseImpl;
import br.com.seatecnologia.in.importador.dou.service.util.ArticleParserMediaLibraryConnectorUtil;
import br.com.seatecnologia.in.importador.dou.service.util.LegacyXMLFormatArticle;
import br.com.seatecnologia.migracao.model.ConteudoItemMigracao;
import br.com.seatecnologia.migracao.model.ImagemContItemMigracao;
import br.com.seatecnologia.migracao.model.ItemMigracao;
import br.com.seatecnologia.migracao.model.TituloItemMigracao;
import br.com.seatecnologia.migracao.model.TradConteudoItemMigracao;
import br.com.seatecnologia.migracao.service.ConteudoItemMigracaoLocalService;
import br.com.seatecnologia.migracao.service.ImagemContItemMigracaoLocalService;
import br.com.seatecnologia.migracao.service.ItemMigracaoLocalService;
import br.com.seatecnologia.migracao.service.TituloItemMigracaoLocalService;
import br.com.seatecnologia.migracao.service.TradConteudoItemMigracaoLocalService;

/**
 * The implementation of the item importacao local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link br.com.seatecnologia.in.importador.dou.service.ItemImportacaoLocalService} interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author SEA Tecnologia
 * @see ItemImportacaoLocalServiceBaseImpl
 * @see br.com.seatecnologia.in.importador.dou.service._itemImportacaoLocalService
 */
public class ItemImportacaoLocalServiceImpl
	extends ItemImportacaoLocalServiceBaseImpl {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Always use {@link br.com.seatecnologia.in.importador.dou.service._itemImportacaoLocalService} to access the item importacao local service.
	 */
	private static Log log = LogFactoryUtil.getLog(ItemImportacaoLocalServiceImpl.class);

	private static boolean IS_DEBUG = false;

	private static final String DEFAULT_LANGUAGE_ID = "pt_BR";

//	@SuppressWarnings("unused")
//	private boolean isHighlight(File xml) {
//		String xmlArticle = null;
//
//		try {
//			xmlArticle = readXMLFile(xml.getAbsolutePath());
//		} catch (IOException e) {
//			log.error(e);
//		}
//
//		return isHighlight(xmlArticle);
//	}
//	private boolean isHighlight(String xmlArticle) {
//		Document document = Jsoup.parse(xmlArticle, "UTF-8", Parser.xmlParser());
//		Elements article = document.select("article");
//		String highlight = article.attr("highlight");
//		return highlight.length() > 0 ? true : false;
//	}
	private boolean isHighlight(Article douArticle) {
		return (douArticle.getHighlight() != null && douArticle.getHighlight().length() > 0);
	}
	
	private boolean hasHighlightImage(Article douArticle) {
		return (douArticle.getHighlightImage() != null && douArticle.getHighlightImage().length() > 0);
	}

	private void importXml(ItemImportacaoServiceContext itemImportacaoServiceContext, String identificadorAtualizacao, Article douArticle, Map<String, String> brokenXmlTxt, ServiceContext serviceContext) throws SystemException {
		String pubOrder = douArticle.getPubName() + douArticle.getArtClass();
		ItemMigracao itemMigracao = createItemMigracao(itemImportacaoServiceContext, identificadorAtualizacao, douArticle, serviceContext);
		if (itemMigracao == null)
			return;

		TituloItemMigracao titulo = _tituloItemMigracaoLocalService.createTituloItemMigracao(nextId());
		titulo.setItemMigracaoId(itemMigracao.getItemMigracaoId());
		titulo.setLocale(DEFAULT_LANGUAGE_ID);

		if (!douArticle.getIdentifica().isEmpty()) {
			titulo.setTitulo(douArticle.getIdentifica());
		} else if (!douArticle.getArtTitulo().isEmpty()) {
			titulo.setTitulo(douArticle.getArtTitulo());
		} else if (!douArticle.getSubTitulo().isEmpty()) {
			titulo.setTitulo(douArticle.getSubTitulo());
		}

		if (titulo.getTitulo().isEmpty()) {
			titulo.setTitulo("Z...");
		}

		if (!IS_DEBUG)
			_tituloItemMigracaoLocalService.addTituloItemMigracao(titulo);

		//article elements
		createConteudoItemTraducao(itemMigracao, "idDou", douArticle.getID(), null);
		createConteudoItemTraducao(itemMigracao, "name", douArticle.getName(), null);
		createConteudoItemTraducao(itemMigracao, "artType", douArticle.getArtType(), null);
		createConteudoItemTraducao(itemMigracao, "idOficio", douArticle.getIDOficio(), null);
		createConteudoItemTraducao(itemMigracao, "pubName", douArticle.getPubName(), null);
		createConteudoItemTraducao(itemMigracao, "pubDate", douArticle.getPubDate(), null);
		createConteudoItemTraducao(itemMigracao, "artSection", douArticle.getArtSection(), null);
		createConteudoItemTraducao(itemMigracao, "artCategory", douArticle.getArtCategory(), null);
		createConteudoItemTraducao(itemMigracao, "numberPage", douArticle.getNumberPage(), null);
		createConteudoItemTraducao(itemMigracao, "artClass", douArticle.getArtClass(), null);
		createConteudoItemTraducao(itemMigracao, "hierarchy", douArticle.getHierarchy(), null);
		createConteudoItemTraducao(itemMigracao, "pubOrder", pubOrder, null);
		createConteudoItemTraducao(itemMigracao, "idMateria", douArticle.getIDMateria(), null);
		createConteudoItemTraducao(itemMigracao, "highlightPriority", douArticle.getHighlightPriority(), null);
		createConteudoItemTraducao(itemMigracao, "highlight", douArticle.getHighlight(), null);
		createConteudoItemTraducao(itemMigracao, "highlightType", douArticle.getHighlightType(), null);
		
		if(isHighlight(douArticle) && hasHighlightImage(douArticle)) {
			long imgContentId = createConteudoItemTraducao(itemMigracao, "HighlightImage",douArticle.getHighlightImage(), null);
			createConteudoItemImagem(itemMigracao, douArticle.getHighlightImage(), imgContentId, getFolderPath(douArticle));
		}

		String[] pdfPageSplitted = douArticle.getPdfPage().split(",");

		if (pdfPageSplitted[0] != null) {
			createConteudoItemTraducao(itemMigracao, "pdfPage", pdfPageSplitted[0], null);
		}else{
			createConteudoItemTraducao(itemMigracao, "pdfPage", douArticle.getPdfPage(), null);
		}

		//materia nodes
		createConteudoItemTraducao(itemMigracao, "editionNumber", douArticle.getEditionNumber(), null);
		createConteudoItemTraducao(itemMigracao, "titulo",  douArticle.getArtTitulo(), null);
		createConteudoItemTraducao(itemMigracao, "subTitulo", douArticle.getSubTitulo(), null);
		createConteudoItemTraducao(itemMigracao, "data", douArticle.getData(), null);
		createConteudoItemTraducao(itemMigracao, "ementa", douArticle.getEmenta(), null);

		//get autores
		if (douArticle.getAutores() != null && !douArticle.getAutores().isEmpty()) {
			for (Map.Entry<String, String> autor : douArticle.getAutores().entrySet()) { 
				Long conteudoPaiId = createConteudoItemTraducao(itemMigracao, "assina", autor.getKey(),null);
				if(!autor.getValue().isEmpty()){
					createConteudoItemTraducao(itemMigracao, "cargo", autor.getValue(),conteudoPaiId);
				}
			}
		}

		//adiciona o texto dos xmls quebrados caso houver
		String textoFinal = StringPool.BLANK;

		if (brokenXmlTxt != null && !brokenXmlTxt.isEmpty()) {
			StringBuilder sb =  new StringBuilder();
			sb.append(douArticle.getTexto());
			for (Map.Entry<String, String> element : brokenXmlTxt.entrySet()) { 
				sb.append(element.getValue());
			}
			textoFinal = sb.toString();
		} else {
			textoFinal = douArticle.getTexto();
		}

		File[] imgs = null;
		File dirImgs = new File(itemImportacaoServiceContext.getResourcesPath());
		//pega as imagens no diretorio somente dessa materia caso exista imagem na materia
		if (textoFinal.contains("<img name=")) {
			imgs = getFilesByExtension(dirImgs, ".jpg",textoFinal);
		}

		String folderPath = getFolderPath(douArticle);

		boolean existsImg = false;
		if (imgs != null) {
			//limpa o nome de todas as imagens e retorna os nomes antigos para serem substituidos
			ArrayList<String> oldFileNames = cleanImgsFileNames(imgs);
			if (!oldFileNames.isEmpty()) {
				File[] imgsParsed = getFilesByExtension(dirImgs, ".jpg",textoFinal);
				int imgsParsedSize = imgsParsed.length;

				if (imgsParsedSize == oldFileNames.size()) {
					int imgCount = 0;
					HashMap<String, String> textReplace =  new HashMap<>();
					for (File img : imgsParsed) {
						String imgPath = img.getAbsolutePath();
						textReplace.put("<img name=\""+oldFileNames.get(imgCount)+"\"", "<img class=\"dou-img\" src=\""+imgPath+"\"");
						imgCount++;
					}

					textoFinal =  replaceAllImgLinks(textoFinal, textReplace);

					Long conteudoId = createConteudoItemTraducao(itemMigracao, "texto", 
							textoFinal.replaceAll("<p>", "<p class=\"dou-paragraph\" >")
							.replaceAll("<strong>", "<strong class=\"dou-strong\" >")
							.replaceAll("<em>","<em class=\"dou-em\" >")
							.replaceAll("<table>", "<table class=\"dou-table\" >")
							.replaceAll("/documents/", "&#47;documents&#47;"),null);
					for (File img : imgsParsed) {
						String imgPath = img.getAbsolutePath();
						createConteudoItemImagem(itemMigracao, imgPath, conteudoId, folderPath);
					}
					existsImg = true;
				} else {
					log.error("[ERROR] Nem todas imagens foram parseadas ou nao existem, veja o log completo para mais info.");
					existsImg = false;
				}
			} else {
				log.error("[ERROR] Imagens nao parseadas ou nao existentes: " + dirImgs.getAbsolutePath());
				existsImg = false;
			}
		}

		//caso nao exista nenhuma imagem na publicacao o texto final permanece
		if (!existsImg) {
			createConteudoItemTraducao(itemMigracao, "texto", textoFinal.replaceAll("<p>", "<p class=\"dou-paragraph\" >")
					.replaceAll("<strong>", "<strong class=\"dou-strong\" >")
					.replaceAll("<em>","<em class=\"dou-em\" >")
					.replaceAll("<table>", "<table class=\"dou-table\" >")
					.replaceAll("/documents/", "&#47;documents&#47;"),null);
		}

		ItemImportacao itemImportacao = itemImportacaoLocalService.createItemImportacao(nextId());
		itemImportacao.setDataImportacao(new Date());
		itemImportacao.setIdentificadorAtualizacao(identificadorAtualizacao);
		itemImportacao.setTipoItem(douArticle.getPubName());
		itemImportacao.setImportado(true);

		if(!IS_DEBUG)			
			itemImportacaoLocalService.addItemImportacao(itemImportacao);
	}

	private String getFolderPath(Article douArticle) {
		String yearFolder = getYearOrMonthOrDay(douArticle.getPubDate(), "year");
		String monthFolder = getYearOrMonthOrDay(douArticle.getPubDate(), "month");
		String dayFolder = getYearOrMonthOrDay(douArticle.getPubDate(), "day");
		String folderPath = "DOU" + StringPool.SLASH +
				douArticle.getPubName() + " - " + getSection(douArticle.getPubName()) + StringPool.SLASH +
				yearFolder + StringPool.SLASH +
				monthFolder + StringPool.SLASH +
				dayFolder;

		return folderPath;
	}

	public ItemImportacao importarXML(ItemImportacaoServiceContext itemImportacaoServiceContext, String identificadorAtualizacao, Article douArticle, Map<String, String> additionalXmlTxt, ServiceContext serviceContext) {
		ItemImportacao itemImportacao = null;

		try {
			if (identificadorAtualizacao == null || identificadorAtualizacao.length() == 0) {
				identificadorAtualizacao = douArticle.getUniqueName();
			}

			if (itemImportacaoPersistence.countByIdentificadorAtualizacao(identificadorAtualizacao) > 0) {
				itemImportacao = itemImportacaoPersistence.fetchByIdentificadorAtualizacao_First(identificadorAtualizacao, null);
				if (itemImportacao.getImportado() && !douArticle.isRetification()) {
					if (douArticle.isRetification()) {
						log.info("XML é uma retificação: " + identificadorAtualizacao);
					} else {
						log.info("XML já tratado: " + identificadorAtualizacao);
						return itemImportacao;
					}
				}
			}

			log.debug("Importando XML: " + identificadorAtualizacao);
			importXml(itemImportacaoServiceContext, identificadorAtualizacao, douArticle, additionalXmlTxt, serviceContext);

			itemImportacao = itemImportacaoPersistence.fetchByIdentificadorAtualizacao_First(identificadorAtualizacao, null);
		} catch (SystemException e) {
			log.error("Conteúdo XML não foi importado (" + e.getMessage() + ")"); // Materia não foi importada
		}

		return itemImportacao;
	}

	public ItemImportacao importarXML(Long groupId, Long idSecaoVocab, Long idArranjoSecaoVocab, Long idTipoMateriaVocab, String basePathImgs, String identificadorAtualizacao, Article douArticle, Map<String, String> additionalXmlTxt, ServiceContext serviceContext) {
		return importarXML(new ItemImportacaoServiceContext(groupId, idSecaoVocab, idArranjoSecaoVocab, idTipoMateriaVocab, basePathImgs), identificadorAtualizacao, douArticle, additionalXmlTxt, serviceContext);
	}

	public ItemImportacao importarXML(ItemImportacaoServiceContext itemImportacaoServiceContext, String identificadorAtualizacao, String xmlContent, Map<String, String> additionalXmlTxt, ServiceContext serviceContext) {
		ItemImportacao itemImportacao = null;
		try {
			if (identificadorAtualizacao != null && identificadorAtualizacao.length() > 0) {
				if (itemImportacaoPersistence.countByIdentificadorAtualizacao(identificadorAtualizacao) > 0) {
					itemImportacao = itemImportacaoPersistence.fetchByIdentificadorAtualizacao_First(identificadorAtualizacao, null);
					if (itemImportacao.getImportado()) {
						log.info("XML já tratado: " + identificadorAtualizacao);
						return itemImportacao;
					}
				}
			}
			
			/*TODO
			 *Deve ser uma configuracao
			 */
			String mediaPath = itemImportacaoServiceContext.getResourcesPath();
			String highlightPath = mediaPath.endsWith("/") ? mediaPath+"highlight/" : mediaPath+"/highlight/";
			Article douArticle = new LegacyXMLFormatArticle(xmlContent,new  ArticleParserMediaLibraryConnectorUtil(highlightPath));

			if (identificadorAtualizacao == null || identificadorAtualizacao.length() == 0) {
				identificadorAtualizacao = douArticle.getUniqueName();

				if (itemImportacaoPersistence.countByIdentificadorAtualizacao(identificadorAtualizacao) > 0) {
					itemImportacao = itemImportacaoPersistence.fetchByIdentificadorAtualizacao_First(identificadorAtualizacao, null);
					if (itemImportacao.getImportado()) {
						if (douArticle.isRetification()) {
							log.info("XML é uma retificação: " + identificadorAtualizacao);
						} else {
							log.info("XML já tratado: " + identificadorAtualizacao);
							return itemImportacao;
						}
					}
				}
			}

			log.debug("Importando XML: " + identificadorAtualizacao);
			importXml(itemImportacaoServiceContext, identificadorAtualizacao, douArticle, additionalXmlTxt, serviceContext);

			itemImportacao = itemImportacaoPersistence.fetchByIdentificadorAtualizacao_First(identificadorAtualizacao, null);
		} catch (SystemException e) {
			log.error("Conteúdo XML não foi importado (" + e.getMessage() + ")"); // Materia não foi importada
		}

		return itemImportacao;
	}

	public ItemImportacao importarXML(Long groupId, Long idSecaoVocab, Long idArranjoSecaoVocab, Long idTipoMateriaVocab, String basePathImgs, String identificadorAtualizacao, String xmlContent, Map<String, String> additionalXmlTxt, ServiceContext serviceContext) {
		return importarXML(new ItemImportacaoServiceContext(groupId, idSecaoVocab, idArranjoSecaoVocab, idTipoMateriaVocab, basePathImgs), identificadorAtualizacao, xmlContent, additionalXmlTxt, serviceContext);
	}

	private String getCategoriesAsString(long[] categories) {

		String[] string_list = new String[categories.length];

		for(int i = 0; i < categories.length; i++) {
			if (categories[i] > 0) {
				string_list[i] = String.valueOf(categories[i]);
			}
		}

		return StringUtils.join(string_list, ",");
	}

	private File[] getFilesByExtension(File dir,String extension, String textoFinal) {
		final String fileExtension = extension;

		File[] files = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(fileExtension);
			}
		});

		//caso seja imagem,pegar somente a que pertence a essa materia
		if(extension.equals(".jpg")){
			List<File> filesImgList = new ArrayList<File>();
			for (File file : files) {
				if(textoFinal.contains(file.getName())){
					filesImgList.add(file);
				}
			}

			if(filesImgList.size() > 0){
				files = new File[filesImgList.size()];

				for (int i =0; i<filesImgList.size();i++) {
					files[i] = filesImgList.get(i);
				}
			}
		}

		//e extremamente importante manter a ordem dos arquivos por causa dos xmls quebrados
		Arrays.sort(files);
		return files;
	}

	private void createConteudoItemImagem(ItemMigracao itemMigracao, String url, Long conteudId, String folderName) throws SystemException {
		ImagemContItemMigracao imagem = _imagemContItemMigracaoLocalService.createImagemContItemMigracao(nextId());
		imagem.setConteudoItemMigracaoId(conteudId);
		imagem.setFolderName(folderName);
		imagem.setLocale(DEFAULT_LANGUAGE_ID);
		imagem.setNome(url.substring(url.lastIndexOf("/")));
		imagem.setUrl(url);
		if (!IS_DEBUG)
			_imagemContItemMigracaoLocalService.addImagemContItemMigracao(imagem);
	}

	/**
	 * Método responsável por criar os elementos relacionados ao conteúdo localizado da estrutura de migração.
	 * 
	 * <p>
	 * Adiciona o objeto do conteúdo de migração recebido à base de dados. 
	 * Cria o registro de conteúdo localizado, traduzido, relativo ao 
	 * conteúdo de migração e também o adiciona na base de dados.
	 * </p>
	 * @param itemMigracao objeto da estrutura de migração a qual o conteúdo pertence
	 * @param campo String com o nome do campo da do conteúdo
	 * @param texto String do conteúdo a ser inserido
	 * @param conteudoPaiId id do conteúdo ao qual esse conteúdo pertence (se houver)
	 * @return retorna o id do conteúdo de migração
	 * @throws SystemException
	 * 
	 * @author SEA Tecnologia
	 */
	private Long createConteudoItemTraducao(ItemMigracao itemMigracao, String campo, String texto, Long conteudoPaiId) throws SystemException {
		ConteudoItemMigracao conteudo = _conteudoItemMigracaoLocalService.createConteudoItemMigracao(nextId());

		if (conteudoPaiId != null) {
			conteudo.setPaiConteudoItemMigracaoId(conteudoPaiId);
		}
		conteudo.setItemMigracaoId(itemMigracao.getItemMigracaoId());
		conteudo.setCampo(campo);

		if (!IS_DEBUG)
			_conteudoItemMigracaoLocalService.addConteudoItemMigracao(conteudo);

		TradConteudoItemMigracao traducao = _tradConteudoItemMigracaoLocalService.createTradConteudoItemMigracao(nextId());
		traducao.setConteudoItemMigracaoId(conteudo.getConteudoItemMigracaoId());
		traducao.setLocale(DEFAULT_LANGUAGE_ID);
		traducao.setConteudo(texto);
		if (!IS_DEBUG)
			_tradConteudoItemMigracaoLocalService.addTradConteudoItemMigracao(traducao);

		Long conteudoId = conteudo.getConteudoItemMigracaoId();

		return conteudoId;
	}

	private Long nextId() throws SystemException {
		return IS_DEBUG ? 0L : counterLocalService.increment();
	}

	private Date parseDate(String date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			log.error("[ERROR] Ocorreu um erro ao fazer o parse da data: " + date, e);
		}

		return null;
	}

	private String getSection(String s) {
		String section = StringPool.BLANK;
		switch (s) {
		case "DO1":
			section = "Seção 1";
			break;
		case "DO1A":
			section = "Seção 1";
			break;
		case "DO2":
			section = "Seção 2";
			break;
		case "DO2A":
			section = "Seção 2";
			break;
		case "DO3":
			section = "Seção 3";
			break;
		case "DO3A":
			section = "Seção 3";
			break;
		default:
			section = s;
			break;
		}

		return section;
	}

	private String getYearOrMonthOrDay(String date, String identifier) {
		String[] dateSplitted;
		String dt = StringPool.BLANK;
		try {
			if (!date.isEmpty()) {
				dateSplitted = date.split("/");
				switch (identifier) {
				case "year":
					dt = dateSplitted[2];
					break;
				case "month":
					dt = dateSplitted[1];
					break;
				case "day":
					dt = dateSplitted[0];
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			log.error("[ERROR] Ocorreu um erro ao fazer o parse do ano da data" + date, e);
		}

		return dt;
	}

	/**
	 * 
	 * limpa o nome (remove tags non ascii e os espacos) de todas as imagens e retorna o nome antigo para ser substituido
	 **/
	private ArrayList<String> cleanImgsFileNames(File[] imgs) {
		ArrayList<String> oldFileNames = new ArrayList<>();
		for (File file : imgs) {
			Path source = Paths.get(file.getAbsolutePath());
			try {
				oldFileNames.add(file.getName().replace(".jpg", StringPool.BLANK));
				Files.move(source, source.resolveSibling(file.getAbsolutePath()
						.replaceAll("\\P{Print}", StringPool.BLANK).replaceAll("\\s+",StringPool.BLANK)));
			} catch (IOException e) {
				log.error("[ERROR] Erro ao fazer o parse do nome da imagem.jpg"+file.getAbsolutePath()+","+ e);
			}
		}
		return oldFileNames;
	}

	/**
	 * Procura pelo id de categoria a partir do seu título, caso não exista a categoria, esta será criada
	 * 
	 * @param title título da categoria que se deseja ter o id
	 * @param vocabularyId vocabulario ao qual pertence a categoria
	 * @param parentCategoryId categoria pai a qual pertence a categoria
	 * @param serviceContext objeto com informações do contexto da camada de serviço em uso
	 * @return retorna o id da categoria desejada
	 * @throws SystemException 
	 * @throws PortalException 
	 */
	private long getCategoryId(String title, long vocabularyId, long parentCategoryId, ServiceContext serviceContext) throws SystemException, PortalException {

		Locale locale = LocaleUtil.fromLanguageId(DEFAULT_LANGUAGE_ID);
		Map<Locale, String> mapTitle = new HashMap<Locale, String>();
		String[] properties = { StringPool.BLANK };
		mapTitle.put(locale, title);

//		if (assetVocabularyLocalService.getAssetVocabulary(vocabularyId).getGroupId() != serviceContext.getScopeGroupId()) {
//			throw new PortalException("Cannot create category on that vocabulary because they are of different scope " + 
//					"(VocabularyGroupId:" + assetVocabularyLocalService.getAssetVocabulary(vocabularyId).getGroupId() + 
//					" vs currentScopeGroupId:" + serviceContext.getScopeGroupId() + ")");
//		}

		try {
			AssetCategory assetCategory = assetCategoryLocalService.addCategory(serviceContext.getUserId(), assetVocabularyLocalService.getAssetVocabulary(vocabularyId).getGroupId(), parentCategoryId, mapTitle, new HashMap<Locale, String>(), vocabularyId, properties, serviceContext);
			return assetCategory.getCategoryId();
		} catch (DuplicateCategoryException e) {
			long categoryId = getCategoryId(vocabularyId, title);
			return categoryId;
		}
	}

	/**
	 * Dado um caminho de pasta, cria a sua estrutura, ou não faz nada caso ela
	 * já exista.
	 * 
	 * @param caminho
	 *            - exemplo: /reavaliacoes_agrotoxicos/anteriores_2006
	 * @param groupId
	 *            - groupId para se criar a estrutura de pastas
	 * 
	 * @return ultimo folderId criado
	 */
	private String montarEstruturaPastasWebContent(String caminho, long groupId, ServiceContext serviceContext) throws Exception {

		String[] pastas = caminho.split(StringPool.SLASH);

		long currentFolderId = 0;

		if (ArrayUtil.contains(pastas, StringPool.BLANK)) {
			pastas = ArrayUtil.remove(pastas, StringPool.BLANK);
		}

		for (String pasta : pastas) {
			currentFolderId = getOrCreateWebContentFolder(pasta, groupId, currentFolderId, serviceContext.getUserId());
		}

		return String.valueOf(currentFolderId);
	}

	private long getOrCreateWebContentFolder(String title, long groupId, long parentFolderId, long userId) {

		String[] guestPermissions = { ActionKeys.VIEW };

		ServiceContext serviceContext = new ServiceContext();
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);
		serviceContext.setWorkflowAction(WorkflowConstants.STATUS_APPROVED);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setIndexingEnabled(true);
		serviceContext.setAddGroupPermissions(true);
		serviceContext.setGuestPermissions(guestPermissions);
		Map<String, Serializable> workflowContext = new HashMap<String, Serializable>();
		workflowContext.put("event", DLSyncConstants.EVENT_ADD);

		try {
			return journalFolderLocalService.addFolder(serviceContext.getUserId(), groupId, parentFolderId, title,
					title, serviceContext).getFolderId();
		} catch (DuplicateFolderNameException e) {

			try {
				return journalFolderLocalService.fetchFolder(groupId, parentFolderId, title).getFolderId();
			} catch (SystemException ex) {
				log.error(ex);
			}

		} catch (PortalException e) {
			log.error(e);
		} catch (SystemException e) {
			log.error(e);
		}
		return 0;
	}

	private long getVocabularyId(String title, long groupId, ServiceContext serviceContext) throws Exception {
		try {
			return assetVocabularyLocalService.addVocabulary(serviceContext.getUserId(), groupId, title, serviceContext).getVocabularyId();
		} catch (DuplicateVocabularyException e) {
			long vocabularyId = getVocabularyId(title);
			return vocabularyId;
		}

	}

	private long getVocabularyId(String vocabulario) throws Exception {
		List<AssetVocabulary> assetVocabularies = assetVocabularyLocalService.getAssetVocabularies(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		long vocabularyId = 0;

		for (AssetVocabulary asset : assetVocabularies) {
			if (asset.getTitle(LocaleUtil.BRAZIL).trim().equalsIgnoreCase(vocabulario.trim())) {
				vocabularyId = asset.getPrimaryKey();
				break;
			}
		}

		return vocabularyId;
	}

	private long getCategoryId(long vocabularyId, String categoria) throws SystemException {
		List<AssetCategory> assetCategories = assetCategoryLocalService.getVocabularyCategories(vocabularyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		for (AssetCategory assetCategory : assetCategories) {
			if (assetCategory.getTitle(LocaleUtil.BRAZIL).trim().equalsIgnoreCase(categoria.trim())){
				return assetCategory.getCategoryId();
			}
		}
		throw new NullPointerException("ID de categoria " + categoria + " nao encontrada do vocabulario de ID: " + Long.toString(vocabularyId));
	}

//	private void createCategory(ServiceContext serviceContext, List<AssetCategory> categoriasDoItem,
//			Map<String, String> vocabularioCategoriasDoItem, AssetVocabulary assetVocabularyItem)
//					throws SystemException, PortalException {
//		// ... procura a categoria...
//		List<AssetCategory> categoriasDoVocabulario = assetCategoryLocalService.getVocabularyCategories(
//				assetVocabularyItem.getVocabularyId(), QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
//
//		List<String> categorias = ListUtil.fromArray(ArrayUtil.remove(
//				vocabularioCategoriasDoItem.get(assetVocabularyItem.getName()).split(StringPool.FORWARD_SLASH),
//				StringPool.BLANK));
//
//		ArrayList<String> categoriasTemp = new ArrayList<String>();
//
//		// insere a categoria de acordo com o json e caso ela ja
//		// exista
//		for (AssetCategory assetCategory : categoriasDoVocabulario) {
//			if (SearchStringList.searchStringListIgnoreCase(assetCategory.getName().trim(), categorias)) {
//				categoriasDoItem.add(assetCategory);
//			}
//			categoriasTemp.add(assetCategory.getName().trim().toLowerCase());
//		}
//
//		// caso a categoria ainda não exista ela é criada
//		for (String categoria : categorias) {
//			if (!categoriasTemp.contains(categoria.trim().toLowerCase())) {
//				AssetCategory assetCategoryCreated = AssetCategoryServiceUtil.addCategory(serviceContext.getScopeGroupId(), categoria,
//						assetVocabularyItem.getVocabularyId(), serviceContext);
//				categoriasDoItem.add(assetCategoryCreated);
//			}
//		}
//	}

	private String replaceAllImgLinks(final String text, final Map<String, String> definitions ) {

		final String[] keys = new String[definitions.size()];
		final String[] values = new String[definitions.size()];
		int index = 0;

		for (Entry<String, String> mapEntry : definitions.entrySet()) {
			keys[index] = mapEntry.getKey();
			values[index] = mapEntry.getValue();
			index++;
		}

		return StringUtils.replaceEach(text, keys, values);
	}

	public int contarItensImportacaoPorIdentificadorAtualizacao(String identificadorAtualizacao) {
		int result = 0;
		result = itemImportacaoPersistence.countByIdentificadorAtualizacao(identificadorAtualizacao);
		return result;
	}

	public void limparItemsImportacao() throws SystemException {
		List<ItemImportacao> itemsImportacao = itemImportacaoLocalService.getItemImportacaos(-1, -1);
		for (ItemImportacao item : itemsImportacao) {
			itemImportacaoLocalService.deleteItemImportacao(item);
		}
	}

	private ItemMigracao createItemMigracao(ItemImportacaoServiceContext itemImportacaoServiceContext, String identificadorAtualizacao, Article douArticle, ServiceContext serviceContext) {

		String folderPath = getFolderPath(douArticle);
		long idTipoMateriaPubName = itemImportacaoServiceContext.getIdTipoMateriaVocab();

		String folderId = "0";
		try {
			folderId = montarEstruturaPastasWebContent(folderPath, itemImportacaoServiceContext.getGroupId(), serviceContext);
			idTipoMateriaPubName = getVocabularyId("Tipo de matéria - " + douArticle.getPubName(), assetVocabularyLocalService.getAssetVocabulary(itemImportacaoServiceContext.getIdSecaoVocab()).getGroupId(), serviceContext);
		} catch (Exception e) {
			log.error("Error creating folders and/or vocabulary: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		//Get Categories
		String categoriesAsString = StringPool.BLANK;
		try {
			long highlightTypeCategory = 0;
			long highlightCategory = 0;
			long secaoCategory = getCategoryId(douArticle.getPubName(), itemImportacaoServiceContext.getIdSecaoVocab(), 0, serviceContext);
			long artSectionCategory = getCategoryId(douArticle.getArtSection(), itemImportacaoServiceContext.getIdArranjoSecaoVocab(), 0, serviceContext);
			long artCategoryCategory = getCategoryId(douArticle.getArtCategory(), itemImportacaoServiceContext.getIdArranjoSecaoVocab(), artSectionCategory, serviceContext);
			long artTypeCategory = getCategoryId(douArticle.getArtType(), itemImportacaoServiceContext.getIdTipoMateriaVocab(), 0, serviceContext);
			long pubNameCategory = getCategoryId(douArticle.getArtType(), idTipoMateriaPubName, 0, serviceContext);

			if (isHighlight(douArticle)) {
				highlightTypeCategory = getCategoryId(douArticle.getHighlightType(), idTipoMateriaPubName, 0, serviceContext);
				highlightCategory = getCategoryId("destaque", idTipoMateriaPubName, 0, serviceContext);
			}

			long[] categories = new long[]{secaoCategory, artSectionCategory, artCategoryCategory, artTypeCategory, pubNameCategory, highlightTypeCategory, highlightCategory } ;

			//Fazer um método que retorne uma string com os ids separados por virgula.
			categoriesAsString = getCategoriesAsString(categories);
		} catch (Exception e) {
			log.error("Error creating/getting categories: " + e.getMessage());
			e.printStackTrace();
			return null;
		}

		ItemMigracao itemMigracao = _itemMigracaoLocalService.createItemMigracao(nextId());
		itemMigracao.setMigrado(false);
		itemMigracao.setGroupId(itemImportacaoServiceContext.getGroupId());
		itemMigracao.setGroupName("DOU");
		itemMigracao.setFolderName(folderId);
		itemMigracao.setTipoItem(douArticle.getPubName());
		itemMigracao.setDataReferencia(parseDate(douArticle.getPubDate()));
		itemMigracao.setCategorias(categoriesAsString);
		itemMigracao.setIdentificador_atualizacao(identificadorAtualizacao);

		if (!IS_DEBUG)
			_itemMigracaoLocalService.addItemMigracao(itemMigracao);

		return itemMigracao;
	}

	@ServiceReference(type = ItemMigracaoLocalService.class)
	private ItemMigracaoLocalService _itemMigracaoLocalService;
	@ServiceReference(type = ConteudoItemMigracaoLocalService.class)
	private ConteudoItemMigracaoLocalService _conteudoItemMigracaoLocalService;
	@ServiceReference(type = ImagemContItemMigracaoLocalService.class)
	private ImagemContItemMigracaoLocalService _imagemContItemMigracaoLocalService;
	@ServiceReference(type = TituloItemMigracaoLocalService.class)
	private TituloItemMigracaoLocalService _tituloItemMigracaoLocalService;
	@ServiceReference(type = TradConteudoItemMigracaoLocalService.class)
	private TradConteudoItemMigracaoLocalService _tradConteudoItemMigracaoLocalService;
}