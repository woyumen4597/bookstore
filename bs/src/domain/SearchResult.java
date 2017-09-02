package domain;

import java.util.List;

public class SearchResult {
	private List<SearchBook> bookList;
	private long recordCount;
	private long pageCount;
	private long curPage;
	public List<SearchBook> getBookList() {
		return bookList;
	}
	public void setBookList(List<SearchBook> bookList) {
		this.bookList = bookList;
	}
	public long getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(long recordCount) {
		this.recordCount = recordCount;
	}
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public long getCurPage() {
		return curPage;
	}
	public void setCurPage(long curPage) {
		this.curPage = curPage;
	}
}
