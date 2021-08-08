package com.iucse.passnetbucket.usecase.service;

import com.iucse.passnetbucket.domain.aggregate.vo.OwnerType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class OwnerTypeMappingService {
    @Value("config.ownerType.personal")
    private String personalType;
    @Value("config.ownerType.classroom")
    private String classroomType;

    private final Map<String, OwnerType> typeMap = new HashMap<>();

    public OwnerTypeMappingService() {
        this.typeMap.put(this.personalType, OwnerType.PERSONAL);
        this.typeMap.put(this.classroomType, OwnerType.CLASSROOM);
    }

    public OwnerType getOwnerType(String key) {
        return this.typeMap.get(key);
    }
}
