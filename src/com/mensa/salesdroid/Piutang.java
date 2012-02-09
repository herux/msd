/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * Created: 06.02.2012	
 */

package com.mensa.salesdroid;

public class Piutang {

	private String site;
	private String divisi;
	private String rayon_sales;
	private String customerid;
	private String invoice_no;
	private String invoice_date;
	private String due_date;
	private double invoice_amount;
	private double open_amount;

	public Piutang(String site, String divisi, String rayon_sales,
			String customerid, String invoice_no, String invoice_date,
			String due_date, double invoice_amount, double open_amount) {
		this.site =site;
		this.divisi=divisi;
		this.rayon_sales=rayon_sales;
		this.customerid=customerid;
		this.invoice_no=invoice_no;
		this.invoice_date=invoice_date;
		this.due_date=due_date;
		this.invoice_amount=invoice_amount;
		this.open_amount=open_amount;
	}

	public double getOpen_amount() {
		return open_amount;
	}

	public void setOpen_amount(double open_amount) {
		this.open_amount = open_amount;
	}

	public double getInvoice_amount() {
		return invoice_amount;
	}

	public void setInvoice_amount(double invoice_amount) {
		this.invoice_amount = invoice_amount;
	}

	public String getDue_date() {
		return due_date;
	}

	public void setDue_date(String due_date) {
		this.due_date = due_date;
	}

	public String getInvoice_date() {
		return invoice_date;
	}

	public void setInvoice_date(String invoice_date) {
		this.invoice_date = invoice_date;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getDivisi() {
		return divisi;
	}

	public void setDivisi(String divisi) {
		this.divisi = divisi;
	}

	public String getRayon_sales() {
		return rayon_sales;
	}

	public void setRayon_sales(String rayon_sales) {
		this.rayon_sales = rayon_sales;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getInvoice_no() {
		return invoice_no;
	}

	public void setInvoice_no(String invoice_no) {
		this.invoice_no = invoice_no;
	}

}
