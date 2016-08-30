package models

/**
 * Simple value object to store configuration information for a project.
 *
 * Member variables without a value defined are required and those with a value
 * defined are optional.
 */
class ProjectConfig {
    /*
     * Required
     */
    String project
    String repo
    String email

    /*
     * Optional
     */
    String command_test = "mvn clean test"
}
