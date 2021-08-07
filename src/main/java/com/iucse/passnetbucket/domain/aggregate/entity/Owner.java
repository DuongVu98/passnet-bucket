package com.iucse.passnetbucket.domain.aggregate.entity;

import com.iucse.passnetbucket.domain.aggregate.vo.OwnerId;
import com.iucse.passnetbucket.domain.aggregate.vo.OwnerType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Owner extends BaseEntity {

    @Id
    private String id;

    @Field("ownerType")
    private OwnerType ownerType;

    @Field("ownerId")
    private OwnerId ownerId;
}
