package mysite.vo;

public class Paging {
	
	private int page=1;	//현재 페이지
	private int totalRow;	//row의 전체 수
	private int beginPage;	//시작 출력
	private int endPage;	//끝 출력
	private int displayRow = 7;	//한 페이지에 몇 개 row?
	private int displayPage = 5;	//한 페이지에 몇 개 페이지 버튼?
	boolean prev;	//prev 버튼 보이는 여부
	boolean next;	//next 버튼 보이는 여부
	
	public Paging() {
		super();
		//paging();
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalRow() {
		return totalRow;
	}
	public void setTotalRow(int totalRow) {
		this.totalRow = totalRow;
	}
	public int getBeginPage() {
		return beginPage;
	}
	public int getEndPage() {
		return endPage;
	}
	public int getDisplayRow() {
		return displayRow;
	}
	public void setDisplayRow(int displayRow) {
		this.displayRow = displayRow;
	}
	public int getDisplayPage() {
		return displayPage;
	}
	public void setDisplayPage(int displayPage) {
		this.displayPage = displayPage;
	}
	public boolean isPrev() {
		return prev;
	}
	public boolean isNext() {
		return next;
	}
	
	public void paging() {
		System.out.println("==여기는 paging()메소드==");
		endPage = ((int)Math.ceil(page/(double)displayPage))*displayPage;
        System.out.println("endPage : " + endPage);
	        
        beginPage = endPage - (displayPage - 1);
        System.out.println("beginPage : " + beginPage);

        int totalPage = (int)Math.ceil(totalRow/(double)displayRow);
        System.out.println("totalPage: " + totalPage);
        
        if(totalPage < endPage) {
        	endPage = totalPage;
        	next = false;
        } else {
        	next = true;
        }
        
        prev = (beginPage==1) ? false : true;	//page가 displayPage보다 클 때만 true
        System.out.println("beginPage: " + beginPage);
        System.out.println("endPage: " + endPage);
	}
}
