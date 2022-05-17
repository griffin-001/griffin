import React, {FunctionComponent} from 'react';
import {Box} from "@mui/system";
import PageContainer from "../../components/PageContainer";
import Link from "next/link";
import {Button, Typography} from "@mui/material";

interface Props {

}

// todo add cards for each projectDependencies or even just a list / table
// todo this is deprecated/unused
//  - saving this in case it is needed for a later sprint
const ProjectsDashboard: FunctionComponent<Props> = (props) => {

  return (
    <PageContainer>
      <Box>
        Projects Dashboard
      </Box>
      <Link href={"/dashboard/projectDependencies"} passHref>
        <Button>
          <Typography variant="h6">See Project Dependencies</Typography>
        </Button>
      </Link>
      <Link href={"/dashboard/projectVulnerabilities"} passHref>
        <Button>
          <Typography variant="h6">See Project Vulnerabilities</Typography>
        </Button>
      </Link>
    </PageContainer>
  );
};

export default ProjectsDashboard;