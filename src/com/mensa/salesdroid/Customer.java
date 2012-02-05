/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com		
 * Created: 16.11.2011
 */

package com.mensa.salesdroid;

//"master_customer":[
//{
//"CUSTOMER_ID":"BDG1",
//"NAMA":"PT. MBS - Bandung",
//"CGROUP":"INT",
//"CUSTOMER_CHAIN":null,
//"NAMA_KIRIM":"PT. MBS - Bandung",
//"NAMA_TAGIHAN":"PT. MBS - Bandung",
//"ALAMAT_KIRIM":"JL. IBRAHIM ADJIE NO.358BANDUNG 40284",
//"ALAMAT_TAGIHAN":"JL. IBRAHIM ADJIE NO.358BANDUNG 40284",
//"KOORDINAT":null
//},

public class Customer {
	private String customerid, 
		customername, 
		cgroup, 
		customerchain,
		namakirim,
		namatagihan,
		alamatkirim,
		alamattagihan,
		koordinat;
	
	public Customer(String customerid, 
			String customername, 
			String cgroup, 
			String customerchain,
			String namakirim,
			String namatagihan,
			String alamatkirim,
			String alamattagihan,
			String koordinat) {
		
		this.customerid = customerid; 
		this.customername = customername; 
		this.cgroup = cgroup; 
		this.customerchain = customerchain;
		this.namakirim = namakirim;
		this.namatagihan = namatagihan;
		this.alamatkirim = alamatkirim;
		this.alamattagihan = alamattagihan;
		this.koordinat = koordinat;
	}

	public String getCustomerid() {
		return customerid;
	}

	public void setCustomerid(String customerid) {
		this.customerid = customerid;
	}

	public String getCustomername() {
		return customername;
	}

	public void setCustomername(String customername) {
		this.customername = customername;
	}

	public String getCgroup() {
		return cgroup;
	}

	public void setCgroup(String cgroup) {
		this.cgroup = cgroup;
	}

	public String getCustomerchain() {
		return customerchain;
	}

	public void setCustomerchain(String customerchain) {
		this.customerchain = customerchain;
	}

	public String getNamakirim() {
		return namakirim;
	}

	public void setNamakirim(String namakirim) {
		this.namakirim = namakirim;
	}

	public String getKoordinat() {
		return koordinat;
	}

	public void setKoordinat(String koordinat) {
		this.koordinat = koordinat;
	}

	public String getAlamattagihan() {
		return alamattagihan;
	}

	public void setAlamattagihan(String alamattagihan) {
		this.alamattagihan = alamattagihan;
	}
	
	public String getAlamatkirim() {
		return alamatkirim;
	}
	
	public void setAlamatkirim(String alamatkirim) {
		this.alamatkirim = alamatkirim;
	}
	
	public String getNamatagihan() {
		return namatagihan;
	}
	
	public void setNamatagihan(String namatagihan) {
		this.namatagihan = namatagihan;
	}
	
	
}
