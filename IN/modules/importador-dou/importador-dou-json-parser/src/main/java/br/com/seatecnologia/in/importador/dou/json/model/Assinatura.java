
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
"assinante",
"cargo"
})
public class Assinatura {

@JsonProperty("assinante")
private String assinante;
@JsonProperty("cargo")
private String cargo;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("assinante")
public String getAssinante() {
return assinante;
}

@JsonProperty("assinante")
public void setAssinante(String assinante) {
this.assinante = assinante;
}

@JsonProperty("cargo")
public String getCargo() {
return cargo;
}

@JsonProperty("cargo")
public void setCargo(String cargo) {
this.cargo = cargo;
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