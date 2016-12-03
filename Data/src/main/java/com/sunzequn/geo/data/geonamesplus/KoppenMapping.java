package com.sunzequn.geo.data.geonamesplus;

/**
 * 
 * @author sunzequn
 *
 */
public class KoppenMapping {

    private String koppentype;
    private String zhongxuetype;
    private String description;
    private String latconstraint;
    private String lngconstraint;

    public KoppenMapping() {
    }

    public KoppenMapping(String koppentype, String zhongxuetype, String description) {
        this.koppentype = koppentype;
        this.zhongxuetype = zhongxuetype;
        this.description = description;
    }

    public String getKoppentype() {
        return koppentype;
    }

    public void setKoppentype(String koppentype) {
        this.koppentype = koppentype;
    }

    public String getZhongxuetype() {
        return zhongxuetype;
    }

    public void setZhongxuetype(String zhongxuetype) {
        this.zhongxuetype = zhongxuetype;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

	public String getLatconstraint() {
		return latconstraint;
	}

	public void setLatconstraint(String latconstraint) {
		this.latconstraint = latconstraint;
	}

	public String getLngconstraint() {
		return lngconstraint;
	}

	public void setLngconstraint(String lngconstraint) {
		this.lngconstraint = lngconstraint;
	}

	@Override
	public String toString() {
		return "KoppenMapping [koppentype=" + koppentype + ", zhongxuetype=" + zhongxuetype + ", description="
				+ description + ", latconstraint=" + latconstraint + ", lngconstraint=" + lngconstraint + "]";
	}
    
    
}
