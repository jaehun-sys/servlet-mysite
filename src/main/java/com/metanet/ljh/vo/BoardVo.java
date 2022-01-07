package com.metanet.ljh.vo;

public class BoardVo {
	
	private int rowno;
	private int no;
	private String title;
	private String content;
	private int hit;
	private String regdate;
	private int userno;
	private String name;

	public BoardVo() {
		super();
	}

	public BoardVo(String title, String content, int userno) {
		super();
		this.title = title;
		this.content = content;
		this.userno = userno;
	}

	public BoardVo(int no, String title, String content) {
		super();
		this.no = no;
		this.title = title;
		this.content = content;
	}

	public int getRowno() {
		return rowno;
	}
	
	public void setRowno(int rowno) {
		this.rowno = rowno;
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

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getUserno() {
		return userno;
	}

	public void setUserno(int userno) {
		this.userno = userno;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "BoardVo [no=" + no + ", rowno=" + rowno + ", title=" + title + ", content=" + content + ", hit=" + hit + ", regdate="
				+ regdate + ", userno=" + userno + ", name=" + name + "]";
	}

	
}
