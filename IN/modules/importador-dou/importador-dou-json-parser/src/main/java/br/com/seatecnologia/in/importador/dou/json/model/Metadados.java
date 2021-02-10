
package br.com.seatecnologia.in.importador.dou.json.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"idMateria",
"idOficio",
"idXML",
"ordem",
"imprensaOficial",
"idImprensaOficial",
"materiaPai",
"tipoAto",
"idTipoAto",
"origem",
"destaque",
"materiasRelacionadas",
"publicacao"
})
public class Metadados {

@JsonProperty("idMateria")
private Integer idMateria;
@JsonProperty("idOficio")
private Integer idOficio;
@JsonProperty("idXML")
private Object idXML;
@JsonProperty("ordem")
private String ordem;
@JsonProperty("imprensaOficial")
private String imprensaOficial;
@JsonProperty("idImprensaOficial")
private Integer idImprensaOficial;
@JsonProperty("materiaPai")
private Object materiaPai;
@JsonProperty("tipoAto")
private String tipoAto;
@JsonProperty("idTipoAto")
private Integer idTipoAto;
@JsonProperty("origem")
private Origem origem;
@JsonProperty("destaque")
private Destaque destaque;
@JsonProperty("materiasRelacionadas")
private Object materiasRelacionadas;
@JsonProperty("publicacao")
private Publicacao publicacao;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("idMateria")
public Integer getIdMateria() {
return idMateria;
}

@JsonProperty("idMateria")
public void setIdMateria(Integer idMateria) {
this.idMateria = idMateria;
}

@JsonProperty("idOficio")
public Integer getIdOficio() {
return idOficio;
}

@JsonProperty("idOficio")
public void setIdOficio(Integer idOficio) {
this.idOficio = idOficio;
}

@JsonProperty("idXML")
public Object getIdXML() {
return idXML;
}

@JsonProperty("idXML")
public void setIdXML(Object idXML) {
this.idXML = idXML;
}

@JsonProperty("ordem")
public String getOrdem() {
return ordem;
}

@JsonProperty("ordem")
public void setOrdem(String ordem) {
this.ordem = ordem;
}

@JsonProperty("imprensaOficial")
public String getImprensaOficial() {
return imprensaOficial;
}

@JsonProperty("imprensaOficial")
public void setImprensaOficial(String imprensaOficial) {
this.imprensaOficial = imprensaOficial;
}

@JsonProperty("idImprensaOficial")
public Integer getIdImprensaOficial() {
return idImprensaOficial;
}

@JsonProperty("idImprensaOficial")
public void setIdImprensaOficial(Integer idImprensaOficial) {
this.idImprensaOficial = idImprensaOficial;
}

@JsonProperty("materiaPai")
public Object getMateriaPai() {
return materiaPai;
}

@JsonProperty("materiaPai")
public void setMateriaPai(Object materiaPai) {
this.materiaPai = materiaPai;
}

@JsonProperty("tipoAto")
public String getTipoAto() {
return tipoAto;
}

@JsonProperty("tipoAto")
public void setTipoAto(String tipoAto) {
this.tipoAto = tipoAto;
}

@JsonProperty("idTipoAto")
public Integer getIdTipoAto() {
return idTipoAto;
}

@JsonProperty("idTipoAto")
public void setIdTipoAto(Integer idTipoAto) {
this.idTipoAto = idTipoAto;
}

@JsonProperty("origem")
public Origem getOrigem() {
return origem;
}

@JsonProperty("origem")
public void setOrigem(Origem origem) {
this.origem = origem;
}

@JsonProperty("destaque")
public Destaque getDestaque() {
return destaque;
}

@JsonProperty("destaque")
public void setDestaque(Destaque destaque) {
this.destaque = destaque;
}

@JsonProperty("materiasRelacionadas")
public Object getMateriasRelacionadas() {
return materiasRelacionadas;
}

@JsonProperty("materiasRelacionadas")
public void setMateriasRelacionadas(Object materiasRelacionadas) {
this.materiasRelacionadas = materiasRelacionadas;
}

@JsonProperty("publicacao")
public Publicacao getPublicacao() {
return publicacao;
}

@JsonProperty("publicacao")
public void setPublicacao(Publicacao publicacao) {
this.publicacao = publicacao;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}