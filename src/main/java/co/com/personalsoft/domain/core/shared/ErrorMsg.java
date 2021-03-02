package co.com.personalsoft.domain.core.shared;

import java.util.HashMap;
import java.util.Map;

public final class ErrorMsg {

  public static final Map<String, String> msg = new HashMap<>();
  
  public static final String COORDINATE_NOT_AVAILABLE = "coordinateNotAvailable";
  
  public static final String MATERIAL_1_NOT_AVAILABLE = "material1NotAvailable";
  
  public static final String MATERIAL_2_NOT_AVAILABLE = "material2NotAvailable";
  
  public static final String MATERIAL_3_NOT_AVAILABLE = "material3NotAvailable";

  public static final String MATERIAL_4_NOT_AVAILABLE = "material4NotAvailable";
  
  public static final String MATERIAL_5_NOT_AVAILABLE = "material5NotAvailable";
  
  public static final String HAS_NOT_BUILD_TYPE = "hastNotBuildType";
  
  public static final String HAS_NOT_CITADEL = "hastNotCitadel";
  
  public static final String NOT_VALID_BUILD_TYPE = "notValidaBuildType";
  
  public static final String NOT_VALID_CITADEL = "notValidaCitadel";
  
  static {
    msg.put(COORDINATE_NOT_AVAILABLE, "En las coordenadas indicadas ya existe una orden de construcción, por favor cambielas e intente nuevamente");
    msg.put(MATERIAL_1_NOT_AVAILABLE, "No hay suficiente Cemento");
    msg.put(MATERIAL_2_NOT_AVAILABLE, "No hay suficiente Grava");
    msg.put(MATERIAL_3_NOT_AVAILABLE, "No hay suficiente Arena");
    msg.put(MATERIAL_4_NOT_AVAILABLE, "No hay suficiente Madera");
    msg.put(MATERIAL_5_NOT_AVAILABLE, "No hay suficiente Adobe");
    msg.put(HAS_NOT_BUILD_TYPE, "Debe indicar un tipo de construcción");
    msg.put(HAS_NOT_CITADEL, "Debe indicar una ciudadela");
    msg.put(NOT_VALID_BUILD_TYPE, "El tipo de construcción indicado no existe");
    msg.put(NOT_VALID_CITADEL, "La ciudadela indicada no existe");
  }
  
  private ErrorMsg() {
  }
}
