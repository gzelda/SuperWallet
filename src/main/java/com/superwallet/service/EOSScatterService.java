package com.superwallet.service;

import com.superwallet.common.SuperResult;
import com.superwallet.response.ResponseEOSScatterCPUNETEntry;

public interface EOSScatterService {
    SuperResult getOrRequestIdentity(String UID);

    SuperResult identityFromPermissions(String UID);

    SuperResult requestSignature(String UID, String buf, int restFreeTimes);

    SuperResult getOriginData(String data);

    ResponseEOSScatterCPUNETEntry getCPUNETPercent(String UID);
}
