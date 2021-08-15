package com.iucse.passnetbucket.usecase.proxy;

import com.iucse.passnetbucket.domain.entry.PosterAccessEntry;
import com.iucse.passnetbucket.domain.exception.CommandNotCompatibleException;
import com.iucse.passnetbucket.domain.repository.FileRepository;
import com.iucse.passnetbucket.usecase.executor.CommandExecutor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import java.util.concurrent.atomic.AtomicBoolean;

@FieldDefaults(makeFinal = true)
public class PosterProxy extends ExecutorProxy {

    private FileRepository fileRepository;

    @Builder
    public PosterProxy(CommandExecutor executor, FileRepository fileRepository) {
        super(executor);
        this.fileRepository = fileRepository;
    }

    @Override
    protected boolean isExecutable() {
        AtomicBoolean result = new AtomicBoolean(false);

        if (command instanceof PosterAccessEntry) {
            PosterAccessEntry entry = (PosterAccessEntry) command;
            fileRepository.findById(entry.getFileId())
               .ifPresent(
                  file -> result.set(file.getPosterId().getValue().equals(entry.getFilePosterId()))
               );
        } else {
            throw new CommandNotCompatibleException("Command must instance of ProxyEntry");
        }

        return result.get();
    }
}
