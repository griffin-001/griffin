/*
 * This is a basic d.ts file.
 *
 * It lists some common types that the frontend uses.
 *
 * YOU DO NOT NEED TO IMPORT THESE
 * - THEY ARE AUTOMATICALLY ADDED TO THE FILE PATH
 *
*/

// I have deliberately made this longer so that the backend team can more easily understand it
// I will break this up into more child interfaces later

// @BACKEND TEAM - you will need to write two GET function
// Both will return a list of scans

// #1 readScanHistory
// simply returns the current list of scans

// #2 readUpdatedScanHistory
// runs the vulnerability scanner
// then returns the current list of scans, including the newly added scan

// @BACKEND TEAM - this must be sorted in order of dateTime,
// the first must be the most recent so that it appears at the top of the page
type ListOfScans = Scan[];

interface Scan {
  // Please use this format:
  // https://en.wikipedia.org/wiki/ISO_8601
  date: string;

  // the summary information could technically be obtained from the raw data on the frontend
  // however this is much neater if the backend can just return it
  summary: {
    newVulnerabilities: number; // introduced to the system in the last scan (type = new) (red)
    unresolvedExistingVulnerabilities: number; // already existed in the previous scan (type = unresolved) (yellow)
    resolvedExistingVulnerabilities: number; // existed in the previous scan, but have been removed (type = resolved) (green)
    projectsAffected: number; // numerator (number of projects that have a new or unresolved vulnerability)
    totalProjects: number; // denominator
    repositoriesAffected: number; // numerator (number of repos that have a new or unresolved vulnerability)
    totalRepositories: number; // denominator
  };

  // this is an array
  // @ BACKEND TEAM - please sort the data array in order of:
  // project name,
  // then repo name,
  // then dependency name,
  // then dependency version,
  // and finally status
  data: {
    projectName: string;
    repoName: string;
    dependencyName: string;
    dependencyVersion: string;
    vulnerabilityStatus: "new" | "unresolved" | "resolved"; // (red | yellow | green)
  }[];
}

interface VulnerabilitySummary {
  newVulnerabilities: number; // introduced to the system in the last scan (type = new)
  unresolvedExistingVulnerabilities: number; // already existed in the previous scan (type = unresolved)
  resolvedExistingVulnerabilities: number; // existed in the previous scan, but have been removed (type = resolved)
  projectsAffected: number; // numerator (number of projects that have a new or unresolved vulnerability)
  totalProjects: number; // denominator
  repositoriesAffected: number; // numerator (number of repos that have a new or unresolved vulnerability)
  totalRepositories: number; // denominator
}

interface VulnerabilityData {
  projectName: string;
  repoName: string;
  dependencyName: string;
  dependencyVersion: string;
  vulnerabilityStatus: "new" | "unresolved" | "resolved"; // (red | yellow | green)
}

//////////////////////////////////////////////////
// todo everything below here is kind of deprecated now
//////////////////////////////////////////////////

/**
 * A single dependency
 */
interface Dependency {
  name: string;
  version: string; // perhaps this could be changed to a "number" type instead?
  description: string;
}

/**
 * A vulnerability has a name and version just like a dependency
 * It may also contain some other meta-data like a description
 */
interface Vulnerability {
  name: string;
  dependency: Dependency;
  description: string;

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
 * A projectDependencies containing a number of repositories
 */
interface Project {
  name: string;
  childRepos: Repo[];
}
