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

    public ExecutorConcernFactory withExecutor(CommandExecutor executor) {
        this.commandExecutor = executor;
        return this;
    }

    public ProxyBuilder proxy() {
        var executor = new ProxyBuilder(fileRepository, this);
        executor.setCommandExecutor(commandExecutor);
        return executor;
    }

    public CommandExecutor build() {
        return this.commandExecutor;
    }

    public static class ProxyBuilder extends ConcernBuilder {
        private final FileRepository fileRepository;

        public ProxyBuilder(FileRepository fileRepository, ExecutorConcernFactory factory) {
            super(factory);
            this.fileRepository = fileRepository;
        }

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

    @RequiredArgsConstructor
    private static class ConcernBuilder {

        protected final ExecutorConcernFactory factory;

        @Setter
        protected CommandExecutor commandExecutor;

        public final ExecutorConcernFactory compose() {
            return factory.withExecutor(commandExecutor);
        }
    }
}
