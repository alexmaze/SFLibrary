package com.successfactors.library.client.widget;

import java.util.ArrayList;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ListBox;
import com.rednels.ofcgwt.client.ChartWidget;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.helper.SLChartHelper;
import com.successfactors.library.shared.model.SLChartData;

public class AdminStaticsInfomationLayout extends VLayout {
	
	private VLayout vLayout1;
	private VLayout vLayout2;
	private VLayout vLayout3;

	ChartWidget chartPlanPlan;
	ArrayList<SLChartData> chartPlanPlanDataList;
	
	ChartWidget chartPlanActurally;
	ArrayList<SLChartData> chartPlanActurallyDataList;
	
	ChartWidget chartSvn;
	ArrayList<SLChartData> chartSvnDataList1;
	ArrayList<SLChartData> chartSvnDataList2;
	
	ArrayList<ArrayList<SLChartData>> chartSvnDataList;
	
	public AdminStaticsInfomationLayout() {
		super();

		vLayout1 = new VLayout();
		vLayout2 = new VLayout();
		vLayout3 = new VLayout();
		
		vLayout1.setStyleName("alex_myDecoratorPanel");
		vLayout2.setStyleName("alex_myDecoratorPanel");
		vLayout3.setStyleName("alex_myDecoratorPanel");
		vLayout1.setPadding(10);
		vLayout2.setPadding(10);
		vLayout3.setPadding(10);
		
		initProjectForm();
		initPlanChart();
		initSvnChart();
		
		this.addMember(vLayout1);
		this.addMember(vLayout2);
		this.addMember(vLayout3);
		this.setPadding(10);
		this.setMembersMargin(5);
		
	}
	
	private void initProjectForm() {
		
		Label label1 = new Label("图书馆信息");
		label1.setStyleName("alex_header_label");
		label1.setHeight(20);
		label1.setWidth("100%");
		vLayout1.addMember(label1);
		
		DynamicForm formProject = new DynamicForm();
		formProject.setNumCols(4);
		
		StaticTextItem progressItem = new StaticTextItem("bookNum", "图书总量");
		StaticTextItem statusItem = new StaticTextItem("instoreNum", "库中数量");
		
		StaticTextItem startDateItem = new StaticTextItem("borrowOkNum", "可借数量");
		StaticTextItem planNumItem = new StaticTextItem("rec", "剩余推荐");

		StaticTextItem expected1DateItem = new StaticTextItem("exspare1", "超期数量");
		StaticTextItem finishDate1Item = new StaticTextItem("lost1", "丢失数量");		
		StaticTextItem expectedDateItem = new StaticTextItem("exspare", "超期率");
		StaticTextItem finishDateItem = new StaticTextItem("lost", "丢失率");		
		
		formProject.setFields(
				progressItem, 
				statusItem, 
				startDateItem, 
				planNumItem,
				expected1DateItem,
				finishDate1Item,
				expectedDateItem, 
				finishDateItem
				);

		formProject.setCellBorder(1);
		formProject.setCellPadding(5);
		
		vLayout1.addMember(formProject);
		
				formProject.setValue("bookNum", "1002本");
				formProject.setValue("instoreNum", "597本");
				formProject.setValue("borrowOkNum", "323本");
				formProject.setValue("rec", "63本");
				formProject.setValue("exspare1", "6本");
				formProject.setValue("lost1", "3本");
				formProject.setValue("exspare", "2%");
				formProject.setValue("lost", "1%");
				


	}
	
	private void initPlanChart() {
		
		chartPlanPlanDataList  = new ArrayList<SLChartData>();
		chartPlanActurallyDataList  = new ArrayList<SLChartData>();
		
		HLayout headHLayout = new HLayout();
		Label label2 = new Label("各部门借阅、预订量统计");
		label2.setStyleName("alex_header_label");
		label2.setHeight(20);
		label2.setWidth("200px");
		
		ListBox selectTypeBox = new ListBox();
		selectTypeBox.addItem("饼图", "饼图");
		selectTypeBox.addItem("柱状图", "柱状图");
		selectTypeBox.setVisibleItemCount(1);
		selectTypeBox.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				if (((ListBox)(event.getSource())).getValue(((ListBox)(event.getSource())).getSelectedIndex()).equals("饼图")) {
					chartPlanPlan.setChartData(SLChartHelper.getAniPieChartData("借阅比重", "本", chartPlanPlanDataList));
					
					chartPlanActurally.setChartData(SLChartHelper.getAniPieChartData("预订比重", "本", chartPlanActurallyDataList));
					
				} else {
					chartPlanPlan.setChartData(SLChartHelper.getBarChartTransparentData("借阅比重", "本", chartPlanPlanDataList));
					
					chartPlanActurally.setChartData(SLChartHelper.getBarChartTransparentData("预订比重", "本", chartPlanActurallyDataList));
					
				}
				
			}
		});
		
		headHLayout.addMember(label2);
		headHLayout.addMember(selectTypeBox);
		
		vLayout2.addMember(headHLayout);
		
		final HLayout hLayout = new HLayout();
		hLayout.setHeight("200px");
		hLayout.setWidth100();
		vLayout2.addMember(hLayout);
		
		final HLayout hLayout1 = new HLayout();
		hLayout1.setHeight("200px");
		hLayout1.setWidth("300px");
		hLayout.addMember(hLayout1);
		
		final HLayout hLayout2 = new HLayout();
		hLayout2.setHeight("200px");
		hLayout2.setWidth("300px");
		hLayout.addMember(hLayout2);
		
		// fake data1
		SLChartData data11 = new SLChartData();
		data11.setName("RE");
		data11.setValue(10);

		SLChartData data12 = new SLChartData();
		data12.setName("ECT");
		data12.setValue(15);

		SLChartData data13 = new SLChartData();
		data13.setName("PLT");
		data13.setValue(22);

		SLChartData data14 = new SLChartData();
		data14.setName("PM");
		data14.setValue(5);

		SLChartData data15 = new SLChartData();
		data15.setName("Mobile");
		data15.setValue(8);
		
		chartPlanPlanDataList.add(data11);
		chartPlanPlanDataList.add(data12);
		chartPlanPlanDataList.add(data13);
		chartPlanPlanDataList.add(data14);
		chartPlanPlanDataList.add(data15);
		
		chartPlanPlan = new ChartWidget();
		chartPlanPlan.setSize("300px", "200px");
		chartPlanPlan.setChartData(SLChartHelper.getAniPieChartData("借阅比重", "本", chartPlanPlanDataList));
		

		
		// fake data 2
		SLChartData data21 = new SLChartData();
		data21.setName("RE");
		data21.setValue(5);

		SLChartData data22 = new SLChartData();
		data22.setName("ECT");
		data22.setValue(6);

		SLChartData data23 = new SLChartData();
		data23.setName("PLT");
		data23.setValue(1);

		SLChartData data24 = new SLChartData();
		data24.setName("PM");
		data24.setValue(4);

		SLChartData data25 = new SLChartData();
		data25.setName("Mobile");
		data25.setValue(6);
		
		chartPlanActurallyDataList.add(data21);
		chartPlanActurallyDataList.add(data22);
		chartPlanActurallyDataList.add(data23);
		chartPlanActurallyDataList.add(data24);
		chartPlanActurallyDataList.add(data25);
		
		chartPlanActurally = new ChartWidget();
		chartPlanActurally.setSize("300px", "200px");
		chartPlanActurally.setChartData(SLChartHelper.getAniPieChartData("预订比重", "本", chartPlanActurallyDataList));

		hLayout1.addChild(chartPlanPlan);
		hLayout2.addChild(chartPlanActurally);
		hLayout.setMembers(hLayout1, hLayout2);
		
	}
	
	private void initSvnChart() {
		
		HLayout headHLayout = new HLayout();
		Label label2 = new Label("借阅、预订走势统计");
		label2.setStyleName("alex_header_label");
		label2.setHeight(20);
		label2.setWidth("200px");

		final ListBox selectTypeBox2 = new ListBox();
		
		ListBox selectTypeBox1 = new ListBox();
		selectTypeBox1.addItem("日统计", "日统计");
		selectTypeBox1.addItem("周统计", "周统计");
		selectTypeBox1.addItem("月统计", "月统计");
		selectTypeBox1.setVisibleItemCount(1);
		selectTypeBox1.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				if (((ListBox)(event.getSource())).getValue(((ListBox)(event.getSource())).getSelectedIndex()).equals("日统计")) {

					SLChartData data11 = new SLChartData();
					data11.setName("2012-11-1");
					data11.setValue(10);

					SLChartData data13 = new SLChartData();
					data13.setName("2012-11-25");
					data13.setValue(22);

					SLChartData data14 = new SLChartData();
					data14.setName("2012-11-30");
					data14.setValue(7);

					SLChartData data15 = new SLChartData();
					data15.setName("2012-12-8");
					data15.setValue(9);
					
					SLChartData data16 = new SLChartData();
					data16.setName("2012-12-16");
					data16.setValue(24);
					
					SLChartData data17 = new SLChartData();
					data17.setName("2012-12-21");
					data17.setValue(20);
					
					SLChartData data18 = new SLChartData();
					data18.setName("2013-01-01");
					data18.setValue(8);
					
					chartSvnDataList1 = new ArrayList<SLChartData>();
					chartSvnDataList1.add(data11);
					chartSvnDataList1.add(data13);
					chartSvnDataList1.add(data14);
					chartSvnDataList1.add(data15);
					chartSvnDataList1.add(data16);
					chartSvnDataList1.add(data17);
					chartSvnDataList1.add(data18);
					
					SLChartData data21 = new SLChartData();
					data21.setName("2012-11-1");
					data21.setValue(4);

					SLChartData data23 = new SLChartData();
					data23.setName("2012-11-25");
					data23.setValue(9);

					SLChartData data24 = new SLChartData();
					data24.setName("2012-11-30");
					data24.setValue(7);

					SLChartData data25 = new SLChartData();
					data25.setName("2012-12-8");
					data25.setValue(9);
					
					SLChartData data26 = new SLChartData();
					data26.setName("2012-12-16");
					data26.setValue(12);
					
					SLChartData data27 = new SLChartData();
					data27.setName("2012-12-21");
					data27.setValue(8);
					
					SLChartData data28 = new SLChartData();
					data28.setName("2013-01-01");
					data28.setValue(6);
					
					chartSvnDataList2 = new ArrayList<SLChartData>();
					chartSvnDataList2.add(data21);
					chartSvnDataList2.add(data23);
					chartSvnDataList2.add(data24);
					chartSvnDataList2.add(data25);
					chartSvnDataList2.add(data26);
					chartSvnDataList2.add(data27);
					chartSvnDataList2.add(data28);
					
					chartSvnDataList = new ArrayList<ArrayList<SLChartData>>();
					chartSvnDataList.add(chartSvnDataList1);
					chartSvnDataList.add(chartSvnDataList2);
					
							chartSvn.setChartData(SLChartHelper.getLineChartData("借阅量", "日借阅量","本", chartSvnDataList));
							selectTypeBox2.setSelectedIndex(0);

				} else if (((ListBox)(event.getSource())).getValue(((ListBox)(event.getSource())).getSelectedIndex()).equals("周统计")) {
					SLChartData data11 = new SLChartData();
					data11.setName("2012 第1周");
					data11.setValue(10);

					SLChartData data12 = new SLChartData();
					data12.setName("2012 第2周");
					data12.setValue(15);

					SLChartData data13 = new SLChartData();
					data13.setName("2012 第3周");
					data13.setValue(22);

					SLChartData data14 = new SLChartData();
					data14.setName("2012 第4周");
					data14.setValue(7);

					SLChartData data15 = new SLChartData();
					data15.setName("2012 第5周");
					data15.setValue(9);
					
					SLChartData data16 = new SLChartData();
					data16.setName("2012 第6周");
					data16.setValue(24);
					
					SLChartData data17 = new SLChartData();
					data17.setName("2012 第7周");
					data17.setValue(20);
					
					chartSvnDataList1 = new ArrayList<SLChartData>();
					chartSvnDataList1.add(data11);
					chartSvnDataList1.add(data12);
					chartSvnDataList1.add(data13);
					chartSvnDataList1.add(data14);
					chartSvnDataList1.add(data15);
					chartSvnDataList1.add(data16);
					chartSvnDataList1.add(data17);
					
					SLChartData data21 = new SLChartData();
					data21.setName("2012 第1周");
					data21.setValue(10);

					SLChartData data22 = new SLChartData();
					data22.setName("2012 第2周");
					data22.setValue(5);

					SLChartData data23 = new SLChartData();
					data23.setName("2012 第3周");
					data23.setValue(21);

					SLChartData data24 = new SLChartData();
					data24.setName("2012 第4周");
					data24.setValue(12);

					SLChartData data25 = new SLChartData();
					data25.setName("2012 第5周");
					data25.setValue(10);
					
					SLChartData data26 = new SLChartData();
					data26.setName("2012 第6周");
					data26.setValue(5);
					
					SLChartData data27 = new SLChartData();
					data27.setName("2012 第7周");
					data27.setValue(6);
					
					chartSvnDataList2 = new ArrayList<SLChartData>();
					chartSvnDataList2.add(data21);
					chartSvnDataList2.add(data22);
					chartSvnDataList2.add(data23);
					chartSvnDataList2.add(data24);
					chartSvnDataList2.add(data25);
					chartSvnDataList2.add(data26);
					chartSvnDataList2.add(data27);

					chartSvnDataList = new ArrayList<ArrayList<SLChartData>>();
					chartSvnDataList.add(chartSvnDataList1);
					chartSvnDataList.add(chartSvnDataList2);
					
							chartSvn.setChartData(SLChartHelper.getLineChartData("借阅量", "周借阅量","本", chartSvnDataList));
							selectTypeBox2.setSelectedIndex(0);
							
				} else {
					SLChartData data11 = new SLChartData();
					data11.setName("2012-01");
					data11.setValue(10);

					SLChartData data12 = new SLChartData();
					data12.setName("2012-02");
					data12.setValue(15);

					SLChartData data13 = new SLChartData();
					data13.setName("2012-03");
					data13.setValue(15);

					SLChartData data14 = new SLChartData();
					data14.setName("2012-04");
					data14.setValue(7);

					SLChartData data15 = new SLChartData();
					data15.setName("2012-05");
					data15.setValue(12);
					
					SLChartData data16 = new SLChartData();
					data16.setName("2012-06");
					data16.setValue(8);
					
					
					chartSvnDataList1 = new ArrayList<SLChartData>();
					chartSvnDataList1.add(data11);
					chartSvnDataList1.add(data12);
					chartSvnDataList1.add(data13);
					chartSvnDataList1.add(data14);
					chartSvnDataList1.add(data15);
					chartSvnDataList1.add(data16);
					
					SLChartData data21 = new SLChartData();
					data21.setName("2012-01");
					data21.setValue(5);

					SLChartData data22 = new SLChartData();
					data22.setName("2012-02");
					data22.setValue(2);

					SLChartData data23 = new SLChartData();
					data23.setName("2012-03");
					data23.setValue(3);

					SLChartData data24 = new SLChartData();
					data24.setName("2012-04");
					data24.setValue(9);

					SLChartData data25 = new SLChartData();
					data25.setName("2012-05");
					data15.setValue(12);
					
					SLChartData data26 = new SLChartData();
					data26.setName("2012-06");
					data26.setValue(8);
					
					
					chartSvnDataList2 = new ArrayList<SLChartData>();
					chartSvnDataList2.add(data21);
					chartSvnDataList2.add(data22);
					chartSvnDataList2.add(data23);
					chartSvnDataList2.add(data24);
					chartSvnDataList2.add(data25);
					chartSvnDataList2.add(data26);

					chartSvnDataList = new ArrayList<ArrayList<SLChartData>>();
					chartSvnDataList.add(chartSvnDataList1);
					chartSvnDataList.add(chartSvnDataList2);
					
							chartSvn.setChartData(SLChartHelper.getLineChartData("借阅量", "月借阅量","本", chartSvnDataList));
							selectTypeBox2.setSelectedIndex(0);
							
				}
				
			}
		});

		selectTypeBox2.addItem("折线图", "折线图");
		selectTypeBox2.addItem("柱状图", "柱状图");
		selectTypeBox2.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				if (((ListBox)(event.getSource())).getValue(((ListBox)(event.getSource())).getSelectedIndex()).equals("折线图")) {

						chartSvn.setChartData(SLChartHelper.getLineChartData("借阅量","",  "本",chartSvnDataList));

				} else {

						chartSvn.setChartData(SLChartHelper.getSketchChartData("借阅量", "本", chartSvnDataList1, chartSvnDataList2, chartSvnDataList2));

				}
			}
		});
		
		headHLayout.addMember(label2);
		headHLayout.addMember(selectTypeBox1);
		headHLayout.addMember(selectTypeBox2);
		
		vLayout3.addMember(headHLayout);
		
		final HLayout hLayout = new HLayout();
		hLayout.setHeight("200px");
		vLayout3.addMember(hLayout);
		
		SLChartData data11 = new SLChartData();
		data11.setName("2012-11-1");
		data11.setValue(10);

		SLChartData data13 = new SLChartData();
		data13.setName("2012-11-25");
		data13.setValue(22);

		SLChartData data14 = new SLChartData();
		data14.setName("2012-11-30");
		data14.setValue(7);

		SLChartData data15 = new SLChartData();
		data15.setName("2012-12-8");
		data15.setValue(9);
		
		SLChartData data16 = new SLChartData();
		data16.setName("2012-12-16");
		data16.setValue(24);
		
		SLChartData data17 = new SLChartData();
		data17.setName("2012-12-21");
		data17.setValue(20);
		
		SLChartData data18 = new SLChartData();
		data18.setName("2013-01-01");
		data18.setValue(8);
		
		chartSvnDataList1 = new ArrayList<SLChartData>();
		chartSvnDataList1.add(data11);
		chartSvnDataList1.add(data13);
		chartSvnDataList1.add(data14);
		chartSvnDataList1.add(data15);
		chartSvnDataList1.add(data16);
		chartSvnDataList1.add(data17);
		chartSvnDataList1.add(data18);
		
		SLChartData data21 = new SLChartData();
		data21.setName("2012-11-1");
		data21.setValue(4);

		SLChartData data23 = new SLChartData();
		data23.setName("2012-11-25");
		data23.setValue(9);

		SLChartData data24 = new SLChartData();
		data24.setName("2012-11-30");
		data24.setValue(7);

		SLChartData data25 = new SLChartData();
		data25.setName("2012-12-8");
		data25.setValue(9);
		
		SLChartData data26 = new SLChartData();
		data26.setName("2012-12-16");
		data26.setValue(12);
		
		SLChartData data27 = new SLChartData();
		data27.setName("2012-12-21");
		data27.setValue(8);
		
		SLChartData data28 = new SLChartData();
		data28.setName("2013-01-01");
		data28.setValue(6);
		
		chartSvnDataList2 = new ArrayList<SLChartData>();
		chartSvnDataList2.add(data21);
		chartSvnDataList2.add(data23);
		chartSvnDataList2.add(data24);
		chartSvnDataList2.add(data25);
		chartSvnDataList2.add(data26);
		chartSvnDataList2.add(data27);
		chartSvnDataList2.add(data28);
		
		chartSvnDataList = new ArrayList<ArrayList<SLChartData>>();
		chartSvnDataList.add(chartSvnDataList1);
		chartSvnDataList.add(chartSvnDataList2);
		
				
				chartSvn = new ChartWidget();
				chartSvn.setSize("600px", "200px");
				chartSvn.setChartData(SLChartHelper.getLineChartData("借阅量", "日借阅量","本", chartSvnDataList));
				
				hLayout.addChild(chartSvn);

		
	}
	
}
