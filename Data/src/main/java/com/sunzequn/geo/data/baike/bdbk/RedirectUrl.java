package com.sunzequn.geo.data.baike.bdbk;

/**
 * Created by sunzequn on 2016/4/27.
 */
public class RedirectUrl {

    private String url;
    private String redirect_url;
    private String type;

    public RedirectUrl() {
    }

    public RedirectUrl(String url, String redirect_url, String type) {
        this.url = url;
        this.redirect_url = redirect_url;
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRedirect_url() {
        return redirect_url;
    }

    public void setRedirect_url(String redirect_url) {
        this.redirect_url = redirect_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
