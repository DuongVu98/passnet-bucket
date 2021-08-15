package com.iucse.passnetbucket.domain.command;

import com.iucse.passnetbucket.domain.entry.PosterAccessEntry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@AllArgsConstructor
@FieldDefaults(makeFinal = true)
public class DeleteFileCommand extends BaseCommand implements PosterAccessEntry {
    private String fileId;
    private String posterId;

    @Override
    public String getFilePosterId() {
        return posterId;
    }
}
