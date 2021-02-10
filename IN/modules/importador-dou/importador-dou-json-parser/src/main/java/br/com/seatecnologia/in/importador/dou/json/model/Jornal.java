
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
"ano",
"idJornal",
"jornal",
"numeroEdicao",
"idSecao",
"secao",
"isExtra",
"isSuplemento"
})
public class Jornal {

@JsonProperty("ano")
private String ano;
@JsonProperty("idJornal")
private Integer idJornal;
@JsonProperty("jornal")
private String jornal;
@JsonProperty("numeroEdicao")
private String numeroEdicao;
@JsonProperty("idSecao")
private Integer idSecao;
@JsonProperty("secao")
private String secao;
@JsonProperty("isExtra")
private Boolean isExtra;
@JsonProperty("isSuplemento")
private Boolean isSuplemento;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("ano")
public String getAno() {
return ano;
}

@JsonProperty("ano")
public void setAno(String ano) {
this.ano = ano;
}

@JsonProperty("idJornal")
public Integer getIdJornal() {
return idJornal;
}

@JsonProperty("idJornal")
public void setIdJornal(Integer idJornal) {
this.idJornal = idJornal;
}

@JsonProperty("jornal")
public String getJornal() {
return jornal;
}

@JsonProperty("jornal")
public void setJornal(String jornal) {
this.jornal = jornal;
}

@JsonProperty("numeroEdicao")
public String getNumeroEdicao() {
return numeroEdicao;
}

@JsonProperty("numeroEdicao")
public void setNumeroEdicao(String numeroEdicao) {
this.numeroEdicao = numeroEdicao;
}

@JsonProperty("idSecao")
public Integer getIdSecao() {
return idSecao;
}

@JsonProperty("idSecao")
public void setIdSecao(Integer idSecao) {
this.idSecao = idSecao;
}

@JsonProperty("secao")
public String getSecao() {
return secao;
}

@JsonProperty("secao")
public void setSecao(String secao) {
this.secao = secao;
}

@JsonProperty("isExtra")
public Boolean getIsExtra() {
return isExtra;
}

@JsonProperty("isExtra")
public void setIsExtra(Boolean isExtra) {
this.isExtra = isExtra;
}

@JsonProperty("isSuplemento")
public Boolean getIsSuplemento() {
return isSuplemento;
}

@JsonProperty("isSuplemento")
public void setIsSuplemento(Boolean isSuplemento) {
this.isSuplemento = isSuplemento;
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