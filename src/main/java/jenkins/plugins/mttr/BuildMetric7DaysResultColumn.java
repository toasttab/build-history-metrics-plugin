package jenkins.plugins.mttr;

import hudson.Extension;
import hudson.model.Job;
import hudson.views.ListViewColumn;
import hudson.views.ListViewColumnDescriptor;
import jenkins.plugins.util.ReadUtil;
import org.kohsuke.stapler.DataBoundConstructor;
import hudson.model.Run;


import java.io.IOException;

public class BuildMetric7DaysResultColumn extends ListViewColumn implements ResultColumn {

    @DataBoundConstructor
    public BuildMetric7DaysResultColumn() {
    }

    @Override
    public String getResult(Run<?,?> job) throws IOException {
        return ReadUtil.getColumnResult(job, MetricsAction.MTTR_LAST_7_DAYS);
    }

    @Override
    public String getGraph(Run<?,?> job) throws IOException {
        return null;
    }

    @Extension
    public static class DescriptorImpl extends ListViewColumnDescriptor {
        public DescriptorImpl() {
        }

        public String getDisplayName() {
            return Messages.last7DaysBuildsColumnTitle();
        }

        @Override
        public boolean shownByDefault() {
            return false;
        }
    }
}
