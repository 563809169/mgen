package com.npj;

import com.npj.jgit.Jgit;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

/**
 * @author pengjie.nan
 * @date 2019-03-20
 */
@Mojo(
        name = "delBranch",
        defaultPhase = LifecyclePhase.GENERATE_SOURCES,
        requiresDependencyResolution = ResolutionScope.RUNTIME
)
public class MybatisDelBranch extends AbstractMojo {


    @Override
    public void execute() throws MojoExecutionException {
        Jgit jgit = Jgit.open(getLog());
        jgit.delBranch(branchName);
    }

    @Parameter(defaultValue = "gen_branch_001_001_001_001")
    private String branchName;
}
