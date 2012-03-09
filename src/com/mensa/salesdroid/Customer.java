/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com		
 * Created: 16.11.2011
 */

package com.mensa.salesdroid;

//{
//	"CB_NOMOR":"P2011FR07005",
//	"CABANG":"JKT2",
//	"PERSON_ID":"JKT2-FR-RUB",
//	"RAYON":"RS-FR-07",
//	"PERIODE":"2012-02",
//	"DIVISI":"1",
//	"CUSTOMER_CODE":"JKT2-0182",
//	"NAMA":"HERMINA, RSIA",
//	"KLAS":"C",
//	"VALUE_GUIDE":"90357888.25",
//	"ALAMAT_KIRIM":"JL. JATINEGARA BARAT NO.126 KAMPUNG MELAYU - JATINEGARA JAKARTA TIMUR",
//	"KOTA":"JAKARTA TIMUR",
//	"CREDIT_LIMIT":"1169144000",
//	"PIUTANG":"267506835"
//	}

public class Customer {
	private String CB_NOMOR;
	private String CABANG;
	private String PERSON_ID;
	private String RAYON;
	private String PERIODE;
	private String DIVISI;
	private String CUSTOMER_CODE;
	private String NAMA;
	private String KLAS;
	private double VALUE_GUIDE;
	private String ALAMAT_KIRIM;
	private String KOTA;
	private double CREDIT_LIMIT;
	private double PIUTANG;
	private String COORDINATE;

	public Customer(String CB_NOMOR, String CABANG, String PERSON_ID,
			String RAYON, String PERIODE, String DIVISI, String CUSTOMER_CODE,
			String NAMA, String KLAS, double VALUE_GUIDE, String ALAMAT_KIRIM,
			String KOTA, double CREDIT_LIMIT, double PIUTANG, String COORDINATE) {

		this.setCB_NOMOR(CB_NOMOR);
		this.setCABANG(CABANG);
		this.setPERSON_ID(PERSON_ID);
		this.setRAYON(RAYON);
		this.setPERIODE(PERIODE);
		this.setDIVISI(DIVISI);
		this.setCUSTOMER_CODE(CUSTOMER_CODE);
		this.setNAMA(NAMA);
		this.setKLAS(KLAS);
		this.setVALUE_GUIDE(VALUE_GUIDE);
		this.setALAMAT_KIRIM(ALAMAT_KIRIM);
		this.setKOTA(KOTA);
		this.setCREDIT_LIMIT(CREDIT_LIMIT);
		this.setPIUTANG(PIUTANG);
		this.setCOORDINATE(COORDINATE);
	}

	public String getCB_NOMOR() {
		return CB_NOMOR;
	}

	public void setCB_NOMOR(String cB_NOMOR) {
		CB_NOMOR = cB_NOMOR;
	}

	public String getCABANG() {
		return CABANG;
	}

	public void setCABANG(String cABANG) {
		CABANG = cABANG;
	}

	public double getVALUE_GUIDE() {
		return VALUE_GUIDE;
	}

	public void setVALUE_GUIDE(double vALUE_GUIDE) {
		VALUE_GUIDE = vALUE_GUIDE;
	}

	public String getPERSON_ID() {
		return PERSON_ID;
	}

	public void setPERSON_ID(String pERSON_ID) {
		PERSON_ID = pERSON_ID;
	}

	public String getRAYON() {
		return RAYON;
	}

	public void setRAYON(String rAYON) {
		RAYON = rAYON;
	}

	public String getPERIODE() {
		return PERIODE;
	}

	public void setPERIODE(String pERIODE) {
		PERIODE = pERIODE;
	}

	public String getDIVISI() {
		return DIVISI;
	}

	public void setDIVISI(String dIVISI) {
		DIVISI = dIVISI;
	}

	public String getCUSTOMER_CODE() {
		return CUSTOMER_CODE;
	}

	public void setCUSTOMER_CODE(String cUSTOMER_CODE) {
		CUSTOMER_CODE = cUSTOMER_CODE;
	}

	public String getNAMA() {
		return NAMA;
	}

	public void setNAMA(String nAMA) {
		NAMA = nAMA;
	}

	public String getKLAS() {
		return KLAS;
	}

	public void setKLAS(String kLAS) {
		KLAS = kLAS;
	}

	public String getALAMAT_KIRIM() {
		return ALAMAT_KIRIM;
	}

	public void setALAMAT_KIRIM(String aLAMAT_KIRIM) {
		ALAMAT_KIRIM = aLAMAT_KIRIM;
	}

	public String getKOTA() {
		return KOTA;
	}

	public void setKOTA(String kOTA) {
		KOTA = kOTA;
	}

	public double getCREDIT_LIMIT() {
		return CREDIT_LIMIT;
	}

	public void setCREDIT_LIMIT(double cREDIT_LIMIT) {
		CREDIT_LIMIT = cREDIT_LIMIT;
	}

	public double getPIUTANG() {
		return PIUTANG;
	}

	public void setPIUTANG(double pIUTANG) {
		PIUTANG = pIUTANG;
	}

	public String getCOORDINATE() {
		return COORDINATE;
	}

	public void setCOORDINATE(String cOORDINATE) {
		COORDINATE = cOORDINATE;
	}

}
