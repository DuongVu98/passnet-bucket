package com.iucse.passnetbucket.domain.aggregate.entity;

import com.iucse.passnetbucket.domain.aggregate.vo.FileName;
import com.iucse.passnetbucket.domain.aggregate.vo.FileType;
import com.iucse.passnetbucket.domain.aggregate.vo.SignedUrl;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadedFile extends BaseEntity {

    @Id
    private String id;

    @Field("fileName")
    private FileName fileName;

    @Field("signedUrl")
    private SignedUrl signedUrl;

    @Field("fileType")
    private FileType fileType;
}
