package net.karlmartens.docfx;

import org.gradle.api.tasks.TaskAction;

import java.io.File;

public class Clean extends DocfxDefaultTask {

    @TaskAction
    void exec() {
        DocfxExtension ext = getExtension();
        whenHasValue(ext.getDocsSrc(), this::cleanDocs);
    }

    private void cleanDocs(String docsSrc) {
        String baseDir = directoryName(docsSrc);
        getProject().fileTree(baseDir, t -> {
            t.include("api/*.yml");
        }).forEach(File::delete);

        getProject().delete(baseDir + "/_site");
    }
}
