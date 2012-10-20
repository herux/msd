/**
 * author : herux
 * email  : heruxi@gmail.com
 * site   : http://www.herux.com
 * blogs  : http://herux.wordpress.com	
 * Created: 07.02.2012	
 */


package com.mensa.salesdroid;

import java.util.ArrayList;

public class Piutangs extends BaseDataListObj  {
	
	private ArrayList<Piutang> piutangs;
	
	public Piutangs() {
		setPiutangs(new ArrayList<Piutang>());
	}
	
	public void AddPiutang(Piutang piutang){
		piutangs.add(piutang);
	}

	public ArrayList<Piutang> getPiutangs() {
		return piutangs;
	}

	public void setPiutangs(ArrayList<Piutang> piutangs) {
		this.piutangs = piutangs;
	}

}
