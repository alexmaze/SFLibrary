package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.otherService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.rednels.ofcgwt.client.ChartWidget;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.Label;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.client.helper.SLChartHelper;
import com.successfactors.library.shared.model.SLChartData;
import com.successfactors.library.shared.model.SLStatisticalData;

public class AdminStaticsInfomationLayout extends VLayout {
	
	private VLayout vLayout1;
	private VLayout vLayout2;
	private VLayout vLayout3;

	SLStatisticalData theData;
	
	ChartWidget eachTeamBorrowChart;
	ArrayList<SLChartData> eachTeamBorrowChartData;
	
	ChartWidget eachTeamOrderChart;
	ArrayList<SLChartData> eachTeamOrderChartData;
	
	ChartWidget eachMonthTrendChart;
	ArrayList<SLChartData> eachMonthBorrowTrendChartData;
	ArrayList<SLChartData> eachMonthOrderTrendChartData;
	
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
		
		this.setPadding(10);
		this.setMembersMargin(5);
		
		this.addMember(vLayout1);
		this.addMember(vLayout2);
		this.addMember(vLayout3);
		
		fetchDataAndRenderChart();
		
	}
	
	private void fetchDataAndRenderChart() {
		new RPCCall<SLStatisticalData>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}
			@Override
			public void onSuccess(SLStatisticalData result) {
				if (result == null) {
					SC.say("暂无资料。。。囧rz");
					return;
				}
				theData = result;
				
				prepareChartDataList();
				
				initProjectForm();
				initPlanChart();
				initSvnChart();
			}
			@Override
			protected void callService(AsyncCallback<SLStatisticalData> cb) {
				otherService.getThisMonthStatisticalData(cb);
			}
		}.retry(3);
	}

	private void prepareChartDataList() {

		
		// ---------------------------------------------------------------------
		eachTeamBorrowChartData  = new ArrayList<SLChartData>();
		Iterator<Map.Entry<String, Long>> iterator1 = theData.getBorrowNumberEachTeam().entrySet().iterator();
		while (iterator1.hasNext()) {
			Map.Entry<String, Long> entry = iterator1.next();
			SLChartData newData = new SLChartData();
			newData.setName(entry.getKey());
			newData.setValue(entry.getValue().intValue());
			eachTeamBorrowChartData.add(newData);
		}
		
		Collections.sort(eachTeamBorrowChartData, new Comparator<SLChartData>() {
			@Override
			public int compare(SLChartData o1, SLChartData o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		ArrayList<SLChartData> tempList1  = new ArrayList<SLChartData>();
		SLChartData lastData1 = new SLChartData();
		lastData1.setName("其他");
		lastData1.setValue(0);
		int iCount = 0;
		for (SLChartData cData : eachTeamBorrowChartData) {
			if (iCount < 5) {
				if (cData.getName().equals("")) {
					lastData1.setValue(lastData1.getValue() + cData.getValue());
					iCount--;
				} else {
					tempList1.add(cData);
				}
			} else {
				lastData1.setValue(lastData1.getValue() + cData.getValue());
			}
			iCount++;
		}
		tempList1.add(lastData1);
		eachTeamBorrowChartData = tempList1;

		// ---------------------------------------------------------------------
		eachTeamOrderChartData  = new ArrayList<SLChartData>();
		Iterator<Map.Entry<String, Long>> iterator2 = 
				theData.getOrderNumberEachTeam().entrySet().iterator();
		while (iterator2.hasNext()) {
			Map.Entry<String, Long> entry = iterator2.next();
			SLChartData newData = new SLChartData();
			newData.setName(entry.getKey());
			newData.setValue(entry.getValue().intValue());
			eachTeamOrderChartData.add(newData);
		}
		Collections.sort(eachTeamOrderChartData, new Comparator<SLChartData>() {
			@Override
			public int compare(SLChartData o1, SLChartData o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		ArrayList<SLChartData> tempList2  = new ArrayList<SLChartData>();
		SLChartData lastData2 = new SLChartData();
		lastData2.setName("其他");
		lastData2.setValue(0);
		int iCount2 = 0;
		for (SLChartData cData : eachTeamOrderChartData) {
			if (iCount2 < 5) {
				if (cData.getName().equals("")) {
					lastData2.setValue(lastData2.getValue() + cData.getValue());
					iCount2--;
				} else {
					tempList2.add(cData);
				}
			} else {
				lastData2.setValue(lastData2.getValue() + cData.getValue());
			}
			iCount2++;
		}
		tempList2.add(lastData2);
		eachTeamOrderChartData = tempList2;
		
		// ---------------------------------------------------------------------

		eachMonthBorrowTrendChartData = new ArrayList<SLChartData>();
		@SuppressWarnings("deprecation")
		int thisMonth = (new Date()).getMonth() + 1;
		for (int i = thisMonth+1; i < thisMonth + 13; i++) {
			int realMonth = (i <= 12) ? i : (i - 12);
			SLChartData newData = new SLChartData();
			Long valueLong = theData.getBorrowNumberEachMonth().get(realMonth);
			newData.setName(realMonth + "月");
			newData.setValue(valueLong == null?0:valueLong.intValue());
			eachMonthBorrowTrendChartData.add(newData);
		}

		// ---------------------------------------------------------------------
		
		eachMonthOrderTrendChartData = new ArrayList<SLChartData>();
		for (int i = thisMonth+1; i < thisMonth + 13; i++) {
			int realMonth = (i <= 12) ? i : (i - 12);
			SLChartData newData = new SLChartData();
			Long valueLong = theData.getOrderNumberEachMonth().get(realMonth);
			newData.setName(realMonth + "月");
			newData.setValue(valueLong == null?0:valueLong.intValue());
			eachMonthOrderTrendChartData.add(newData);
		}
		
	}
	
	private void initProjectForm() {
		
		Label label1 = new Label("图书馆信息");
		label1.setStyleName("alex_header_label");
		label1.setHeight(20);
		label1.setWidth("100%");
		vLayout1.addMember(label1);
		
		DynamicForm formProject = new DynamicForm();
		formProject.setNumCols(4);
		
		StaticTextItem progressItem = new StaticTextItem("totalBookNumber", "图书总量");
		StaticTextItem statusItem = new StaticTextItem("inStoreBookNumber", "库中数量");
		
		StaticTextItem startDateItem = new StaticTextItem("avaliableBookNumber", "可借数量");
		StaticTextItem planNumItem = new StaticTextItem("remainingRecommendNumber", "剩余推荐");

		StaticTextItem expected1DateItem = new StaticTextItem("nowOverdueBorrowNumber", "当前超期数量");
		StaticTextItem finishDate1Item = new StaticTextItem("totalLostBookNumber", "总丢失数量");		
		StaticTextItem expectedDateItem = new StaticTextItem("nowOverdueRate", "当前超期率");
		StaticTextItem finishDateItem = new StaticTextItem("totalLostBookRate", "总丢失率");		
		
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
		
		formProject.setValue("totalBookNumber", theData.getTotalBookNumber()+"本");
		formProject.setValue("inStoreBookNumber", theData.getInStoreBookNumber()+"本");
		formProject.setValue("avaliableBookNumber", theData.getAvaliableBookNumber()+"本");
		formProject.setValue("remainingRecommendNumber", theData.getRemainingRecommendNumber()+"本");
		formProject.setValue("nowOverdueBorrowNumber", theData.getNowOverdueBorrowNumber()+"本");
		formProject.setValue("totalLostBookNumber", theData.getTotalLostBookNumber()+"本");
		formProject.setValue("nowOverdueRate", theData.getNowOverdueRate()*100+"%");
		formProject.setValue("totalLostBookRate", theData.getTotalLostBookRate()*100+"%");
		
	}
	
	private void initPlanChart() {
		
		HLayout headHLayout = new HLayout();
		Label label2 = new Label("近一年各部门借阅、预订统计");
		label2.setStyleName("alex_header_label");
		label2.setHeight(20);
		label2.setWidth("240px");
		
		ListBox selectTypeBox = new ListBox();
		selectTypeBox.addItem("饼图", "饼图");
		selectTypeBox.addItem("柱状图", "柱状图");
		selectTypeBox.setVisibleItemCount(1);
		selectTypeBox.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange(ChangeEvent event) {
				
				if (((ListBox)(event.getSource())).getValue(((ListBox)(event.getSource())).getSelectedIndex()).equals("饼图")) {
					eachTeamBorrowChart.setChartData(SLChartHelper.getAniPieChartData("借阅比重", "本", eachTeamBorrowChartData));
					eachTeamOrderChart.setChartData(SLChartHelper.getAniPieChartData("预订比重", "本", eachTeamOrderChartData));
				} else {
					eachTeamBorrowChart.setChartData(SLChartHelper.getBarChartTransparentData("借阅比重", "本", eachTeamBorrowChartData));
					eachTeamOrderChart.setChartData(SLChartHelper.getBarChartTransparentData("预订比重", "本", eachTeamOrderChartData));
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
		
		eachTeamBorrowChart = new ChartWidget();
		eachTeamBorrowChart.setSize("300px", "200px");
		eachTeamBorrowChart.setChartData(SLChartHelper.getAniPieChartData("借阅比重", "本", eachTeamBorrowChartData));
		
		eachTeamOrderChart = new ChartWidget();
		eachTeamOrderChart.setSize("300px", "200px");
		eachTeamOrderChart.setChartData(SLChartHelper.getAniPieChartData("预订比重", "本", eachTeamOrderChartData));

		hLayout1.addChild(eachTeamBorrowChart);
		hLayout2.addChild(eachTeamOrderChart);
		hLayout.setMembers(hLayout1, hLayout2);
		
	}
	
	private void initSvnChart() {
		
		HLayout headHLayout = new HLayout();
		Label label2 = new Label("近一年借阅、预订走势统计");
		label2.setStyleName("alex_header_label");
		label2.setHeight(20);
		label2.setWidth("240px");

		final ListBox selectTypeBox2 = new ListBox();
		
		selectTypeBox2.addItem("折线图", "折线图");
		selectTypeBox2.addItem("柱状图", "柱状图");
		selectTypeBox2.addChangeHandler(new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				
				if (((ListBox)(event.getSource())).getValue(((ListBox)(event.getSource())).getSelectedIndex()).equals("折线图")) {
						eachMonthTrendChart.setChartData(SLChartHelper.getLineChartData("借阅、预订量","",  "本",chartSvnDataList));
				} else {
						eachMonthTrendChart.setChartData(SLChartHelper.getSketchChartData("借阅、预订量", "本", eachMonthBorrowTrendChartData, eachMonthOrderTrendChartData));
				}
			}
		});
		
		headHLayout.addMember(label2);
		headHLayout.addMember(selectTypeBox2);
		
		vLayout3.addMember(headHLayout);
		
		final HLayout hLayout = new HLayout();
		hLayout.setHeight("200px");
		vLayout3.addMember(hLayout);
		
		chartSvnDataList = new ArrayList<ArrayList<SLChartData>>();
		chartSvnDataList.add(eachMonthBorrowTrendChartData);
		chartSvnDataList.add(eachMonthOrderTrendChartData);
		
		eachMonthTrendChart = new ChartWidget();
		eachMonthTrendChart.setSize("600px", "200px");
		eachMonthTrendChart.setChartData(SLChartHelper.getLineChartData("借阅量", "月借阅量","本", chartSvnDataList));
		
		hLayout.addChild(eachMonthTrendChart);

		
	}
	
}
