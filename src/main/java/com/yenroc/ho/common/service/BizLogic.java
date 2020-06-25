package com.yenroc.ho.common.service;

public interface BizLogic<P, R> {
    R execute(P arg0) throws Exception;
}
