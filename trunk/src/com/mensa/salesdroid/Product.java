/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * Created: 16.11.2011	
 */

package com.mensa.salesdroid;

public class Product {
	private String CONTRACT, DIV, PART_NO, DESCRIPTION, LOCATION_NO,
			LOT_BATCH_NO;
	private float QTY_ONHAND, QTY_RESERVED;
	private double PRICE;
	private String fileSource;

	public Product(String ACONTRACT, String ADIV, String APART_NO,
			String ADESCRIPTION, String ALOCATION_NO, String ALOT_BATCH_NO,
			float AQTY_ONHAND, float AQTY_RESERVED, double APRICE) {
		CONTRACT = ACONTRACT;
		DIV = ADIV;
		PART_NO = APART_NO;
		DESCRIPTION = ADESCRIPTION;
		LOCATION_NO = ALOCATION_NO;
		LOT_BATCH_NO = ALOT_BATCH_NO;
		QTY_ONHAND = AQTY_ONHAND;
		QTY_RESERVED = AQTY_RESERVED;
		PRICE = APRICE;
	}

	public String getCONTRACT() {
		return CONTRACT;
	}

	public void setCONTRACT(String cONTRACT) {
		CONTRACT = cONTRACT;
	}

	public String getDIV() {
		return DIV;
	}

	public void setDIV(String dIV) {
		DIV = dIV;
	}

	public String getPART_NO() {
		return PART_NO;
	}

	public void setPART_NO(String pART_NO) {
		PART_NO = pART_NO;
	}

	public void setDESCRIPTION(String dESCRIPTION) {
		DESCRIPTION = dESCRIPTION;
	}

	public String getDESCRIPTION() {
		return DESCRIPTION;
	}

	public String getLOCATION_NO() {
		return LOCATION_NO;
	}

	public void setLOCATION_NO(String lOCATION_NO) {
		LOCATION_NO = lOCATION_NO;
	}

	public String getLOT_BATCH_NO() {
		return LOT_BATCH_NO;
	}

	public void setLOT_BATCH_NO(String lOT_BATCH_NO) {
		LOT_BATCH_NO = lOT_BATCH_NO;
	}

	public float getQTY_ONHAND() {
		return QTY_ONHAND;
	}

	public void setQTY_ONHAND(float qTY_ONHAND) {
		QTY_ONHAND = qTY_ONHAND;
	}

	public float getQTY_RESERVED() {
		return QTY_RESERVED;
	}

	public void setQTY_RESERVED(float qTY_RESERVED) {
		QTY_RESERVED = qTY_RESERVED;
	}

	public double getPRICE() {
		return PRICE;
	}

	public void setPRICE(double pRICE) {
		PRICE = pRICE;
	}

	public String getFileSource() {
		return fileSource;
	}

	public void setFileSource(String fileSource) {
		this.fileSource = fileSource;
	}

}
