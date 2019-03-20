package com.npj.jgit;

import org.apache.commons.lang3.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;

import java.io.File;
import java.io.IOException;

/**
 * @author pengjie.nan
 * @date 2019-03-15
 */
public class Jgit {

    private final Log log;
    private static Git git;
    private String oldBranch;

    private Jgit(Log log) {
        this.log = log;
    }


    public String getOldBranch() {
        return oldBranch;
    }

    public static Jgit init(Log log) throws MojoFailureException, MojoExecutionException {
        return new Jgit(log).initGit();
    }

    public ObjectId resolve() throws MojoExecutionException {
        try {
            return git.getRepository().resolve("gen_branch_001_001_001");
        } catch (IOException e) {
            close();
            throw new MojoExecutionException("resolve异常", e);
        }
    }

    public Jgit addAll() throws MojoExecutionException {
        try {
            git.add().addFilepattern(".").call();
        } catch (GitAPIException e) {
            close();
            throw new MojoExecutionException("add异常", e);
        }
        return this;
    }

    public Jgit createBranch(String name) throws MojoExecutionException {
        try {
            // check分支是否存在，如果存在直接返回
            boolean present = git.branchList().call().stream().anyMatch(it -> StringUtils.endsWith(it.getName(), name));

            if (present) {
                return this;
            }

            git.branchCreate().setName(name).call();
        } catch (GitAPIException e) {
            close();
            throw new MojoExecutionException("创建分支异常!", e);
        }
        return this;
    }

    private Jgit initGit() throws MojoExecutionException, MojoFailureException {
        File file = new File(System.getProperty("user.dir"));
        // check是否存在.git文件夹
        log.info("----" + file.getAbsolutePath());
        File gitDir = new File(file.getAbsolutePath() + "/.git");
        if (!gitDir.exists()) {
            git = initGit(file);
            addAll();
            // 如果是init得删除
            commit();
        } else {
            git = openGit(file);
            // check是否有未提交的内容
            boolean clean = gitStatus().isClean();
            if (!clean) {
                git.close();
                throw new MojoFailureException("当前仓库有未提交的内容, 请提交后执行!");
            }
        }
        return this;
    }

    private Git openGit(File file) throws MojoExecutionException {
        try {
            git = Git.open(file);
        } catch (IOException e) {
            close();
            throw new MojoExecutionException("打开文件失败", e);
        }
        return git;
    }

    public Jgit checkout(String name) throws MojoExecutionException {
        try {
            oldBranch = getCurrentBranch();
            log.info("before branch is " + oldBranch);
            git.checkout().setName(name).call();
            log.info("current branch is " + name);
        } catch (GitAPIException e) {
            close();
            throw new MojoExecutionException("分支切换异常", e);
        }
        return this;
    }

    public String getCurrentBranch() throws MojoExecutionException {
        try {
            return git.getRepository().getBranch();
        } catch (IOException e) {
            close();
            throw new MojoExecutionException("获取分支名称异常", e);
        }
    }

    public Jgit commit() throws MojoExecutionException {
        try {
            git.commit().setMessage("gen_001_001_001").call();
        } catch (GitAPIException e) {
            close();
            throw new MojoExecutionException("提交失败", e);
        }
        return this;
    }

    public Status gitStatus() throws MojoExecutionException {
        try {
            return git.status().call();
        } catch (GitAPIException e) {
            close();
            throw new MojoExecutionException("获取git状态异常");
        }
    }

    private Git initGit(File file) throws MojoExecutionException {
        try {
            return Git.init().setDirectory(file).call();
        } catch (GitAPIException e1) {
            throw new MojoExecutionException("init error!", e1);
        }
    }


    public Jgit execute(Runnable runnable) {
        runnable.run();
        return this;
    }


    public void close() {
        git.close();
    }
}
