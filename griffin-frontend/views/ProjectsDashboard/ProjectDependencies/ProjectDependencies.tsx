/**
 * ProjectDependencies dependencies page
 * By Chuyang Chen
 */

import React, {FunctionComponent, useState} from 'react';
import {Box} from "@mui/system";
import DependencyList from "./DependencyList";
import DependencyDisplay from "./DependencyDisplay";
import {Button, Grid, Typography} from "@mui/material";
import PageContainer from "../../../components/PageContainer";

interface Props {
    dependencies: Array<Dependency>,
}

const ProjectDependencies: FunctionComponent<Props> = (props) => {

    const [selectedItem, setSelectedItem] = useState(props.dependencies[0]);

    const handleSelect = (item: Dependency) => {
        setSelectedItem(item);
    }

  // toggle between a table showing current vs existing dependencies
  return (
      <PageContainer>
         <Box>
            <Typography variant={"h6"}>
                Project Dependencies
            </Typography>
            <Grid container spacing={2}>
                <Grid item xs={4}>
                    <DependencyList items={props.dependencies} onSelect={handleSelect}/>
                </Grid>
                <Grid item xs={8}>
                    <DependencyDisplay item={selectedItem}/>
                </Grid>
            </Grid>
         </Box>
      </PageContainer>
  );
};

export default ProjectDependencies;