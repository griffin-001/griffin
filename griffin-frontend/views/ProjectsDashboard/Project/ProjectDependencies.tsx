/**
 * List to display dependencies, could theoretically be used to display projects or vulnerabilities as well
 * by Chuyang Chen
 */

import React, {FunctionComponent, useState} from 'react';
import {Box} from "@mui/system";
import DependencyList from "../../../components/DependencyList";

interface Props {

}

const TestDependencies = [{name: "Dep1"}, {name: "Dep2"}]

const displayedDependency = null;

const handleSelect = (item: {name: string}) => {
    console.log(item)
}

const ProjectDependencies: FunctionComponent<Props> = (props) => {

  // toggle between a table showing current vs existing dependencies
  return (
    <Box>
      Project dependencies
        <DependencyList items={TestDependencies} onSelect={handleSelect}/>

    </Box>
  );
};

export default ProjectDependencies;