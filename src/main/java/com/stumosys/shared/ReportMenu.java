package com.stumosys.shared;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the report_menu database table.
 * 
 */
@Entity
@Table(name="report_menu")
@NamedQuery(name="ReportMenu.findAll", query="SELECT r FROM ReportMenu r")
public class ReportMenu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="report_menu_id")
	private int reportMenuId;

	@Column(name="report_description")
	private String reportDescription;

	@Column(name="report_name")
	private String reportName;

	@Column(name="report_subtype")
	private String reportSubtype;

	@Column(name="report_type")
	private String reportType;


	private String status;

	//bi-directional many-to-one association to ReportFilterTable
	@OneToMany(mappedBy="reportMenu")
	private List<ReportFilterTable> reportFilterTables;
	
	//bi-directional many-to-one association to School
	@ManyToOne
	@JoinColumn(name="school_ID")
	private School school_ID;

	public ReportMenu() {
	}

	public int getReportMenuId() {
		return this.reportMenuId;
	}

	public void setReportMenuId(int reportMenuId) {
		this.reportMenuId = reportMenuId;
	}

	public String getReportDescription() {
		return this.reportDescription;
	}

	public void setReportDescription(String reportDescription) {
		this.reportDescription = reportDescription;
	}

	public String getReportName() {
		return this.reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getReportSubtype() {
		return this.reportSubtype;
	}

	public void setReportSubtype(String reportSubtype) {
		this.reportSubtype = reportSubtype;
	}

	public String getReportType() {
		return this.reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public School getSchool_ID() {
		return school_ID;
	}

	public void setSchool_ID(School school_ID) {
		this.school_ID = school_ID;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<ReportFilterTable> getReportFilterTables() {
		return this.reportFilterTables;
	}

	public void setReportFilterTables(List<ReportFilterTable> reportFilterTables) {
		this.reportFilterTables = reportFilterTables;
	}

	public ReportFilterTable addReportFilterTable(ReportFilterTable reportFilterTable) {
		getReportFilterTables().add(reportFilterTable);
		reportFilterTable.setReportMenu(this);

		return reportFilterTable;
	}

	public ReportFilterTable removeReportFilterTable(ReportFilterTable reportFilterTable) {
		getReportFilterTables().remove(reportFilterTable);
		reportFilterTable.setReportMenu(null);

		return reportFilterTable;
	}

}