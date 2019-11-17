package me.zhengjie.modules.system.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @author Bobby Kimutai
* @date 2019-08-05
*/
@Entity
@Data
@Table(name="nw_incidents")
public class NwIncidents implements Serializable {

    // ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    // Fault Clearance Time
    @Column(name = "faultclearancetime")
    private Timestamp faultclearancetime;

    // Fault Contractor TicketNo
    @Column(name = "faultcontractorticketno")
    private String faultcontractorticketno;

    // Fault Customer Name
    @Column(name = "faultcustomername")
    private String faultcustomername;

    // Fault Customer Response Time
    @Column(name = "faultcustomereesponsetime")
    private String faultcustomerresponsetime;

    // Fault Description
    @Column(name = "faultdescription")
    private String faultdescription;

    // Fault Excused Outage Type1
    @Column(name = "faultexcusedoutagetype1")
    private String faultexcusedoutagetype1;

    // Fault Excused Outage Type1 EndTime
    @Column(name = "faultexcusedoutagetype1endtime")
    private Timestamp faultexcusedoutagetype1endtime;

    // Fault Excused Outage Type1 Start Time
    @Column(name = "faultexcusedoutagetype1starttime")
    private Timestamp faultexcusedoutagetype1starttime;

    // Fault Excused Outage Type2
    @Column(name = "faultexcusedoutagetype2")
    private String faultexcusedoutagetype2;

    // Fault Excused Outage Type2 End Time
    @Column(name = "faultexcusedoutagetype2endtime")
    private Timestamp faultexcusedoutagetype2endtime;

    // Fault Excused Outage Type2 Start Time
    @Column(name = "faultexcusedoutagetype2starttime")
    private Timestamp faultexcusedoutagetype2starttime;

    // Fault Excused Outage Type3
    @Column(name = "faultexcusedoutagetype3")
    private String faultexcusedoutagetype3;

    // Fault Excused Outage Type3 End Time
    @Column(name = "faultexcusedoutagetype3endtime")
    private Timestamp faultexcusedoutagetype3endtime;

    // Fault Excused OutageType3 Start Time
    @Column(name = "FaultExcusedOutageType3StartTime")
    private Timestamp faultexcusedoutagetype3starttime;

    // Fault Incident ID
    @Column(name = "faultincidentid")
    private String faultincidentid;

    // Fault Link SiteA
    @Column(name = "faultlinksitea")
    private String faultlinksitea;

    // Fault Link SiteB
    @Column(name = "faultlinksiteb")
    private String faultlinksiteb;

    // Fault Location
    @Column(name = "faultlocation")
    private String faultlocation;

    // Fault Occurrence Time
    @Column(name = "faultoccurrencetime")
    private Timestamp faultoccurrencetime;

    // Fault Priority
    @Column(name = "faultpriority")
    private String faultpriority;

    // Fault Responsibility
    @Column(name = "faultresponsibility")
    private String faultresponsibility;

    // Fault Site ID
    @Column(name = "faultsiteid")
    private String faultsiteid;

    // Fault Site Name
    @Column(name = "faultsitename")
    private String faultsitename;

    // Fault Ticket No
    @Column(name = "faultticketno")
    private String faultticketno;

    // Fault Ticket Open Time
    @Column(name = "faultticketopentime")
    private Timestamp faultticketopentime;

    // Fault Type
    @Column(name = "faulttype")
    private String faulttype;

    // is Fault Excused Outages Reported
    @Column(name = "isfaultexcusedoutagesreported")
    private Integer isfaultexcusedoutagesreported;

    // is Fault Origin
    @Column(name = "isfaultorigin")
    private Integer isfaultorigin;

    // Resubmission
    @Column(name = "resubmission")
    private String resubmission;

    // Timestamp
    @Column(name = "timestamp")
    private Timestamp timestamp;

    // Username
    @Column(name = "username")
    private String username;

    // incidents_id
    @Column(name = "incidents_id",nullable = false)
    private Integer incidentsId;

    // DaysInMonth
    @Column(name = "daysinmonth")
    private String daysinmonth;

    // Fault Additional Details
    @Column(name = "faultadditionaldetails")
    private String faultadditionaldetails;

    // Fault Contractor Ticket Id
    @Column(name = "faultcontractorticketid")
    private String faultcontractorticketid;

    // Fault Country
    @Column(name = "faultcountry")
    private String faultcountry;

    // Fault Site Deactivation Date
    @Column(name = "faultsitedeactivationdate")
    private String faultsitedeactivationdate;

    // Fault Site Lc Prorated Days
    @Column(name = "faultsitelcprorateddays")
    private String faultsitelcprorateddays;

    // Fault Site Product
    @Column(name = "faultsiteproduct")
    private String faultsiteproduct;

    // Fault Site Protection Status
    @Column(name = "faultsiteprotectionstatus")
    private String faultsiteprotectionstatus;

    // Fault Site Protection Status Date
    @Column(name = "faultsiteprotectionstatusdate")
    private String faultsiteprotectionstatusdate;

    // Fault Site Service Commencement Date
    @Column(name = "faultsiteservicecommencementdate")
    private String faultsiteservicecommencementdate;

    // Fault Site SLA
    @Column(name = "faultsitesla")
    private String faultsitesla;

    // Fault Site isBillable
    @Column(name = "faultsiteisbillable")
    private String faultsiteisbillable;

    public void copy(NwIncidents source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}