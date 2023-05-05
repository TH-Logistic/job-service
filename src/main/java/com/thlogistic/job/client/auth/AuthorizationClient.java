package com.thlogistic.job.client.auth;

import com.thlogistic.job.adapters.dtos.BaseResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AuthorizationClient {
    @RequestLine("POST /check-permissions")
    @Headers({
            "Content-Type: application/json",
            "Authorization: {token}"
    })
    BaseResponse<PermissionDto> checkPermission(@Param("token") String token, @RequestBody List<String> roles);
}
