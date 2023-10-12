package com.thc.chengojbackendjudgeservice.judge.codesandbox.impl;

import chengojbackendmodel.model.codesandbox.ExecuteCodeRequest;
import chengojbackendmodel.model.codesandbox.ExecuteCodeResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.thc.chengojbackendcommon.common.ErrorCode;
import com.thc.chengojbackendcommon.exception.BusinessException;
import com.thc.chengojbackendjudgeservice.judge.codesandbox.CodeSandBox;
import org.apache.commons.lang3.StringUtils;

public class RemoteCodeSandBox implements CodeSandBox {

    private static final String AUTH_REQUEST_HEADER = "auth";

    private static final String AUTH_REQUEST_SECRET = "secretKey";

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        System.out.println("远程代码沙箱");
        String url = "http://localhost:8090/executeCode";
        String json = JSONUtil.toJsonStr(executeCodeRequest);
        String responseStr = HttpUtil.createPost(url)
                .header(AUTH_REQUEST_HEADER, AUTH_REQUEST_SECRET)
                .body(json)
                .execute()
                .body();
        if (StringUtils.isBlank(responseStr)) {
            throw new BusinessException(ErrorCode.API_REQUEST_ERROR, "executeCode remoteSandbox error, message = " + responseStr);
        }
        return JSONUtil.toBean(responseStr, ExecuteCodeResponse.class);
    }
}
