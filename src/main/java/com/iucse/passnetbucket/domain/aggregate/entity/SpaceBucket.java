package com.iucse.passnetbucket.domain.aggregate.entity;

import com.iucse.passnetbucket.domain.aggregate.vo.GcpBucketId;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("bucket")
public class Bucket {
    @Id
    private String id;

    @Field("owner")
    private Owner owner;

    @Field("gcpBucketId")
    private GcpBucketId gcpBucketId;

    @Field("uploadedFiles")
    private List<UploadedFile> uploadedFiles;
}
