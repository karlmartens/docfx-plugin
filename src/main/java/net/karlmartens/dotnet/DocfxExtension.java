package net.karlmartens.dotnet;

import org.apache.tools.ant.taskdefs.condition.Os;

import java.nio.file.Path;
import java.nio.file.Paths;

public class DocfxExtension {

    private String _docsHome = null;
    private String _docsSrc = "./docs/docfx.json";

    public String getDocsHome() {
        return _docsHome;
    }

    public void setDocsHome(String path) {
        _docsHome = path;
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

    public String getDocsSrc() {
        return _docsSrc;
    }

    public void setDocsSrc(String src) {
        _docsSrc = src;
    }

}
