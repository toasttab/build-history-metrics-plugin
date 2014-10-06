package jenkins.plugins.model;

import com.google.common.collect.Lists;
import hudson.model.Result;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by aidan on 06/10/14.
 */
public class StandardDeviationMetricTest {
    private static final long TODAY = new Date().getTime();
    private static final BuildMessage FIRST_BUILD = new BuildMessage(TODAY, Result.SUCCESS.toString());
    private static final BuildMessage SECOND_BUILD = new BuildMessage(TODAY + 1000, Result.FAILURE.toString());
    private static final BuildMessage THIRD_BUILD = new BuildMessage(TODAY + 2000, Result.FAILURE.toString());
    private static final BuildMessage FOURTH_BUILD = new BuildMessage(TODAY + 3000, Result.SUCCESS.toString());
    private static final BuildMessage FIFTH_BUILD = new BuildMessage(TODAY + 4000, Result.FAILURE.toString());
    private static final BuildMessage SIXTH_BUILD = new BuildMessage(TODAY + 5000, Result.SUCCESS.toString());


    @Test
    @Ignore
    public void should_return_1_second_when_first_success_build() {
        runAndVerifyResult(Lists.newArrayList(FIRST_BUILD), 1000L, 1);
    }

    @Test
    @Ignore
    public void should_return_2_second_when_first_failure_build() {
        runAndVerifyResult(Lists.newArrayList(SECOND_BUILD), 2000L, 1);
    }

    private void runAndVerifyResult(ArrayList<BuildMessage> builds, long expectTime, int expectCount) {
        AggregateBuildMetric mttrMetric = new StandardDeviationMetric("test", builds);
        assertEquals("Metric Name", "test", mttrMetric.getName());
        assertEquals("MTTR Metric", expectTime, mttrMetric.calculateMetric());
        assertEquals("Build Count", expectCount, mttrMetric.getOccurences());
    }
}
