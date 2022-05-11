import React, {FunctionComponent} from 'react';
import {Box} from "@mui/system";
import DependencyList from "../../../components/DependencyList";

interface Props {

}

const ProjectDependencies: FunctionComponent<Props> = (props) => {

  // toggle between a table showing current vs existing dependencies
  return (
    <Box>
      Project dependencies
        <DependencyList items={[{name: 'Test dependency'}]}/>

    </Box>
  );
};

export default ProjectDependencies;