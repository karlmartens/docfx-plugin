package net.karlmartens.docfx;

import org.gradle.api.logging.Logger;
import org.gradle.api.logging.Logging;
import org.gradle.api.tasks.TaskAction;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Docs extends DocfxDefaultTask {

    private static final Logger LOGGER = Logging.getLogger(Docs.class);

    @TaskAction
    void exec() {
        String source = getExtension().getSource();
        if (source == null || source.trim().isEmpty()) {
            LOGGER.warn("Source not set, no documentation generated.");
            return;
        }

        LOGGER.quiet(String.format(Locale.US, "Processing '%s'", source));
        doMetadata();
        doBuild();
    }

    private void doMetadata() {
        DocfxExtension ext = getExtension();

        List<String> args = new ArrayList<>();
        args.add("metadata");
        args.add(ext.getSource());

        getProject().exec(execSpec -> {
            execSpec.setExecutable(ext.getDocsExecutable());
            execSpec.setArgs(args);
        });
    }

    private void doBuild() {
        DocfxExtension ext = getExtension();

        List<String> args = new ArrayList<>();
        args.add("build");
        args.add(ext.getSource());

        getProject().exec(execSpec -> {
            execSpec.setExecutable(ext.getDocsExecutable());
            execSpec.setArgs(args);
        });
    }
}
