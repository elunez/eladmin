package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import me.zhengjie.annotation.Query;

/**
* @author Bobby Kimutai
* @date 2019-08-05
*/
@Data
public class NwIncidentsQueryCriteria{

    // 精确
    @Query
    private Integer id;

    // 精确
    @Query(type = Query.Type.INNER_LIKE)
    private Timestamp faultclearancetime;

    // 精确
    @Query
    private String faultcontractorticketno;

    // 精确
    @Query
    private String faultcustomername;

    // 精确
    @Query
    private String faultcustomerresponsetime;

    // 精确
    @Query
    private String faultdescription;

    // 精确
    @Query
    private String faultexcusedoutagetype1;

    // 精确
    @Query
    private Timestamp faultexcusedoutagetype1endtime;

    // 精确
    @Query
    private Timestamp faultexcusedoutagetype1starttime;

    // 精确
    @Query
    private String faultexcusedoutagetype2;

    // 精确
    @Query
    private Timestamp faultexcusedoutagetype2endtime;

    // 精确
    @Query
    private Timestamp faultexcusedoutagetype2starttime;

    // 精确
    @Query
    private String faultexcusedoutagetype3;

    // 精确
    @Query
    private Timestamp faultexcusedoutagetype3endtime;

    // 精确
    @Query
    private Timestamp faultexcusedoutagetype3starttime;

    // 精确
    @Query
    private String faultincidentid;

    // 精确
    @Query
    private String faultlinksitea;

    // 精确
    @Query
    private String faultlinksiteb;

    // 精确
    @Query
    private String faultlocation;

    // 精确
    @Query
    private Timestamp faultoccurrencetime;

    // 精确
    @Query
    private String faultpriority;

    // 精确
    @Query
    private String faultresponsibility;

    // 精确
    @Query
    private String faultsiteid;

    // 精确
    @Query
    private String faultsitename;

    // 精确
    @Query
    private String faultticketno;

    // 精确
    @Query
    private Timestamp faultticketopentime;

    // 精确
    @Query
    private String faulttype;

    // 精确
    @Query
    private Integer isfaultexcusedoutagesreported;

    // 精确
    @Query
    private Integer isfaultorigin;

    // 精确
    @Query
    private String resubmission;

    // 精确
    @Query
    private Timestamp timestamp;

    // 精确
    @Query
    private String username;

    // 精确
    @Query
    private Integer incidentsId;

    // 精确
    @Query
    private String daysinmonth;

    // 精确
    @Query
    private String faultadditionaldetails;

    // 精确
    @Query
    private String faultcontractorticketid;

    // 精确
    @Query
    private String faultcountry;

    // 精确
    @Query
    private String faultsitedeactivationdate;

    // 精确
    @Query
    private String faultsitelcprorateddays;

    // 精确
    @Query
    private String faultsiteproduct;

    // 精确
    @Query
    private String faultsiteprotectionstatus;

    // 精确
    @Query
    private String faultsiteprotectionstatusdate;

    // 精确
    @Query
    private String faultsiteservicecommencementdate;

    // 精确
    @Query
    private String faultsitesla;

    // 精确
    @Query
    private String faultsiteisbillable;
}