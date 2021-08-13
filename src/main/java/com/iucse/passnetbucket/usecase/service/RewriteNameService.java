package com.iucse.passnetbucket.usecase.service;

import com.iucse.passnetbucket.domain.aggregate.entity.SpaceBucket;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class RewriteNameService {

    public String rewriteName(SpaceBucket bucket, String originFileName) {
        var originName = extractOriginName(originFileName);
        var extension = getExtension(originFileName).orElse("");

        long fileNameCount = bucket.getUploadedFiles().stream()
           .filter(uploadedFile -> checkPattern(originName, uploadedFile.getFileName().getValue()))
           .count();

        return String.format("%s (%d).%s", originName, fileNameCount + 1, extension);
    }

    private boolean checkPattern(String originName, String fileName) {
        String pattern = originName + " \\(\\d+\\).([a-z]+)$";
        return Pattern.matches(pattern, fileName);
    }

    private String extractOriginName(String originName) {
        return originName.substring(0, originName.lastIndexOf('.'));
    }

    private Optional<String> getExtension(String originName) {
        return Optional.ofNullable(originName)
           .filter(f -> f.contains("."))
           .map(f -> f.substring(originName.lastIndexOf(".") + 1));
    }
}
