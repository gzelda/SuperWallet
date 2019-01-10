package com.superwallet.service;

import com.superwallet.common.SuperResult;

public interface EOSScatterService {
    public SuperResult getOrRequestIdentity(String UID);

    public SuperResult identityFromPermissions(String UID);

    public SuperResult requestSignature(String UID, String buf);

    public SuperResult getOriginData(String data);
}
