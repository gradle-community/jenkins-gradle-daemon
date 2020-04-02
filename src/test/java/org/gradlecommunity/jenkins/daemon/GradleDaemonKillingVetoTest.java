package org.gradlecommunity.jenkins.daemon;

import com.google.common.collect.Lists;
import hudson.EnvVars;
import hudson.util.ProcessKillingVeto;
import hudson.util.ProcessTree;
import hudson.util.ProcessTreeRemoting;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class GradleDaemonKillingVetoTest {
    private GradleDaemonKillingVeto killer = new GradleDaemonKillingVeto();

    @Test
    public void testSunnyDay() {
        ProcessKillingVeto.VetoCause veto = killer.vetoProcessKilling(test("org.gradle.launcher.daemon.bootstrap.GradleDaemon"));
        assertNotNull(veto);
    }

    @Test
    public void testRainyDay() {
        ProcessKillingVeto.VetoCause veto = killer.vetoProcessKilling(test("GradleProfiler"));
        assertNull(veto);
    }

    private ProcessTreeRemoting.IOSProcess test(String name) {
        return new ProcessTreeRemoting.IOSProcess() {
            @Override
            public void killRecursively() throws InterruptedException {
            }

            @Override
            public void kill() throws InterruptedException {
            }

            @Override
            public int getPid() {
                return 0;
            }

            @Override
            public ProcessTreeRemoting.IOSProcess getParent() {
                return null;
            }

            @Override
            public EnvVars getEnvironmentVariables() {
                return null;
            }

            @Override
            public List<String> getArguments() {
                return Lists.newArrayList(
                        "/java/bin/java"
                        ,"-Xmx6144m"
                        ,"-Dfile.encoding=UTF-8"
                        ,"-Duser.country=GB"
                        ,"-Duser.language=en"
                        ,"-Duser.variant"
                        ,"-cp,/home/deepy/.gradle/wrapper/dists/gradle-5.4.1-bin/e75iq110yv9r9wt1a6619x2xm/gradle-5.4.1/lib/gradle-launcher-5.4.1.jar"
                        ,name
                        ,"5.4.1");
            }

            @Override
            public <T> T act(ProcessTree.ProcessCallable<T> arg0) throws IOException, InterruptedException {
                return null;
            }
        };
    }
}
