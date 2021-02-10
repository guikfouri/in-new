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
"versao",
"textoHTML",
"textoEstruturado",
"estrutura",
"metadados"
})
public class Materia {

@JsonProperty("versao")
private String versao;
@JsonProperty("textoHTML")
private String textoHTML;
@JsonProperty("textoEstruturado")
private TextoEstruturado textoEstruturado;
@JsonProperty("estrutura")
private Estrutura estrutura;
@JsonProperty("metadados")
private Metadados metadados;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("versao")
public String getVersao() {
return versao;
}

@JsonProperty("versao")
public void setVersao(String versao) {
this.versao = versao;
}

@JsonProperty("textoHTML")
public String getTextoHTML() {
return textoHTML;
}

@JsonProperty("textoHTML")
public void setTextoHTML(String textoHTML) {
this.textoHTML = textoHTML;
}

@JsonProperty("textoEstruturado")
public TextoEstruturado getTextoEstruturado() {
return textoEstruturado;
}

@JsonProperty("textoEstruturado")
public void setTextoEstruturado(TextoEstruturado textoEstruturado) {
this.textoEstruturado = textoEstruturado;
}

@JsonProperty("estrutura")
public Estrutura getEstrutura() {
return estrutura;
}

@JsonProperty("estrutura")
public void setEstrutura(Estrutura estrutura) {
this.estrutura = estrutura;
}

@JsonProperty("metadados")
public Metadados getMetadados() {
return metadados;
}

@JsonProperty("metadados")
public void setMetadados(Metadados metadados) {
this.metadados = metadados;
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