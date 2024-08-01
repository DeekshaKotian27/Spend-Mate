package com.example.deeksha.spendmate;

public class incomedata {
    private String trdate;
    private String type;
    private String amt;
    private String sign;
    private Integer flag;
    private String tr_id;
    private int cust_id;

    public incomedata(String trdate,String type, String amt,String tr_id,int cust_id,String sign,Integer flag) {
        this.trdate = trdate;
        this.type = type;
        this.amt= amt;
        this.sign=sign;
        this.flag=flag;
        this.tr_id=tr_id;
        this.cust_id=cust_id;
    }

    public int getCust_id() {
        return cust_id;
    }

    public String getTr_id() {
        return tr_id;
    }

    public String getAmt() {
        return amt;
    }

    public String getType() {
        return type;
    }

    public String getTrdate() {
        return trdate;
    }

    public String getSign() {
        return sign;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setAmt(String amt) {
        this.amt = amt;
    }

    public void setTrdate(String trdate) {
        this.trdate = trdate;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public void setTr_id(String tr_id) {
        this.tr_id=tr_id;
    }

    public void setCust_id(int cust_id) {
        this.cust_id=cust_id;;
    }


}
