package net.karlmartens.docfx;

import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;

public class Info extends DocfxDefaultTask {
    @TaskAction
    void exec() {
        DocfxExtension ext = getExtension();

        List<String> args = new ArrayList<>();
        args.add("--version");

        getProject().exec(execSpec -> {
            execSpec.setExecutable(ext.getDocsExecutable());
            execSpec.setArgs(args);
        });
    }
}
