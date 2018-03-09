package net.karlmartens.docfx;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.Internal;

import java.io.File;
import java.util.function.Consumer;

class DocfxDefaultTask extends DefaultTask {

    private DocfxExtension _ext = new DocfxExtension();

    @Internal
    public final void setExtension(DocfxExtension ext) {
        _ext = ext;
    }

    @Internal
    public final DocfxExtension getExtension() {
        return _ext;
    }

    protected static void whenHasValue(String value, Consumer<String> consumer) {
        if (value != null && !value.isEmpty()) {
            consumer.accept(value);
        }
    }


    protected final String directoryName(String filePath) {
        String[] baseDir = { "." };
        whenHasValue(filePath, s -> {
            File file = new File(s);
            baseDir[0] = file.getAbsoluteFile().getParent();
        });
        return baseDir[0];
    }


}
