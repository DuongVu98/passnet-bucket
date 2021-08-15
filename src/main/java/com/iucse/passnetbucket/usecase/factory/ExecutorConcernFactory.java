package com.iucse.passnetbucket.usecase.factory;

import com.iucse.passnetbucket.domain.repository.FileRepository;
import com.iucse.passnetbucket.usecase.executor.CommandExecutor;
import com.iucse.passnetbucket.usecase.proxy.ExecutorProxy;
import com.iucse.passnetbucket.usecase.proxy.PosterProxy;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExecutorConcernFactory {

    private final FileRepository fileRepository;
    private CommandExecutor commandExecutor;

    @Autowired
    public ExecutorConcernFactory(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public synchronized ExecutorConcernFactory withExecutor(CommandExecutor executor) {
        this.commandExecutor = executor;
        return this;
    }

    public ProxyBuilder proxy() {
        var executor = new ProxyBuilder(fileRepository);
        executor.setCommandExecutor(commandExecutor);
        return executor;
    }

    @RequiredArgsConstructor
    public static class ProxyBuilder extends ConcernBuilder{
        private final FileRepository fileRepository;

        public ProxyBuilder addProxy(Class<? extends ExecutorProxy> proxyClass) {
            if (proxyClass.isInstance(PosterProxy.class)) {
                this.setCommandExecutor(
                   PosterProxy.builder()
                      .executor(commandExecutor)
                      .fileRepository(fileRepository)
                      .build()
                );
            }
            return this;
        }
    }

    private static class ConcernBuilder {

        @Setter
        protected CommandExecutor commandExecutor;

        public final CommandExecutor compose() {
            return this.commandExecutor;
        }
    }
}
