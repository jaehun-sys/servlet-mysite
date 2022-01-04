package mysite.vo;

public class BoardVo {
	
	private int no;
	private String title;
	private String content;
	private int hit;
	private String date;
	private int userno;

	
	public BoardVo() {
		super();
	}

	public BoardVo(String title, String content, int userno) {
		super();
		this.title = title;
		this.content = content;
		this.userno = userno;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getHit() {
		return hit;
	}

	public void setHit(int hit) {
		this.hit = hit;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getUserno() {
		return userno;
	}

	public void setUserno(int userno) {
		this.userno = userno;
	}
	
	
	
}
