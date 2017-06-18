package lins.com.qz.bean;

/**
 * Created by LINS on 2017/6/18.
 */

public class Friend {

    private String objid;
    private String fname;
    private String ficonurl;
    private String fnote;

    public Friend(String objid, String fname, String ficonurl, String fnote) {
        this.objid = objid;
        this.fname = fname;
        this.ficonurl = ficonurl;
        this.fnote = fnote;
    }

    public String getObjid() {
        return objid;
    }

    public void setObjid(String objid) {
        this.objid = objid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getFiconurl() {
        return ficonurl;
    }

    public void setFiconurl(String ficonurl) {
        this.ficonurl = ficonurl;
    }

    public String getFnote() {
        return fnote;
    }

    public void setFnote(String fnote) {
        this.fnote = fnote;
    }

}
