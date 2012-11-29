package com.successfactors.library.client.widget;

import static com.successfactors.library.client.SFLibrary.bookService;
import static com.successfactors.library.client.SFLibrary.recommendedBookService;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.smartgwt.client.data.Record;
import com.smartgwt.client.types.Alignment;
import com.smartgwt.client.types.TitleOrientation;
import com.smartgwt.client.util.BooleanCallback;
import com.smartgwt.client.util.SC;
import com.smartgwt.client.widgets.IButton;
import com.smartgwt.client.widgets.Img;
import com.smartgwt.client.widgets.Window;
import com.smartgwt.client.widgets.events.ClickEvent;
import com.smartgwt.client.widgets.events.ClickHandler;
import com.smartgwt.client.widgets.form.DynamicForm;
import com.smartgwt.client.widgets.form.fields.FormItemIcon;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.StaticTextItem;
import com.smartgwt.client.widgets.form.fields.TextItem;
import com.smartgwt.client.widgets.form.fields.events.FormItemClickHandler;
import com.smartgwt.client.widgets.form.fields.events.FormItemIconClickEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressEvent;
import com.smartgwt.client.widgets.form.fields.events.KeyPressHandler;
import com.smartgwt.client.widgets.layout.HLayout;
import com.smartgwt.client.widgets.layout.VLayout;
import com.successfactors.library.client.SFLibrary;
import com.successfactors.library.client.datasource.SLRecommendedBookDS;
import com.successfactors.library.client.helper.RPCCall;
import com.successfactors.library.shared.FieldVerifier;
import com.successfactors.library.shared.model.SLBook;
import com.successfactors.library.shared.model.SLRecommendedBook;

public class RecommendedBookWindow extends Window {

	public interface FinishEditBook {
		void doRefreshPage();
		void doAddToBuy(SLRecommendedBook addBook);
	}

	private static final String WINDOW_WIDTH = "660px";
	private static final String WINDOW_HEIGHT = "360px";
	private static final int IMG_HEIGHT = 165;
	private static final int IMG_WIDTH = 116;

	private SLRecommendedBook theRecBook;

	private DynamicForm bookForm1;
	private DynamicForm bookForm3;
	private IButton newButton;
	
	private IButton addToBuyButton;
	private IButton dismissButton;

	private Img bookPicUrlItem;
	private VLayout imgVLayout;
	private FinishEditBook finishEdit = null;

	private String strBookPicUrl = "nopic.jpg";

	public RecommendedBookWindow() {
		super();
		theRecBook = new SLRecommendedBook();
		initNewWindow();
		bind();
	}

	public RecommendedBookWindow(SLRecommendedBook recBook, boolean isEditable,
			FinishEditBook finish) {
		super();
		theRecBook = recBook;
		finishEdit = finish;
		if (isEditable) {
			initEditWindow();
		} else {
			initReadWindow();
		}
		bind();
	}

	private void initNewWindow() {

		this.setAutoSize(true);
		this.setTitle("推荐新书");
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		VLayout vLayout;
		HLayout hLayout;
		HLayout buttonLayout;

		vLayout = new VLayout();
		vLayout.setWidth(WINDOW_WIDTH);
		vLayout.setHeight(WINDOW_HEIGHT);
		vLayout.setBorder("2px solid #7598C7");
		vLayout.setMargin(12);
		vLayout.setPadding(14);

		hLayout = new HLayout();
		hLayout.setWidth(WINDOW_WIDTH);

		// HLayout
		// ---------------------------------------------------------------------------------------
		imgVLayout = new VLayout();
		imgVLayout.setWidth(IMG_WIDTH);
		bookPicUrlItem = new Img("/images/upload/" + strBookPicUrl, IMG_WIDTH,
				IMG_HEIGHT);

		imgVLayout.setMembers(bookPicUrlItem);
		imgVLayout.setMembersMargin(10);

		// Form
		// 1-----------------------------------------------------------------------------------------

		bookForm1 = new DynamicForm();
		bookForm1.setNumCols(4);
		bookForm1.setWidth("*");
		bookForm1.setColWidths(100, "*", 100, "*");
		bookForm1.setCellPadding(5);

		StaticTextItem bookNameItem = new StaticTextItem("bookName", "书名");
		bookNameItem.setColSpan(4);
		bookNameItem.setWidth("100%");
		bookNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookNameItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookAuthorItem = new StaticTextItem("bookAuthor", "作者");
		bookAuthorItem.setColSpan(4);
		bookAuthorItem.setWidth("100%");
		bookAuthorItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookAuthorItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		TextItem bookISBNItem = new TextItem("bookISBN", "ISBN");
		bookISBNItem.setColSpan(4);
		bookISBNItem.setWidth("100%");
		bookISBNItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookISBNItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		bookISBNItem.setShowIcons(true);
		FormItemIcon fetchBookInfoBut = new FormItemIcon();
		fetchBookInfoBut.setSrc("/images/actions/approve.png");
		bookISBNItem.setIcons(fetchBookInfoBut);
		bookISBNItem.addKeyPressHandler(new KeyPressHandler() {
			@Override
			public void onKeyPress(KeyPressEvent event) {
				if (event.getKeyName().equals("Enter")) {
					fetchDataFromDouban();
				}
			}
		});
		fetchBookInfoBut.addFormItemClickHandler(new FormItemClickHandler() {

			@Override
			public void onFormItemClick(FormItemIconClickEvent event) {
				fetchDataFromDouban();
			}
		});
		bookISBNItem.setIconPrompt("从豆瓣获取书籍信息");

		StaticTextItem bookPublisherItem = new StaticTextItem("bookPublisher",
				"出版社");
		bookPublisherItem
				.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPublisherItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookPublishDateItem = new StaticTextItem(
				"bookPublishDate", "出版日期");
		bookPublishDateItem
				.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPublishDateItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		SelectItem bookClassItem = new SelectItem("bookClass", "类别");
		bookClassItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookClassItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		bookClassItem.setValueMap("计算机/网络", "小说/文学", "哲学/文化", "经济/管理", "政治/军事",
				"励志/成长", "儿童/育儿", "心理", "法律", "历史", "其他");
		bookClassItem.setDefaultToFirstOption(true);

		SelectItem bookLanguageItem = new SelectItem("bookLanguage", "语言");
		bookLanguageItem
				.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookLanguageItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		bookLanguageItem.setValueMap("中文", "英语", "法语", "德语", "日语", "俄语", "韩语");
		bookLanguageItem.setDefaultToFirstOption(true);

		StaticTextItem bookPriceItem = new StaticTextItem("bookPrice", "价格");
		bookPriceItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPriceItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		bookForm1.setFields(bookISBNItem, bookNameItem, bookAuthorItem,
				bookPublisherItem, bookPublishDateItem, bookClassItem,
				bookLanguageItem, bookPriceItem);

		// Form
		// 3-----------------------------------------------------------------------------------------
		bookForm3 = new DynamicForm();
		bookForm3.setWidth(WINDOW_WIDTH);
		bookForm3.setCellPadding(3);
		// bookForm2.setNumCols(2);
		bookForm3.setTitleOrientation(TitleOrientation.TOP);

		StaticTextItem bookIntroItemTitle = new StaticTextItem(
				"bookIntroTitle", "");
		bookIntroItemTitle
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_title");
		bookIntroItemTitle.setShowTitle(false);

		StaticTextItem bookIntroItem = new StaticTextItem("bookIntro", "");
		bookIntroItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_intro_content");
		bookIntroItem.setShowTitle(false);
		bookIntroItem.setColSpan(2);
		bookIntroItem.setWidth("100%");

		bookForm3.setFields(bookIntroItemTitle, bookIntroItem);

		// buttonLayout
		// --------------------------------------------------------------------------------------
		buttonLayout = new HLayout();
		newButton = new IButton("确认推荐");
		newButton.setIcon("actions/approve.png");
		buttonLayout.setMembers(newButton);
		buttonLayout.setAlign(Alignment.RIGHT);

		hLayout.setMembers(imgVLayout, bookForm1);
		vLayout.setMembers(hLayout, bookForm3, buttonLayout);
		vLayout.setMembersMargin(20);

		bookForm3.setValue("bookIntroTitle", "简介：");

		this.addItem(vLayout);

	}

	// 从豆瓣获取图书信息
	protected void fetchDataFromDouban() {

		theRecBook.setBookISBN(bookForm1.getValueAsString("bookISBN"));
		if (!FieldVerifier.isNotEmptyValid(theRecBook.getBookISBN())) {
			SC.say("请先输入书籍ISBN！");
			return;
		}

		new RPCCall<SLBook>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(SLBook result) {
				if (result == null) {
					SC.say("无此ISBN编号的书籍信息，请检查输入是否正确！");
					return;
				}
				theRecBook = SLRecommendedBook.parse(result, SFLibrary.get()
						.getNowUser());

				bookForm1.setValue("bookName", theRecBook.getBookName());
				bookForm1.setValue("bookAuthor", theRecBook.getBookAuthor());
				bookForm1.setValue("bookISBN", theRecBook.getBookISBN());
				bookForm1.setValue("bookPublisher",
						theRecBook.getBookPublisher());

				bookForm1.setValue("bookPublishDate",
						theRecBook.getBookPublishDate());
				bookForm1.setValue("bookClass", theRecBook.getBookClass());
				bookForm1
						.setValue("bookLanguage", theRecBook.getBookLanguage());
				bookForm1.setValue("bookPrice", theRecBook.getBookPrice());

				bookForm3.setValue("bookIntro", theRecBook.getBookIntro());

			}

			@Override
			protected void callService(AsyncCallback<SLBook> cb) {
				bookService.getBookByDoubanAPI(theRecBook.getBookISBN(), cb);
			}
		}.retry(3);

	}

	private void bind() {

		if (newButton != null)
			newButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					if (!updateBookInfo()) {
						return;
					}
					doRecBook();
				}
			});
		

		if (dismissButton != null)
			dismissButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					SC.ask("忽略推荐", "您确定要忽略此推荐么？", new BooleanCallback() {
						
						@Override
						public void execute(Boolean value) {
							if (value) {
								doDismissRecBook();
							}
						}
					} );
				}
			});
		if (addToBuyButton != null)
			addToBuyButton.addClickHandler(new ClickHandler() {

				@Override
				public void onClick(ClickEvent event) {
					doAddRecBookToBuy();
				}
			});

	}

	private void doDismissRecBook() {
		theRecBook.setRecStatus("已忽略");
		new RPCCall<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result == null || !result) {
					SC.say("修改状态失败，请稍候重试！");
					return;
				}
				SC.say("成功忽略此书！");
				if (finishEdit != null) {
					finishEdit.doRefreshPage();
				}
				destroy();
			}

			@Override
			protected void callService(AsyncCallback<Boolean> cb) {
				recommendedBookService.updateRecommendedBookStatus(theRecBook, cb);
			}
		}.retry(3);
		
	}

	private void doAddRecBookToBuy() {
		if (finishEdit != null) {
			finishEdit.doAddToBuy(theRecBook);
		}
	}

	private void initEditWindow() {
		
		SLRecommendedBookDS theDataSource = new SLRecommendedBookDS();
		Record theRecord = theRecBook.getRecord();
		theDataSource.addData(theRecord);
		
		this.setAutoSize(true);
		this.setTitle("推荐书籍《"+theRecBook.getBookName()+"》");
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		VLayout vLayout;
		HLayout hLayout;
		HLayout buttonLayout;

		vLayout = new VLayout();
		vLayout.setWidth(WINDOW_WIDTH);
		vLayout.setHeight(WINDOW_HEIGHT);
		vLayout.setBorder("2px solid #7598C7");
		vLayout.setMargin(12);
		vLayout.setPadding(14);

		hLayout = new HLayout();
		hLayout.setWidth(WINDOW_WIDTH);

		// HLayout
		// ---------------------------------------------------------------------------------------
		strBookPicUrl = theRecBook.getBookPicUrl();
		imgVLayout = new VLayout();
		imgVLayout.setWidth(IMG_WIDTH);
		bookPicUrlItem = new Img(strBookPicUrl, IMG_WIDTH,
				IMG_HEIGHT);

		imgVLayout.setMembers(bookPicUrlItem);
		imgVLayout.setMembersMargin(10);

		// Form
		// 1-----------------------------------------------------------------------------------------

		bookForm1 = new DynamicForm();
		bookForm1.setNumCols(4);
		bookForm1.setWidth("*");
		bookForm1.setColWidths(100, "*", 100, "*");
		bookForm1.setCellPadding(5);
		bookForm1.setDataSource(theDataSource);

		StaticTextItem bookNameItem = new StaticTextItem("bookName", "书名");
		bookNameItem.setColSpan(4);
		bookNameItem.setWidth("100%");
		bookNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookNameItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookAuthorItem = new StaticTextItem("bookAuthor", "作者");
		bookAuthorItem.setColSpan(4);
		bookAuthorItem.setWidth("100%");
		bookAuthorItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookAuthorItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookISBNItem = new StaticTextItem("bookISBN", "ISBN");
		bookISBNItem.setColSpan(4);
		bookISBNItem.setWidth("100%");
		bookISBNItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookISBNItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");


		StaticTextItem bookPublisherItem = new StaticTextItem("bookPublisher",
				"出版社");
		bookPublisherItem
				.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPublisherItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookPublishDateItem = new StaticTextItem(
				"bookPublishDate", "出版日期");
		bookPublishDateItem
				.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPublishDateItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookClassItem = new StaticTextItem("bookClass", "类别");
		bookClassItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookClassItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookLanguageItem = new StaticTextItem("bookLanguage", "语言");
		bookLanguageItem
				.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookLanguageItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookPriceItem = new StaticTextItem("bookPrice", "价格");
		bookPriceItem.setColSpan(4);
		bookPriceItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPriceItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem recUserNameItem = new StaticTextItem("recUserName", "推荐人");
		recUserNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		recUserNameItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem recStatusItem = new StaticTextItem("recStatus", "推荐状态");
		recStatusItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		recStatusItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem recDateItem = new StaticTextItem("recDate", "推荐日期");
		recDateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		recDateItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem recRateItem = new StaticTextItem("recRate", "推荐热度");
		recRateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		recRateItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		bookForm1.setFields(bookISBNItem, bookNameItem, bookAuthorItem,
				bookPublisherItem, bookPublishDateItem, bookClassItem,
				bookLanguageItem, bookPriceItem, recUserNameItem,
				recStatusItem, recDateItem, recRateItem);

		bookForm1.selectRecord(theRecord);
		bookForm1.fetchData();
		
		// Form
		// 3-----------------------------------------------------------------------------------------
		bookForm3 = new DynamicForm();
		bookForm3.setWidth(WINDOW_WIDTH);
		bookForm3.setCellPadding(3);
		// bookForm2.setNumCols(2);
		bookForm3.setTitleOrientation(TitleOrientation.TOP);

		StaticTextItem bookIntroItemTitle = new StaticTextItem(
				"bookIntroTitle", "");
		bookIntroItemTitle
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_title");
		bookIntroItemTitle.setShowTitle(false);

		StaticTextItem bookIntroItem = new StaticTextItem("bookIntro", "");
		bookIntroItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_intro_content");
		bookIntroItem.setShowTitle(false);
		bookIntroItem.setColSpan(2);
		bookIntroItem.setWidth("100%");

		bookForm3.setFields(bookIntroItemTitle, bookIntroItem);
		bookForm3.setValue("bookIntroTitle", "简介：");
		bookForm3.setValue("bookIntro", theRecBook.getBookIntro());

		// buttonLayout
		// --------------------------------------------------------------------------------------
		buttonLayout = new HLayout();
		dismissButton = new IButton("忽略");
		dismissButton.setIcon("icons/16/delete.png");
		addToBuyButton = new IButton("选购");
		addToBuyButton.setIcon("icons/16/add.png");
		
		buttonLayout.setMembers(addToBuyButton, dismissButton);
		buttonLayout.setAlign(Alignment.RIGHT);

		hLayout.setMembers(imgVLayout, bookForm1);
		vLayout.setMembers(hLayout, bookForm3, buttonLayout);
		vLayout.setMembersMargin(20);

		this.addItem(vLayout);
		
	}

	private void initReadWindow() {
		
		SLRecommendedBookDS theDataSource = new SLRecommendedBookDS();
		Record theRecord = theRecBook.getRecord();
		theDataSource.addData(theRecord);
		
		this.setAutoSize(true);
		this.setTitle("推荐书籍《"+theRecBook.getBookName()+"》");
		this.setCanDragReposition(true);
		this.setCanDragResize(false);
		this.setAutoCenter(true);
		this.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		VLayout vLayout;
		HLayout hLayout;
		HLayout buttonLayout;

		vLayout = new VLayout();
		vLayout.setWidth(WINDOW_WIDTH);
		vLayout.setHeight(WINDOW_HEIGHT);
		vLayout.setBorder("2px solid #7598C7");
		vLayout.setMargin(12);
		vLayout.setPadding(14);

		hLayout = new HLayout();
		hLayout.setWidth(WINDOW_WIDTH);

		// HLayout
		// ---------------------------------------------------------------------------------------
		strBookPicUrl = theRecBook.getBookPicUrl();
		imgVLayout = new VLayout();
		imgVLayout.setWidth(IMG_WIDTH);
		bookPicUrlItem = new Img(strBookPicUrl, IMG_WIDTH,
				IMG_HEIGHT);

		imgVLayout.setMembers(bookPicUrlItem);
		imgVLayout.setMembersMargin(10);

		// Form
		// 1-----------------------------------------------------------------------------------------

		bookForm1 = new DynamicForm();
		bookForm1.setNumCols(4);
		bookForm1.setWidth("*");
		bookForm1.setColWidths(100, "*", 100, "*");
		bookForm1.setCellPadding(5);
		bookForm1.setDataSource(theDataSource);

		StaticTextItem bookNameItem = new StaticTextItem("bookName", "书名");
		bookNameItem.setColSpan(4);
		bookNameItem.setWidth("100%");
		bookNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookNameItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookAuthorItem = new StaticTextItem("bookAuthor", "作者");
		bookAuthorItem.setColSpan(4);
		bookAuthorItem.setWidth("100%");
		bookAuthorItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookAuthorItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookISBNItem = new StaticTextItem("bookISBN", "ISBN");
		bookISBNItem.setColSpan(4);
		bookISBNItem.setWidth("100%");
		bookISBNItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookISBNItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");


		StaticTextItem bookPublisherItem = new StaticTextItem("bookPublisher",
				"出版社");
		bookPublisherItem
				.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPublisherItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookPublishDateItem = new StaticTextItem(
				"bookPublishDate", "出版日期");
		bookPublishDateItem
				.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPublishDateItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookClassItem = new StaticTextItem("bookClass", "类别");
		bookClassItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookClassItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookLanguageItem = new StaticTextItem("bookLanguage", "语言");
		bookLanguageItem
				.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookLanguageItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem bookPriceItem = new StaticTextItem("bookPrice", "价格");
		bookPriceItem.setColSpan(4);
		bookPriceItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		bookPriceItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem recUserNameItem = new StaticTextItem("recUserName", "推荐人");
		recUserNameItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		recUserNameItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");
		
		StaticTextItem recStatusItem = new StaticTextItem("recStatus", "推荐状态");
		recStatusItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		recStatusItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem recDateItem = new StaticTextItem("recDate", "推荐日期");
		recDateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		recDateItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		StaticTextItem recRateItem = new StaticTextItem("recRate", "推荐热度");
		recRateItem.setTitleStyle("alex_bookdisplaywindow_form_text_title");
		recRateItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_content");

		bookForm1.setFields(bookISBNItem, bookNameItem, bookAuthorItem,
				bookPublisherItem, bookPublishDateItem, bookClassItem,
				bookLanguageItem, bookPriceItem, recUserNameItem,
				recStatusItem, recDateItem, recRateItem);

		bookForm1.selectRecord(theRecord);
		bookForm1.fetchData();
		
		// Form
		// 3-----------------------------------------------------------------------------------------
		bookForm3 = new DynamicForm();
		bookForm3.setWidth(WINDOW_WIDTH);
		bookForm3.setCellPadding(3);
		// bookForm2.setNumCols(2);
		bookForm3.setTitleOrientation(TitleOrientation.TOP);

		StaticTextItem bookIntroItemTitle = new StaticTextItem(
				"bookIntroTitle", "");
		bookIntroItemTitle
				.setTextBoxStyle("alex_bookdisplaywindow_form_text_title");
		bookIntroItemTitle.setShowTitle(false);

		StaticTextItem bookIntroItem = new StaticTextItem("bookIntro", "");
		bookIntroItem
				.setTextBoxStyle("alex_bookdisplaywindow_form_intro_content");
		bookIntroItem.setShowTitle(false);
		bookIntroItem.setColSpan(2);
		bookIntroItem.setWidth("100%");

		bookForm3.setFields(bookIntroItemTitle, bookIntroItem);
		bookForm3.setValue("bookIntroTitle", "简介：");
		bookForm3.setValue("bookIntro", theRecBook.getBookIntro());

		// buttonLayout
		// --------------------------------------------------------------------------------------
		buttonLayout = new HLayout();
		newButton = new IButton("推荐 +1");
		newButton.setIcon("actions/approve.png");
		buttonLayout.setMembers(newButton);
		buttonLayout.setAlign(Alignment.RIGHT);

		hLayout.setMembers(imgVLayout, bookForm1);
		vLayout.setMembers(hLayout, bookForm3, buttonLayout);
		vLayout.setMembersMargin(20);

		this.addItem(vLayout);

	}

	protected void doRecBook() {
		new RPCCall<Boolean>() {
			@Override
			public void onFailure(Throwable caught) {
				SC.say("通信失败，请检查您的网络连接！");
			}

			@Override
			public void onSuccess(Boolean result) {
				if (result == null || !result) {
					SC.say("您推荐的书已收入库中！");
					return;
				}
				SC.say("推荐成功！");
				if (finishEdit != null) {
					finishEdit.doRefreshPage();
				}
				destroy();

			}

			@Override
			protected void callService(AsyncCallback<Boolean> cb) {
				recommendedBookService.recommendBook(theRecBook, cb);
			}
		}.retry(3);
	}

	private boolean updateBookInfo() {

		if (SFLibrary.get().getNowUser() == null) {
			SC.say("请先登录！");
			return false;
		}
		
		theRecBook
				.setBookPublisher(bookForm1.getValueAsString("bookPublisher"));
		theRecBook.setBookClass(bookForm1.getValueAsString("bookClass"));
		theRecBook.setBookLanguage(bookForm1.getValueAsString("bookLanguage"));

		if (!FieldVerifier.isNotEmptyValid(theRecBook.getBookISBN())) {
			SC.say("请先输入图书ISBN，并获取图书信息！");
			return false;
		}
		return true;
	}
}
