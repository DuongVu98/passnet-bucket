package com.iucse.passnetbucket.domain.command;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class UploadFileCommand extends BaseCommand {
    private String ownerId;
    private MultipartFile file;
    private boolean rewriteName;
    private String posterId;
}
