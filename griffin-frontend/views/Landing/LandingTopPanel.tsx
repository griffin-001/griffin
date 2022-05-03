import React, { FunctionComponent } from 'react';
import {Box, Typography} from "@mui/material";

interface Props {

}

const LandingTopPanel: FunctionComponent<Props> = (props) => {

  return (
    <Box style={{width: "100%"}}>
      <Typography variant={"h3"}>
        Hello! Welcome to the site
      </Typography>
    </Box>
  );
};

export default LandingTopPanel;