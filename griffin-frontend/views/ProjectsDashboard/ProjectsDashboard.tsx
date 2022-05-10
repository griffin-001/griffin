import React, {FunctionComponent} from 'react';
import {Box} from "@mui/system";
import PageContainer from "../../components/PageContainer";
import Link from "next/link";
import {Button, Typography} from "@mui/material";

interface Props {

}

// todo add cards for each project or even just a list / table
const ProjectsDashboard: FunctionComponent<Props> = (props) => {

  return (
    <PageContainer>
      <Box>
        Projects Dashboard
      </Box>
      <Link href={"/projectsDashboard/project"} passHref>
        <Button>
          <Typography variant="h6">Sample Project</Typography>
        </Button>
      </Link>
    </PageContainer>
  );
};

export default ProjectsDashboard;