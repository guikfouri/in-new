
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
"idOrigem",
"nomeOrigem",
"idSiorg",
"origemPai",
"uf",
"nomeMunicipio",
"idMunicipioIbge"
})
public class Origem {

@JsonProperty("idOrigem")
private Integer idOrigem;
@JsonProperty("nomeOrigem")
private String nomeOrigem;
@JsonProperty("idSiorg")
private Integer idSiorg;
@JsonProperty("origemPai")
private Origem origemPai;
@JsonProperty("uf")
private Object uf;
@JsonProperty("nomeMunicipio")
private Object nomeMunicipio;
@JsonProperty("idMunicipioIbge")
private Object idMunicipioIbge;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("idOrigem")
public Integer getIdOrigem() {
return idOrigem;
}

@JsonProperty("idOrigem")
public void setIdOrigem(Integer idOrigem) {
this.idOrigem = idOrigem;
}

@JsonProperty("nomeOrigem")
public String getNomeOrigem() {
return nomeOrigem;
}

@JsonProperty("nomeOrigem")
public void setNomeOrigem(String nomeOrigem) {
this.nomeOrigem = nomeOrigem;
}

@JsonProperty("idSiorg")
public Integer getIdSiorg() {
return idSiorg;
}

@JsonProperty("idSiorg")
public void setIdSiorg(Integer idSiorg) {
this.idSiorg = idSiorg;
}

@JsonProperty("origemPai")
public Origem getOrigemPai() {
return origemPai;
}

@JsonProperty("origemPai")
public void setOrigemPai(Origem origemPai) {
this.origemPai = origemPai;
}

@JsonProperty("uf")
public Object getUf() {
return uf;
}

@JsonProperty("uf")
public void setUf(Object uf) {
this.uf = uf;
}

@JsonProperty("nomeMunicipio")
public Object getNomeMunicipio() {
return nomeMunicipio;
}

@JsonProperty("nomeMunicipio")
public void setNomeMunicipio(Object nomeMunicipio) {
this.nomeMunicipio = nomeMunicipio;
}

@JsonProperty("idMunicipioIbge")
public Object getIdMunicipioIbge() {
return idMunicipioIbge;
}

@JsonProperty("idMunicipioIbge")
public void setIdMunicipioIbge(Object idMunicipioIbge) {
this.idMunicipioIbge = idMunicipioIbge;
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