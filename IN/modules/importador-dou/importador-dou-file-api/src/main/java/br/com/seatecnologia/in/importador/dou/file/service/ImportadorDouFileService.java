package br.com.seatecnologia.in.importador.dou.file.service;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.service.ServiceContext;

/**
 * @author mgsasaki
 */
public interface ImportadorDouFileService {
	/**
	 * Método que conta a quantidade de itens a serem importados.
	 * 
	 * <p>
	 * Recebe o caminho dos xmls no sistema de arquivos
	 * itera sobre os arquivos desse diretório
	 * faz uma contagem dos itens e retorna
	 * </p>
	 * @param  basePathXMLs String contendo o caminho dos xmls
	 * @return Integer
	 * 
	 * @author SEA Tecnologia
	 */
	public Integer contarItensParaImportacao(String basePathXMLs);

	/**
	 * Método responsavel por importar os arquivos xmls na estrutura de migração.
	 * 
	 * <p>
	 * Itera sobre os arquivos em formato xml
	 * extrai as informações necessárias armazenando em variáveis,
	 * monta os objetos da estrutura de migração e grava na base de dados
	 * para migração futura no liferay utilizando a API do Liferay.
	 * </p>
	 * @param  groupId id do site de destino
	 * @param  idSecao id do vocabulário de seção
	 * @param  idArranjoSecaoVocab id do vocabulário de arranjos
	 * @param  idTipoMateriaVocab id do vocabulário de tipo de matéria
	 * @param  basePathXMLs String com o caminho dos xmls no sistema de arquivos
	 * @param  themeDisplay Objeto ThemeDisplay com as informações da requisição
	 * @param  maxNumberTasks quantidade máxima de instâncias de importação paralela
	 * @return Integer
	 * 
	 * @author SEA Tecnologia
	 * @see br.com.seatecnologia.migracao.service.impl.ItemMigracaoLocalServiceImpl
	 */
	public Integer importarXMLs(Long groupId, Long idSecaoVocab, Long idArranjoSecaoVocab, Long idTipoMateriaVocab, String basePathXMLs, Long maxNumberTasks, Long timeOutPerFileSeconds, ServiceContext serviceContext) 
			throws SystemException;

}