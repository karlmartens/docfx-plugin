package net.karlmartens.docfx;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Clean extends DocfxDefaultTask {


    @TaskAction
    void exec() {
        String source = getExtension().getSource();
        if (source == null || source.trim().length() == 0)
            return;

        File configFile = getProject().file(source);
        cleanMetadata(configFile);
        cleanBuild(configFile);
    }

    private List<Path> parseMetadata(File file) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.reader();
        try (FileInputStream in = new FileInputStream(file)) {
            JsonNode result = reader.readTree(in);

            List<Path> paths = new ArrayList<>();
            if (result.has("metadata")) {
                List<JsonNode> metadatas = result.findValues("metadata");
                for (JsonNode metadata : metadatas) {
                    Path path = Paths.get("api");
                    if (metadata.has("dest")) {
                        path = Paths.get(metadata.findValue("dest").asText());
                    }
                    paths.add(path);
                }
            }
            return paths;
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void cleanMetadata(File configFile) {
        List<Path> apis = parseMetadata(configFile);

        List<String> includes  = apis
                .stream()
                .map(p -> p.resolve("**/*.yml").toString())
                .collect(Collectors.toList());

        String baseDir = directoryName(configFile.getPath());
        getProject().fileTree(baseDir, t -> {
            t.include(includes);
        }).forEach(File::delete);
    }

    private Path parseBuild(File configFile) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectReader reader = mapper.reader();
        try (FileInputStream in = new FileInputStream(configFile)) {
            JsonNode result = reader.readTree(in);
            if (result.has("build")) {
                JsonNode build = result.findValue("build");
                if (build.has("dest")) {
                    return Paths.get(build.findValue("dest").asText());
                }
            }
            return Paths.get("_site");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void cleanBuild(File configFile) {
        Path path = parseBuild(configFile);
        Path baseDir = Paths.get(directoryName(configFile.getPath()));
        getProject().delete(baseDir.resolve(path).toString());
    }

}
