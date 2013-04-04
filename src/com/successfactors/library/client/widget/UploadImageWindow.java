package com.successfactors.library.client.widget;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FileUpload;
import com.google.gwt.user.client.ui.FormHandler;
import com.google.gwt.user.client.ui.FormPanel;
import com.google.gwt.user.client.ui.FormSubmitCompleteEvent;
import com.google.gwt.user.client.ui.FormSubmitEvent;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.types.Positioning;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.shared.ServerInfo;

@SuppressWarnings("deprecation")
public class UploadImageWindow extends Window {

	public interface FinishUploadOperatable {
		void doAfterFinishUpload(String picName);
	}
	
	private static final String WINDOW_WIDTH = "405px";
	private static final String WINDOW_HEIGHT = "180px";
	private static final int iWINDOW_WIDTH = 405;
	private static final int iWINDOW_HEIGHT = 180;
	
	
	public static final String UPLOADED_IMAGES_PATH = ServerInfo.SERVRE_ADDRESS+"/images/upload/";

	public VLayout allVLayout = new VLayout();

	//------------------------------------------------
	private FormPanel formPanel_small;
	private FileUpload fileUpload_small;
	private VerticalPanel vPanel_Inner_small;
	private Button uploadButton_small;
	private Image stateImg_small;
	private boolean isUploaded;
	private VerticalPanel vLayout_small;
	private String picName;
	//------------------------------------------------

	
	private FinishUploadOperatable finishUpload;

	public UploadImageWindow(FinishUploadOperatable finish) {
		super();
		finishUpload = finish;
		initNewWindow();
	}

	private void initNewWindow() {
		this.setAutoSize(false);
		this.setTitle("图片上传");
		this.setCanDragReposition(true);
		this.setCanDragResize(true);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
		this.setMinWidth(iWINDOW_WIDTH);
		this.setMinHeight(iWINDOW_HEIGHT);
		this.setPadding(10);

		// -----------------------------------------------------------------------------------
		Label title_smaill = new Label("请选择：");
		title_smaill.setStyleName("alex_upload_title");
		
		
		this.vLayout_small = new VerticalPanel();
		this.formPanel_small = new FormPanel();
		this.vPanel_Inner_small = new VerticalPanel();
		
		this.isUploaded = false;
		this.fileUpload_small = new FileUpload();
		this.uploadButton_small = new Button("上传");
		this.stateImg_small = new Image();
		
		this.uploadButton_small.setWidth("220px");

		formPanel_small.setAction("/pictureupload");
		formPanel_small.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel_small.setMethod(FormPanel.METHOD_POST);
		formPanel_small.setWidget(vPanel_Inner_small);

		fileUpload_small.setName("file");
		fileUpload_small.setStyleName("alex_fileupload");
		
		vPanel_Inner_small.add(fileUpload_small);

		this.vLayout_small.setSize("240px", "100px");

		this.vLayout_small.add(title_smaill);
		this.vLayout_small.add(formPanel_small);
		this.vLayout_small.add(stateImg_small);
		this.vLayout_small.add(uploadButton_small);
		this.vLayout_small.setStyleName("alex_upload_panel");

		// -----------------------------------------------------------------------------------

		allVLayout.addMember(vLayout_small);
		allVLayout.setPosition(Positioning.RELATIVE);
		this.addChild(allVLayout);
		
		bind();
	}

	private void bind() {
		this.uploadButton_small.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String temp = fileUpload_small.getFilename();
				// 未选择文件
				if (temp == null || temp.length() < 1)
					return;
				formPanel_small.submit();
				stateImg_small.setUrl("/images/loading_upload_file.gif");

			}
		});
		formPanel_small.addFormHandler(new FormHandler() {
			public void onSubmitComplete(FormSubmitCompleteEvent event) {
				picName = event.getResults();// 服务端的返回值
				stateImg_small.setUrl("/images/Mark.gif");
				isUploaded = true;
				if (finishUpload!=null) {
					finishUpload.doAfterFinishUpload(UPLOADED_IMAGES_PATH + picName);
				}
			}

			public void onSubmit(FormSubmitEvent event) {
			}
		});
	}

	public boolean isUploaded() {
		return isUploaded;
	}

	public void setUploaded(boolean isUploaded) {
		this.isUploaded = isUploaded;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

}
