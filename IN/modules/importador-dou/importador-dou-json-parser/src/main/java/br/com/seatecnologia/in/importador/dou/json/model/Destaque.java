
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
"tipo",
"prioridade",
"texto",
"nomeImagem",
"fonteImagem"
})
public class Destaque {

@JsonProperty("tipo")
private String tipo;
@JsonProperty("prioridade")
private Integer prioridade;
@JsonProperty("texto")
private String texto;
@JsonProperty("nomeImagem")
private String nomeImagem;
@JsonProperty("fonteImagem")
private String fonteImagem;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("tipo")
public String getTipo() {
return tipo;
}

@JsonProperty("tipo")
public void setTipo(String tipo) {
this.tipo = tipo;
}

@JsonProperty("prioridade")
public Integer getPrioridade() {
return prioridade;
}

@JsonProperty("prioridade")
public void setPrioridade(Integer prioridade) {
this.prioridade = prioridade;
}

@JsonProperty("texto")
public String getTexto() {
return texto;
}

@JsonProperty("texto")
public void setTexto(String texto) {
this.texto = texto;
}

@JsonProperty("nomeImagem")
public String getNomeImagem() {
return nomeImagem;
}

@JsonProperty("nomeImagem")
public void setNomeImagem(String nomeImagem) {
this.nomeImagem = nomeImagem;
}

@JsonProperty("fonteImagem")
public String getFonteImagem() {
return fonteImagem;
}

@JsonProperty("fonteImagem")
public void setFonteImagem(String fonteImagem) {
this.fonteImagem = fonteImagem;
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