
package br.com.seatecnologia.in.importador.dou.json.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"identificacao",
"titulo",
"subtitulo",
"dataTexto",
"ementa",
"resumo",
"assinaturas"
})
public class Estrutura {

@JsonProperty("identificacao")
private String identificacao;
@JsonProperty("titulo")
private String titulo;
@JsonProperty("subtitulo")
private String subtitulo;
@JsonProperty("dataTexto")
private String dataTexto;
@JsonProperty("ementa")
private String ementa;
@JsonProperty("resumo")
private String resumo;
@JsonProperty("assinaturas")
private List<Assinatura> assinaturas = null;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("identificacao")
public String getIdentificacao() {
return identificacao;
}

@JsonProperty("identificacao")
public void setIdentificacao(String identificacao) {
this.identificacao = identificacao;
}

@JsonProperty("titulo")
public String getTitulo() {
return titulo;
}

@JsonProperty("titulo")
public void setTitulo(String titulo) {
this.titulo = titulo;
}

@JsonProperty("subtitulo")
public String getSubtitulo() {
return subtitulo;
}

@JsonProperty("subtitulo")
public void setSubtitulo(String subtitulo) {
this.subtitulo = subtitulo;
}

@JsonProperty("dataTexto")
public String getDataTexto() {
return dataTexto;
}

@JsonProperty("dataTexto")
public void setDataTexto(String dataTexto) {
this.dataTexto = dataTexto;
}

@JsonProperty("ementa")
public String getEmenta() {
return ementa;
}

@JsonProperty("ementa")
public void setEmenta(String ementa) {
this.ementa = ementa;
}

@JsonProperty("resumo")
public String getResumo() {
return resumo;
}

@JsonProperty("resumo")
public void setResumo(String resumo) {
this.resumo = resumo;
}

@JsonProperty("assinaturas")
public List<Assinatura> getAssinaturas() {
return assinaturas;
}

@JsonProperty("assinaturas")
public void setAssinaturas(List<Assinatura> assinaturas) {
this.assinaturas = assinaturas;
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