package jenkins.plugins.mttr;

import hudson.model.Run;

import java.io.IOException;

public interface ResultColumn {
    String getResult(Run<?,?> job) throws IOException;
    String getGraph(Run<?,?> job) throws IOException;
}
