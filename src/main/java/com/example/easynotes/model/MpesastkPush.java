package com.example.easynotes.model;/**
 * Created by admin on 7/31/18.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Fredrick Oluoch
 * http://www.blaqueyard.com
 * 0720106420 | 0722508906
 * email: menty44@gmail.com
 */

@Entity
@Table(name = "mpesapush")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class MpesastkPush {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String merchantrequestid;

    private String checkoutrequestid;

    private int responsecode;

    private String responsedescription;

    private String customermessage;

    private int userid;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date createdAt;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updatedAt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMerchantrequestid() {
        return merchantrequestid;
    }

    public void setMerchantrequestid(String merchantrequestid) {
        this.merchantrequestid = merchantrequestid;
    }

    public String getCheckoutrequestid() {
        return checkoutrequestid;
    }

    public void setCheckoutrequestid(String checkoutrequestid) {
        this.checkoutrequestid = checkoutrequestid;
    }

    public int getResponsecode() {
        return responsecode;
    }

    public void setResponsecode(int responsecode) {
        this.responsecode = responsecode;
    }

    public String getResponsedescription() {
        return responsedescription;
    }

    public void setResponsedescription(String responsedescription) {
        this.responsedescription = responsedescription;
    }

    public String getCustomermessage() {
        return customermessage;
    }

    public void setCustomermessage(String customermessage) {
        this.customermessage = customermessage;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }


//    public MpesastkPush(String merchantrequestid, String checkoutrequestid, int responsecode, String responsedescription, String customermessage, String userid, Date createdAt, Date updatedAt) {
//        this.merchantrequestid = merchantrequestid;
//        this.checkoutrequestid = checkoutrequestid;
//        this.responsecode = responsecode;
//        this.responsedescription = responsedescription;
//        this.customermessage = customermessage;
//        this.userid = userid;
//        this.createdAt = createdAt;
//        this.updatedAt = updatedAt;
//    }
}
