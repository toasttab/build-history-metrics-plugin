package hudson.tasks;

import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.AutoCompletionCandidates;
import hudson.Extension;
import hudson.Launcher;
import hudson.Util;
import static hudson.Util.fixNull;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.Fingerprint.RangeSet;
import hudson.model.InvisibleAction;
import hudson.model.ItemGroup;
import jenkins.model.Jenkins;
import hudson.model.Item;
import hudson.model.Job;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Fingerprinter.FingerprintAction;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;
import hudson.util.FormValidation;
import jenkins.tasks.SimpleBuildStep;
import hudson.FilePath;
import hudson.model.Run;
import jenkins.plugins.mttr.MetricsAction;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.QueryParameter;

import java.util.logging.Logger;

import net.sf.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.CheckForNull;

public class MetricResultPublisher extends Recorder implements SimpleBuildStep {
    private static final Logger LOGGER = Logger.getLogger(MetricResultPublisher.class.getName());

    @DataBoundConstructor
    public MetricResultPublisher() {
      this.LOGGER.severe("Wow, I'm in a constructor.");
    }

    @Override
    public void perform(Run<?,?> build, FilePath ws, Launcher launcher, TaskListener listener) throws InterruptedException {
        build.addAction(new MetricsAction(build));
        return;
    }

    public BuildStepMonitor getRequiredMonitorService() {
        return BuildStepMonitor.NONE;
    }

    /*@Override public Collection<? extends Action> getProjectActions(AbstractProject<?, ?> project) {
        return Collections.singleton(new TestResultProjectAction(project));
    }*/
    @Deprecated
    public static volatile DescriptorImpl DESCRIPTOR;

    @Extension
    public static class DescriptorImpl extends BuildStepDescriptor<Publisher> {
        public DescriptorImpl() {
            DESCRIPTOR = this; // backward compatibility
        }

        public String getDisplayName() {
            return "MetricResultPublisher";
        }

        @Override
        public MetricResultPublisher newInstance(StaplerRequest req, JSONObject formData) throws FormException {
            return req.bindJSON(MetricResultPublisher.class,formData);
        }

        public boolean isApplicable(Class<? extends AbstractProject> jobType) {
            return true;
        }
    }
}
