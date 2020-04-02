package org.gradlecommunity.jenkins.daemon;


import hudson.Extension;
import hudson.util.ProcessKillingVeto;
import hudson.util.ProcessTreeRemoting;
import jenkins.YesNoMaybe;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.logging.Logger;

@Extension(optional = true, dynamicLoadable = YesNoMaybe.YES)
public class GradleDaemonKillingVeto extends ProcessKillingVeto {
    private static final VetoCause VETO_CAUSE = new VetoCause("Gradle Daemon is re-used and kills itself, vetoed by Gradle Daemon saver");

    @Override
    public VetoCause vetoProcessKilling(@Nonnull ProcessTreeRemoting.IOSProcess proc) {
        List<String> cmdLine = proc.getArguments();
        if (cmdLine == null || cmdLine.isEmpty())
            return null;

        if (cmdLine.contains("org.gradle.launcher.daemon.bootstrap.GradleDaemon")) {
            return VETO_CAUSE;
        }

        return null;
    }
}
