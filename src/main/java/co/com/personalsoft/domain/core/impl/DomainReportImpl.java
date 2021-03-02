package co.com.personalsoft.domain.core.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.com.personalsoft.domain.core.DomainReport;
import co.com.personalsoft.domain.core.shared.Pair;
import co.com.personalsoft.domain.enumeration.BuildOrderState;
import co.com.personalsoft.service.dto.BuildOrderDTO;
import co.com.personalsoft.service.dto.BuildTypeDTO;
import co.com.personalsoft.service.dto.RequisitionDTO;
import co.com.personalsoft.service.dto.core.BuidOrderReportDTO;

public abstract class DomainReportImpl implements DomainReport {
  
  private static final Logger logger = LoggerFactory.getLogger(DomainReportImpl.class);
  
  public BuidOrderReportDTO report() {
    List<BuildOrderDTO> pendingBuildOrders = findBuildOrderByState(BuildOrderState.PENDING);
    pendingBuildOrders.forEach(buildOrder -> {
      Optional<RequisitionDTO> oRequisition = findRequisitionById(buildOrder.getRequisition().getId());
      buildOrder.setRequisition(oRequisition.isPresent() ? oRequisition.get() : null);
    });
    List<BuildTypeDTO> buildTypes = findBuildTypes();
    Map<String, Integer> amountFinishedByType = buildTypes.stream().map(buildType -> {
      Integer size = findBuildOrderByStateAndBuildType(BuildOrderState.FINISHED, buildType.getId()).size();
      return new Pair<String, Integer>(buildType.getName(), size);
    }).collect(Collectors.toMap(Pair::getLeft, Pair::getRight));
    Integer amountBuilding = findBuildOrderByState(BuildOrderState.BUILDING).size();
    
    BuidOrderReportDTO report = new BuidOrderReportDTO();
    report.setPendings(pendingBuildOrders);
    report.setAmountFinishedByType(amountFinishedByType);
    report.setAmountBuilding(amountBuilding);
    
    String fileName = createFileReport(report);
    report.setFileReportPath(fileName);
    return report;
  }

  private String createFileReport(BuidOrderReportDTO report) {
    OutputStreamWriter file = null;
    try {
      StringBuilder buffer = new StringBuilder();
      buffer.append("Constructores S.A.S.\t\tCiudadela del Futuro\n\n");
      buffer.append("Reporte: Ordenes de construcción\n\n");
      buffer.append("Ordenes pendientes: \n");
      List<BuildOrderDTO> pendingBuildOrders = report.getPendings();
      buffer.append("\tNombre\t:: Tipo\t::Estado\t::Inicio\t::Finalización\t\n");
      for (BuildOrderDTO buildOrder : pendingBuildOrders) {
        buffer.append("\t" + buildOrder.getRequisition().getName() + 
                  "\t::" + buildOrder.getRequisition().getBuildType().getName() +
                  "\t::" + buildOrder.getState() +
                  "\t::" + buildOrder.getStart() +
                  "\t::" + buildOrder.getFinish() + "\n");
      }
      buffer.append("\nCantidad de órdenes finalizadas por tipo:\n");
      Map<String, Integer> map = report.getAmountFinishedByType();
      for (Entry<String, Integer> nameAndAmount : map.entrySet()) {
        buffer.append("\t" + nameAndAmount.getKey() + "\t: " + nameAndAmount.getValue() + "\n");
      }
      buffer.append("\nCantidad de órdenes en progreso\t:" + report.getAmountBuilding());
      String filePath = "/src/main/resources/report/report_"+ LocalDateTime.now() +".txt";
      file = new OutputStreamWriter(new FileOutputStream("." + filePath), StandardCharsets.UTF_8);
      file.write(buffer.toString());
      file.close();
      return "PATH_PROJECT" + filePath;
    } catch (IOException e) {
      logger.error("An error occurred: {0}", e);
      return "";
    }
  }
}
