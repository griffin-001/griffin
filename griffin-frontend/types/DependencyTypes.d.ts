/**
 * This is a basic d.ts file.
 *
 * It lists some common types that the frontend uses.
 *
 * YOU DO NOT NEED TO IMPORT THESE
 * - THEY ARE AUTOMATICALLY ADDED TO THE FILE PATH
 *
 */

/**
 * A single dependency
 */
interface Dependency {
  name: string;
  version: string; // perhaps this could be changed to a "number" type instead?
}

/**
 * A vulnerability has a name and version just like a dependency
 * It may also contain some other meta-data like a description
 */
interface Vulnerability extends Dependency {
  metaData: {
    description: string;
  };
}

/**
 * When a dependency is displayed, it has additional information
 * This is in addition to name and version.
 */
interface DependencyInfo extends Dependency {
  // the total number of times that specific dependency is used
  numUsages: number;

  // probably don't worry about specific projects
  // as per Shan's advice, we are more interested in the repositories themselves
  reposWhereUsed: Repo[];

  // the number of vulnerabilities, there could be more than one
  numVulnerabilities: number;
  vulnerabilities: Vulnerability[];
}

/**
 * A single repository / instance
 */
interface Repo {
  name: string;
  parentProject: Project;
}

/**
 * A project containing a number of repositories
 */
interface Project {
  name: string;
  childRepos: Repo[];
}
