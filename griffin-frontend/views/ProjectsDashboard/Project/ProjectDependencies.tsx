/**
 * List to display dependencies, could theoretically be used to display projects or vulnerabilities as well
 * by Chuyang Chen
 */

import React, {FunctionComponent, useState} from 'react';
import {Box} from "@mui/system";
import DependencyList from "../../../components/DependencyList";
import DependencyDisplay from "../../../components/DependencyDisplay";

interface Props {

}


const ProjectDependencies: FunctionComponent<Props> = (props) => {


    const TestDependencies = [  {name: "Dep1", version: "1.4.1", description: "Dependency 1"},
        {name: "Dep2", version: "2.1.1", description: "Dependency 2"}]

    const [selectedItem, setSelectedItem] = useState(TestDependencies[0]);

    const handleSelect = (item: {name: string, version: string, description: string}) => {
        setSelectedItem(item);
    }

  // toggle between a table showing current vs existing dependencies
  return (
    <Box>
      Project dependencies
        <DependencyList items={TestDependencies} onSelect={handleSelect}/>
        <DependencyDisplay item={selectedItem}/>
    </Box>
  );
};

export default ProjectDependencies;