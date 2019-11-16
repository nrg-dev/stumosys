package com.stumosys.shared;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the report_filter_table database table.
 * 
 */
@Entity
@Table(name="report_filter_table")
@NamedQuery(name="ReportFilterTable.findAll", query="SELECT r FROM ReportFilterTable r")
public class ReportFilterTable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int report_filter_table_ID;

	private String filter_activeStatus;

	@Column(name="filter_name")
	private String filterName;

	//bi-directional many-to-one association to ReportMenu
	@ManyToOne
	@JoinColumn(name="has_report_menu_ID")
	private ReportMenu reportMenu;

	public ReportFilterTable() {
	}

	public int getReport_filter_table_ID() {
		return this.report_filter_table_ID;
	}

	public void setReport_filter_table_ID(int report_filter_table_ID) {
		this.report_filter_table_ID = report_filter_table_ID;
	}

	public String getFilter_activeStatus() {
		return this.filter_activeStatus;
	}

	public void setFilter_activeStatus(String filter_activeStatus) {
		this.filter_activeStatus = filter_activeStatus;
	}

	public String getFilterName() {
		return this.filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public ReportMenu getReportMenu() {
		return this.reportMenu;
	}

	public void setReportMenu(ReportMenu reportMenu) {
		this.reportMenu = reportMenu;
	}

}