package br.com.seatecnologia.in.importador.dou.file.service.impl;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import br.com.seatecnologia.in.importador.dou.file.service.ImportadorDouFileService;
import br.com.seatecnologia.in.importador.dou.service.ItemImportacaoLocalService;

/**
 * @author mgsasaki
 */
@Component(
	immediate = true,
	property = {
		// TODO enter required service properties
	},
	service = ImportadorDouFileService.class
)
public class ImportadorDouFileImpl implements ImportadorDouFileService {
	private static Log log = LogFactoryUtil.getLog(ImportadorDouFileImpl.class);
	private static boolean IS_DEBUG = false;
	private Integer itensImportadosSoFar;
	private ExecutorService executor = null;

	@Override
	public Integer contarItensParaImportacao(String basePathXMLs) {
		int itemsParaImportacao = 0;
		File dirXMLs = new File(basePathXMLs);

		File[] xmls = dirXMLs.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith(".xml");
			}
		});

		itemsParaImportacao = xmls != null ? xmls.length : 0;
		for (File xml : xmls) {
			String identificadorAtualizacao = xml.getAbsolutePath();
			if (_itemImportacaoLocalService.contarItensImportacaoPorIdentificadorAtualizacao(identificadorAtualizacao) > 0) {
				itemsParaImportacao--;

			}	
			boolean isXMLPart = isXMLPart(xml);

			if (isXMLPart) {
				itemsParaImportacao--;
			}
		}

		return itemsParaImportacao;
	}

	@Override
	public Integer importarXMLs(Long groupId, Long idSecaoVocab, Long idArranjoSecaoVocab, Long idTipoMateriaVocab, String basePathXMLs, Long maxNumberTasks, Long timeOutPerFileSeconds, ServiceContext serviceContext) throws SystemException {
		Integer itensImportados = 0;

		if (executor != null) {
			String errorMessage = "Processo de importação em andamento. Não pode iniciar outro enquanto não finalizar!";
			log.error(errorMessage);
			throw new SystemException(errorMessage);
		}

		File[] xmls = getFilesByExtension(basePathXMLs, ".xml", "");

		if (xmls == null)
			return itensImportados;

		serviceContext.setAddGuestPermissions(true);
		serviceContext.setAttribute("defaultLanguageId", "pt_BR");
		serviceContext.setGuestPermissions(new String[] { "VIEW", "ADD_IMAGE" });
		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		Map<String, String> brokenXmlIdentifier = new ConcurrentHashMap<String, String>();
		executor = Executors.newFixedThreadPool(maxNumberTasks.intValue());
		itensImportadosSoFar = 0;

		for (File xml : xmls) {

			//Interrompe a importacao a cada 5 itens, para efeito de demonstração
			if (itensImportados > 0 && itensImportados % 5 == 0 && IS_DEBUG) {
				break;
			}

			String identificadorAtualizacao = xml.getAbsolutePath();
			if (_itemImportacaoLocalService.contarItensImportacaoPorIdentificadorAtualizacao(identificadorAtualizacao) > 0) {
				log.info("XML já tratado: " + xml);
				continue;
			} else if (isXMLPart(xml)) {
				log.info("XML já tratado: " + xml);
				continue;
			}

			Runnable worker = new Runnable() {

				@Override
				public void run() {
					PrincipalThreadLocal.setName(serviceContext.getUserId());

					if (!LocaleUtil.getDefault().equals(serviceContext.getLocale())) {
						log.info("Changing default locale to " + serviceContext.getLocale());
						LocaleUtil.setDefault(serviceContext.getLocale().getLanguage(), 
								serviceContext.getLocale().getCountry(), 
								serviceContext.getLocale().getVariant());
					}

					try {
						PermissionChecker permissionChecker = PermissionCheckerFactoryUtil.create(UserLocalServiceUtil.getUser(serviceContext.getUserId()));
						PermissionThreadLocal.setPermissionChecker(permissionChecker);
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					log.info("Importando XML: " + identificadorAtualizacao);

					try {
						if (!brokenXmlIdentifier.containsKey(xml.getAbsolutePath())) {

							//hash com todos textos e path das partes do xml quebrado
							Map<String, String> brokenXmlTxt = new LinkedHashMap<String, String>();
							brokenXmlTxt.putAll(verifyBrokenXML(basePathXMLs,xml));
							brokenXmlIdentifier.putAll(brokenXmlTxt);

							String xmlArticle = readXMLFile(xml.getAbsolutePath());

							_itemImportacaoLocalService.importarXML(groupId, idSecaoVocab, idArranjoSecaoVocab, idTipoMateriaVocab, basePathXMLs, identificadorAtualizacao, xmlArticle, brokenXmlTxt, serviceContext);

							brokenXmlIdentifier.putAll(brokenXmlTxt);

							synchronized (itensImportadosSoFar) {
								++itensImportadosSoFar;
							}
						}
					} catch(SystemException | IOException e) {
						log.error("Erro: :" + e.getMessage());
					}

					log.info("OK");
				}
			};
			executor.execute(worker);
		}

		executor.shutdown();

		int nofthreads = maxNumberTasks.intValue();
		long toutsec;
		if (xmls.length < nofthreads) {
			toutsec = xmls.length * timeOutPerFileSeconds;
		} else {
			toutsec = ((xmls.length * timeOutPerFileSeconds) + (nofthreads - 1)) / nofthreads;
		}
		if (toutsec < 120) {
			toutsec = 120;
		}

		log.debug("Aguaradando o processo terminar por até " + toutsec + " segundos");

		while (!executor.isTerminated()) {
			try {
				if (!executor.awaitTermination(toutsec, TimeUnit.SECONDS)) {
					log.debug("Processo está demorando demais, tentando finalizar!");
					executor.shutdownNow();
				}
			} catch (InterruptedException e) {
				log.debug("Interruption: " + e.getMessage());
			}
		}
		executor = null;

		log.info("Terminado: " + itensImportadosSoFar + " item(ns) importado(s)!");

		itensImportados = itensImportadosSoFar;

		return itensImportados;
	}

	/**
	 * Método que verifica se o xml é uma parte da matéria ou a matéria completa.
	 * 
	 * <p>
	 * 	Recebe como parâmetro um arquivo xml e faz as verificações no nome do arquivo
	 * 	retorna um booleando com o resultado
	 * 	esse método é chamado no momento da importação.
	 * </p>
	 * @param  xml File arquivo xml
	 * @return boolean
	 * 
	 * @author SEA Tecnologia
	 * @see br.com.seatecnologia.migracao.service.impl.ItemImportacaoLocalServiceImpl.importarXMLs
	 */
	private boolean isXMLPart(File xml) {
		String[] splittedFileName= xml.getName().split(StringPool.MINUS);
		boolean isXMLPart = false;
		if (splittedFileName.length > 1) {
			if (splittedFileName[0] != null && splittedFileName[1] != null) {
				int xmlPart = Integer.valueOf(splittedFileName[1].split("\\.")[0]);
				if (xmlPart > 1) {
					isXMLPart = true;
				}
			}
		}
		return isXMLPart;
	}

	/**
	 *Verifica se um xml esta quebrado em varios, por exemplo: 10287335-1.xml e 10287335-2.xml e assim por diante.
	 *Pega somente o texto dos elementos seguintes e retorna para adicionar ao elemento pai(1). 
	 * @return 
	 * 
	 */
	private LinkedHashMap<String, String> verifyBrokenXML(String basePathXMLs,  File xml) throws IOException {
		ArrayList<String> brokenXmls =  new ArrayList<String>();

		LinkedHashMap<String, String> brokenXmlTxt = new LinkedHashMap<String, String>();

		if(xml.getName().contains("-")){
			String[] splittedFileName = xml.getName().split("-");
			if(splittedFileName[0] != null && splittedFileName[1] != null){
				int idCount = 1;
				boolean existsXMLPath = true;
				do{
					idCount++;
					String brokenXmlPath = "";
					if(basePathXMLs.endsWith("/")){
						brokenXmlPath = basePathXMLs+splittedFileName[0]+"-"+idCount+".xml";
					}else{
						brokenXmlPath = basePathXMLs+"/"+splittedFileName[0]+"-"+idCount+".xml";
					}
					File f = new File(brokenXmlPath);
					if(f.exists() && !f.isDirectory()) { 
						brokenXmls.add(brokenXmlPath);
					}else{
						existsXMLPath = false;
					}
				}while(existsXMLPath);

			}
		}

		if(!brokenXmls.isEmpty()){
			for (String brokenXml : brokenXmls) {
				String xmlArticleBroken = readXMLFile(brokenXml);
				Document document = Jsoup.parse(xmlArticleBroken, "UTF-8", Parser.xmlParser());
				Elements txt = document.select("Texto");
				brokenXmlTxt.put(brokenXml,txt.text());
			}	
		}

		return brokenXmlTxt;
	}

	/**
	 * Método responsavel por ler um arquivo XML e retornar seu conteúdo em uma String
	 * 
	 * <p>
	 * Abre o arquivo para leitura, lê linha por linha, convertendo o código de 
	 * caractere de UTF-8 para String e adiciona a um objeto 
	 * de formação de string até o fim do arquivo. 
	 * </p>
	 * @param path caminho absoluto para o arquivo xml a ser lido
	 * @return retorna a tring com todo o conteúdo lido do arquivo
	 * @throws IOException
	 */
	private String readXMLFile(String path) throws IOException {
		StringBuilder sb = new StringBuilder();
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}

		} catch (Exception e) {
			log.error("[ERROR] Ocorreu um erro ao fazer o parse do xml: " + path + e);
		}
		byte[] b = sb.toString().getBytes(Charset.forName("UTF-8"));
		String finalText = new String(b, "UTF-8");
		return finalText;
	}

	private File[] getFilesByExtension(String dirPath,String extension, String textoFinal) {
		File dir = new File(dirPath);
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

	@Reference(unbind = "-")
	protected void setItemImportacaoService(ItemImportacaoLocalService itemImportacaoLocalService) {
		_itemImportacaoLocalService = itemImportacaoLocalService;
	}

	private ItemImportacaoLocalService _itemImportacaoLocalService;
}