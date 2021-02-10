
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
"retificacaoTecnica",
"jornal",
"dataPublicacao",
"numeroPaginaPdf",
"urlVersaoOficialPdf",
"urlVersaoOficialHtml"
})
public class Publicacao {

@JsonProperty("retificacaoTecnica")
private Boolean retificacaoTecnica;
@JsonProperty("jornal")
private Jornal jornal;
@JsonProperty("dataPublicacao")
private String dataPublicacao;
@JsonProperty("numeroPaginaPdf")
private Integer numeroPaginaPdf;
@JsonProperty("urlVersaoOficialPdf")
private String urlVersaoOficialPdf;
@JsonProperty("urlVersaoOficialHtml")
private String urlVersaoOficialHtml;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("retificacaoTecnica")
public Boolean getRetificacaoTecnica() {
return retificacaoTecnica;
}

@JsonProperty("retificacaoTecnica")
public void setRetificacaoTecnica(Boolean retificacaoTecnica) {
this.retificacaoTecnica = retificacaoTecnica;
}

@JsonProperty("jornal")
public Jornal getJornal() {
return jornal;
}

@JsonProperty("jornal")
public void setJornal(Jornal jornal) {
this.jornal = jornal;
}

@JsonProperty("dataPublicacao")
public String getDataPublicacao() {
return dataPublicacao;
}

@JsonProperty("dataPublicacao")
public void setDataPublicacao(String dataPublicacao) {
this.dataPublicacao = dataPublicacao;
}

@JsonProperty("numeroPaginaPdf")
public Integer getNumeroPaginaPdf() {
return numeroPaginaPdf;
}

@JsonProperty("numeroPaginaPdf")
public void setNumeroPaginaPdf(Integer numeroPaginaPdf) {
this.numeroPaginaPdf = numeroPaginaPdf;
}

@JsonProperty("urlVersaoOficialPdf")
public String getUrlVersaoOficialPdf() {
return urlVersaoOficialPdf;
}

@JsonProperty("urlVersaoOficialPdf")
public void setUrlVersaoOficialPdf(String urlVersaoOficialPdf) {
this.urlVersaoOficialPdf = urlVersaoOficialPdf;
}

@JsonProperty("urlVersaoOficialHtml")
public String getUrlVersaoOficialHtml() {
return urlVersaoOficialHtml;
}

@JsonProperty("urlVersaoOficialHtml")
public void setUrlVersaoOficialHtml(String urlVersaoOficialHtml) {
this.urlVersaoOficialHtml = urlVersaoOficialHtml;
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