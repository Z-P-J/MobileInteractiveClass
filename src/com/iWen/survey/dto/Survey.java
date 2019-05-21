package com.iWen.survey.dto;

import java.util.Date;

public class Survey implements java.io.Serializable {

    private static final long serialVersionUID = -5612405124894422928L;
    private Long SId; // �ʾ�ID
    private String SName; //�ʾ�����
    private String SDesc; //�ʾ�����
    private String SAuthor; // �ʾ�����
    private String SImg; // �ʾ�ͼƬ
    private Date SCreateDate;  // �ʾ�������
    private String SPassword;
    private Boolean SIsOpen; // �Ƿ񹫿����ʾ�
    private Date SExpireDate;  //�ʾ��������
    private Boolean SIsAudited; // �ʾ��Ƿ���� 0����δ��� 1 �������ͨ��
    private Long SUsehits; // �û������
    //��������� "1"���ʾ� "2"��ͶƱ
    private String sType;

    public Long getSId() {
        return SId;
    }

    public void setSId(Long sId) {
        SId = sId;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String sName) {
        SName = sName;
    }

    public String getSDesc() {
        return SDesc;
    }

    public void setSDesc(String sDesc) {
        SDesc = sDesc;
    }

    public String getSAuthor() {
        return SAuthor;
    }

    public void setSAuthor(String sAuthor) {
        SAuthor = sAuthor;
    }

    public String getSImg() {
        return SImg;
    }

    public void setSImg(String sImg) {
        SImg = sImg;
    }

    public Date getSCreateDate() {
        return SCreateDate;
    }

    public void setSCreateDate(Date sCreateDate) {
        SCreateDate = sCreateDate;
    }

    public String getSPassword() {
        return SPassword;
    }

    public void setSPassword(String sPassword) {
        SPassword = sPassword;
    }

    public Boolean getSIsOpen() {
        return SIsOpen;
    }

    public void setSIsOpen(Boolean sIsOpen) {
        SIsOpen = sIsOpen;
    }

    public Date getSExpireDate() {
        return SExpireDate;
    }

    public void setSExpireDate(Date sExpireDate) {
        SExpireDate = sExpireDate;
    }

    public Boolean getSIsAudited() {
        return SIsAudited;
    }

    public void setSIsAudited(Boolean sIsAudited) {
        SIsAudited = sIsAudited;
    }

    public Long getSUsehits() {
        return SUsehits;
    }

    public void setSUsehits(Long sUsehits) {
        SUsehits = sUsehits;
    }

    public String getsType() {
        return sType;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

}