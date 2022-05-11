/**
 * Project dependencies page
 * By Chuyang Chen
 */

import React, {FunctionComponent, useState} from 'react';
import {Box} from "@mui/system";
import DependencyList from "../../../components/DependencyList";
import DependencyDisplay from "../../../components/DependencyDisplay";
import {Button, Grid, Typography} from "@mui/material";

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
        <Typography variant={"h6"}>
            Project Dependencies
        </Typography>
        <Grid container spacing={2}>
            <Grid item xs={6}>
                <DependencyList items={TestDependencies} onSelect={handleSelect}/>
            </Grid>
            <Grid item xs={6}>
                <DependencyDisplay item={selectedItem}/>
            </Grid>
        </Grid>
    </Box>
  );
};

export default ProjectDependencies;