package templates

class PullRequestTemplate {
    static void create(job, config) {
        job.with {
            description("Builds all pull requests opened against <code>${config.repo}</code>.<br><br><b>Note</b>: This job is managed <a href='https://github.com/curalate/jenkins-job-dsl-demo'>programmatically</a>; any changes will be lost.")

            logRotator {
                daysToKeep(7)
                numToKeep(50)
            }

            concurrentBuild(true)

            scm {
                git {
                    remote {
                        github(config.repo)
                        refspec('+refs/pull/*:refs/remotes/origin/pr/*')
                     }
                     branch('${sha1}')
                 }
            }

            triggers {
                githubPullRequest {
                    cron('H/5 * * * *')
                    triggerPhrase('@curalatebot rebuild')
                    onlyTriggerPhrase(false)
                    useGitHubHooks(true)
                    permitAll(true)
                    autoCloseFailedPullRequests(false)
                }
            }

            publishers {
                githubCommitNotifier()
            }

            steps {
                shell(config.command_test)
            }
        }
    }
}
