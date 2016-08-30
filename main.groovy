import models.*
import templates.*

import hudson.FilePath
import org.yaml.snakeyaml.Yaml

createJobs()

void createJobs() {
    def yaml = new Yaml()

    // Build a list of all config files ending in .yml
    def cwd = hudson.model.Executor.currentExecutor().getCurrentWorkspace().absolutize()
    def configFiles = new FilePath(cwd, 'configs').list('*.yml')

    // Create/update a pull request job for each config file
    configFiles.each { file ->
        def projectConfig = yaml.loadAs(file.readToString(), ProjectConfig.class)
        def project = projectConfig.project.replaceAll(' ', '-')

        PullRequestTemplate.create(job("${project}-Pull-Request"), projectConfig)
    }
}
