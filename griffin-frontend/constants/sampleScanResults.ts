export const sampleScanResults: ListOfScans = [
  {
    date: (new Date).toISOString(),
    summary: {
      newVulnerabilities: 1,
      unresolvedExistingVulnerabilities: 1,
      resolvedExistingVulnerabilities: 2,
      projectsAffected: 1,
      totalProjects: 3,
      repositoriesAffected: 2,
      totalRepositories: 5,
    },
    data: [
      {
        projectName: "my first project",
        repoName: "my first repo",
        dependencyName: "other dep",
        dependencyVersion: "2.2",
        vulnerabilityStatus: "new",
      },
      {
        projectName: "my first project",
        repoName: "my second repo",
        dependencyName: "my dep",
        dependencyVersion: "1.1",
        vulnerabilityStatus: "unresolved",
      },
      {
        projectName: "my second project",
        repoName: "my other repo",
        dependencyName: "my dep12",
        dependencyVersion: "1.3",
        vulnerabilityStatus: "resolved",
      },
      {
        projectName: "my second project",
        repoName: "my other repo2",
        dependencyName: "my dep1232",
        dependencyVersion: "1.4",
        vulnerabilityStatus: "resolved",
      },
    ],
  },
  {
    date: (new Date).toISOString(),
    summary: {
      newVulnerabilities: 3,
      unresolvedExistingVulnerabilities: 0,
      resolvedExistingVulnerabilities: 0,
      projectsAffected: 2,
      totalProjects: 3,
      repositoriesAffected: 3,
      totalRepositories: 5,
    },
    data: [
      {
        projectName: "my first project",
        repoName: "my second repo",
        dependencyName: "my dep",
        dependencyVersion: "1.1",
        vulnerabilityStatus: "new",
      },
      {
        projectName: "my second project",
        repoName: "my other repo",
        dependencyName: "my dep12",
        dependencyVersion: "1.3",
        vulnerabilityStatus: "new",
      },
      {
        projectName: "my second project",
        repoName: "my other repo2",
        dependencyName: "my dep1232",
        dependencyVersion: "1.4",
        vulnerabilityStatus: "new",
      },
    ],
  },
];


//////////////////////////////////////////////////
// todo below this is deprecated
//////////////////////////////////////////////////


const testDeps = [
  {
    name: "Dependency 1",
    description: "This is a dependency",
    version: "1.4.6"
  },
  {
    name: "Dependency 2",
    description: "This is another dependency",
    version: "1.1.5"
  }
]

export const testVulnerabilities = [
  {
    name: "Vulnerability 1",
    description: "This is a vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 2",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 3",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 4",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 5",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 6",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 7",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 8",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 9",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 10",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 11",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 12",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 13",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  },
  {
    name: "Vulnerability 14",
    description: "This is another vulnerability",
    dependency: testDeps[0]
  }]
