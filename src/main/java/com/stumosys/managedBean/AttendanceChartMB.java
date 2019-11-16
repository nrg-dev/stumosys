package com.stumosys.managedBean;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.apache.log4j.Logger;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import com.stumosys.controler.SmsController;
import com.stumosys.domain.AttendanceDataBean;
import com.stumosys.util.CommonValidate;

@ManagedBean(name = "attendanceChartMB")
public class AttendanceChartMB {
	private static Logger logger = Logger.getLogger(AttendanceMB.class);
	AttendanceDataBean attendanceDataBean = new AttendanceDataBean();
	SmsController controller = null;
	private BarChartModel barchart;
	private boolean flag = false;
	private boolean flag1 = false;
	private boolean flag2 = false;
	private boolean flag3 = false;
	private List<String> studentList;
	private List<String> classList;
	private String chartValues;
	private boolean chartFlag = false;
	public boolean isChartFlag() {
		return chartFlag;
	}

	public void setChartFlag(boolean chartFlag) {
		this.chartFlag = chartFlag;
	}

	public String getChartValues() {
		return chartValues;
	}

	public void setChartValues(String chartValues) {
		this.chartValues = chartValues;
	}

	public boolean isFlag3() {
		return flag3;
	}

	public void setFlag3(boolean flag3) {
		this.flag3 = flag3;
	}

	public boolean isFlag2() {
		return flag2;
	}

	public void setFlag2(boolean flag2) {
		this.flag2 = flag2;
	}

	public List<String> getClassList() {
		return classList;
	}

	public void setClassList(List<String> classList) {
		this.classList = classList;
	}

	public List<String> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<String> studentList) {
		this.studentList = studentList;
	}

	public boolean isFlag1() {
		return flag1;
	}

	public void setFlag1(boolean flag1) {
		this.flag1 = flag1;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public BarChartModel getBarchart() {
		return barchart;
	}

	public void setBarchart(BarChartModel barchart) {
		this.barchart = barchart;
	}

	public AttendanceDataBean getAttendanceDataBean() {
		return attendanceDataBean;
	}

	public void setAttendanceDataBean(AttendanceDataBean attendanceDataBean) {
		this.attendanceDataBean = attendanceDataBean;
	}

	public String attendancechart() {
		logger.info("-----------Inside attendancechart method()----------------");
		String personID = "";
		try {
			if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role").equals("Student")) {
				flag1 = false;
				return "attendanceChart";
			} else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
					.equals("Parent")) {
				flag1 = true;
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if (personID != null) {
					studentList = controller.parentAttlist(personID, attendanceDataBean);
					Collections.sort(studentList);
					return "attendanceChart";
				}
			} else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
					.equals("Teacher")
					|| FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
							.equals("Admin")) {
				ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
				controller = (SmsController) ctx.getBean("controller");
				personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if (personID != null) {
					classList = controller.ClassListAttView(personID, attendanceDataBean);
					Collections.sort(classList);
					flag2 = false;
					return "attendanceChartView";
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			attendanceDataBean.setMonthyear("");
			attendanceDataBean.setClassStudent("");
			attendanceDataBean.setStudentID("");
			attendanceDataBean.setClassname("");
			flag2 = false;chartFlag = false; 
			flag = false;
			flag3 = false;
		}
		return "";
	}

	public String studentChart() {
		//logger.debug("student chart select -- " +attendanceDataBean.getMonthyear() + " - " + attendanceDataBean.getStudentID());
		logger.info("-----------Inside studentChart method()----------------");
		String personID = "";
		flag = false;String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Parent")) {
					if (validate(true)) {
						attendanceDataBean.setMonthyear(attendanceDataBean.getMonthyear());
						genarateChart();
						flag = true;
					}
				} else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Admin")
						|| FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
								.equals("Teacher")) {
					if (validate1(true)) {
						attendanceDataBean.setMonthyear(attendanceDataBean.getMonthyear());
						genarateChart();
						flag = true;
					}
				} else if (FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("Role")
						.equals("Student")) {
					if (validate2(true)) {
						attendanceDataBean.setMonthyear(attendanceDataBean.getMonthyear());
						genarateChart();
						flag = true;
					}
				}
				if(chartValues.equalsIgnoreCase("empty")){
					fieldName = CommonValidate.findComponentInRoot("monthyearid").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Yet not took Attendance"));
					chartFlag = false; 
				}
				else{
					chartFlag = true;
				}
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		}
		return "";
	}



	private boolean validate2(boolean flag) {
		logger.info("-----------Inside validate2 method()----------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (attendanceDataBean.getMonthyear().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("monthyearid").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Month or Year."));
			}
			valid = false;

		}
		return valid;
	}
	
	public void genarateChart() {
		logger.info("-----------Inside genarateChart method()----------------");
		barchart = initBarModel();
		barchart.setTitle("Attendance Percentage");
		barchart.setAnimate(true);
		barchart.setLegendPosition("ne");
		Axis yAxis = barchart.getAxis(AxisType.Y);
		yAxis.setMin(0);
		yAxis.setMax(100);
		if(barchart.getTitle().equalsIgnoreCase("")){
			logger.debug("inside if --- >>");
		}else{
			logger.debug("inside else --- >>");
		}
	}

	private BarChartModel initBarModel() {
		logger.info("-----------Inside initBarModel method()----------------");
		int pm = 0;
		int pmy = 0;
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			SmsController controller = (SmsController) ctx.getBean("controller");
			BarChartModel model = new BarChartModel();
			model.setDatatipFormat("");// remove mouse over value
			model.setShowPointLabels(false);// show percentage
			model.setMouseoverHighlight(true);
			model.setShowDatatip(false);// hide the mouse over hole
			ChartSeries presentstatus = new ChartSeries();
			presentstatus.setLabel("Present");
			ChartSeries absentstatus = new ChartSeries();
			absentstatus.setLabel("Absent");
			ChartSeries leavestatus = new ChartSeries();
			leavestatus.setLabel("Leave");
			ChartSeries Latestatus = new ChartSeries();
			Latestatus.setLabel("Late");
			Date d = Calendar.getInstance().getTime();
			SimpleDateFormat df = new SimpleDateFormat("yyyy");
			SimpleDateFormat dfm = new SimpleDateFormat("MM");
			SimpleDateFormat dffm = new SimpleDateFormat("MMM");
			int year = Integer.parseInt(df.format(d));
			int month = Integer.parseInt(dfm.format(d));
			pm = month - 4;
			pmy = year;
			List<Date> pdf = new ArrayList<Date>();
			if (attendanceDataBean.getMonthyear().equals("Month")) {
				int count=0;
				for (int i = pm; i < pm + 12; i++) {
					GregorianCalendar gc1 = new GregorianCalendar(pmy, i - 1, Calendar.DAY_OF_MONTH);
					gc1.set(Calendar.DAY_OF_MONTH, gc1.getActualMinimum(Calendar.DAY_OF_MONTH));
					pdf.add(gc1.getTime());
					Date pdff = gc1.getTime();
					Date pdee = null;
					gc1.set(Calendar.DAY_OF_MONTH, gc1.getActualMaximum(Calendar.DAY_OF_MONTH));
					pdee = gc1.getTime();
					BigDecimal presents = BigDecimal.valueOf(0), absents = BigDecimal.valueOf(0),
							leaves = BigDecimal.valueOf(0), lates = BigDecimal.valueOf(0);
					attendanceDataBean.setChart(null);
					personID = "" + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
					if ((personID != null) || (!personID.equalsIgnoreCase(""))) {
						attendanceDataBean.setStdate(pdff);
						attendanceDataBean.setEndate(pdee);
						//attendanceDataBean.setYear("" + pdee.getYear());
						logger.debug("start date - " + attendanceDataBean.getStdate() + " end - "
								+ attendanceDataBean.getEndate());
						controller.attendanceChartStudent(personID, attendanceDataBean);
						String months = dffm.format(pdff);
						if (attendanceDataBean.getChart().size() > 0) {
							StringTokenizer stringtoken = new StringTokenizer(
									attendanceDataBean.getChart().get(0).getPercentage());
							String present = stringtoken.nextToken("%");
							presents = new BigDecimal(present);
							presentstatus.set(months, presents);
							StringTokenizer stringtoken1 = new StringTokenizer(
									attendanceDataBean.getChart().get(0).getAbsent());
							String absent = stringtoken1.nextToken("%");
							absents = new BigDecimal(absent);
							absentstatus.set(months, absents);
							StringTokenizer stringtoken2 = new StringTokenizer(
									attendanceDataBean.getChart().get(0).getLeave());
							String leave = stringtoken2.nextToken("%");
							leaves = new BigDecimal(leave);
							leavestatus.set(months, leaves);
							StringTokenizer stringtoken3 = new StringTokenizer(
									attendanceDataBean.getChart().get(0).getLate());
							String late = stringtoken3.nextToken("%");
							lates = new BigDecimal(late);
							Latestatus.set(months, lates);
							count++;
						} else {
							String present = "0.0";
							presents = new BigDecimal(present);
							presentstatus.set(months, presents);
							String absent = "0.0";
							absents = new BigDecimal(absent);
							absentstatus.set(months, absents);
							String leave = "0.0";
							leaves = new BigDecimal(leave);
							leavestatus.set(months, leaves);
							String late = "0.0";
							lates = new BigDecimal(late);
							Latestatus.set(months, lates);
						}
					}
				}
				if(count==0) chartValues="empty";
				else chartValues="not empty";
			} else if (attendanceDataBean.getMonthyear().equals("Year")) {
				logger.debug("inside year ");chartValues="empty";
				personID = "" + FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
				if ((personID != null) || (!personID.equalsIgnoreCase(""))) {
					controller.attendanceChartStudentYear(personID, attendanceDataBean);
					logger.debug("mb year "+attendanceDataBean.getYears());
					if (attendanceDataBean.getYears().size() > 0) {
						String yearz = attendanceDataBean.getYears().get(0);
						logger.debug("years "+yearz);
						BigDecimal presents = BigDecimal.valueOf(0), absents = BigDecimal.valueOf(0),
								leaves = BigDecimal.valueOf(0), lates = BigDecimal.valueOf(0);
						if (yearz == null || yearz.equalsIgnoreCase("")) {
							chartValues="empty";
							logger.debug("inside year empty");
							String years = "" + Calendar.getInstance().get(Calendar.YEAR);
							String present = "0.0";
							presents = new BigDecimal(present);
							presentstatus.set(years, presents);
							String absent = "0.0";
							absents = new BigDecimal(absent);
							absentstatus.set(years, absents);
							String leave = "0.0";
							leaves = new BigDecimal(leave);
							leavestatus.set(years, leaves);
							String late = "0.0";
							lates = new BigDecimal(late);
							Latestatus.set(years, lates);
						} else {
							logger.debug("inside year");
							chartValues="not empty";
							for (int i = 0; i < attendanceDataBean.getYears().size(); i++) {
								attendanceDataBean.setYear(attendanceDataBean.getYears().get(i));
								controller.attendanceChartStudent(personID, attendanceDataBean);
								String years = attendanceDataBean.getYears().get(i);
								if (attendanceDataBean.getChart().size() > 0) {
									StringTokenizer stringtoken = new StringTokenizer(
											attendanceDataBean.getChart().get(0).getPercentage());
									String present = stringtoken.nextToken("%");
									presents = new BigDecimal(present);
									presentstatus.set(years, presents);
									StringTokenizer stringtoken1 = new StringTokenizer(
											attendanceDataBean.getChart().get(0).getAbsent());
									String absent = stringtoken1.nextToken("%");
									absents = new BigDecimal(absent);
									absentstatus.set(years, absents);
									StringTokenizer stringtoken2 = new StringTokenizer(
											attendanceDataBean.getChart().get(0).getLeave());
									String leave = stringtoken2.nextToken("%");
									leaves = new BigDecimal(leave);
									leavestatus.set(years, leaves);
									StringTokenizer stringtoken3 = new StringTokenizer(
											attendanceDataBean.getChart().get(0).getLate());
									String late = stringtoken3.nextToken("%");
									lates = new BigDecimal(late);
									Latestatus.set(years, lates);
								} else {
									String present = "0.0";
									presents = new BigDecimal(present);
									presentstatus.set(years, presents);
									String absent = "0.0";
									absents = new BigDecimal(absent);
									absentstatus.set(years, absents);
									String leave = "0.0";
									leaves = new BigDecimal(leave);
									leavestatus.set(years, leaves);
									String late = "0.0";
									lates = new BigDecimal(late);
									Latestatus.set(years, lates);
								}
							}
						}
					}
				}
			}
			model.addSeries(presentstatus);
			model.addSeries(absentstatus);
			model.addSeries(leavestatus);
			model.addSeries(Latestatus);
			return model;
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
			//logger.warn("Inside Exception",e);
			return null;
		} finally {

		}
	}

	public void serachby(ValueChangeEvent v) {
		logger.info("-----------Inside serachby method()----------------");
		flag = false;chartFlag = false; 
		try {
			if (v.getNewValue().equals("Class")) {
				flag3 = true;
				flag2 = false;
				attendanceDataBean.setClassname("");
			} else if (v.getNewValue().equals("Student")) {
				flag3 = true;
				flag2 = true;
				attendanceDataBean.setClassname("");
				attendanceDataBean.setStudentID("");
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			attendanceDataBean.setMonthyear("");
		}
	}

	public void classSearch(ValueChangeEvent v) {
		logger.info("-----------Inside classSearch method()----------------");
		String personID = "";
		try {
			ApplicationContext ctx = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
			controller = (SmsController) ctx.getBean("controller");
			personID = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("LogID");
			if (personID != null) {
				if (attendanceDataBean.getClassStudent().equals("Student")) {
					attendanceDataBean.setClassname(v.getNewValue().toString());
					controller.classStudent(attendanceDataBean, personID);
					studentList = attendanceDataBean.getStudents();
					Collections.sort(studentList);
				} 
			}
		} catch (Exception e) {
			logger.warn("Exception -->"+e.getMessage());
		} finally {
			chartFlag = false; 
		}
	}
	
	public void studentSelect(ValueChangeEvent v) {		
		chartFlag = false; 
	}

	public void monthSelect(ValueChangeEvent v) {		
		chartFlag = false; 
	}
	
	private boolean validate(boolean flag) {
		logger.info("-----------Inside validate method()----------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (attendanceDataBean.getStudentID().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("studentID").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select the Student Name."));
			}
			valid = false;

		}
		if (attendanceDataBean.getMonthyear().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("monthyearid").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Month or Year."));
			}
			valid = false;

		}
		return valid;
	}
	private boolean validate1(boolean flag) {
		logger.info("-----------Inside validate1 method()----------------");
		boolean valid = true;
		String fieldName;
		FacesContext fc = FacesContext.getCurrentInstance();
		if (attendanceDataBean.getClassStudent().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("serach").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select one search."));
			}
			valid = false;

		} 
		if (attendanceDataBean.getClassStudent().equals("Class")) {
			if (attendanceDataBean.getClassname().equalsIgnoreCase("")) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("classID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select one Class."));
				}
				valid = false;

			}
		} else if (attendanceDataBean.getClassStudent().equals("Student")) {
			if (attendanceDataBean.getClassname().equalsIgnoreCase("")) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("classID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select one Class Name."));
				}
				valid = false;

			}
			if (attendanceDataBean.getStudentID().equalsIgnoreCase("")) {
				if (flag) {
					fieldName = CommonValidate.findComponentInRoot("studentID").getClientId(fc);
					fc.addMessage(fieldName, new FacesMessage("Please Select one Student Name."));
				}
				valid = false;

			}
		}
		if (attendanceDataBean.getMonthyear().equalsIgnoreCase("")) {
			if (flag) {
				fieldName = CommonValidate.findComponentInRoot("monthyearid").getClientId(fc);
				fc.addMessage(fieldName, new FacesMessage("Please Select Month or Year."));
			}
			valid = false;

		}
		return valid;
	}
	
}
