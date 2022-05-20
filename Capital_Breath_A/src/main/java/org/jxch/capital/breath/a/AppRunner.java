package org.jxch.capital.breath.a;

import org.jxch.capital.breath.a.service.task.BreathTask;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class AppRunner implements ApplicationRunner {
    private final BreathTask breathTask;

    public AppRunner(BreathTask breathTask) {
        this.breathTask = breathTask;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        breathTask.breathToDatabaseTask();
    }
}
