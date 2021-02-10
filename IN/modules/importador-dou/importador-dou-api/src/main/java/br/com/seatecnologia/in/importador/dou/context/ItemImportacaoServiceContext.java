package br.com.seatecnologia.in.importador.dou.context;

public class ItemImportacaoServiceContext {
	private Long groupId;
	private Long idSecaoVocab;
	private Long idArranjoSecaoVocab;
	private Long idTipoMateriaVocab;
	private String resourcesPath;

	public ItemImportacaoServiceContext(Long groupId, Long idSecaoVocab, Long idArranjoSecaoVocab,
			Long idTipoMateriaVocab, String resourcesPath) {
		super();
		this.groupId = groupId;
		this.idSecaoVocab = idSecaoVocab;
		this.idArranjoSecaoVocab = idArranjoSecaoVocab;
		this.idTipoMateriaVocab = idTipoMateriaVocab;
		this.resourcesPath = resourcesPath;
	}

	public Long getGroupId() {
		return groupId;
	}
	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}
	public Long getIdSecaoVocab() {
		return idSecaoVocab;
	}
	public void setIdSecaoVocab(Long idSecaoVocab) {
		this.idSecaoVocab = idSecaoVocab;
	}
	public Long getIdArranjoSecaoVocab() {
		return idArranjoSecaoVocab;
	}
	public void setIdArranjoSecaoVocab(Long idArranjoSecaoVocab) {
		this.idArranjoSecaoVocab = idArranjoSecaoVocab;
	}
	public Long getIdTipoMateriaVocab() {
		return idTipoMateriaVocab;
	}
	public void setIdTipoMateriaVocab(Long idTipoMateriaVocab) {
		this.idTipoMateriaVocab = idTipoMateriaVocab;
	}
	public String getResourcesPath() {
		return resourcesPath;
	}
	public void setResourcesPath(String resourcesPath) {
		this.resourcesPath = resourcesPath;
	}
}
