import React, {FunctionComponent} from 'react';
import {Box, Grid} from "@mui/material";

interface Props {

}

const LeftLandingPanel: FunctionComponent<Props> = (props) => {

  return (
    <Grid
      item
      xs={6}
      sx={{
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
      }}
    >
      <Box
        component="img"
        sx={{
          height: 350,
          width: 350,
        }}
        src="/assets/griffin-logo.png"
      />
    </Grid>
  );
};

export default LeftLandingPanel;