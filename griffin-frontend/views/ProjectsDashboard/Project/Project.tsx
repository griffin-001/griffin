import React, {FunctionComponent, useState} from 'react';
import PageContainer from "../../../components/PageContainer";
import ProjectDependencies from "./ProjectDependencies";
import ProjectVulnerabilties from "./ProjectVulnerabilties";

interface Props {

}

const Project: FunctionComponent<Props> = (props) => {

  const [showDependencies, setShowDependencies] = useState(false);

  return (
    <PageContainer>
      {/*todo toggle between these two on this page - by default show vulnerabilities*/}
      <ProjectDependencies/>
      <ProjectVulnerabilties/>
    </PageContainer>
  );
};

export default Project;