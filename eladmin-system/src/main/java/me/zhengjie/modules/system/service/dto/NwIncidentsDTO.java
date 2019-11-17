package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.io.Serializable;


/**
* @author Bobby Kimutai
* @date 2019-08-05
*/
@Data
public class NwIncidentsDTO implements Serializable {

    // ID
    private Integer id;

    // Fault Clearance Time
    private Timestamp faultclearancetime;

    // Fault Contractor TicketNo
    private String faultcontractorticketno;

    // Fault Customer Name
    private String faultcustomername;

    // Fault Customer Response Time
    private String faultcustomerresponsetime;

    // Fault Description
    private String faultdescription;

    // Fault Excused Outage Type1
    private String faultexcusedoutagetype1;

    // Fault Excused Outage Type1 EndTime
    private Timestamp faultexcusedoutagetype1endtime;

    // Fault Excused Outage Type1 Start Time
    private Timestamp faultexcusedoutagetype1starttime;

    // Fault Excused Outage Type2
    private String faultexcusedoutagetype2;

    // Fault Excused Outage Type2 End Time
    private Timestamp faultexcusedoutagetype2endtime;

    // Fault Excused Outage Type2 Start Time
    private Timestamp faultexcusedoutagetype2starttime;

    // Fault Excused Outage Type3
    private String faultexcusedoutagetype3;

    // Fault Excused Outage Type3 End Time
    private Timestamp faultexcusedoutagetype3endtime;

    // Fault Excused OutageType3 Start Time
    private Timestamp faultexcusedoutagetype3starttime;

    // Fault Incident ID
    private String faultincidentid;

    // Fault Link SiteA
    private String faultlinksitea;

    // Fault Link SiteB
    private String faultlinksiteb;

    // Fault Location
    private String faultlocation;

    // Fault Occurrence Time
    private Timestamp faultoccurrencetime;

    // Fault Priority
    private String faultpriority;

    // Fault Responsibility
    private String faultresponsibility;

    // Fault Site ID
    private String faultsiteid;

    // Fault Site Name
    private String faultsitename;

    // Fault Ticket No
    private String faultticketno;

    // Fault Ticket Open Time
    private Timestamp faultticketopentime;

    // Fault Type
    private String faulttype;

    // is Fault Excused Outages Reported
    private Integer isfaultexcusedoutagesreported;

    // is Fault Origin
    private Integer isfaultorigin;

    // Resubmission
    private String resubmission;

    // Timestamp
    private Timestamp timestamp;

    // Username
    private String username;

    // incidents_id
    private Integer incidentsId;

    // DaysInMonth
    private String daysinmonth;

    // Fault Additional Details
    private String faultadditionaldetails;

    // Fault Contractor Ticket Id
    private String faultcontractorticketid;

    // Fault Country
    private String faultcountry;

    // Fault Site Deactivation Date
    private String faultsitedeactivationdate;

    // Fault Site Lc Prorated Days
    private String faultsitelcprorateddays;

    // Fault Site Product
    private String faultsiteproduct;

    // Fault Site Protection Status
    private String faultsiteprotectionstatus;

    // Fault Site Protection Status Date
    private String faultsiteprotectionstatusdate;

    // Fault Site Service Commencement Date
    private String faultsiteservicecommencementdate;

    // Fault Site SLA
    private String faultsitesla;

    // Fault Site isBillable
    private String faultsiteisbillable;
}