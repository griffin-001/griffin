import React, {FunctionComponent, useState} from 'react';
import {Box} from "@mui/system";
import {Grid, Typography} from "@mui/material";
import VulnerabilityDisplay from "./VulnerabilityDisplay";
import VulnerabilityList from "./VulnerabilityList";
import PageContainer from "../../../components/PageContainer";

interface Props {

    vulnerabilities: Array<Vulnerability>,
}

const ProjectVulnerabilties: FunctionComponent<Props> = (props) => {

    const [selectedItem, setSelectedItem] = useState(props.vulnerabilities[0]);

    const handleSelect = (item: Vulnerability) => {
        setSelectedItem(item);
    }

  // toggle between a table showing current vs existing vulnerabilities
  return (
      <PageContainer>
         <Box>
            <Typography variant={"h6"}>
                Project Vulnerabilities
            </Typography>
            <Grid container spacing={2}>
                <Grid item xs={4}>
                    <VulnerabilityList items={props.vulnerabilities} onSelect={handleSelect}/>
                </Grid>
                <Grid item xs={8}>
                    <VulnerabilityDisplay item={selectedItem}/>
                </Grid>
            </Grid>
        </Box>
      </PageContainer>
  );
};

export default ProjectVulnerabilties;