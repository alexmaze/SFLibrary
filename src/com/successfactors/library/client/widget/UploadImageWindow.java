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
import com.google.gwt.user.client.ui.VerticalPanel;
import com.smartgwt.client.widgets.Window;

@SuppressWarnings("deprecation")
public class UploadImageWindow extends Window {

	public interface FinishUploadOperatable {
		void doAfterFinishUpload(String picName);
	}
	
	private static final String WINDOW_WIDTH = "300px";
	private static final String WINDOW_HEIGHT = "150px";
	
	public static final String UPLOADED_IMAGES_PATH = "http://192.168.25.194/images/upload/";

	private FormPanel formPanel;
	private FileUpload fileUpload;

	private VerticalPanel vPanel_Inner;

	private Button uploadButton;
	private Image stateImg;
	private boolean isUploaded;

	private VerticalPanel vLayout;

	private String picName;
	
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
		this.setPadding(10);

		this.vLayout = new VerticalPanel();
		this.formPanel = new FormPanel();
		this.vPanel_Inner = new VerticalPanel();
		
		this.isUploaded = false;
		this.fileUpload = new FileUpload();
		this.uploadButton = new Button("上传");
		this.stateImg = new Image();
		
		this.uploadButton.setWidth("220px");

		formPanel.setAction("/pictureupload");
		formPanel.setEncoding(FormPanel.ENCODING_MULTIPART);
		formPanel.setMethod(FormPanel.METHOD_POST);
		formPanel.setWidget(vPanel_Inner);

		fileUpload.setName("file");
		fileUpload.setStyleName("alex_fileupload");
		vPanel_Inner.add(fileUpload);

		this.addChild(vLayout);

		this.vLayout.setSize("240px", "100px");
		
		this.vLayout.add(formPanel);
		this.vLayout.add(stateImg);
		this.vLayout.add(uploadButton);
		this.vLayout.setStyleName("alex_upload_panel");

		bind();
	}

	private void bind() {
		this.uploadButton.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				String temp = fileUpload.getFilename();
				// 未选择文件
				if (temp == null || temp.length() < 1)
					return;
				formPanel.submit();
				stateImg.setUrl("/images/loading_upload_file.gif");

			}
		});
		formPanel.addFormHandler(new FormHandler() {
			public void onSubmitComplete(FormSubmitCompleteEvent event) {
				picName = event.getResults();// 服务端的返回值
				stateImg.setUrl("/images/Mark.gif");
				isUploaded = true;
				finishUpload.doAfterFinishUpload(UPLOADED_IMAGES_PATH + picName);
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
