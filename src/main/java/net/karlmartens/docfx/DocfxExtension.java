package net.karlmartens.docfx;

import org.apache.tools.ant.taskdefs.condition.Os;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DocfxExtension {

    private String _docsHome = null;
    private String _source;

    public DocfxExtension() {
        _docsHome = System.getenv("DOCFX_HOME");
    }

    public String getDocsHome() {
        return _docsHome;
    }

    public void setDocsHome(String path) {
        _docsHome = path;
    }

    public String getSource() {
        return _source;
    }

    public void setSource(String src) {
        _source = src;
    }

    public String getDocsExecutable() {
        String executable = "docfx";
        if (Os.isFamily(Os.FAMILY_WINDOWS))
            executable += ".exe";

        if (_docsHome == null)
            return executable;

        Path path = Paths.get(_docsHome, executable);
        return path.toString();
    }

}
