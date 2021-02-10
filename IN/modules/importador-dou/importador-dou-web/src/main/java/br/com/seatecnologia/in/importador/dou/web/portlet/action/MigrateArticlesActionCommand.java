package br.com.seatecnologia.in.importador.dou.web.portlet.action;

import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLocalService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.xml.DocumentException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;

import br.com.seatecnologia.in.importador.dou.web.constants.ImportadorDouPortletKeys;
import br.com.seatecnologia.in.importador.dou.web.portlet.util.ConfigurationsUtil;
import br.com.seatecnologia.migracao.model.ConteudoItemMigracao;
import br.com.seatecnologia.migracao.model.ItemMigracao;
import br.com.seatecnologia.migracao.model.TradConteudoItemMigracao;
import br.com.seatecnologia.migracao.service.ConteudoItemMigracaoLocalService;
import br.com.seatecnologia.migracao.service.ItemMigracaoLocalService;
import br.com.seatecnologia.migracao.service.TradConteudoItemMigracaoLocalService;
import br.com.seatecnologia.migrador.social.publisher.service.ItemPublicacaoLocalService;

@Component(
		immediate = true,
		property = {
			"javax.portlet.name=" + ImportadorDouPortletKeys.ImportadorDou,
			"mvc.command.name=/migrate_articles"
		},
		service = MVCActionCommand.class
	)

public class MigrateArticlesActionCommand extends BaseMVCActionCommand {
	private static Log log = LogFactoryUtil.getLog(MigrateArticlesActionCommand.class);

	@Override
	protected void doProcessAction(ActionRequest actionRequest, ActionResponse actionResponse) throws Exception {
		log.debug("entering");

		PortletPreferences prefs = actionRequest.getPreferences();

		long idEstrutura = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_ESTRUTURA, "0"));
		long idTemplate = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_TEMPLATE, "0"));
		long idOwner = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_USUARIO, "0"));
		long idGrupo = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ID_GRUPO, "0"));
		
		int displayPageType = Integer.parseInt(prefs.getValue(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_TYPE, "0"));
		long assetDisplayPageId = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_ASSET_DISPLAY_PAGE_ID, "0"));
		String layoutUuid = prefs.getValue(ImportadorDouPortletKeys.P_LAYOUT_UUID, null);
		
		if ((displayPageType == AssetDisplayPageConstants.TYPE_DEFAULT) ||
				(displayPageType == AssetDisplayPageConstants.TYPE_SPECIFIC)) {

			Layout targetLayout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
					layoutUuid, idGrupo, false);

			if (targetLayout == null) {
				targetLayout = _layoutLocalService.fetchLayoutByUuidAndGroupId(
					layoutUuid, idGrupo, true);
			}

			if ((assetDisplayPageId != 0) || (targetLayout == null)) {
				layoutUuid = null;
			}
		}
		else {
			assetDisplayPageId = 0;
			layoutUuid = null;
		}

		long threadSize = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_THREAD_SIZE, "1"));
		long poolSize = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_POOL_SIZE, "1"));

		long timeout = Long.parseLong(prefs.getValue(ImportadorDouPortletKeys.P_MIGRATION_TIMEOUT, "10"));

		String basePath = prefs.getValue(ImportadorDouPortletKeys.P_BASE_PATH_XMLS, "");

		String portalURL = prefs.getValue(ImportadorDouPortletKeys.P_HIGHLIGHTS_LINK_PORTAL_URL, null);

		if (!ConfigurationsUtil.checkSocialNetworkConfiguration(prefs, actionRequest, _itemPublicacaoLocalService)) {
			log.error("Erro ao configurar publicação nas redes sociais");
			return;
		}

		ServiceContext serviceContext = ServiceContextFactory.getInstance(actionRequest);

		if (portalURL != null && portalURL.length() > 0) {
			serviceContext.setPortalURL(portalURL);
		}

		String alternateXmlPath = ParamUtil.get(actionRequest, "alternateXmlPath", "");
		if (alternateXmlPath.length() > 0) {
			basePath = alternateXmlPath;
		}
		log.debug("basePath: " + basePath);

		List<ItemMigracao> itensMigracao = _itemMigracaoLocalService.recuperarItensParaMigracao();
		itensMigracao = filterListByPath(itensMigracao, basePath);

		long totalItensMigracao = itensMigracao.size();
		long itensMigrados = 0;

		if (threadSize > 1) {
			itensMigrados = migrarNoticias(itensMigracao, serviceContext, idEstrutura, idTemplate, idOwner, idGrupo, 
					displayPageType, assetDisplayPageId, layoutUuid, basePath, threadSize, poolSize, timeout);
		} else {
			itensMigrados = migrarNoticias(itensMigracao, serviceContext, idEstrutura, idTemplate, idOwner, idGrupo, 
					displayPageType, assetDisplayPageId, layoutUuid, basePath);
		}
		
		if (log.isDebugEnabled()) {
			log.debug("Itens migrados: " + itensMigrados + "/" + totalItensMigracao);
		}
		
		
		if(itensMigrados > 0) {
			boolean enableNotification = Boolean.parseBoolean(prefs.getValue(ImportadorDouPortletKeys.ENABLE_NOTIFICATION, "false"));
			log.info("HABILITAR NOTIFICACOES está setado como: " + enableNotification);
			if(enableNotification) {
				String url = getUrlNotificationServer(prefs);
				Map<String, List<ItemMigracao>> collect = itensMigracao.stream().collect(Collectors.groupingBy(ItemMigracao::getTipoItem));
				for (Map.Entry<String, List<ItemMigracao>> currentEntry : collect.entrySet()) {
					String notSecao = getSection(currentEntry);
					Date pubDate = getPubDate(currentEntry);
					String notificationPubDate = formatPubDate(pubDate, "yyyyMMdd");
					String edition = getEdition(currentEntry);
					String jsonString = getJsonString(notSecao, edition, notificationPubDate);
					
					if(sendNotification(url, jsonString)) {
						log.info("Notificacao enviada com sucesso...");
					}else {
						log.info("Notificacao Falhou...");
					}
				}
			}
		}

		if (itensMigrados != totalItensMigracao) {
			SessionErrors.add(actionRequest, "error-migration-partial-failure");
		}
	}
	
	
	private boolean sendNotification(String serverUrl, String jsonInputString ) throws IOException {
		try {
			   URL url = new URL (serverUrl);
		        HttpURLConnection con = (HttpURLConnection)url.openConnection();
		        con.setRequestMethod("POST");
		        con.setRequestProperty("Authorization", "B17C7918F4D051924C54E519170AA309");
		        con.setRequestProperty("Content-Type", "application/json; utf-8");
		        con.setRequestProperty("Accept", "application/json");
		        con.setDoOutput(true);	
		        try(OutputStream os = con.getOutputStream()) {
		            byte[] input = jsonInputString.getBytes("utf-8");
		            os.write(input, 0, input.length);           
		        }
		        try(BufferedReader br = new BufferedReader(
		            new InputStreamReader(con.getInputStream(), "utf-8"))) {
		            StringBuilder response = new StringBuilder();
		            String responseLine = null;
		            while ((responseLine = br.readLine()) != null) {
		                response.append(responseLine.trim());
		            }
//		            System.out.println(response.toString());
		        }
			return true;
		}catch (Exception e) {
			log.info("EXEPTION ENVIO " + e.getMessage());
			log.debug(e.getMessage());
			return false;			
		}
	}
	
	
	private String getJsonString(String secao, String edicao, String pubDate) {
        String jsonInputString = "{\"secao\": \""+secao+"\", \"edicao\":\""+edicao+"\", \"dataPublicacao\": \""+pubDate+"\"}";
		return jsonInputString;
	}
	
	
	private String formatPubDate(Date pubDate, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String formattedPubDate = format.format(pubDate);
		return formattedPubDate;
	}
	
	private Date getPubDate(Map.Entry<String, List<ItemMigracao>> currentEntry) {
		return  currentEntry.getValue().get(0).getDataReferencia();
	}
	
	private String getEdition(Entry<String, List<ItemMigracao>> currentEntry) {
		long itemMigracaoId = currentEntry.getValue().get(0).getItemMigracaoId();
		List<ConteudoItemMigracao> conteudosItemMigracao = _conteudoItemMigracaoLocalService.findByItemMigracaoId(itemMigracaoId);
		for (ConteudoItemMigracao conteudoItemMigracao : conteudosItemMigracao) {
			if(conteudoItemMigracao.getCampo().equalsIgnoreCase("editionNumber")) {
				long conteudoItemMigracaoId = conteudoItemMigracao.getConteudoItemMigracaoId();
				List<TradConteudoItemMigracao> tradConteudoItemMigracaoPorConteudoItemMigracaoItem = _tradConteudoItemMigracaoLocalService.recuperarTradConteudoItemMigracaoPorConteudoItemMigracaoId(conteudoItemMigracaoId);
				return tradConteudoItemMigracaoPorConteudoItemMigracaoItem.get(0).getConteudo();
			}
		}
		return  "0";
	}
	
	private String getSection(Entry<String, List<ItemMigracao>> currentEntry) {
		return  currentEntry.getKey().toLowerCase();
	}
	
	private String getUrlNotificationServer(PortletPreferences prefs) {
		return prefs.getValue(ImportadorDouPortletKeys.URL_SERVER, "");
	}
	

	private int migrarNoticias(List<ItemMigracao> itensMigracao, ServiceContext serviceContext, 
			long structureId, long templateId, long ownerId, long groupId, 
			int displayPageType, long layoutPageTemplateEntryId, String layoutUuid, String basePath)
					throws PortalException {
		int count = 0;
		long companyId = serviceContext.getCompanyId();
		long userId = serviceContext.getUserId();
		long scopeGroupId = serviceContext.getScopeGroupId();
		String languageId = serviceContext.getLanguageId();
		DDMStructure structure = _ddmStructureLocalService.fetchDDMStructure(structureId);
		DDMTemplate template = _ddmTemplateLocalService.fetchTemplate(templateId);

		for (ItemMigracao itemMigracao : itensMigracao) {
			try {
				JournalArticle journalArticle = _itemMigracaoLocalService.migrarNoticia(itemMigracao,
						companyId,
						userId,
						scopeGroupId,
						languageId,
						structure,
						template,
						ownerId,
						groupId,
						displayPageType,
						layoutPageTemplateEntryId,
						layoutUuid,
						basePath);
				if (journalArticle != null) {
					log.info("Migrado: " + journalArticle.getArticleId());
					++count;

					_itemPublicacaoLocalService.publishArticleHighlightsOnSocialNetworks(itemMigracao, journalArticle,
							serviceContext);
				} else {
					log.error("Item nao migrado: " + itemMigracao.getIdentificador_atualizacao());
				}
			} catch (SystemException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return count;
	}

	private List<ItemMigracao> filterListByPath(List<ItemMigracao> list, String path) {
		List<ItemMigracao> result = new LinkedList<>();

		String finalPath = path.endsWith(File.separator) ?
			path.substring(0, path.length() - File.separator.length()) :
				path;
		list.forEach(itemMigracao->{
			File file = new File(itemMigracao.getIdentificador_atualizacao());
			String pathName = file.getParent();
			if (pathName == null) {
				pathName = "";
			}
			if (pathName.equals(finalPath)) {
				result.add(itemMigracao);
			}
		});
		return result;
	}

	private ExecutorServiceThreadPool executorServiceThreadPool;

	private int migrarNoticias(List<ItemMigracao> itensMigracao, ServiceContext serviceContext,
			long structureId, long templateId, long ownerId, long groupId, 
			int displayPageType, long layoutPageTemplateEntryId, String layoutUuid, String basePath, long threadSize, long poolSize, long timeoutPerItemSeconds) 
					throws PortalException, SystemException, DocumentException, IOException {
		DDMStructure estrutura = _ddmStructureLocalService.getStructure(structureId);
		DDMTemplate template = _ddmTemplateLocalService.getTemplate(templateId);

		if (executorServiceThreadPool != null) {
			log.error("Processo de migração em andamento. Não pode iniciar outro enquanto não finalizar!");
			throw new PortalException("Unfinished process is running");
		}
		executorServiceThreadPool = new ExecutorServiceThreadPool(poolSize);
		executorServiceThreadPool.setTotalItensMigracao(itensMigracao.size());

		// Creating Producer pool
		executorServiceThreadPool.addThread(new MigradorProducer(itensMigracao, executorServiceThreadPool));

		// Creating Consumer threads 
		for (int count=1;count < threadSize;count++) {
			executorServiceThreadPool.addThread(new MigradorConsumer(executorServiceThreadPool, serviceContext, estrutura, template, ownerId, groupId, displayPageType, layoutPageTemplateEntryId, layoutUuid, basePath));	
		}

		executorServiceThreadPool.finish(timeoutPerItemSeconds);
		executorServiceThreadPool.info();
		
		int counter = (int) executorServiceThreadPool.getCounter();
		
		executorServiceThreadPool = null;

		return counter;
	}
	
	private class ExecutorServiceThreadPool {
		private Log log = LogFactoryUtil.getLog(ExecutorServiceThreadPool.class);

		public final BlockingQueue <ItemMigracao> queue = new LinkedBlockingQueue<ItemMigracao>();
//		public final BlockingQueue <File> xmlQueue = new LinkedBlockingQueue<File>();

		private ThreadPoolExecutor executor;
		private int itensMigrados;
		private int totalItensMigracao;

//		public ExecutorServiceThreadPool() {
//			this(500);
//		}

		public ExecutorServiceThreadPool(long poolSize) {
			executor = (ThreadPoolExecutor) Executors.newFixedThreadPool((int)poolSize);  
		}

		public void addThread(Runnable r) {
			executor.execute(r);
		}

		public void finish(long timeoutPerItemSeconds) {
			executor.shutdown();
			int nofthreads = executor.getActiveCount();
			long toutsec = totalItensMigracao * timeoutPerItemSeconds;
			if ((nofthreads > 0) && (totalItensMigracao > nofthreads)) {
				toutsec = (toutsec + nofthreads - 1) / nofthreads;
			}
			if (toutsec < 120) {
				toutsec = 120;
			}

			log.debug("Aguardando termino por até " + toutsec + " segundos.");

			while (!executor.isTerminated()) {
				try {
					if (!executor.awaitTermination(toutsec, TimeUnit.SECONDS)) {
						executor.shutdownNow();
					}
				} catch (InterruptedException ex) {
					Logger.getLogger(ExecutorServiceThreadPool.class.getName()).log(Level.SEVERE, null, ex);
					executor.shutdownNow();
				}
			}
		}

		/*
		 * Getters and Setters
		 */
		public int getTotalItensMigracao() {
			return this.totalItensMigracao;
		}

		public void setTotalItensMigracao(int totalItensMigracao) {
			this.totalItensMigracao = totalItensMigracao;
		}

		public void addCounter() {
			this.itensMigrados++;

			if(itensMigrados % 1000 == 0)
				log.info("[ Ainda migrando... ]");
		}

		public long getCounter(){
			return this.itensMigrados;
		}

		/*
		 * Show information about the thread work
		 */
		public void info() {
				log.debug("Pool Size: " + executor.getPoolSize());
				log.debug("Active Threads: " + executor.getActiveCount());
				log.debug("Completed Threads: " + executor.getCompletedTaskCount());
				log.info("Itens Migrados: " + this.getCounter() + " / " + this.getTotalItensMigracao());

				MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
				long heapPcent = (mem.getHeapMemoryUsage().getUsed() * 100) / mem.getHeapMemoryUsage().getMax();
				log.debug("Memoria utilizada (heap): " + Long.toString(heapPcent) + "%");
				while (heapPcent > 75L) {
					log.warn("Memoria livre esta baixa, tentando limpar: " + Long.toString(heapPcent) + "% utilizada");
					mem.gc();
					heapPcent = (mem.getHeapMemoryUsage().getUsed() * 100) / mem.getHeapMemoryUsage().getMax();
				}
				if (this.getCounter() == this.getTotalItensMigracao())
					log.info("[ Bom trabalho!!! :) Serviço de migração concluído! ]");
		}
	}
	
	private class MigradorProducer implements Runnable {
		List<ItemMigracao> itensMigracao;
		private ExecutorServiceThreadPool executorServiceThreadPool;

		public MigradorProducer(
				List<ItemMigracao> itensMigracao,
				ExecutorServiceThreadPool executorServiceThreadPool) 
		{
			this.executorServiceThreadPool = executorServiceThreadPool;
			this.itensMigracao = itensMigracao;
		}

		@Override
		public void run() {

			for(ItemMigracao itemMigracao : itensMigracao) 
			{
				try {
					executorServiceThreadPool.queue.add(itemMigracao);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
	}

	private class MigradorConsumer implements Runnable {
		private Log log = LogFactoryUtil.getLog(MigradorConsumer.class);

		private ItemMigracao itemMigracao;
		private ExecutorServiceThreadPool executorServiceThreadPool;
		private ServiceContext serviceContext;
		private DDMStructure estrutura; 
		private DDMTemplate template;
		private long ownerId;
		private long groupId; 
		private int assetDisplayPageType;
		private long assetDisplayPageId;
		private String layoutUuid;
		private String basePath;

		private ServiceTracker<UserLocalService, UserLocalService> _userLocalServiceTracker;
		private ServiceTracker<ItemMigracaoLocalService, ItemMigracaoLocalService> _itemMigracaoLocalServiceTracker;
		private ServiceTracker<ItemPublicacaoLocalService, ItemPublicacaoLocalService> _itemPublicacaoLocalServiceTracker;
		private ServiceTracker<ConteudoItemMigracaoLocalService, ConteudoItemMigracaoLocalService> _conteudoItemMigracaoLocalServiceTracker;
		private ServiceTracker<TradConteudoItemMigracaoLocalService, TradConteudoItemMigracaoLocalService> _tradConteudoItemMigracaoLocalServiceTracker;



		private ItemMigracaoLocalService _itemMigracaoLocalService;
		private ConteudoItemMigracaoLocalService _conteudoItemMigracaoLocalService;
		private TradConteudoItemMigracaoLocalService _tradConteudoItemMigracaoLocalService;
		private ItemPublicacaoLocalService _itemPublicacaoLocalService;
		private UserLocalService _userLocalService;

		public MigradorConsumer(
				ExecutorServiceThreadPool executorServiceThreadPool,
				ServiceContext serviceContext,
				DDMStructure estrutura,
				DDMTemplate template,
				long ownerId,
				long groupId,
				int assetDisplayPageType,
				long assetDisplayPageId,
				String layoutUuid,
				String basePath)
		{
			this.executorServiceThreadPool = executorServiceThreadPool;
			this.serviceContext = serviceContext;
			this.estrutura = estrutura;
			this.template = template;
			this.ownerId = ownerId;
			this.groupId = groupId;
			this.assetDisplayPageType = assetDisplayPageType;
			this.assetDisplayPageId = assetDisplayPageId;
			this.layoutUuid = layoutUuid;
			this.basePath = basePath;
		}

		@Override
		public void run() {
			loadLocalServices();

			try {
				PrincipalThreadLocal.setName(serviceContext.getUserId());
				PermissionChecker permissionChecker;
				permissionChecker = PermissionCheckerFactoryUtil.create(_userLocalService.getUser(serviceContext.getUserId()));
				PermissionThreadLocal.setPermissionChecker(permissionChecker);
			} catch (Exception e1) {
				e1.printStackTrace();
				unloadLocalServices();
			}

			while ((itemMigracao = executorServiceThreadPool.queue.poll()) != null) {
				try {
					JournalArticle ja = _itemMigracaoLocalService.migrarNoticia(itemMigracao, serviceContext.getCompanyId(), serviceContext.getUserId(),
							serviceContext.getScopeGroupId(), serviceContext.getLanguageId(), estrutura, template, ownerId, groupId, assetDisplayPageType, assetDisplayPageId, layoutUuid, basePath);
					if (ja != null) {
						_itemPublicacaoLocalService.publishArticleHighlightsOnSocialNetworks(itemMigracao, ja,
								serviceContext);
					}
					executorServiceThreadPool.addCounter();
				} catch (Exception e) {
					log.error("Item not migrated: " + e.getMessage());
					e.printStackTrace();
					continue;
				}

				executorServiceThreadPool.info();
			}

			unloadLocalServices();
		}

		private void loadLocalServices() {
			Bundle bundle = FrameworkUtil.getBundle(this.getClass());
			BundleContext bundleContext = bundle.getBundleContext();

			try {
				_userLocalServiceTracker = new ServiceTracker<>(bundleContext, UserLocalService.class, null);
				_userLocalServiceTracker.open();
				_userLocalService = _userLocalServiceTracker.waitForService(500);

				_itemMigracaoLocalServiceTracker = new ServiceTracker<>(bundleContext, ItemMigracaoLocalService.class, null);
				_itemMigracaoLocalServiceTracker.open();
				_itemMigracaoLocalService = _itemMigracaoLocalServiceTracker.waitForService(500);
				
				_conteudoItemMigracaoLocalServiceTracker = new ServiceTracker<>(bundleContext, ConteudoItemMigracaoLocalService.class, null);
				_conteudoItemMigracaoLocalServiceTracker.open();
				_conteudoItemMigracaoLocalService = _conteudoItemMigracaoLocalServiceTracker.waitForService(500);

				_tradConteudoItemMigracaoLocalServiceTracker = new ServiceTracker<>(bundleContext, TradConteudoItemMigracaoLocalService.class, null);
				_tradConteudoItemMigracaoLocalServiceTracker.open();
				_tradConteudoItemMigracaoLocalService = _tradConteudoItemMigracaoLocalServiceTracker.waitForService(500);
				
				_itemPublicacaoLocalServiceTracker = new ServiceTracker<>(bundleContext, ItemPublicacaoLocalService.class, null);
				_itemPublicacaoLocalServiceTracker.open();
				_itemPublicacaoLocalService = _itemPublicacaoLocalServiceTracker.waitForService(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private void unloadLocalServices() {
			_userLocalService = null;
			_userLocalServiceTracker.close();
			_userLocalServiceTracker = null;

			_itemMigracaoLocalService = null;
			_itemMigracaoLocalServiceTracker.close();
			_itemMigracaoLocalServiceTracker = null;
			
			_conteudoItemMigracaoLocalService = null;
			_conteudoItemMigracaoLocalServiceTracker.close();
			_conteudoItemMigracaoLocalServiceTracker = null;
			
			_tradConteudoItemMigracaoLocalService = null;
			_tradConteudoItemMigracaoLocalServiceTracker.close();
			_tradConteudoItemMigracaoLocalServiceTracker = null;

			_itemPublicacaoLocalService = null;
			_itemPublicacaoLocalServiceTracker.close();
			_itemPublicacaoLocalServiceTracker = null;
		}
	}

	@Reference(unbind = "-")
	protected void setItemMigracaoService(ItemMigracaoLocalService itemMigracaoLocalService) {
		_itemMigracaoLocalService = itemMigracaoLocalService;
	}

	@Reference(unbind = "-")
	protected void setItemPublicacaoLocalService(ItemPublicacaoLocalService itemPublicacaoLocalService) {
		_itemPublicacaoLocalService = itemPublicacaoLocalService;
	}
	
	@Reference(unbind = "-")
	protected void setConteudoItemMigracaoService(ConteudoItemMigracaoLocalService conteudoItemMigracaoLocalService) {
		_conteudoItemMigracaoLocalService = conteudoItemMigracaoLocalService;
	}
	
	@Reference(unbind = "-")
	protected void setTradConteudoItemMigracaoService(TradConteudoItemMigracaoLocalService tradConteudoItemMigracaoLocalService) {
		_tradConteudoItemMigracaoLocalService = tradConteudoItemMigracaoLocalService;
	}

	private ItemMigracaoLocalService _itemMigracaoLocalService;
	private ItemPublicacaoLocalService _itemPublicacaoLocalService;
	private ConteudoItemMigracaoLocalService _conteudoItemMigracaoLocalService;
	private TradConteudoItemMigracaoLocalService _tradConteudoItemMigracaoLocalService;


	@Reference
	private LayoutLocalService _layoutLocalService;
	@Reference
	private LayoutPageTemplateEntryLocalService _layoutPageTemplateEntryLocalService;
	@Reference
	private AssetDisplayPageEntryLocalService _assetDisplayPageEntryLocalService;
	@Reference
	private Portal _portal;
	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;
	@Reference
	private DDMTemplateLocalService _ddmTemplateLocalService;
}
