package com.lib.appinstatsdklib.models;

public interface ApiResponseInterface
{
    void isError(String errorCode, int serviceCode, int modifiedPosition);
    void isSuccess(Object response, int serviceCode, int modifiedPosition);
}
