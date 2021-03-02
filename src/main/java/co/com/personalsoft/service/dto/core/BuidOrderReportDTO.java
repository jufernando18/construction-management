package co.com.personalsoft.service.dto.core;

import java.util.List;
import java.util.Map;

import co.com.personalsoft.service.dto.BuildOrderDTO;

public class BuidOrderReportDTO {

  private List<BuildOrderDTO> pendings;
  
  private Map<String, Integer> amountFinishedByType;
  
  private Integer amountBuilding;
  
  private String fileReportPath;

  public List<BuildOrderDTO> getPendings() {
    return pendings;
  }

  public void setPendings(List<BuildOrderDTO> pendings) {
    this.pendings = pendings;
  }

  public Map<String, Integer> getAmountFinishedByType() {
    return amountFinishedByType;
  }

  public void setAmountFinishedByType(Map<String, Integer> amountFinishedByType) {
    this.amountFinishedByType = amountFinishedByType;
  }

  public Integer getAmountBuilding() {
    return amountBuilding;
  }

  public void setAmountBuilding(Integer amountBuilding) {
    this.amountBuilding = amountBuilding;
  }

  public String getFileReportPath() {
    return fileReportPath;
  }

  public void setFileReportPath(String fileReportPath) {
    this.fileReportPath = fileReportPath;
  }

  @Override
  public String toString() {
    return "BuidOrderReportDTO [pendings=" + pendings + ", amountFinishedByType="
        + amountFinishedByType + ", amountBuilding=" + amountBuilding + ", fileReportPath="
        + fileReportPath + "]";
  }
}
