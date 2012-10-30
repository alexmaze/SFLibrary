/*
 * $Id$
 */
package com.successfactors.library.shared;

public enum BookSearchType {
	/**
	 * book name
	 */
	BOOKNAME,
	/**
	 * book author
	 */
	BOOKAUTHOR,
	/**
	 * book publisher
	 */
	BOOKPUBLISHER,
	/**
	 * book intro
	 */
	BOOKINTRO,
	/**
	 * book contributor
	 */
	BOOKCONTRIBUTOR,
	/**
	 * book class
	 */
	BOOKCLASS,
	/**
	 * book language
	 */
	BOOKLANGUAGE;

	public static BookSearchType parse(String strType) {

		if (strType.equalsIgnoreCase("书名")) {
			return BookSearchType.BOOKNAME;
		} else if (strType.equalsIgnoreCase("作者")) {
			return BookSearchType.BOOKAUTHOR;
		} else if (strType.equalsIgnoreCase("出版社")) {
			return BookSearchType.BOOKPUBLISHER;
		} else if (strType.equalsIgnoreCase("简介")) {
			return BookSearchType.BOOKINTRO;
		} else if (strType.equalsIgnoreCase("贡献者")) {
			return BookSearchType.BOOKCONTRIBUTOR;
		} else if (strType.equalsIgnoreCase("类别")) {
			return BookSearchType.BOOKCLASS;
		} else if (strType.equalsIgnoreCase("语言")) {
			return BookSearchType.BOOKLANGUAGE;
		}

		return null;
	}
}
