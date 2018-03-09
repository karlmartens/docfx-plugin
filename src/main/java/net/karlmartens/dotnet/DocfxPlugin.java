package net.karlmartens.dotnet;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.util.Arrays;

public class DocfxPlugin implements Plugin<Project> {

    private static final String CLEAN_TASK = "clean";
    private static final String DOCS_TASK = "docs";

    @Override
    public void apply(Project project) {
        final DocfxExtension extension = project.getExtensions().create("docfx", DocfxExtension.class);

        project.getTasks().create(CLEAN_TASK, Clean.class, task -> {
            task.setExtension(extension);
        });
        project.getTasks().create(DOCS_TASK, Docs.class, task -> {
            task.setExtension(extension);
        });
    }
}
