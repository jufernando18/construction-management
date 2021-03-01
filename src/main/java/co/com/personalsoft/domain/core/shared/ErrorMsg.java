package co.com.personalsoft.domain.core.shared;

import java.util.HashMap;
import java.util.Map;

public final class ErrorMsg {

  public static final Map<String, String> msg = new HashMap<>();
  
  static {
    msg.put("coordinateNotAvailable", "En las coordenadas indicadas ya existe una orden de construcción, por favor cambielas e intente nuevamente");
    msg.put("material1NotAvailable", "No hay suficiente Cemento");
    msg.put("material2NotAvailable", "No hay suficiente Grava");
    msg.put("material3NotAvailable", "No hay suficiente Arena");
    msg.put("material4NotAvailable", "No hay suficiente Madera");
    msg.put("material5NotAvailable", "No hay suficiente Adobe");
  }
  
  private ErrorMsg() {
  }
}
