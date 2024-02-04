package com.epam.ryzvanovich.backendservicesmentoring.endpoint;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Endpoint(id = "applicationInfo")
@RequiredArgsConstructor
public class AppInfoEndpoint {
    private final Environment environment;
    @Value("${spring.datasource.url}")
    private String dbUrl;

    @ReadOperation
    public Map<String, String> getAppInfo() {
        Map<String, String> infoMap = new HashMap<>();
        infoMap.put("ActiveProfiles", String.join(", ", environment.getActiveProfiles()));
        infoMap.put("DefaultProfiles", String.join(", ", environment.getDefaultProfiles()));
        infoMap.put("DbUrl", dbUrl);

        return infoMap;
    }
}
