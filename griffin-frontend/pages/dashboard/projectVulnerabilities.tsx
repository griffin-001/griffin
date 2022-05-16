import React, {FunctionComponent} from 'react';
import ProjectVulnerabilities
  from "../../views/ProjectsDashboard/ProjectVulnerabilities/ProjectVulnerabilties";

interface Props {

}

const TestDeps = [
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

const TestVulns = [
  {
    name: "Vulnerability 1",
    description: "This is a vulnerability",
    dependency: TestDeps[0]
  },
  {
    name: "Vulnerability 2",
    description: "This is another vulnerability",
    dependency: TestDeps[0]
  }]

// todo this is deprecated/unused
//  - saving this in case it is needed for a later sprint
const ProjectVulnerabilitiesPage: FunctionComponent<Props> = (props) => {
  return <ProjectVulnerabilities vulnerabilities={TestVulns}/>;
};

export default ProjectVulnerabilitiesPage;