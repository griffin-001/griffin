import React, {FunctionComponent} from 'react';
import ProjectDependencies
  from "../../views/ProjectsDashboard/ProjectDependencies/ProjectDependencies";

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

// todo this is deprecated/unused
//  - saving this in case it is needed for a later sprint
const ProjectDependenciesPage: FunctionComponent<Props> = (props) => {
  return <ProjectDependencies dependencies={TestDeps}/>;
};

export default ProjectDependenciesPage;